package com.easycore.digestio.view.activities;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.easycore.digestio.Config;
import com.easycore.digestio.R;
import com.facebook.rebound.*;
import com.google.gson.JsonElement;

import java.util.Locale;
import java.util.Map;

public class SpeechActivity extends AppCompatActivity implements AIListener, SpringListener {

    private AIService aiService;
    private TextToSpeech tts;
    private String parameters;

    private Spring spring;

    private static double TENSION = 800;
    private static double DAMPER = 20; //friction

    private boolean isListening = false;



    @BindView(R.id.testButton) ImageButton testButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);
        ButterKnife.bind(this);

        final AIConfiguration config = new AIConfiguration(Config.CLIENT_ACCESS_TOKEN,
                ai.api.AIConfiguration.SupportedLanguages.French,
                AIConfiguration.RecognitionEngine.System);

        aiService = AIService.getService(this, config);

        aiService.setListener(this);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.CANADA_FRENCH);
                    tts.setOnUtteranceProgressListener(utteranceProgressListener);
                }
            }
        });

        SpringSystem springSystem = SpringSystem.create();

        spring = springSystem.createSpring();
        spring.addListener(this);

        SpringConfig springConfig = new SpringConfig(TENSION, DAMPER);
        spring.setSpringConfig(springConfig);

        if (savedInstanceState != null) {
            isListening = savedInstanceState.getBoolean("isListening");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        aiService.stopListening();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isListening", isListening);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tts.shutdown();
    }

    @OnClick(R.id.testButton)
    public void onButtonClicked() {
        if (isListening) {
            aiService.cancel();
        } else {
            aiService.startListening();
        }
    }

    @OnClick(R.id.skipButton)
    public void onSkipButtonClicked() {
        MainActivity.startActivity(this, "");
    }

    private void processAIResponse(AIResponse response) {
        final Result result = response.getResult();

        // Get parameters
        if (result.getParameters() != null && !result.getParameters().isEmpty()) {
            for (final Map.Entry<String, JsonElement> entry : result.getParameters().entrySet()) {
                this.parameters += "(" + entry.getKey() + ": " + entry.getValue() + ") ";
            }
        }

        // null(news-topic: "Porte") (news-keyword: []) (news-topic: "Sport") (news-keyword: [])

        final String query = result.getResolvedQuery();
        final String action = result.getAction();
        final String fulfillment = result.getFulfillment().getSpeech();

        tts.speak(fulfillment, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onResult(AIResponse response) {
        processAIResponse(response);
    }

    @Override
    public void onError(AIError error) {
    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {
        isListening = true;
        spring.setEndValue(1f);
    }

    @Override
    public void onListeningCanceled() {
        isListening = false;
        spring.setEndValue(0f);
    }

    @Override
    public void onListeningFinished() {
        isListening = false;
        spring.setEndValue(0f);
    }

    private final UtteranceProgressListener utteranceProgressListener = new UtteranceProgressListener() {
        @Override
        public void onStart(String s) {

        }

        @Override
        public void onDone(String s) {
            if (parameters == null) {
                return;
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MainActivity.startActivity(SpeechActivity.this, parameters);
                }
            });
        }

        @Override
        public void onError(String s) {

        }
    };

    @Override
    public void onSpringUpdate(Spring spring) {
        float value = (float) spring.getCurrentValue();
        float scale = 1f - (value * 0.5f);
        testButton.setScaleX(scale);
        testButton.setScaleY(scale);
    }

    @Override
    public void onSpringAtRest(Spring spring) {

    }

    @Override
    public void onSpringActivate(Spring spring) {

    }

    @Override
    public void onSpringEndStateChange(Spring spring) {

    }
}

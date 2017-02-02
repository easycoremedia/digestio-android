package com.easycore.digestio.view.activities;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.easycore.digestio.Config;
import com.easycore.digestio.R;
import com.google.gson.JsonElement;

import java.util.Locale;
import java.util.Map;

public class SpeechActivity extends AppCompatActivity implements AIListener {

    private AIService aiService;
    private TextToSpeech tts;

    private static final ai.api.AIConfiguration.SupportedLanguages ENGLISH = ai.api.AIConfiguration.SupportedLanguages.English;
    private static final ai.api.AIConfiguration.SupportedLanguages ITALIAN = ai.api.AIConfiguration.SupportedLanguages.Italian;

    @BindView(R.id.resultTextView) TextView resultTextView;
    @BindView(R.id.testButton) Button testButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);
        ButterKnife.bind(this);

        final AIConfiguration config = new AIConfiguration(Config.CLIENT_ACCESS_TOKEN,
                ITALIAN,
                AIConfiguration.RecognitionEngine.System);

        aiService = AIService.getService(this, config);

        aiService.setListener(this);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                tts.setLanguage(Locale.ITALY);
            }
        });

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
    protected void onDestroy() {
        super.onDestroy();
        tts.shutdown();
    }

    @OnClick(R.id.testButton)
    public void onButtonClicked() {
        aiService.startListening();
    }

    @OnClick(R.id.skipButton)
    public void onSkipButtonClicked() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void processAIResponse(AIResponse response) {
        final Result result = response.getResult();

        // Get parameters
        String parameterString = "";
        if (result.getParameters() != null && !result.getParameters().isEmpty()) {
            for (final Map.Entry<String, JsonElement> entry : result.getParameters().entrySet()) {
                parameterString += "(" + entry.getKey() + ", " + entry.getValue() + ") ";
            }
        }

        final String fulfillment = result.getFulfillment().getSpeech();

        // Show results in TextView.
        resultTextView.setText("Query:" + result.getResolvedQuery() +
                "\nAction: " + result.getAction() +
                "\nParameters: " + parameterString +
                "\nFullfillment: " + fulfillment);

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

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }
}

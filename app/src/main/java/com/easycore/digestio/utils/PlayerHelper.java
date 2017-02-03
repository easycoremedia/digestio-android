package com.easycore.digestio.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;

import com.easycore.digestio.data.model.AudioItem;
import com.easycore.digestio.view.adapters.AudioItemAdapter;

import java.util.ArrayList;
import java.util.TimerTask;

import timber.log.Timber;

/**
 * Created by Jakub Begera (jakub@easycoreapps.com) on 03.02.17.
 */
public class PlayerHelper {

    private final static long PROGRESS_UPDATE_INTERVAL = 50;

    private final Context context;
    private MediaPlayer mediaPlayer;

    private ArrayList<AudioItem> items;
    private Callback callback;
    private int currentItemPosition;
    private AudioItem currentItem;

    private Handler progressUpdateHandler = new Handler();
    private TimerTask progressUpdateTask = new TimerTask() {
        @Override
        public void run() {
            if (isPlaying() && currentItem != null) {
                currentItem.setPlayingProgress(getProgressInPercentage());
                callback.progressChanged(currentItemPosition);
                progressUpdateHandler.postDelayed(this, PROGRESS_UPDATE_INTERVAL);
            }
        }
    };

    public PlayerHelper(Context context) {
        this.context = context;
    }


    public void init(ArrayList<AudioItem> items, final Callback callback) {
        this.items = items;
        this.callback = callback;

        playItem(0);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                callback.playbackFinished();
                playItem(getNext());
            }
        });
    }

    public void playItem(int position) {
        playItem(items.get(position));
    }

    public void playItem(final AudioItem item) {
        progressUpdateHandler.removeCallbacks(progressUpdateTask);
        for (AudioItem i : items) {
            i.setPlaying(false);
            i.setPlayingProgress(0);
        }
        item.setPlaying(true);
        callback.playbackStarted();

        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(item.getAudioUrl());
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    currentItem = item;
                    progressUpdateHandler.postDelayed(progressUpdateTask, PROGRESS_UPDATE_INTERVAL);
                }
            });
        } catch (Exception e) {
            Timber.e(e, "Player init failed.");
        }
    }

    public void stop() {
        callback.playbackFinished();
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            try {
                mediaPlayer.release();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isPlaying() {
        if (mediaPlayer == null)
            return false;
        try {
            return mediaPlayer.isPlaying();
        } catch (IllegalStateException ise) {
            return false;
        }
    }

    public AudioItem getNext() {
        currentItemPosition++;
        if (currentItemPosition >= items.size()) {
            currentItemPosition = 0;
        }

        return items.get(currentItemPosition);
    }

    public AudioItem getPrevious() {
        currentItemPosition--;
        if (currentItemPosition < 0) {
            currentItemPosition = 0;
        }

        return items.get(0);
    }

    public double getProgressInPercentage() {
        try {
            return ((double) mediaPlayer.getCurrentPosition() / (double) mediaPlayer.getDuration()) * 100.0;
        } catch (IllegalStateException ex) {
            return 0;
        }

    }

    public interface Callback {
        void playbackStarted();
        void playbackFinished();
        void progressChanged(int position);
    }

}

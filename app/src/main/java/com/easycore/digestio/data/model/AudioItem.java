package com.easycore.digestio.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Jakub Begera (jakub@easycoreapps.com) on 02.02.17.
 */
public class AudioItem implements Parcelable {

    @Expose
    @SerializedName("title")
    private String name;
    @Expose
    @SerializedName("fileUrl")
    private String audioUrl;
    private String duration;
    @Expose
    @SerializedName("imageUrl")
    private String pictureUrl;

    @Expose
    @SerializedName("description")
    private String description;

    private ArrayList<String> tags = new ArrayList<>();

    private boolean isPlaying;
    private double playingProgress;

    public AudioItem() {
    }

    public AudioItem(String name, String pictureUrl, String audioUrl, String duration) {
        this.name = name;
        this.audioUrl = audioUrl;
        this.duration = duration;
        this.pictureUrl = pictureUrl;
    }

    protected AudioItem(Parcel in) {
        name = in.readString();
        audioUrl = in.readString();
        duration = in.readString();
        pictureUrl = in.readString();
        tags = in.createStringArrayList();
    }

    public static final Creator<AudioItem> CREATOR = new Creator<AudioItem>() {
        @Override
        public AudioItem createFromParcel(Parcel in) {
            return new AudioItem(in);
        }

        @Override
        public AudioItem[] newArray(int size) {
            return new AudioItem[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(audioUrl);
        dest.writeString(duration);
        dest.writeString(pictureUrl);
        dest.writeStringList(tags);
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public double getPlayingProgress() {
        return playingProgress;
    }

    public void setPlayingProgress(double playingProgress) {
        this.playingProgress = playingProgress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

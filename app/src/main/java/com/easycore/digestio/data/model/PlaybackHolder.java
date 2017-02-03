package com.easycore.digestio.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Jakub Begera (jakub@easycoreapps.com) on 02.02.17.
 */
public class PlaybackHolder implements Parcelable {

    private ArrayList<AudioItem> items;
    private int currentTrack;

    public PlaybackHolder() {
        items = new ArrayList<>();
    }

    public PlaybackHolder(ArrayList<AudioItem> items, int currentTrack) {
        this.items = items;
        this.currentTrack = currentTrack;
    }

    protected PlaybackHolder(Parcel in) {
        items = in.createTypedArrayList(AudioItem.CREATOR);
        currentTrack = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(items);
        dest.writeInt(currentTrack);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PlaybackHolder> CREATOR = new Creator<PlaybackHolder>() {
        @Override
        public PlaybackHolder createFromParcel(Parcel in) {
            return new PlaybackHolder(in);
        }

        @Override
        public PlaybackHolder[] newArray(int size) {
            return new PlaybackHolder[size];
        }
    };

    public ArrayList<AudioItem> getItems() {
        return items;
    }

    public AudioItem getNext() {
        currentTrack++;
        if (currentTrack >= items.size()) {
            currentTrack = 0;
        }

        return items.get(currentTrack);
    }

    public AudioItem getPrevious() {
        currentTrack--;
        if (currentTrack < 0) {
            currentTrack = 0;
        }

        return items.get(0);
    }

    public void setItems(ArrayList<AudioItem> items) {
        this.items = items;
    }

    public int getCurrentTrack() {
        return currentTrack;
    }

    public void setCurrentTrack(int currentTrack) {
        this.currentTrack = currentTrack;
    }
}

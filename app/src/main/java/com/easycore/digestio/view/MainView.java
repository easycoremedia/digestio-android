package com.easycore.digestio.view;

import com.easycore.digestio.data.model.AudioItem;

import java.util.ArrayList;

/**
 * Created by Jakub Begera (jakub.begera@gmail.com) on 07.01.17.
 * (C) Copyright 2017
 */

public interface MainView extends View {

    void fillAudioItems(ArrayList<AudioItem> items);

    void notifyItemsChanged();

    void finishOnEmptyResponse();

}

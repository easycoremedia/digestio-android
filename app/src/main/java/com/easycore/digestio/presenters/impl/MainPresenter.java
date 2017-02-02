package com.easycore.digestio.presenters.impl;

import android.os.Bundle;

import com.easycore.digestio.App;
import com.easycore.digestio.data.model.AudioItem;
import com.easycore.digestio.data.repository.network.NetworkDataRepository;
import com.easycore.digestio.presenters.Presenter;
import com.easycore.digestio.utils.Preferences;
import com.easycore.digestio.view.MainView;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Jakub Begera (jakub@easycoreapps.com) on 02.02.17.
 */
public class MainPresenter implements Presenter<MainView> {

    @Inject
    protected NetworkDataRepository dataRepository;
    @Inject
    protected Preferences preferences;

    private MainView view;
    private CompositeSubscription compositeSubscription;

    public MainPresenter(App app) {
        compositeSubscription = new CompositeSubscription();
        app.getAppComponent().inject(this);
    }

    @Override
    public void onEnterScope(Bundle savedState) {

    }

    @Override
    public void onAttach(MainView view) {
        this.view = view;
    }

    @Override
    public void onDetach(MainView view) {
        compositeSubscription.clear();
    }

    @Override
    public void onExitScope(Bundle outState) {

    }

    public void initContent() {
        ArrayList<AudioItem> items = new ArrayList<>();
        AudioItem i;

        i = new AudioItem(
                "Bull in a china shop",
                "http://blogs-images.forbes.com/robertwood/files/2016/02/Trump1.jpg?width=960",
                "http://www.evidenceaudio.com/wp-content/uploads/2014/10/lyricslap.mp3",
                "00:18"
        );
        i.getTags().add("Trump");
        i.getTags().add("politics");
        items.add(i);

        i = new AudioItem(
                "Geneve",
                "http://www.davidfraga.ch/blog/wp-content/uploads/cathedraleStPierre_vignette.jpg",
                "http://www.evidenceaudio.com/wp-content/uploads/2014/10/monsterslap.mp3",
                "00:34"
        );
        i.getTags().add("traveling");
        i.getTags().add("Geneve");
        items.add(i);

        i = new AudioItem(
                "Discovery of a new particle",
                "http://press.cern/sites/press.web.cern.ch/files/image/slideshow/0910152_02.jpg",
                "http://www.evidenceaudio.com/wp-content/uploads/2014/10/lyricchords.mp3",
                "04:12"
        );
        i.getTags().add("cern");
        i.getTags().add("lhc");
        i.getTags().add("higgs boson");
        items.add(i);

        view.fillAudioItems(items);
    }
}

package com.easycore.digestio.injection;

import com.easycore.digestio.presenters.impl.MainPresenter;
import com.easycore.digestio.view.activities.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        NetworkModule.class
})
public interface AppComponent {

    void inject(MainActivity activity);

    void inject(MainPresenter mainPresenter);
}

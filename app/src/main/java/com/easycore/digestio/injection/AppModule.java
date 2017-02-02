package com.easycore.digestio.injection;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import cz.followme.utils.Preferences;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    public Preferences provideDefaultPreferences(Application application) {
        return new Preferences(
                application.getSharedPreferences("my_preffs", Context.MODE_PRIVATE),
                application
        );
    }
}

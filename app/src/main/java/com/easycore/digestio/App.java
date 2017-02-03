package com.easycore.digestio;

import android.app.Application;

import com.easycore.digestio.injection.AppComponent;
import com.easycore.digestio.injection.AppModule;
import com.easycore.digestio.injection.DaggerAppComponent;
import com.easycore.digestio.injection.NetworkModule;

import timber.log.Timber;

/**
 * Created by Jakub Begera (jakub@easycoreapps.com) on 02.02.17.
 */
public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initLoggers();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(BuildConfig.SERVER_ENVIRONMENT))
                .build();
    }

    private void initLoggers() {
        Timber.plant(new Timber.DebugTree());
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}

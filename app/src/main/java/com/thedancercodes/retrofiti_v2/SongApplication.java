package com.thedancercodes.retrofiti_v2;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by TheDancerCodes on 04/10/2018.
 */
public class SongApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        Timber.i("Creating our Application");
    }


}

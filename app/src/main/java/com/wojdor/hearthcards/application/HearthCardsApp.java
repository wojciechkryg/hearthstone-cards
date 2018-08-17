package com.wojdor.hearthcards.application;

import android.app.Application;

import timber.log.Timber;

public class HearthCardsApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initTimber();
    }

    private void initTimber() {
        Timber.plant(new Timber.DebugTree());
    }
}

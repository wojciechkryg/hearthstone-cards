package com.wojdor.hearthstonecards.application;

import android.app.Application;

import timber.log.Timber;

public class HearthstoneCardsApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initTimber();
    }

    private void initTimber() {
        Timber.plant(new Timber.DebugTree());
    }
}

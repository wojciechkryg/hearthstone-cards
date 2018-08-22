package com.wojdor.hearthcards.application;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.wojdor.hearthcards.BuildConfig;

import timber.log.Timber;

public class HearthCardsApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initTimber();
        initAdMob();
    }

    private void initAdMob() {
        MobileAds.initialize(this, BuildConfig.ADMOB_HEARTHCARDS_ID);
    }

    private void initTimber() {
        Timber.plant(new Timber.DebugTree());
    }
}

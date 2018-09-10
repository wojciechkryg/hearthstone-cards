package com.wojdor.hearthstonecards.application

import android.app.Application

import timber.log.Timber

class HearthstoneCardsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}

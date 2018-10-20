package com.wojdor.hearthstonecards.application

import android.app.Application
import com.wojdor.hearthstonecards.di.appModules
import org.koin.android.ext.android.startKoin

import timber.log.Timber

class HearthstoneCardsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initTimber()
    }

    private fun initKoin() {
        startKoin(this, appModules)
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}

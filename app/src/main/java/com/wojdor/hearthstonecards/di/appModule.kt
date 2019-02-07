package com.wojdor.hearthstonecards.di

import android.arch.persistence.room.Room
import com.wojdor.hearthstonecards.application.card.CardViewModel
import com.wojdor.hearthstonecards.application.classcards.ClassCardsViewModel
import com.wojdor.hearthstonecards.application.classpager.ClassPagerViewModel
import com.wojdor.hearthstonecards.application.settings.SettingsViewModel
import com.wojdor.hearthstonecards.application.splash.SplashViewModel
import com.wojdor.hearthstonecards.application.util.CardImageDownloader
import com.wojdor.hearthstonecards.application.util.FileStorage
import com.wojdor.hearthstonecards.data.database.CardDatabase
import com.wojdor.hearthstonecards.data.repository.CardRepository
import com.wojdor.hearthstonecards.data.service.CardService
import com.wojdor.hearthstonecards.data.session.UserSession
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModules = module {
    viewModel { CardViewModel(get(), get()) }
    viewModel { ClassCardsViewModel(get()) }
    viewModel { ClassPagerViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { SplashViewModel(get()) }
}

val utilModules = module {
    single { FileStorage(androidApplication()) }
    single { CardImageDownloader(get()) }
}

val dataModules = module {
    single { CardService().cardApi }
    single {
        Room.databaseBuilder(androidApplication(), CardDatabase::class.java,
                CardDatabase.DATABASE_NAME).build().cardDao()
    }
    single { UserSession(androidApplication()) }
    single { CardRepository(get(), get(), get(), get()) }
}

val appModules = listOf(viewModelModules, utilModules, dataModules)

package com.wojdor.hearthstonecards.di

import androidx.room.Room
import com.wojdor.hearthstonecards.app.card.CardViewModel
import com.wojdor.hearthstonecards.app.classcards.ClassCardsViewModel
import com.wojdor.hearthstonecards.app.classpager.ClassPagerViewModel
import com.wojdor.hearthstonecards.app.settings.SettingsViewModel
import com.wojdor.hearthstonecards.app.splash.SplashViewModel
import com.wojdor.hearthstonecards.app.util.CardImageDownloader
import com.wojdor.hearthstonecards.app.util.FileStorage
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

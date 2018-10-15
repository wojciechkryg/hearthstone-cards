package com.wojdor.hearthstonecards.application.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.wojdor.hearthstonecards.application.util.CardImageDownloader
import com.wojdor.hearthstonecards.data.database.CardDatabase
import com.wojdor.hearthstonecards.data.repository.CardRepository
import com.wojdor.hearthstonecards.data.service.CardService
import com.wojdor.hearthstonecards.data.session.UserSession

abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {

    protected val repository by lazy {
        // TODO: Use koin to inject everything!
        CardRepository.getInstance(CardService.getInstance(), CardDatabase.getInstance(application).cardDao(),
                UserSession.getInstance(application), CardImageDownloader(application))
    }

    override fun onCleared() {
        super.onCleared()
        repository.onDestroy()
    }
}

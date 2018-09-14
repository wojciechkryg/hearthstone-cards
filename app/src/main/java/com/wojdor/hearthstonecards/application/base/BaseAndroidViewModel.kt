package com.wojdor.hearthstonecards.application.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.wojdor.hearthstonecards.data.repository.CardRepository

import com.wojdor.hearthstonecards.data.database.CardDatabase
import com.wojdor.hearthstonecards.data.database.dao.CardDao
import com.wojdor.hearthstonecards.data.service.CardService
import com.wojdor.hearthstonecards.data.session.UserSession

abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {

    protected val repository by lazy {
        CardRepository(CardService.getInstance(), CardDatabase.getInstance(application).cardDao(),
                UserSession.getInstance(application))
    }

    override fun onCleared() {
        super.onCleared()
        repository.onDestroy()
    }
}

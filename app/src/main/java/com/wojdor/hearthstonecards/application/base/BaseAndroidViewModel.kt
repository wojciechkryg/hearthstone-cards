package com.wojdor.hearthstonecards.application.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel

import com.wojdor.hearthstonecards.data.database.CardDatabase
import com.wojdor.hearthstonecards.data.database.dao.CardDao
import com.wojdor.hearthstonecards.data.service.CardApi
import com.wojdor.hearthstonecards.data.service.CardService
import com.wojdor.hearthstonecards.data.session.UserSession

import io.reactivex.disposables.CompositeDisposable

abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {

    protected var cardApi: CardApi = CardService.getInstance()
    protected var disposable: CompositeDisposable = CompositeDisposable()
    protected var cardDao: CardDao = CardDatabase.getInstance(application).cardDao()
    protected var userSession: UserSession = UserSession.getInstance(application)

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}

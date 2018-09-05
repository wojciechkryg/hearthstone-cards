package com.wojdor.hearthstonecards.application.card

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

import com.wojdor.hearthstonecards.application.base.BaseAndroidViewModel
import com.wojdor.hearthstonecards.application.util.FileStorage
import com.wojdor.hearthstonecards.domain.Card

import java.io.File

class CardViewModel(application: Application) : BaseAndroidViewModel(application) {

    private val fileStorage: FileStorage = FileStorage(application)

    fun getCardByCardId(cardId: String): LiveData<Card> {
        return cardDao.getCardByCardId(cardId)
    }

    fun getCardImage(cardId: String): LiveData<File> {
        val file = fileStorage.get(cardId)
        return MutableLiveData<File>().apply { postValue(file) }
    }
}

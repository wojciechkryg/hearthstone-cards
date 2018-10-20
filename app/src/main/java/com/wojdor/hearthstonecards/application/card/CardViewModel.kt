package com.wojdor.hearthstonecards.application.card

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.wojdor.hearthstonecards.application.base.BaseViewModel
import com.wojdor.hearthstonecards.application.util.FileStorage
import com.wojdor.hearthstonecards.data.repository.CardRepository
import com.wojdor.hearthstonecards.domain.Card
import java.io.File

class CardViewModel(repository: CardRepository, private val fileStorage: FileStorage) : BaseViewModel(repository) {

    fun getCardByCardId(cardId: String): LiveData<Card> = repository.getCardByCardId(cardId)

    fun getCardImage(cardId: String): LiveData<File> {
        val file = fileStorage.get(cardId)
        return MutableLiveData<File>().apply { postValue(file) }
    }
}

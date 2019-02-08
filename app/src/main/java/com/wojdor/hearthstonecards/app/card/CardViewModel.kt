package com.wojdor.hearthstonecards.app.card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wojdor.hearthstonecards.app.base.BaseViewModel
import com.wojdor.hearthstonecards.app.util.FileStorage
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

package com.wojdor.hearthstonecards.app.classcards

import android.arch.lifecycle.LiveData
import com.wojdor.hearthstonecards.app.base.BaseViewModel
import com.wojdor.hearthstonecards.data.repository.CardRepository
import com.wojdor.hearthstonecards.domain.Card

class ClassCardsViewModel(cardRepository: CardRepository) : BaseViewModel(cardRepository) {

    fun getCardsFromClass(className: String): LiveData<List<Card>> = repository.getCardsFromClass(className)
}

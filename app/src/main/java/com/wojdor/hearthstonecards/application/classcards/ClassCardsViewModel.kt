package com.wojdor.hearthstonecards.application.classcards

import android.arch.lifecycle.LiveData
import com.wojdor.hearthstonecards.application.base.BaseViewModel
import com.wojdor.hearthstonecards.data.repository.CardRepository
import com.wojdor.hearthstonecards.domain.Card

class ClassCardsViewModel(cardRepository: CardRepository) : BaseViewModel(cardRepository) {

    fun getCardsFromClass(className: String): LiveData<List<Card>> = repository.getCardsFromClass(className)
}

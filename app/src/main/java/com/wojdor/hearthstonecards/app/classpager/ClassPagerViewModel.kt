package com.wojdor.hearthstonecards.app.classpager

import androidx.lifecycle.LiveData
import com.wojdor.hearthstonecards.app.base.BaseViewModel
import com.wojdor.hearthstonecards.data.repository.CardRepository

class ClassPagerViewModel(cardRepository: CardRepository) : BaseViewModel(cardRepository) {

    val classesWhichHaveCards: LiveData<List<String>>
        get() = repository.classesWhichHaveCards
}

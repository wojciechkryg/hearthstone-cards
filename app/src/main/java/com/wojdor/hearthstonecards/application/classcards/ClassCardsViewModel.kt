package com.wojdor.hearthstonecards.application.classcards

import android.app.Application
import android.arch.lifecycle.LiveData

import com.wojdor.hearthstonecards.application.base.BaseAndroidViewModel
import com.wojdor.hearthstonecards.domain.Card

class ClassCardsViewModel(application: Application) : BaseAndroidViewModel(application) {

    fun getCardsFromClass(className: String): LiveData<List<Card>> = repository.getCardsFromClass(className)
}

package com.wojdor.hearthstonecards.app.settings

import com.wojdor.hearthstonecards.app.base.BaseViewModel
import com.wojdor.hearthstonecards.data.repository.CardRepository

class SettingsViewModel(cardRepository: CardRepository) : BaseViewModel(cardRepository) {

    // TODO: Delete all cards from database and images
    fun languageChanged() = repository.wasLanguageChanged(true)
}

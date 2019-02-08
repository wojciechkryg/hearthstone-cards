package com.wojdor.hearthstonecards.data.service.mapper

import com.wojdor.hearthstonecards.app.extension.empty
import com.wojdor.hearthstonecards.data.service.model.CardModel
import com.wojdor.hearthstonecards.domain.Card
import timber.log.Timber
import java.util.*

object CardMapper {

    fun map(models: List<CardModel>): List<Card> {
        val cards = ArrayList<Card>()
        models.forEach {
            Timber.d(it.cardId)
            cards.add(map(it))
        }
        return cards
    }

    private fun map(model: CardModel) = Card(
            model.dbfId,
            model.cardId,
            model.name,
            model.cardSet,
            model.type,
            model.rarity,
            model.cost,
            model.text ?: String.empty,
            model.flavor ?: String.empty,
            model.artist,
            model.playerClass
    )
}

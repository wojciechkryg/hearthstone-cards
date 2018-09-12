package com.wojdor.hearthstonecards.data.service.mapper

import com.wojdor.hearthstonecards.data.service.model.CardModel
import com.wojdor.hearthstonecards.domain.Card
import java.util.*

object CardMapper {

    fun map(models: List<CardModel>): List<Card> {
        val cards = ArrayList<Card>()
        for (model in models) {
            cards.add(map(model))
        }
        return cards
    }

    private fun map(model: CardModel) = Card(
            model.dbfId,
            model.cardId,
            model.name,
            model.cardSet,
            model.rarity,
            model.cost,
            model.text,
            model.flavor,
            model.artist,
            model.playerClass
    )
}

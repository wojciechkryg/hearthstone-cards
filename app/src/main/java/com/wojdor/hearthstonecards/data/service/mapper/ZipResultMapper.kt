package com.wojdor.hearthstonecards.data.service.mapper

import com.wojdor.hearthstonecards.data.service.model.CardModel
import java.util.*

object ZipResultMapper {

    private const val HERO_CARD = "hero"

    fun map(objects: Array<Any>): List<CardModel> {
        val allCards = ArrayList<CardModel>()
        for (cardModels in objects) {
            if (isListOfCardModels(cardModels)) {
                ignoreHeroCards(allCards, cardModels as List<CardModel>)
            }
        }
        return allCards
    }

    private fun ignoreHeroCards(allCards: MutableList<CardModel>, cardModels: List<CardModel>) {
        for (cardModel in cardModels) {
            if (isHeroCard(cardModel)) continue
            allCards.add(cardModel)
        }
    }

    private fun isListOfCardModels(cardModels: Any): Boolean {
        return (cardModels is List<*>
                && !cardModels.isEmpty()
                && cardModels.first() is CardModel)
    }

    private fun isHeroCard(cardModel: CardModel): Boolean {
        return cardModel.cardId.toLowerCase().contains(HERO_CARD)
    }
}

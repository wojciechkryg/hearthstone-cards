package com.wojdor.hearthstonecards.data.service.mapper

import com.wojdor.hearthstonecards.data.service.model.CardModel

object ZipResultMapper {

    private const val HERO_CARD = "hero"

    fun mapToCardModels(objects: Array<Any>): List<CardModel> {
        val allCards = mutableListOf<CardModel>()
        val cardModels = objects.filter { isListOfCardModels(it) }
        cardModels.forEach { ignoreHeroCards(allCards, it as List<CardModel>) }
        return allCards
    }

    private fun ignoreHeroCards(allCards: MutableList<CardModel>, cardModels: List<CardModel>) {
        allCards.addAll(cardModels.filterNot { isHeroCard(it) })
    }

    private fun isListOfCardModels(cardModels: Any): Boolean {
        return (cardModels is List<*>
                && cardModels.isNotEmpty()
                && cardModels.first() is CardModel)
    }

    private fun isHeroCard(cardModel: CardModel): Boolean {
        return cardModel.cardId.toLowerCase().contains(HERO_CARD)
    }
}

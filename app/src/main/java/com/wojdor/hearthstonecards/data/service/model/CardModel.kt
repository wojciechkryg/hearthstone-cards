package com.wojdor.hearthstonecards.data.service.model

data class CardModel(
        val cardId: String,
        val dbfId: Int,
        val name: String,
        val cardSet: String,
        val rarity: String,
        val cost: Int,
        val text: String,
        val flavor: String,
        val artist: String,
        val playerClass: String,
        val img: String
)

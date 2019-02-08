package com.wojdor.hearthstonecards.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Card(
        @PrimaryKey val dbfId: Int,
        val cardId: String,
        val name: String,
        val set: String,
        val type: String,
        val rarity: String,
        val cost: Int,
        val text: String,
        val flavorText: String,
        val artist: String,
        val className: String
)

package com.wojdor.hearthstonecards.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wojdor.hearthstonecards.domain.Card

@Database(entities = [(Card::class)], version = 1, exportSchema = false)
abstract class CardDatabase : RoomDatabase() {

    abstract fun cardDao(): CardDao

    companion object {
        const val DATABASE_NAME = "card_db"
    }
}

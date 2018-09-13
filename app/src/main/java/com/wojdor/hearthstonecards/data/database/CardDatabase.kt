package com.wojdor.hearthstonecards.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

import com.wojdor.hearthstonecards.data.database.dao.CardDao
import com.wojdor.hearthstonecards.domain.Card

@Database(entities = [(Card::class)], version = 1, exportSchema = false)
abstract class CardDatabase : RoomDatabase() {

    abstract fun cardDao(): CardDao

    companion object {

        private const val DATABASE_NAME = "card_database"
        private var instance: CardDatabase? = null

        fun getInstance(context: Context): CardDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                        CardDatabase::class.java, DATABASE_NAME).build()
            }
            return instance as CardDatabase
        }
    }
}

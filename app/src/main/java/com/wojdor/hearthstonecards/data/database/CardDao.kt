package com.wojdor.hearthstonecards.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.wojdor.hearthstonecards.domain.Card

@Dao
interface CardDao {

    @Query("SELECT * FROM card")
    fun getAllCards(): List<Card>

    @Query("SELECT * FROM card WHERE className = :className")
    fun getCardsFromClass(className: String): LiveData<List<Card>>

    @Query("SELECT * FROM card WHERE cardId = :cardId")
    fun getCardByCardId(cardId: String): LiveData<Card>

    @Query("SELECT COUNT(*) FROM card WHERE className = :className")
    fun getAmountOfCardsFromClass(className: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCards(cards: List<Card>)
}

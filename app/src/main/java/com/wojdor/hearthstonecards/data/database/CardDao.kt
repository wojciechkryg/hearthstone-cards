package com.wojdor.hearthstonecards.data.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

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

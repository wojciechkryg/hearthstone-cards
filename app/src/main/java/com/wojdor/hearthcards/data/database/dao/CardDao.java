package com.wojdor.hearthcards.data.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.wojdor.hearthcards.domain.Card;

import java.util.List;

@Dao
public interface CardDao {

    @Query("SELECT * FROM card WHERE className = :className")
    LiveData<List<Card>> getCardsFromClass(String className);

    @Query("SELECT * FROM card WHERE cardId = :cardId")
    LiveData<Card> getCardByCardId(String cardId);

    @Query("SELECT COUNT(*) FROM card WHERE className = :className")
    int getAmountOfCardsFromClass(String className);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCards(List<Card> cards);
}

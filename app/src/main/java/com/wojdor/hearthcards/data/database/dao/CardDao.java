package com.wojdor.hearthcards.data.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.wojdor.hearthcards.domain.Card;

import java.util.List;

@Dao
public interface CardDao {

    @Query("SELECT * FROM card")
    LiveData<List<Card>> getCards();

    @Query("SELECT * FROM card WHERE className = :className")
    LiveData<List<Card>> getCardsFromClass(String className);

    @Insert
    void insertCards(List<Card> cards);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCard(Card card);
}

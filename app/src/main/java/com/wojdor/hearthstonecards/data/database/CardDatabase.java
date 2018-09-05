package com.wojdor.hearthstonecards.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.wojdor.hearthstonecards.data.database.dao.CardDao;
import com.wojdor.hearthstonecards.domain.Card;

@Database(entities = {Card.class}, version = 1, exportSchema = false)
public abstract class CardDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "card_database";
    private static final Object LOCK = new Object();
    private static CardDatabase instance;

    public static CardDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        CardDatabase.class, DATABASE_NAME)
                        .build();
            }
        }
        return instance;
    }

    public abstract CardDao cardDao();
}

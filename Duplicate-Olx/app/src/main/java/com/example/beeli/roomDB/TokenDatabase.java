package com.example.beeli.roomDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TokenEntity.class}, version = 1)
public abstract class TokenDatabase extends RoomDatabase {
    public abstract TokenDAO tokenDAO();
}
package com.example.beeli.roomDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface TokenDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertToken(TokenEntity token);

    @Query("SELECT * FROM token LIMIT 1")
    TokenEntity getToken();
    @Query("DELETE FROM token")
    void deleteAllTokens();
}

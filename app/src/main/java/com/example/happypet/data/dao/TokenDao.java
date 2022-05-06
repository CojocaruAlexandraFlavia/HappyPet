package com.example.happypet.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.happypet.model.Doctor;
import com.example.happypet.model.Location;
import com.example.happypet.model.Token;

import java.util.List;

@Dao
public interface TokenDao {

    @Query("DELETE FROM token")
    void deleteAll();

    @Query("SELECT * FROM token WHERE tokenValue=:tokenValue1")
    Token findByTokenValue(String tokenValue1);

    @Query("SELECT * FROM token WHERE locationId =:locationId")
    List<Token> findByLocationId(Long locationId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertToken(Token token);

    @Query("SELECT * FROM token")
    List<Token> getAll();

}

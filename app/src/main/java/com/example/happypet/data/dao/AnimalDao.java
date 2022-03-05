package com.example.happypet.data.dao;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface AnimalDao {

    @Query("DELETE FROM animal")
    void deleteAll();

}

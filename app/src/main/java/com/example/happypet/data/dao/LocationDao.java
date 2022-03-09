package com.example.happypet.data.dao;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface LocationDao {

    @Query("DELETE FROM location")
    void deleteAll();
}

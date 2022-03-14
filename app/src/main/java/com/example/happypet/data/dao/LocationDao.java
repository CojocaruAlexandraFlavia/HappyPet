package com.example.happypet.data.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.happypet.model.Location;

import java.util.List;

@Dao
public interface LocationDao {

    @Query("DELETE FROM location")
    void deleteAll();

    @Query("SELECT * FROM location")
    List<Location> getAllLocations();
}

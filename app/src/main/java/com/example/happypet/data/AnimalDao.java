package com.example.happypet.data;

import androidx.room.Dao;
import androidx.room.Delete;

@Dao
public interface AnimalDao {

    @Delete
    public void deleteAll();

}

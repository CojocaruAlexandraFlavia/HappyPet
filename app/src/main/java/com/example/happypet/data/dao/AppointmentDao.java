package com.example.happypet.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

@Dao
public interface AppointmentDao {

    @Query("DELETE FROM appointment")
    void deleteAll();

}

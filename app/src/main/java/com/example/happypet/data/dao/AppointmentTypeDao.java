package com.example.happypet.data.dao;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface AppointmentTypeDao {

    @Query("DELETE FROM appointment_type")
    void deleteAll();

}

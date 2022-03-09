package com.example.happypet.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

@Dao
public interface DoctorDao {

    @Query("DELETE FROM doctor")
    void deleteAll();

}

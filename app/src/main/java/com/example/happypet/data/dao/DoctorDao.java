package com.example.happypet.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

import com.example.happypet.model.Doctor;

@Dao
public interface DoctorDao {

    @Query("DELETE FROM doctor")
    void deleteAll();

    @Query("SELECT doctorId FROM doctor WHERE email=:email")
    long findByEmail(String email);
}

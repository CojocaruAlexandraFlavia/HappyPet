package com.example.happypet.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.happypet.model.Doctor;

import java.util.List;

@Dao
public interface DoctorDao {

    @Query("DELETE FROM doctor")
    void deleteAll();

    @Query("SELECT * FROM doctor WHERE email=:email")
    Doctor findByEmail(String email);

    @Query("SELECT * FROM doctor WHERE doctorId=:id")
    Doctor findById(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertDoctor(Doctor doctor);

    @Query("SELECT * FROM doctor")
    List<Doctor> getAll();
    @Query("SELECT * FROM doctor WHERE locationId=:locationId")
    List<Doctor> getDoctorsForLocation(long locationId);

    @Query("SELECT * FROM doctor WHERE firstName LIKE :searchQuery OR lastName LIKE :searchQuery")
    List<Doctor> searchDoctor(String searchQuery);
}
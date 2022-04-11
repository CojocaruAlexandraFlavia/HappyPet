package com.example.happypet.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.happypet.model.AppointmentType;

import java.util.List;

@Dao
public interface AppointmentTypeDao {

    @Query("DELETE FROM appointment_type")
    void deleteAll();

    @Query("SELECT * FROM appointment_type")
    List<AppointmentType> getAllAppointmentTypes();

    @Insert
    void insertAppointmentType(AppointmentType appointmentType);

    @Query("SELECT * FROM appointment_type WHERE appointmentTypeId=:id")
    AppointmentType getAppointmentTypeById(long id);

}

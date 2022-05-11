package com.example.happypet.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.happypet.model.Appointment;

import java.util.List;

@Dao
public interface AppointmentDao {

    @Query("DELETE FROM appointment")
    void deleteAll();

    @Insert
    long insertAppointment(Appointment appointment);

    @Query("SELECT * FROM appointment WHERE appointmentId=:id")
    Appointment getAppointmentById(long id);

    @Query("SELECT appointmentId FROM appointment WHERE rowId=:rowId")
    long getAppointmentByRowId(long rowId);

    @Query("SELECT SUBSTR(date, 12, 6) FROM appointment WHERE SUBSTR(date, 1, 10)=:day")
    List<String> getAppointmentsForDay(String day);

    @Query("SELECT * FROM appointment WHERE doctorId =:doctorId")
    List<Appointment> getAppointmentsForDoctor(Long doctorId);

    @Query("SELECT * FROM appointment")
    List<Appointment> getAllAppointments();
}

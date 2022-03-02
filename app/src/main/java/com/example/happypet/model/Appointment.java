package com.example.happypet.model;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity(tableName = "appointment")
public class Appointment {

    @PrimaryKey(autoGenerate = true)
    private long appointmentId;

    private LocalDateTime date;

    private long animalId;

    private long doctorId;

    private long userId;

    public long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setDate(LocalDateTime date) {
        this.date = LocalDateTime.parse(date.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public long getAnimalId() {
        return animalId;
    }

    public void setAnimalId(long animalId) {
        this.animalId = animalId;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}

package com.example.happypet.model;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.happypet.util.LocalDateTimeConverter;

import java.time.LocalDateTime;

@Entity(tableName = "appointment", foreignKeys = {
        @ForeignKey(
                entity = Animal.class,
                parentColumns = "animalId",
                childColumns = "animalId"
        ),
        @ForeignKey(
              entity = Doctor.class,
              parentColumns = "doctorId",
              childColumns = "doctorId"
        ),
        @ForeignKey(
                entity = AppointmentType.class,
                parentColumns = "appointmentTypeId",
                childColumns = "appointmentTypeId"
        )
})
public class Appointment {

    @PrimaryKey(autoGenerate = true)
    private long appointmentId;

    private String date;

    @ColumnInfo(index = true)
    private long animalId;

    @ColumnInfo(index = true)
    private long doctorId;

    @ColumnInfo(index = true)
    private long appointmentTypeId;

    public long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public long getAppointmentTypeId() {
        return appointmentTypeId;
    }

    public void setAppointmentTypeId(long appointmentTypeId) {
        this.appointmentTypeId = appointmentTypeId;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDateTime getDateAsLocalDateTime() {
        return LocalDateTimeConverter.toDate(date);
    }
}

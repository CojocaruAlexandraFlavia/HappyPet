package com.example.happypet.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.lang.annotation.Inherited;

@Entity
public class Doctor {

    @PrimaryKey(autoGenerate = true)
    private long doctorId;

    private String firstName;

    private String lastName;

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

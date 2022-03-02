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



}

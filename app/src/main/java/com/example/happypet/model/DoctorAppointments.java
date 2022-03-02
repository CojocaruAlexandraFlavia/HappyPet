package com.example.happypet.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DoctorAppointments {

    @Embedded public Doctor doctor;
    @Relation(
            parentColumn = "doctorId",
            entityColumn = "doctorId"
    )
    public List<Appointment> appointments;

}

package com.example.happypet.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserAppointments {

    @Embedded public User user;
    @Relation(
            parentColumn = "userId",
            entityColumn = "userId"
    )
    public List<Appointment> appointments;

}

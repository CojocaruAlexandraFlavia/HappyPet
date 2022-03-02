package com.example.happypet.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class AnimalAppointments {

    @Embedded public Animal animal;
    @Relation(
            parentColumn = "animalId",
            entityColumn = "animalId"
    )
    public List<Appointment> appointments;

}

package com.example.happypet.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserAnimals {

    @Embedded public User user;
    @Relation(
            parentColumn = "userId",
            entityColumn = "ownerId"
    )
    public List<Animal> animals;

}

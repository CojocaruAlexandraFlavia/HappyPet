package com.example.happypet.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.happypet.model.Animal;

import java.util.List;

@Dao
public interface AnimalDao {

    @Query("DELETE FROM animal")
    void deleteAll();

    @Insert
    void insertAnimal(Animal animal);

    @Query("SELECT * FROM animal WHERE ownerId=:ownerId")
    List<Animal> getAnimalForOwner(long ownerId);

    @Query("SELECT * FROM animal WHERE animalId=:animalId")
    Animal getAnimalById(long animalId);

}

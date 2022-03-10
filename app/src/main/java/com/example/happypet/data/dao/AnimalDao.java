package com.example.happypet.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.happypet.model.Animal;

import java.util.List;

@Dao
public interface AnimalDao {

    @Insert
    long insertPet(Animal animal);

    @Query("SELECT * FROM animal WHERE ownerId = :id")
    public List<Animal> getAllPetsForOwner(long id);
    @Query("DELETE FROM animal")
    void deleteAll();

}

package com.example.happypet.data.repository;

import android.app.Application;

import com.example.happypet.data.dao.AnimalDao;
import com.example.happypet.data.dao.ClientDao;
import com.example.happypet.model.Animal;
import com.example.happypet.util.RoomDatabaseImpl;

import java.util.List;

import javax.inject.Inject;

public class PetRepository {
    private final AnimalDao animalDao;
    private final ClientDao clientDao;
    @Inject
    public PetRepository(Application application)
    {
        RoomDatabaseImpl db = RoomDatabaseImpl.getDatabase(application);
        animalDao = db.animalDao();
        clientDao = db.clientDao();
    }
    public List<Animal> getAllPetsForOwner(long ownerId){
        return animalDao.getAllPetsForOwner(ownerId);
    }
    public void insertPet(Animal animal){
        animalDao.insertPet(animal);
    }



}
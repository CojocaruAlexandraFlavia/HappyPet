package com.example.happypet.data.repository;

import android.app.Application;

import com.example.happypet.data.dao.AnimalDao;
import com.example.happypet.model.Animal;
import com.example.happypet.model.AppointmentType;
import com.example.happypet.util.RoomDatabaseImpl;

import java.util.List;

import javax.inject.Inject;

public class AnimalRepository {

    private final AnimalDao animalDao;

    @Inject
    public AnimalRepository(Application application){
        RoomDatabaseImpl db = RoomDatabaseImpl.getDatabase(application);
        animalDao = db.animalDao();
    }

    public void insertAnimal(Animal animal){
        RoomDatabaseImpl.databaseWriteExecutor.execute(() -> animalDao.insertAnimal(animal));
    }

    public List<Animal> getAnimalsForOwner(long ownerId){
        return animalDao.getAnimalForOwner(ownerId);
    }

    public Animal getAnimalById(long id){
        return animalDao.getAnimalById(id);
    }


}

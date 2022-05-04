package com.example.happypet.model.view_model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.happypet.data.repository.AnimalRepository;
import com.example.happypet.model.Animal;

import java.util.List;

import javax.inject.Inject;

public class AnimalViewModel extends AndroidViewModel {

    private final AnimalRepository animalRepository;

    @Inject
    public AnimalViewModel(Application application){
        super(application);
        animalRepository = new AnimalRepository(application);
    }

    public List<Animal> getAnimalsForOwner(long ownerId){
        return animalRepository.getAnimalsForOwner(ownerId);
    }

    public void insertAnimal(Animal animal){
        animalRepository.insertAnimal(animal);
    }

    public Animal getAnimalById(long id){
        return animalRepository.getAnimalById(id);
    }

}

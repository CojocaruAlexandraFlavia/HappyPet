package com.example.happypet.model.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.happypet.data.repository.PetRepository;
import com.example.happypet.model.Animal;

import java.util.List;

public class PetViewModel extends AndroidViewModel {

    private final PetRepository petRepository;
    public PetViewModel(@NonNull Application application) {
        super(application);
        this.petRepository = new PetRepository(application);
    }
    public List<Animal> getAllPetsForOwner(long ownerId){
        return petRepository.getAllPetsForOwner(ownerId);
    }
    public void insertPet(Animal animal){
        petRepository.insertPet(animal);
    }
}

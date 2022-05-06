package com.example.happypet.model.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.happypet.data.repository.UserRepository;
import com.example.happypet.model.Client;
import com.example.happypet.model.Doctor;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

public class UserViewModel extends AndroidViewModel {

    private final UserRepository repository;

    @Inject
    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
    }

    public void insertClient(Client client){
        repository.insertClient(client);
    }

    public List<Client> getAllClients(){
        return repository.getAllClients();
    }

    public boolean findUserByEmail(String email){
        return repository.findUserByEmail(email);
    }

    public Doctor findDoctorByEmail(String email){
        return repository.getDoctorByEmail(email);
    }

    public Doctor getDoctorById(long id){
        return repository.getDoctorById(id);
    }

    public void insertDoctor(Doctor doctor){
        repository.insertDoctor(doctor);
    }

    public List<Doctor> getAllDoctors(){
        return repository.getAllDoctors();
    }

    public List<Doctor> getDoctorsFromLocation(long locationId){
        return repository.getDoctorsFromLocation(locationId);
    }

    public Client getClientByEmail(String email){
        return repository.getClientByEmail(email);
    }

    public Client getClientById(long id){
        return repository.getClientById(id);
    }


}

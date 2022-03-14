package com.example.happypet.model.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.happypet.data.repository.UserRepository;
import com.example.happypet.model.Client;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private final UserRepository repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
    }

    public void insertClient(Client client){
        System.out.println("insert client");
        repository.insertClient(client);
    }

    public List<Client> getAllClients(){
        return repository.getAllClients();
    }

    public boolean findUserByEmail(String email){
        return repository.findUserByEmail(email);
    }
}

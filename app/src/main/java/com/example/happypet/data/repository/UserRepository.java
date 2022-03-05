package com.example.happypet.data.repository;

import android.app.Application;

import com.example.happypet.data.dao.ClientDao;
import com.example.happypet.data.dao.DoctorDao;
import com.example.happypet.model.Client;
import com.example.happypet.model.Doctor;
import com.example.happypet.util.RoomDatabaseImpl;

import java.util.List;

public class UserRepository {

    private final ClientDao clientDao;
    private final DoctorDao doctorDao;

    public UserRepository(Application application){
        RoomDatabaseImpl db = RoomDatabaseImpl.getDatabase(application);
        clientDao = db.clientDao();
        doctorDao = db.doctorDao();
    }

    public void insertClient(Client client){
        System.out.println("insert client");
        RoomDatabaseImpl.databaseWriteExecutor.execute(() -> clientDao.insertClient(client));
    }

    public List<Client> getAllClients(){
        return clientDao.getAll();
    }

}

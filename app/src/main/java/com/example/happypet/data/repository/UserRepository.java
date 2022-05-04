package com.example.happypet.data.repository;

import android.app.Application;

import com.example.happypet.data.dao.ClientDao;
import com.example.happypet.data.dao.DoctorDao;
import com.example.happypet.model.Client;
import com.example.happypet.model.Doctor;
import com.example.happypet.model.User;
import com.example.happypet.util.RoomDatabaseImpl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

public class UserRepository {

    private final ClientDao clientDao;
    private final DoctorDao doctorDao;

    @Inject
    public UserRepository(Application application){
        RoomDatabaseImpl db = RoomDatabaseImpl.getDatabase(application);
        clientDao = db.clientDao();
        doctorDao = db.doctorDao();
    }

    public void insertClient(Client client){
        System.out.println("insert client");
        RoomDatabaseImpl.databaseWriteExecutor.execute(() -> clientDao.insertClient(client));
    }

    public void insertDoctor(Doctor doctor){
        RoomDatabaseImpl.databaseWriteExecutor.execute(() -> doctorDao.insertDoctor(doctor));
    }

    public List<Client> getAllClients(){
        return clientDao.getAll();
    }

    public boolean findUserByEmail(String email) {
        Client client = clientDao.findClientByEmail(email);
        Doctor doctor = doctorDao.findByEmail(email);
        return (client != null) || (doctor != null);
    }
    public Client getClientByEmail(String email){
        return clientDao.getClientByEmail(email);

    }

    public List<Doctor> getDoctorsFromLocation(long locationId){
        return doctorDao.getDoctorsForLocation(locationId);
    }

    public Client getClientById(long id){
        return clientDao.getClientById(id);
    }
    public Doctor getDoctorByEmail(String email){
        return doctorDao.findByEmail(email);
    }

    public Doctor getDoctorById(long id){
        return doctorDao.findById(id);
    }


    public List<Doctor> getAllDoctors(){return doctorDao.getAll();}
}
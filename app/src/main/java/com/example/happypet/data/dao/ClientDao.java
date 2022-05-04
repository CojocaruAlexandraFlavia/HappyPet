package com.example.happypet.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.happypet.model.Client;

import java.util.List;

@Dao
public interface ClientDao {

    @Query("DELETE FROM client")
    void deleteAll();

    @Insert
    void insertClient(Client client);

    @Query("SELECT * FROM client")
    List<Client> getAll();

    @Query("SELECT * FROM client WHERE email=:email")
    Client findClientByEmail(String email);

    @Query("SELECT * FROM client WHERE clientId=:clientId")
    Client getClientById(long clientId);

    @Query("SELECT * FROM client WHERE email=:email")
    Client getClientByEmail(String email);

}
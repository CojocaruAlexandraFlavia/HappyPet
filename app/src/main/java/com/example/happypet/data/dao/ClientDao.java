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

}

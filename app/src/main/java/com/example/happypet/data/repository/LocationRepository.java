package com.example.happypet.data.repository;

import android.app.Application;

import com.example.happypet.data.dao.LocationDao;
import com.example.happypet.model.Doctor;
import com.example.happypet.model.Location;
import com.example.happypet.util.RoomDatabaseImpl;

import java.util.List;

public class LocationRepository {

    private final LocationDao locationDao;

    public LocationRepository(Application application) {
        RoomDatabaseImpl db = RoomDatabaseImpl.getDatabase(application);
        locationDao = db.locationDao();
    }

    public List<Location> getAllLocations(){
        return locationDao.getAllLocations();
    }

    public void insertLocation(Location location){
        locationDao.insertLocation(location);
    }

    public Location getLocationById(Long locationId){
        return locationDao.getLocationById(locationId);
    }

}
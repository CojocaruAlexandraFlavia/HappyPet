package com.example.happypet.data.repository;

import android.app.Application;

import com.example.happypet.data.dao.LocationDao;
import com.example.happypet.model.Doctor;
import com.example.happypet.model.Location;
import com.example.happypet.util.RoomDatabaseImpl;

import java.util.List;

import javax.inject.Inject;

public class LocationRepository {

    private final LocationDao locationDao;

    @Inject
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

    public Location getLocationById(long id){
        return locationDao.getLocationById(id);
    }

}

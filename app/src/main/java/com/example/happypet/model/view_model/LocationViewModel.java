package com.example.happypet.model.view_model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.happypet.data.repository.LocationRepository;
import com.example.happypet.model.Location;

import java.util.List;

public class LocationViewModel extends AndroidViewModel {

    private final LocationRepository locationRepository;

    public LocationViewModel(Application application){
        super(application);
        locationRepository = new LocationRepository(application);
    }

    public List<Location> getAllLocations(){
        return locationRepository.getAllLocations();
    }

    public void insertLocation(Location location){
        locationRepository.insertLocation(location);
    }
}

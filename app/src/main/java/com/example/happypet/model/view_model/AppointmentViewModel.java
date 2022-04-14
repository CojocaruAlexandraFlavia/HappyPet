package com.example.happypet.model.view_model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.happypet.data.repository.AppointmentRepository;
import com.example.happypet.model.Appointment;
import com.example.happypet.model.AppointmentType;

import java.util.List;

import javax.inject.Inject;

public class AppointmentViewModel extends AndroidViewModel {

    private final AppointmentRepository appointmentRepository;

    @Inject
    public AppointmentViewModel(Application application){
        super(application);
        appointmentRepository = new AppointmentRepository(application);
    }

    public long insertAppointment(Appointment appointment){
        return appointmentRepository.insertAppointment(appointment);
    }

    public List<AppointmentType> getAllAppointmentTypes() {
        return appointmentRepository.getAllAppointmentTypes();
    }

    public void insertAppointmentType(AppointmentType appointmentType){
        appointmentRepository.insertAppointmentType(appointmentType);
    }

    public Appointment getAppointmentById(long id){
        return appointmentRepository.getAppointmentById(id);
    }

    public AppointmentType getAppointmentTypeById(long id){
        return appointmentRepository.getAppointmentTypeById(id);
    }

    public List<String> getAppointmentsForDay(String day){
        return appointmentRepository.getAppointmentsForDay(day);
    }


}

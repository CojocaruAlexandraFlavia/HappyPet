package com.example.happypet.data.repository;

import android.app.Application;

import com.example.happypet.data.dao.AppointmentDao;
import com.example.happypet.data.dao.AppointmentTypeDao;
import com.example.happypet.model.Appointment;
import com.example.happypet.model.AppointmentType;
import com.example.happypet.util.RoomDatabaseImpl;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

public class AppointmentRepository {

    private final AppointmentDao appointmentDao;
    private final AppointmentTypeDao appointmentTypeDao;

    private final ExecutorService executorService;

    @Inject
    public AppointmentRepository(Application application) {
        RoomDatabaseImpl db = RoomDatabaseImpl.getDatabase(application);
        appointmentDao = db.appointmentDao();
        appointmentTypeDao = db.appointmentTypeDao();
        executorService = Executors.newFixedThreadPool(10);
    }

    public long insertAppointment(Appointment appointment){
        Callable<Long> insertCallable = () -> appointmentDao.insertAppointment(appointment);
        long rowId, savedAppointmentId = 0;

        Future<Long> future = executorService.submit(insertCallable);
        try {
            rowId = future.get();
            savedAppointmentId = appointmentDao.getAppointmentByRowId(rowId);
        } catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
        }
        return savedAppointmentId;
    }

    public List<AppointmentType> getAllAppointmentTypes(){
        return appointmentTypeDao.getAllAppointmentTypes();
    }

    public void insertAppointmentType(AppointmentType appointmentType){
        RoomDatabaseImpl.databaseWriteExecutor.execute(() -> appointmentTypeDao.insertAppointmentType(appointmentType));
    }

    public Appointment getAppointmentById(long id){
        return appointmentDao.getAppointmentById(id);
    }

    public AppointmentType getAppointmentTypeById(long id){
        return appointmentTypeDao.getAppointmentTypeById(id);
    }

    public List<String> getAppointmentsForDay(String day){
        return appointmentDao.getAppointmentsForDay(day);
    }

    public List<Appointment> getAppointmentsForDoctor(Long doctorId) {
        return appointmentDao.getAppointmentsForDoctor(doctorId);
    }


    public List<Appointment> getAllAppointments() {
        return appointmentDao.getAllAppointments();
    }
}

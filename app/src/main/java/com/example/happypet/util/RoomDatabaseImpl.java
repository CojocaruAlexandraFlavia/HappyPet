package com.example.happypet.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.happypet.data.AnimalDao;
import com.example.happypet.data.AppointmentDao;
import com.example.happypet.data.DoctorDao;
import com.example.happypet.data.UserDao;
import com.example.happypet.model.Animal;
import com.example.happypet.model.Appointment;
import com.example.happypet.model.Doctor;
import com.example.happypet.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Doctor.class, Animal.class, Appointment.class}, version = 1, exportSchema = false)
public abstract class RoomDatabaseImpl extends androidx.room.RoomDatabase {

    public abstract UserDao userDao();
    public abstract DoctorDao doctorDao();
    public abstract AnimalDao animalDao();
    public abstract AppointmentDao appointmentDao();

    public static final int NUMBER_OF_THREADS = 10;

    private static volatile RoomDatabaseImpl INSTANCE;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static RoomDatabaseImpl getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (RoomDatabaseImpl.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RoomDatabaseImpl.class, "happy_pet_database").build();
                            //.addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    databaseWriteExecutor.execute(() ->{
                        UserDao userDao = INSTANCE.userDao();
                        userDao.deleteAll();

                        DoctorDao doctorDao = INSTANCE.doctorDao();
                        doctorDao.deleteAll();

                        AppointmentDao appointmentDao = INSTANCE.appointmentDao();
                        appointmentDao.deleteAll();

                        AnimalDao animalDao = INSTANCE.animalDao();
                        animalDao.deleteAll();
                    });
                }
            };

}

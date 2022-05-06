package com.example.happypet.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.happypet.data.dao.AnimalDao;
import com.example.happypet.data.dao.AppointmentDao;
import com.example.happypet.data.dao.AppointmentTypeDao;
import com.example.happypet.data.dao.ClientDao;
import com.example.happypet.data.dao.DoctorDao;
import com.example.happypet.data.dao.LocationDao;
import com.example.happypet.data.dao.TokenDao;
import com.example.happypet.model.Animal;
import com.example.happypet.model.Appointment;
import com.example.happypet.model.AppointmentType;
import com.example.happypet.model.Client;
import com.example.happypet.model.Doctor;
import com.example.happypet.model.Location;
import com.example.happypet.model.Token;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dagger.Provides;

@Database(entities = {Client.class, Doctor.class, Animal.class, Appointment.class, Location.class, AppointmentType.class, Token.class},
        version = 2, exportSchema = false)
public abstract class RoomDatabaseImpl extends RoomDatabase {

    public abstract ClientDao clientDao();
    public abstract DoctorDao doctorDao();
    public abstract AnimalDao animalDao();
    public abstract AppointmentDao appointmentDao();
    public abstract LocationDao locationDao();
    public abstract AppointmentTypeDao appointmentTypeDao();
    public abstract TokenDao tokenDao();

    public static final int NUMBER_OF_THREADS = 10;

    private static volatile RoomDatabaseImpl INSTANCE;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static RoomDatabaseImpl getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (RoomDatabaseImpl.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDatabaseImpl.class, "happy_pet_database")
                            .addCallback(sRoomDatabaseCallback).build();
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
                        ClientDao clientDao = INSTANCE.clientDao();
                        clientDao.deleteAll();

                        DoctorDao doctorDao = INSTANCE.doctorDao();
                        doctorDao.deleteAll();

                        AppointmentDao appointmentDao = INSTANCE.appointmentDao();
                        appointmentDao.deleteAll();

                        AnimalDao animalDao = INSTANCE.animalDao();
                        animalDao.deleteAll();

                        LocationDao locationDao = INSTANCE.locationDao();
                        locationDao.deleteAll();

                        AppointmentTypeDao appointmentTypeDao = INSTANCE.appointmentTypeDao();
                        appointmentTypeDao.deleteAll();

                        TokenDao tokenDao = INSTANCE.tokenDao();
                        tokenDao.deleteAll();
//
//                        Location l = new Location();
//                        l.setAddress("Bujorului 24");
//                        l.setCity("Bucuresti");
//                        l.setLatitude("44.25");
//                        l.setLongitude("26.34");
//                        locationDao.insertLocation(l);
//
//                        String tkn = "AMAMA";
//                        Token newT = new Token();
//                        newT.setTokenValue(tkn);
//                        newT.setLocationId(1L);
//                        tokenDao.insertToken(newT);


                    });
                }
            };

}

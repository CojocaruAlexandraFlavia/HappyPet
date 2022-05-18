package com.example.happypet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.happypet.DoctorAppointmentAdapter;
import com.example.happypet.R;
import com.example.happypet.databinding.ActivityMyAppointmentsDoctorBinding;
import com.example.happypet.model.Animal;
import com.example.happypet.model.Appointment;
import com.example.happypet.model.AppointmentType;
import com.example.happypet.model.Client;
import com.example.happypet.model.Doctor;
import com.example.happypet.model.view_model.AnimalViewModel;
import com.example.happypet.model.view_model.AppointmentViewModel;
import com.example.happypet.model.view_model.PetViewModel;
import com.example.happypet.model.view_model.UserViewModel;
import com.example.happypet.util.ApplicationImpl;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MyAppointmentsDoctorActivity extends DrawerBaseDoctorActivity {

    ActivityMyAppointmentsDoctorBinding activityMyAppointmentsDoctorBinding;



    private FirebaseAuth auth;

    @Inject
    AppointmentViewModel appointmentViewModel;

    @Inject
    UserViewModel userViewModel;
    @Inject
    AnimalViewModel animalViewModel;

    private String doctorEmail;
    private ArrayList<Appointment> myAppointments;
    private ArrayList<String> appointmentDates;
    private ArrayList<String> appointmentsTypes;
    private ArrayList<String> appointmentsPrices;
    private ArrayList<String> petTypes;
    private ArrayList<String> petAges;
    private ArrayList<String> petOwners;
    private ListView appointmentsListView;

    DoctorAppointmentAdapter doctorAppointmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMyAppointmentsDoctorBinding = ActivityMyAppointmentsDoctorBinding.inflate(getLayoutInflater());
        setContentView(activityMyAppointmentsDoctorBinding.getRoot());

        ApplicationImpl.getApp().getApplicationComponent().inject(this);
        auth = FirebaseAuth.getInstance();
        appointmentDates = new ArrayList<>();
        appointmentsTypes = new ArrayList<>();
        appointmentsPrices = new ArrayList<>();
        petTypes = new ArrayList<>();
        petAges = new ArrayList<>();
        petOwners = new ArrayList<>();
        appointmentsListView = findViewById(R.id.appointmentListView);




        if(auth.getCurrentUser()!=null){
            doctorEmail = auth.getCurrentUser().getEmail();
            new Thread(() -> {
                Doctor d = userViewModel.findDoctorByEmail(doctorEmail);
//                AppointmentType app = new AppointmentType();
//                app.setName("CONTROL");
//                app.setPrice(200f);
//                appointmentViewModel.insertAppointmentType(app);
//
//                Animal animal = new Animal();
//                animal.setType(Animal.CAT);
//                animal.setName("Georgiana");
//                animal.setAge(2);
//                animal.setOwnerId(1);
//
//                animalViewModel.insertAnimal(animal);
//
//                Appointment au = new Appointment();
//                au.setAppointmentTypeId(1);
//                au.setDoctorId(d.getDoctorId());
//                au.setDate("2022-05-07");
//                au.setAnimalId(1);
//                appointmentViewModel.insertAppointment(au);



                myAppointments = (ArrayList<Appointment>) appointmentViewModel.getAppointmentsForDoctor(d.getDoctorId());
                for(Appointment a: myAppointments){
                    appointmentDates.add(a.getDate());
                    AppointmentType ap = appointmentViewModel.getAppointmentTypeById(a.getAppointmentTypeId());
                    appointmentsTypes.add(ap.getName());
                    appointmentsPrices.add(String.valueOf(ap.getPrice()));
                    Animal pet = animalViewModel.getAnimalById(a.getAnimalId());
                    petTypes.add(pet.getType());
                    petAges.add(String.valueOf(pet.getAge()));
                    Client c = userViewModel.getClientById(pet.getOwnerId());
                    petOwners.add(c.getFullName());

                }
                this.runOnUiThread(() ->{
                    doctorAppointmentAdapter = new DoctorAppointmentAdapter(this, petTypes, petAges, appointmentDates,
                            appointmentsTypes, appointmentsPrices, petOwners);
                    appointmentsListView.setAdapter(doctorAppointmentAdapter);

                });
            }).start();



        }

    }
}
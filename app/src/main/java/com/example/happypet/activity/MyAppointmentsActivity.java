package com.example.happypet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.happypet.ClientAppointmentAdapter;
import com.example.happypet.DoctorAppointmentAdapter;
import com.example.happypet.R;
import com.example.happypet.databinding.ActivityMyAppointmentsBinding;
import com.example.happypet.model.Animal;
import com.example.happypet.model.Appointment;
import com.example.happypet.model.AppointmentType;
import com.example.happypet.model.Client;
import com.example.happypet.model.Doctor;
import com.example.happypet.model.Location;
import com.example.happypet.model.view_model.AnimalViewModel;
import com.example.happypet.model.view_model.AppointmentViewModel;
import com.example.happypet.model.view_model.LocationViewModel;
import com.example.happypet.model.view_model.UserViewModel;
import com.example.happypet.util.ApplicationImpl;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import javax.inject.Inject;

public class MyAppointmentsActivity extends DrawerBaseActivity {


    ActivityMyAppointmentsBinding activityMyAppointmentsBinding;


    private FirebaseAuth auth;

    @Inject
    AppointmentViewModel appointmentViewModel;

    @Inject
    UserViewModel userViewModel;

    @Inject
    AnimalViewModel animalViewModel;

    @Inject
    LocationViewModel locationViewModel;

    private String clientEmail;
    private ArrayList<Appointment> myAppointments;
    private ArrayList<String> appointmentDates;
    private ArrayList<String> appointmentLocations;
    private ArrayList<String> appointmentsTypes;
    private ArrayList<String> appointmentsPrices;
    private ArrayList<String> petTypes;
    private ArrayList<String> petAges;
    private ArrayList<String> doctorNames;
    private ArrayList<String> latLng;
    private ArrayList<Animal> pets;
    private ArrayList<Appointment> appointments;

    private ListView appointmentsListView;

    ClientAppointmentAdapter clientAppointmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMyAppointmentsBinding = ActivityMyAppointmentsBinding.inflate(getLayoutInflater());
        setContentView(activityMyAppointmentsBinding.getRoot());

        ApplicationImpl.getApp().getApplicationComponent().inject(this);
        auth = FirebaseAuth.getInstance();
        appointmentDates = new ArrayList<>();
        appointmentsTypes = new ArrayList<>();
        appointmentsPrices = new ArrayList<>();
        appointmentLocations = new ArrayList<>();
        petTypes = new ArrayList<>();
        petAges = new ArrayList<>();
        doctorNames = new ArrayList<>();
        pets = new ArrayList<>();
        appointments = new ArrayList<>();
        myAppointments = new ArrayList<>();
        latLng = new ArrayList<>();

        appointmentsListView = findViewById(R.id.appointmentListView);
        if (auth.getCurrentUser() != null) {
            clientEmail = auth.getCurrentUser().getEmail();
            new Thread(() -> {
                Client c = userViewModel.getClientByEmail(clientEmail);
                pets = (ArrayList<Animal>) animalViewModel.getAnimalsForOwner(c.getClientId());
                appointments = (ArrayList<Appointment>) appointmentViewModel.getAllAppointments();
                for(Animal a : pets){
                    for(Appointment app: appointments){
                           if(app.getAnimalId() == a.getAnimalId()){
                               myAppointments.add(app);
                           }
                    }
                }
                for(Appointment ap : myAppointments){
                    appointmentDates.add(ap.getDate());
                    AppointmentType apt = appointmentViewModel.getAppointmentTypeById(ap.getAppointmentTypeId());
                    appointmentsTypes.add(apt.getName());
                    appointmentsPrices.add(String.valueOf(apt.getPrice()));
                    Animal pet = animalViewModel.getAnimalById(ap.getAnimalId());
                    petTypes.add(pet.getType());
                    petAges.add(String.valueOf(pet.getAge()));
                    Doctor d = userViewModel.getDoctorById(ap.getDoctorId());
                    doctorNames.add(d.getFullName());
                    Location loc = locationViewModel.getLocationById(d.getLocationId());
                    appointmentLocations.add(loc.getCity() + " " + loc.getAddress());
                    latLng.add(loc.getLatitude() + " " + loc.getLongitude());

                }

                this.runOnUiThread(() ->{
                    clientAppointmentAdapter = new ClientAppointmentAdapter(this, petTypes, petAges, appointmentDates,
                            appointmentsTypes, appointmentsPrices, doctorNames, appointmentLocations, latLng);
                    appointmentsListView.setAdapter(clientAppointmentAdapter);

                });

            }).start();
        }
    }
}
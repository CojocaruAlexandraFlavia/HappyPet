package com.example.happypet.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happypet.R;
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
import com.example.happypet.util.MyApplication;

import java.util.Objects;

import javax.inject.Inject;

public class SeeAppointmentActivity extends AppCompatActivity {

    private Appointment appointment;
    private Doctor doctor;
    private Client client;
    private Animal pet;
    private AppointmentType appointmentType;
    private Location location;
    private TextView dateView, doctorView, petNameView, appointmentTypeView, clientDataView, locationView;

    @Inject
    AppointmentViewModel appointmentViewModel;

    @Inject
    UserViewModel userViewModel;

    @Inject
    AnimalViewModel animalViewModel;

    @Inject
    LocationViewModel locationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_appointment);

        MyApplication.getApp().getApplicationComponent().inject(this);

        long id = getIntent().getExtras().getLong("id");

        dateView = findViewById(R.id.appointment_date);
        doctorView = findViewById(R.id.appointment_doctor);
        petNameView = findViewById(R.id.appointment_pet_name);
        appointmentTypeView = findViewById(R.id.appointment_type);
        clientDataView = findViewById(R.id.appointment_client_data);
        locationView = findViewById(R.id.appointment_location);

        new Thread(() -> {
            appointment = appointmentViewModel.getAppointmentById(id);
            doctor = userViewModel.getDoctorById(appointment.getDoctorId());
            pet = animalViewModel.getAnimalById(appointment.getAnimalId());
            appointmentType = appointmentViewModel.getAppointmentTypeById(appointment.getAppointmentTypeId());
            client = userViewModel.getClientById(pet.getOwnerId());
            location = locationViewModel.getLocationById(doctor.getLocationId());

            String newTextDate = dateView.getText() + ": " + appointment.getDate();
            String newTextDoctor = doctorView.getText() + ": " + doctor.getFirstName() + " " + doctor.getLastName();
            String newTextPet = petNameView.getText() + ": " + pet.getName();
            String newTextAppointmentType = appointmentTypeView.getText() + ": " + appointmentType.getName();
            String newTextClientData = clientDataView.getText() + ": " + client.getFirstName() + " " + client.getLastName() + "\n " + "Email: " +
                    client.getEmail() + "\n" + "Telefon: " + client.getPhoneNumber();
            String newTextLocationView = locationView.getText() + ": " + location.getAddress() + ", " + location.getCity();

            dateView.setText(newTextDate);
            appointmentTypeView.setText(newTextAppointmentType);
            doctorView.setText(newTextDoctor);
            petNameView.setText(newTextPet);
            clientDataView.setText(newTextClientData);
            locationView.setText(newTextLocationView);

        }).start();

    }
}

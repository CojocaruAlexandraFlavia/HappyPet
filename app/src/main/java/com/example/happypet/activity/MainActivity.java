package com.example.happypet.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happypet.R;
import com.example.happypet.model.Doctor;
import com.example.happypet.model.Location;
import com.example.happypet.model.view_model.LocationViewModel;
import com.example.happypet.model.view_model.UserViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity{

    private UserViewModel userViewModel;
    private LocationViewModel locationViewModel;
    private List<Doctor> doctors;
    private List<Location> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView register = findViewById(R.id.register_link);
        Button seeProfile = findViewById(R.id.go_to_doctor_profile);

        new Thread(() -> {
            userViewModel = new UserViewModel(this.getApplication());
            //locationViewModel = new LocationViewModel(this.getApplication());
//            new Thread(() -> locationViewModel.insertLocation(new Location("Bucharest", "Bd Timisoara 63", "45.203575139935964", "26.040645287933796"))).start();
//            locations = locationViewModel.getAllLocations();
            new Thread(() -> {
                //userViewModel.insertDoctor(new Doctor("token", 1L));
                //doctors = userViewModel.getALlDoctors();
            });

        }).start();

        register.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        seeProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DoctorProfileActivity.class);
            intent.putExtra("previousIntent", "MainActivity");
            intent.putExtra("doctorId", 1L);
            startActivity(intent);
        });

    }

}
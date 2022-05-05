package com.example.happypet.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happypet.R;
import com.example.happypet.model.Client;
import com.example.happypet.model.Doctor;
import com.example.happypet.model.Location;
import com.example.happypet.model.view_model.LocationViewModel;
import com.example.happypet.model.view_model.UserViewModel;
import com.example.happypet.util.ApplicationImpl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

public class DoctorProfileActivity extends AppCompatActivity {

    private TextView doctorFirstNameView, doctorLastNameView, doctorEmailView, doctorLocation;
    private Button askForAppointment;

    private Client client;

    @Inject
    LocationViewModel locationViewModel;

    private Doctor doctorProfile;
    private long doctorId;
   // private ActivityDoctorProfileBinding activityDoctorProfileBinding;

    @Inject
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // activityDoctorProfileBinding = ActivityDoctorProfileBinding.inflate(getLayoutInflater());
       // setContentView(activityDoctorProfileBinding.getRoot());
        setContentView(R.layout.activity_doctor_profile);
        doctorFirstNameView = findViewById(R.id.doctor_first_name);
        doctorLastNameView = findViewById(R.id.doctor_last_name);
        doctorEmailView = findViewById(R.id.doctor_email);
        askForAppointment = findViewById(R.id.make_appointment_button);
        doctorLocation = findViewById(R.id.doctor_location);
        Button shareDoctorProfile = findViewById(R.id.share_doctor_profile);

        ApplicationImpl.getApp().getApplicationComponent().inject(this);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(getIntent().hasExtra("previousIntent")){
            System.out.println("has extra: " + getIntent().getExtras().getLong("doctorId"));
            doctorId = getIntent().getExtras().getLong("doctorId");
        }else{
            System.out.println("has not extra");
            Uri uri = getIntent().getData();
            String path = uri.toString();
            doctorId = Long.parseLong(path.split("=")[1]);
        }

        new Thread(() -> {
            System.out.println("user view model: " +userViewModel != null);
            doctorProfile = userViewModel.getDoctorById(doctorId);
            Location location = locationViewModel.getLocationById(doctorProfile.getLocationId());
            assert currentUser != null;
            client = userViewModel.getClientByEmail(currentUser.getEmail());
            this.runOnUiThread(() -> {
                doctorFirstNameView.setText(doctorProfile.getFirstName());
                doctorLastNameView.setText(doctorProfile.getLastName());
                doctorEmailView.setText(doctorProfile.getEmail());
                //doctorPhoneNumber.setText(doctorProfile.get);
                String locationText = location.getCity() + ", " + location.getAddress();
                doctorLocation.setText(locationText);
                if(client != null){
                    askForAppointment.setEnabled(true);
                }
            });
        }).start();

        shareDoctorProfile.setOnClickListener(v -> sendMessage("http://animalute-fericite-app.ro/?profile="+doctorId));

        askForAppointment.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddAppointmentActivity.class);
            intent.putExtra("doctorId", doctorProfile.getDoctorId());
            startActivity(intent);
        });
    }

    @JavascriptInterface
    public void sendMessage(String url) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Vezi profilul");
        shareIntent.putExtra(Intent.EXTRA_TEXT, url);
        startActivity(Intent.createChooser(shareIntent, "Alege"));
    }

}

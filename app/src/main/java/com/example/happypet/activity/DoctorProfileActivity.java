package com.example.happypet.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happypet.R;
import com.example.happypet.databinding.ActivityDoctorProfileBinding;
import com.example.happypet.model.Doctor;
import com.example.happypet.model.view_model.LocationViewModel;
import com.example.happypet.model.view_model.UserViewModel;
import com.example.happypet.util.ApplicationImpl;

import javax.inject.Inject;

public class DoctorProfileActivity extends AppCompatActivity {

    private TextView doctorFirstNameView, doctorLastNameView, doctorEmailView;

    @Inject
    LocationViewModel locationViewModel;

    private Doctor doctor;
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
        Button shareDoctorProfile = findViewById(R.id.share_doctor_profile);

        ApplicationImpl.getApp().getApplicationComponent().inject(this);

        if(getIntent().hasExtra("previousIntent")){
            doctorId = getIntent().getExtras().getLong("doctorId");
        }else{
            Uri uri = getIntent().getData();
            String path = uri.toString();
            doctorId = Long.parseLong(path.split("=")[1]);
        }

        new Thread(() -> {
            doctor = userViewModel.getDoctorById(doctorId);
            this.runOnUiThread(() -> {
                doctorFirstNameView.setText(doctor.getFirstName());
                doctorLastNameView.setText(doctor.getLastName());
                doctorEmailView.setText(doctor.getEmail());
            });
        }).start();

        shareDoctorProfile.setOnClickListener(v -> sendMessage("http://animalute-fericite-app.ro/?profile="+doctorId));
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

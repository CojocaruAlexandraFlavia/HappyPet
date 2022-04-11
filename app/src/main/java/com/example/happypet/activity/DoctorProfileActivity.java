package com.example.happypet.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.webkit.JavascriptInterface;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import com.example.happypet.R;
import com.example.happypet.model.Doctor;
import com.example.happypet.model.Location;
import com.example.happypet.model.view_model.LocationViewModel;
import com.example.happypet.model.view_model.UserViewModel;
import com.example.happypet.util.MyApplication;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class DoctorProfileActivity extends AppCompatActivity {

    private TextView doctorFirstNameView, doctorLastNameView, doctorEmailView;

    @Inject
    private LocationViewModel locationViewModel;

    private Doctor doctor;
    private long doctorId;

    @Inject
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        doctorFirstNameView = findViewById(R.id.doctor_first_name);
        doctorLastNameView = findViewById(R.id.doctor_last_name);
        doctorEmailView = findViewById(R.id.doctor_email);
        Button shareDoctorProfile = findViewById(R.id.share_doctor_profile);

        MyApplication.getApp().getApplicationComponent().inject(this);

        if(getIntent().hasExtra("previousIntent")){
            System.out.println("has extra");
            doctorId = getIntent().getExtras().getLong("doctorId");
        }else{
            System.out.println("has not extra");
            Uri uri = getIntent().getData();
            String path = uri.getPath();
            doctorId = Long.parseLong(path.split("=")[1]);

        }

        new Thread(() -> {
//            locationViewModel = new LocationViewModel(this.getApplication());
//            new Thread(() -> locationViewModel.insertLocation(new Location("Bucuresti", "Adresa", "45.256595904081074", "25.63166379563"))).start();
//            Doctor doctor1 = new Doctor();
//            doctor1.setLocationId(1L);
//            doctor1.setToken("token");
//            doctor1.setFirstName("Maria");
//            doctor1.setLastName("Florea");
//            doctor1.setEmail("florea@email.com");
//            userViewModel.insertDoctor(doctor1);
            doctor = userViewModel.getDoctorById(doctorId);
//            System.out.println("thread: " + doctor.getToken());
            this.runOnUiThread(() -> {
                doctorFirstNameView.setText(doctor.getFirstName());
                doctorLastNameView.setText(doctor.getLastName());
                doctorEmailView.setText(doctor.getEmail());

            });
        }).start();

        shareDoctorProfile.setOnClickListener(v -> {
            sendMessage("http://animalute-fericite-myapp.ro/?profile="+doctorId);
        });
    }

    @JavascriptInterface
    public void sendMessage(String url) {
        final Intent sendIntent = new Intent(android.content.Intent.ACTION_SEND);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        sendIntent.putExtra(Intent.EXTRA_TEXT, url);
        sendIntent.putExtra(Intent.EXTRA_INTENT, i);
        sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Profil");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Send to friend"));

//        Intent sendIntent = new Intent(Intent.ACTION_SEND);
//        //sendIntent.setDataAndType(Uri.parse(url), Intent.EXTRA_MIME_TYPES);
//        sendIntent.putExtra(Intent.EXTRA_TEXT, "TEST");
//        sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url));
//
//        Intent openIntent = new Intent(Intent.ACTION_VIEW);
//        //openIntent.setDataAndType(Uri.parse(url), Intent.EXTRA_MIME_TYPES);
//        openIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//        Intent chooserIntent = Intent.createChooser(sendIntent, "Alege");
//
//        List<Intent> intents = Collections.singletonList(openIntent);
//        Parcelable[] parcelables = intents.toArray(new Parcelable[1]);
//
//        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, parcelables);
//
//        startActivity(chooserIntent);
    }

}

package com.example.happypet.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import javax.inject.Inject;

public class DoctorProfileActivity extends AppCompatActivity {

    private TextView doctorNameView, doctorEmailView, doctorLocation;
    private Button askForAppointment;
    private LinearLayout buttonsWrapper, askForAppointmentWrapper;
    private ImageView profilePicture;

    private Client client;
    private Doctor doctorProfile;
    private long doctorId;

    @Inject
    LocationViewModel locationViewModel;

    @Inject
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // activityDoctorProfileBinding = ActivityDoctorProfileBinding.inflate(getLayoutInflater());
       // setContentView(activityDoctorProfileBinding.getRoot());
        setContentView(R.layout.activity_doctor_profile);

        doctorNameView = findViewById(R.id.client_name);
        doctorEmailView = findViewById(R.id.client_phone);
        askForAppointment = findViewById(R.id.make_appointment_button);
        doctorLocation = findViewById(R.id.doctor_location);
        buttonsWrapper = findViewById(R.id.doctor_profile_buttons_wrapper);
        askForAppointmentWrapper = findViewById(R.id.ask_for_appointment_wrapper);
        Button shareDoctorProfile = findViewById(R.id.edit_client_profile_button);
        profilePicture = findViewById(R.id.client_profile_picture);

        ApplicationImpl.getApp().getApplicationComponent().inject(this);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(getIntent().hasExtra("doctorId")){
            doctorId = getIntent().getExtras().getLong("doctorId");
        }else{
            Uri uri = getIntent().getData();
            String path = uri.toString();
            doctorId = Long.parseLong(path.split("=")[1]);
        }

        String uid = Objects.requireNonNull(currentUser).getUid();
        StorageReference ref = storage.getReference().child("/images/" + uid +".jpg");
        ref.getDownloadUrl()
                .addOnSuccessListener(uri -> profilePicture.setImageURI(uri))
                .addOnFailureListener(e -> profilePicture.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.android_profile_icon_3)));

        new Thread(() -> {
            doctorProfile = userViewModel.getDoctorById(doctorId);
            Location location = locationViewModel.getLocationById(doctorProfile.getLocationId());
            client = userViewModel.getClientByEmail(currentUser.getEmail());
            System.out.println("client: " + client.getFirstName());
            System.out.println("doctor: " + doctorProfile.getFirstName() + " " + doctorProfile.getLastName());
            String fullName = doctorProfile.getFirstName() + " " + doctorProfile.getLastName();
            this.runOnUiThread(() -> {
                doctorNameView.setText(fullName);
                doctorEmailView.setText(doctorProfile.getEmail());
                String locationText = location.getCity() + ", " + location.getAddress();
                doctorLocation.setText(locationText);
                if(client != null){
                    System.out.println("client != null");
                        askForAppointment.setEnabled(true);
                        buttonsWrapper.setWeightSum(2);
                }else {
                    System.out.println("client == null");
                        askForAppointmentWrapper.setEnabled(false);
                        buttonsWrapper.setWeightSum(1);
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

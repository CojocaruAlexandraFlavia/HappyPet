package com.example.happypet.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.happypet.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class DrawerBaseDoctorActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseFirestore fstore;
    private FirebaseStorage storage;
    private TextView userName;
    private ActionBarDrawerToggle toggle;
    private ImageView profilePic;

    @Override
    @SuppressLint({"InflateParams", "NonConstantResourceId"})
    public void setContentView(View contentView) {
        setContentView(R.layout.activity_drawer_base_doctor);

        LayoutInflater inflater = getLayoutInflater();
        DrawerLayout drawerLayout = (DrawerLayout) inflater.inflate(R.layout.activity_drawer_base_doctor, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(contentView);
        super.setContentView(drawerLayout);
        Toolbar toolbar = drawerLayout.findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_doctor_view);
        View headerView = navigationView.getHeaderView(0);

        auth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        userName = headerView.findViewById(R.id.user_name);
        profilePic = headerView.findViewById(R.id.nav_profile_pic);
        try {
            getUserData();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Intent intent = new Intent(getApplicationContext(), MyAppointmentsDoctorActivity.class);
        Intent intent3 = new Intent(getApplicationContext(), DoctorHomeActivity.class);

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.my_appointments:startActivity(intent);
                case R.id.nav_home:startActivity(intent);
                case R.id.settings:startActivity(intent);


            }
            return true;
        });

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void getUserData() throws IOException {
        String uid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        String path = "images/" + uid + ".jpg";
        StorageReference ref = storage.getReference().child(path);

        File localFile = File.createTempFile("ceva", "jpg");
        ref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                System.out.println(bitmap);
                profilePic.setImageBitmap(bitmap);
                userName.setText(auth.getCurrentUser().getDisplayName());
            }
        }).addOnFailureListener(exception -> System.out.println(exception.getMessage()));

    }
}
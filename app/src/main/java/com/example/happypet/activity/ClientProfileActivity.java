package com.example.happypet.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happypet.R;
import com.example.happypet.databinding.ActivityAddNewPetBinding;
import com.example.happypet.databinding.ActivityClientProfileBinding;
import com.example.happypet.databinding.ActivityHomeBinding;
import com.example.happypet.model.Client;
import com.example.happypet.model.view_model.UserViewModel;
import com.example.happypet.util.ApplicationImpl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import javax.inject.Inject;

public class ClientProfileActivity extends DrawerBaseActivity {

    private TextView clientName, clientPhoneNumber, clientEmail;
    private ImageView clientProfilePicture;

    @Inject
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityClientProfileBinding activityClientProfileBinding = ActivityClientProfileBinding.inflate(getLayoutInflater());
        setContentView(activityClientProfileBinding.getRoot());

        clientEmail = findViewById(R.id.client_email);
        clientName = findViewById(R.id.client_name);
        clientPhoneNumber = findViewById(R.id.client_phone);
        clientProfilePicture = findViewById(R.id.client_profile_picture);
        Button editProfileButton = findViewById(R.id.edit_client_profile_button);

        ApplicationImpl.getApp().getApplicationComponent().inject(this);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        String uid = Objects.requireNonNull(currentUser).getUid();
        StorageReference ref = storage.getReference().child("/images/" + uid +".jpg");
        ref.getDownloadUrl()
                .addOnSuccessListener(uri -> clientProfilePicture.setImageURI(uri))
                .addOnFailureListener(e -> clientProfilePicture.setImageBitmap(BitmapFactory.decodeResource(getResources(),
                        R.drawable.android_profile_icon_3)));

        new Thread(() -> {
            Client client = userViewModel.getClientByEmail(Objects.requireNonNull(currentUser).getEmail());
            String name = client.getFirstName() + " " + client.getLastName();
            this.runOnUiThread(() -> {
                clientName.setText(name);
                clientEmail.setText(client.getEmail());
                clientPhoneNumber.setText(client.getPhoneNumber());
            });
        }).start();

        editProfileButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, EditProfileActivity.class);
            startActivity(intent);
        });
    }
}


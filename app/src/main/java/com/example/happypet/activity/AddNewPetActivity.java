package com.example.happypet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.happypet.R;
import com.example.happypet.data.repository.PetRepository;
import com.example.happypet.data.repository.UserRepository;
import com.example.happypet.databinding.ActivityAddNewPetBinding;
import com.example.happypet.model.Animal;
import com.example.happypet.model.Client;
import com.example.happypet.model.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.regex.Pattern;

public class AddNewPetActivity extends DrawerBaseActivity {

    private EditText petName, petAge;
    private ImageView petImage;
    private Button catButton, dogButton, savePetButton;
    private FirebaseAuth auth;
    private int option = 0;
    private PetRepository petRepository;
    private UserRepository userRepository;
    ActivityAddNewPetBinding activityAddNewPetBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddNewPetBinding = ActivityAddNewPetBinding.inflate(getLayoutInflater());
        setContentView(activityAddNewPetBinding.getRoot());
        petRepository = new PetRepository(getApplication());
        userRepository = new UserRepository(getApplication());

        auth = FirebaseAuth.getInstance();
        petName = findViewById(R.id.editPetName);
        petAge = findViewById(R.id.editPetAge);
        catButton = findViewById(R.id.buttonCat);
        dogButton = findViewById(R.id.buttonDog);
        savePetButton = findViewById(R.id.savePetButton);

        catButton.setOnClickListener(view->{
            option = 1;
            catButton.setEnabled(false);
            dogButton.setEnabled(true);
        });
        dogButton.setOnClickListener(view -> {
            option = 2;
            catButton.setEnabled(true);
            dogButton.setEnabled(false);
        });

        savePetButton.setOnClickListener(view->{
            boolean validation = true;
            if(petName.getText().toString().isEmpty()) {
                petName.setError("Camp obligatoriu");
                validation = false;
            }

            if(petAge.getText().toString().isEmpty()) {
                petAge.setError("Camp obligatoriu");
                validation = false;
            }
            if(option == 0){
                Toast.makeText(AddNewPetActivity.this, "Selectati tip animal!",
                        Toast.LENGTH_SHORT).show();
                validation = false;
            }
            if(validation){
                Animal a = new Animal();
                a.setAge(Integer.parseInt(petAge.getText().toString()));
                a.setName(petName.getText().toString());
                if(option == 1){
                    a.setType(Animal.CAT);
                }else{
                    a.setType(Animal.DOG);
                }
                new Thread(() -> {
                    Client i = userRepository.getClientByEmail(Objects.requireNonNull(auth.getCurrentUser()).getEmail());

                    a.setOwnerId(i.getClientId());

                    petRepository.insertPet(a);
                }).start();

                System.out.println("animal inserat!");
                Intent intent = new Intent(AddNewPetActivity.this, MyPetsActivity.class);
                startActivity(intent);
            }

        });

    }


}
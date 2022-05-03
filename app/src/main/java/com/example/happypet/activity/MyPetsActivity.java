package com.example.happypet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.example.happypet.ProgramAdapter;
import com.example.happypet.R;
import com.example.happypet.data.repository.PetRepository;
import com.example.happypet.data.repository.UserRepository;
import com.example.happypet.databinding.ActivityMyPetsBinding;
import com.example.happypet.model.Animal;
import com.example.happypet.model.Client;
import com.example.happypet.model.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MyPetsActivity extends DrawerBaseActivity {

    private ActivityMyPetsBinding activityMyPetsBinding;
    private ListView lvPets;
    private ArrayList<String> petsNames;
    private ArrayList<String> petsTypes;
    private ArrayList<String> petsAges;
    private ArrayList<Integer> petImages;
    private PetRepository petRepository;
    private UserRepository userRepository;
    private List<Animal> currentUserPets;
    private  ProgramAdapter programAdapter;

    private FirebaseAuth auth;

    ///My pets activity va fi doar pentru clienti deci e ok getClient


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMyPetsBinding = ActivityMyPetsBinding.inflate(getLayoutInflater());
        setContentView(activityMyPetsBinding.getRoot());
        auth = FirebaseAuth.getInstance();
        lvPets = findViewById(R.id.petListView);

        new Thread(() -> {
            petRepository = new PetRepository(getApplication());
            userRepository = new UserRepository(getApplication());
            Client currentClient = userRepository.getClientByEmail(Objects.requireNonNull(auth.getCurrentUser()).getEmail());
            currentUserPets = petRepository.getAllPetsForOwner(currentClient.getClientId());
            if(currentUserPets.isEmpty()){
                System.out.println("e goala");
                Intent i = new Intent(MyPetsActivity.this, AddNewPetActivity.class);
                startActivity(i);
            }else{

                petsNames = new ArrayList<String>();
                petsAges = new ArrayList<String>();
                petsTypes = new ArrayList<String>();
                petImages = new ArrayList<Integer>();

                for (Animal a:currentUserPets) {
                    petsNames.add(a.getName());
                    petsAges.add(String.valueOf(a.getAge()));
                    petsTypes.add(a.getType());
                    if(a.getType().equals(Animal.CAT)){
                        petImages.add(R.mipmap.ic_cat);
                    }else{
                        petImages.add(R.drawable.ic_dog2);
                    }
                }



                runOnUiThread(() -> {
                    programAdapter = new ProgramAdapter(this, petsNames, petImages, petsAges);
                    lvPets.setAdapter(programAdapter);
                    System.out.println(lvPets.getAdapter().getCount());
                    System.out.println("in else");
                    System.out.println(currentUserPets.size());
                });

            }
        }).start();


    }

}
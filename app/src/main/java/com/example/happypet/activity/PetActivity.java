package com.example.happypet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.happypet.R;
import com.example.happypet.databinding.ActivityPetsBinding;
import com.example.happypet.model.Animal;
import com.example.happypet.model.view_model.PetViewModel;
import com.google.android.material.internal.NavigationMenuItemView;

import java.util.List;
import java.util.Objects;

public class PetActivity extends DrawerBaseActivity{

    private ImageButton imageButtonCat, imageButtonDog;

    private ActivityPetsBinding activityPetsBinding;
    PetViewModel petViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityPetsBinding = ActivityPetsBinding.inflate(getLayoutInflater());
        setContentView(activityPetsBinding.getRoot());
        imageButtonCat = findViewById(R.id.imageButton3);
        imageButtonDog = findViewById(R.id.imageButton4);
        imageButtonCat.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PetActivity.this, "PISICA ADAUGATA", Toast.LENGTH_SHORT).show();
            }
        }));
        imageButtonDog.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PetActivity.this, "CAINE ADAUGAT", Toast.LENGTH_SHORT).show();
            }
        }));

        new Thread(() ->{
            petViewModel = new PetViewModel(this.getApplication());
            List<Animal> pets = petViewModel.getAllPetsForOwner(1);
            for (Animal p:pets) {
                System.out.println(p);
            }
        }).start();


    }


}

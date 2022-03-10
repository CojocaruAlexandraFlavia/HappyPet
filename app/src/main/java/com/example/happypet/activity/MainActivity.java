package com.example.happypet.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.happypet.R;
import com.example.happypet.model.Animal;
import com.example.happypet.model.Client;
import com.example.happypet.model.view_model.PetViewModel;
import com.example.happypet.model.view_model.UserViewModel;
import com.google.android.material.internal.NavigationMenuItemView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private PetViewModel petViewModel;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(() ->{
            petViewModel = new PetViewModel(this.getApplication());
            Animal a = new Animal();
            a.setOwnerId(1);
            petViewModel.insertPet(a);
        }).start();

        MenuMethod();

    }

    private void MenuMethod() {
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return navigationMethod(item);
    }

    private boolean navigationMethod(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {

            NavigationMenuItemView profile = findViewById(R.id.nav_account);
            NavigationMenuItemView settings = findViewById(R.id.nav_settings);
            NavigationMenuItemView pets = findViewById(R.id.my_pets);
            NavigationMenuItemView logout = findViewById(R.id.nav_logout);
            profile.setOnClickListener(view -> {
                System.out.println("PROFIL");
                Intent i = new Intent(MainActivity.this, PetActivity.class);
                startActivity(i);
            });
            settings.setOnClickListener(view -> {
                System.out.println("SETARI");
                Intent i = new Intent(MainActivity.this, PetActivity.class);
                startActivity(i);
            });
            pets.setOnClickListener(view -> {
                System.out.println("ANIMALUTE");
                Intent i = new Intent(MainActivity.this, PetActivity.class);
                startActivity(i);
            });
            logout.setOnClickListener(view -> {
                System.out.println("LOGOUT");
                Intent i = new Intent(MainActivity.this, PetActivity.class);
                startActivity(i);
            });

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}
package com.example.happypet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.happypet.R;
import com.google.android.material.internal.NavigationMenuItemView;

import java.util.Objects;

public class PetActivity extends AppCompatActivity{
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets);


        MenuMethod();

    }

    public void MenuMethod() {
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

    public boolean navigationMethod(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {

            NavigationMenuItemView profile = findViewById(R.id.nav_account);
            NavigationMenuItemView settings = findViewById(R.id.nav_settings);
            NavigationMenuItemView pets = findViewById(R.id.my_pets);
            NavigationMenuItemView logout = findViewById(R.id.nav_logout);
            profile.setOnClickListener(view -> {
                System.out.println("PROFIL");
                Intent i = new Intent(PetActivity.this, PetActivity.class);
                startActivity(i);
            });
            settings.setOnClickListener(view -> {
                System.out.println("SETARI");
                Intent i = new Intent(PetActivity.this, PetActivity.class);
                startActivity(i);
            });
            pets.setOnClickListener(view -> {
                System.out.println("ANIMALUTE");
                Intent i = new Intent(PetActivity.this, PetActivity.class);
                startActivity(i);
            });
            logout.setOnClickListener(view -> {
                System.out.println("LOGOUT");
                Intent i = new Intent(PetActivity.this, PetActivity.class);
                startActivity(i);
            });

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}

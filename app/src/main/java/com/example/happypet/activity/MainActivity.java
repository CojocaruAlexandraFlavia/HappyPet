package com.example.happypet.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.happypet.R;
import com.example.happypet.model.Client;
import com.example.happypet.model.view_model.UserViewModel;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private UserViewModel userViewModel;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        new Thread(() -> {
            userViewModel = new UserViewModel(this.getApplication());
            List<Client> allClients = userViewModel.getAllClients();
            userViewModel.insertClient(new Client());
        }).start();

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {

            if (item.getItemId() == R.id.my_pets) {
                System.out.println("ai apasat pe animalute");
                Intent intent = new Intent(this, PetActivity.class);
                //based on item add info to intent
                System.out.println("ai apasat pe animalute");
                this.startActivity(intent);
                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }

}
package com.example.happypet.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.happypet.DoctorAdapter;
import com.example.happypet.R;
import com.example.happypet.data.repository.UserRepository;
import com.example.happypet.databinding.ActivityDoctorHomeBinding;
import com.example.happypet.model.Doctor;
import com.example.happypet.model.view_model.UserViewModel;
import com.example.happypet.util.ApplicationImpl;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

public class DoctorHomeActivity extends DrawerBaseDoctorActivity {

    private RecyclerView doctorRV;


    private FirebaseAuth auth;

    // variable for our adapter
    // class and array list
    private DoctorAdapter adapter;
    private ArrayList<Doctor> doctorArrayList;


    @Inject
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.happypet.databinding.ActivityDoctorHomeBinding activityDoctorHomeBinding = ActivityDoctorHomeBinding.inflate(getLayoutInflater());
        setContentView(activityDoctorHomeBinding.getRoot());
        ApplicationImpl.getApp().getApplicationComponent().inject(this);

        auth = FirebaseAuth.getInstance();

        doctorRV = findViewById(R.id.idDoctorsRV);
        buildRecyclerView();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // below line is to get our inflater
        MenuInflater inflater = getMenuInflater();

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.search_menu, menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        // getting search view of our item.
        SearchView searchView = (SearchView) searchItem.getActionView();

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });
        return true;
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<Doctor> filteredList = new ArrayList<>();

        // running a for loop to compare elements.
        for (Doctor item : doctorArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getFullName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredList);
        }
    }

    private void buildRecyclerView() {

        // below line we are creating a new array list
        doctorArrayList = new ArrayList<>();


        new Thread(() -> {
            doctorArrayList = (ArrayList<Doctor>) userViewModel.getAllDoctors();
            ArrayList<Doctor> filteredList = new ArrayList<>();
            for(Doctor d : doctorArrayList ){
                if(!d.getEmail().equals(Objects.requireNonNull(auth.getCurrentUser()).getEmail())){
                    filteredList.add(d);
                }
            }
            runOnUiThread(()->{
                adapter = new DoctorAdapter(filteredList, DoctorHomeActivity.this);


                // adding layout manager to our recycler view.
                LinearLayoutManager manager = new LinearLayoutManager(this);
                doctorRV.setHasFixedSize(true);

                // setting layout manager
                // to our recycler view.
                doctorRV.setLayoutManager(manager);

                // setting adapter to
                // our recycler view.
                doctorRV.setAdapter(adapter);

            });

        }).start();



    }
}
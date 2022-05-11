package com.example.happypet.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happypet.DoctorAdapter;
import com.example.happypet.R;
import com.example.happypet.data.repository.UserRepository;
import com.example.happypet.databinding.ActivityHomeBinding;
import com.example.happypet.model.Doctor;
import com.example.happypet.model.view_model.AnimalViewModel;
import com.example.happypet.model.view_model.AppointmentViewModel;
import com.example.happypet.model.view_model.LocationViewModel;
import com.example.happypet.util.ApplicationImpl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import javax.inject.Inject;

;

public class HomeActivity extends DrawerBaseActivity {

    private RecyclerView doctorRV;
    //private UserViewModel userViewModel;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    // variable for our adapter
    // class and array list
    private DoctorAdapter adapter;
    private ArrayList<Doctor> doctorArrayList;

    @Inject
    UserRepository userViewModel;

    @Inject
    LocationViewModel locationViewModel;

    @Inject
    AppointmentViewModel appointmentViewModel;

    @Inject
    AnimalViewModel animalViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding activityHomeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(activityHomeBinding.getRoot());

        ApplicationImpl.getApp().getApplicationComponent().inject(this);

        doctorRV = findViewById(R.id.idDoctorsRV);
        buildRecyclerView();

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        new Thread(() -> {

//            Location location = new Location();
//            location.setCity("Bucharest");
//            location.setAddress("Str. Tineretului 63");
//            location.setLatitude("44.444952979474735");
//            location.setLongitude("25.988493872868826");
//            locationViewModel.insertLocation(location);
//
//            Doctor doctor = new Doctor();
//            doctor.setFirstName("Ion");
//            doctor.setLastName("Ion");
//            doctor.setEmail("ion@gmail.com");
//            doctor.setLocationId(1);
//            userViewModel.insertDoctor(doctor);

//            AppointmentType appointmentType = new AppointmentType();
//            appointmentType.setPrice(10.25);
//            appointmentType.setName("tuns");
//            appointmentViewModel.insertAppointmentType(appointmentType);
//
//            AppointmentType appointmentType1 = new AppointmentType();
//            appointmentType1.setPrice(50.50);
//            appointmentType1.setName("consultatie");
//            appointmentViewModel.insertAppointmentType(appointmentType1);

//            Animal animal = new Animal();
//            animal.setOwnerId(1);
//            animal.setName("Rex");
//            animal.setAge(3);
//            animal.setType(Animal.DOG);
//            animalViewModel.insertAnimal(animal);

//            Animal animal1 = new Animal();
//            animal1.setOwnerId(6);
//            animal1.setName("Miau");
//            animal1.setAge(3);
//            animal1.setType(Animal.CAT);
//            animalViewModel.insertAnimal(animal1);

        }).start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
        return true;
    }

    private void filter(String text) {

        ArrayList<Doctor> filteredList = new ArrayList<>();

        for (Doctor item : doctorArrayList) {
            if (item.getFullName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            adapter.filterList(filteredList);
        }
    }

    private void buildRecyclerView() {

        doctorArrayList = new ArrayList<>();

        new Thread(() -> {
            Doctor doctor = userViewModel.getDoctorByEmail(currentUser.getEmail());
            doctorArrayList = (ArrayList<Doctor>) userViewModel.getAllDoctors();
            if(doctor != null){
                doctorArrayList.remove(doctor);
            }

            this.runOnUiThread(()->{
                adapter = new DoctorAdapter(doctorArrayList, HomeActivity.this);

                LinearLayoutManager manager = new LinearLayoutManager(this);
                doctorRV.setHasFixedSize(true);

                doctorRV.setLayoutManager(manager);

                doctorRV.setAdapter(adapter);

            });

        }).start();

        //take all doctors here

        // below line is to add data to our array list.


        // initializing our adapter class.

    }



}
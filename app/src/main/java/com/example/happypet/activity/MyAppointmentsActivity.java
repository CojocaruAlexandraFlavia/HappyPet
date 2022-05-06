package com.example.happypet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.happypet.R;
import com.example.happypet.databinding.ActivityMyAppointmentsBinding;

public class MyAppointmentsActivity extends DrawerBaseActivity {


    ActivityMyAppointmentsBinding activityMyAppointmentsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMyAppointmentsBinding =  ActivityMyAppointmentsBinding.inflate(getLayoutInflater());
        setContentView(activityMyAppointmentsBinding.getRoot());
    }
}
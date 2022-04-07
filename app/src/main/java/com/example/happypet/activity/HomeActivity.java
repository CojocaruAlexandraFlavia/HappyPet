package com.example.happypet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.happypet.R;
import com.example.happypet.databinding.ActivityHomeBinding;
;

public class HomeActivity extends DrawerBaseActivity {

    private ActivityHomeBinding activityHomeBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(activityHomeBinding.getRoot());
    }
}
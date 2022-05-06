package com.example.happypet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.happypet.R;

public class ChooseRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_register);
        Button clientButton = findViewById(R.id.buttonClient);
        Button doctorButton = findViewById(R.id.buttonDoctor);

        clientButton.setOnClickListener(view -> {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        });
        doctorButton.setOnClickListener(view->{
            Intent t = new Intent(this, DoctorRegisterActivity.class);
            startActivity(t);
        });


    }
}
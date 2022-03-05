package com.example.happypet.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happypet.R;
import com.example.happypet.model.Client;
import com.example.happypet.model.view_model.UserViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(() -> {
            userViewModel = new UserViewModel(this.getApplication());
            List<Client> allClients = userViewModel.getAllClients();
            userViewModel.insertClient(new Client());
        }).start();

    }
}
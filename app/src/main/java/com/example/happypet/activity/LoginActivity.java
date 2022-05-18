package com.example.happypet.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happypet.R;

import com.example.happypet.model.Animal;
import com.example.happypet.model.AppointmentType;
import com.example.happypet.model.Client;
import com.example.happypet.model.Location;
import com.example.happypet.model.Token;
import com.example.happypet.model.view_model.AnimalViewModel;
import com.example.happypet.model.view_model.AppointmentViewModel;
import com.example.happypet.model.view_model.LocationViewModel;
import com.example.happypet.model.view_model.TokenViewModel;
import com.example.happypet.model.view_model.UserViewModel;
import com.example.happypet.util.ApplicationImpl;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText email, password;
    private CallbackManager mCallbackManager;
    private TextView textNoAccount;
    private TextView textRegisterAsDoctor;
    @Inject
    UserViewModel userViewModel;

    @Inject
    AnimalViewModel animalViewModel;

    @Inject
    LocationViewModel locationViewModel;

    @Inject
    AppointmentViewModel appointmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.edit_email);
        password = findViewById(R.id.edit_password);
        textNoAccount = findViewById(R.id.noAccountTextView);
        textRegisterAsDoctor = findViewById(R.id.doctorRegister);
        Button loginButton = findViewById(R.id.login_button);

        auth = FirebaseAuth.getInstance();
        Intent home = new Intent(this, HomeActivity.class);
        Intent doctorHome = new Intent(this, DoctorHomeActivity.class);

        ApplicationImpl.getApp().getApplicationComponent().inject(this);

        mCallbackManager = CallbackManager.Factory.create();
        LoginButton fbLoginButton = findViewById(R.id.button_facebook_login);
        fbLoginButton.setPermissions("email", "public_profile");
        fbLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel(){;
            }

            @Override
            public void onError(@NonNull FacebookException error) {

            }
        });
        // [END initialize_fblogin]

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                email.setError("Campul nu poate fi gol ");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()))
                    email.setError("Email invalid!");
            }
        });


        new Thread(() ->{

            AppointmentType a = new AppointmentType();
            a.setName("CONTROL");
            a.setPrice(200.0);
            appointmentViewModel.insertAppointmentType(a);
            AppointmentType b = new AppointmentType();
            b.setName("INTERVENȚIE");
            b.setPrice(300.0);
            appointmentViewModel.insertAppointmentType(b);


        }).start();


        textNoAccount.setOnClickListener(view ->{
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        });
        textRegisterAsDoctor.setOnClickListener(view ->{
            Intent i = new Intent(this, DoctorRegisterActivity.class);
            startActivity(i);
        });

        loginButton.setOnClickListener(view -> {
            if(!(email.getText().toString().isEmpty()) && email.getError() == null) {
                auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        System.out.println("user logat cu succes");

                        new Thread(() -> {
                            if(userViewModel.findDoctorByEmail(email.getText().toString()) !=null){
                                startActivity(doctorHome);
                            }else{
                                if(userViewModel.getClientByEmail(email.getText().toString())!=null){
                                    startActivity(home);
                                }

                            }

                        }).start();


                    }
                    else{
                        String errorCode = ((FirebaseAuthException) Objects.requireNonNull(task.getException())).getErrorCode();
                        switch (errorCode){
                            case "ERROR_INVALID_EMAIL":
                                Toast.makeText(LoginActivity.this, "Email invalid.", Toast.LENGTH_LONG).show();
                                email.setError("Email invalid!");
                                email.requestFocus();
                                break;
                            case "ERROR_WRONG_PASSWORD":
                                Toast.makeText(LoginActivity.this, "Parola gresita.", Toast.LENGTH_LONG).show();
                                password.setError("Parola gresita!");
                                password.requestFocus();

                                break;
                            case "ERROR_USER_NOT_FOUND":
                                Toast.makeText(LoginActivity.this, "User inexistent", Toast.LENGTH_LONG).show();
                                break;
                        }

                    }
                });

            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        checkUserType();
//        updateUI(currentUser);

    }

    private void checkUserType() {
        FirebaseUser currentUser = auth.getCurrentUser();
        Intent home = new Intent(this, HomeActivity.class);
        Intent doctorHome = new Intent(this, DoctorHomeActivity.class);
        if (currentUser!= null){

            new Thread(() -> {
                if(userViewModel.findDoctorByEmail(email.getText().toString()) !=null){
                    startActivity(doctorHome);
                }else{
                    if(userViewModel.getClientByEmail(email.getText().toString())!=null){
                        startActivity(home);
                    }
                }

            }).start();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
    private void handleFacebookAccessToken(AccessToken token) {


        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                    } else {
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateUI(FirebaseUser user) {

    }
}
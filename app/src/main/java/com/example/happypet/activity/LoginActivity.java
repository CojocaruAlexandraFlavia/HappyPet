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
import com.example.happypet.databinding.ActivityLoginBinding;
import com.example.happypet.databinding.ActivityRegisterBinding;
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

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText email, password;
    private CallbackManager mCallbackManager;
    private TextView textNoAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.edit_email);
        password = findViewById(R.id.edit_password);
        textNoAccount = findViewById(R.id.noAccountTextView);
        Button loginButton = findViewById(R.id.login_button);

        auth = FirebaseAuth.getInstance();
        Intent home = new Intent(this, HomeActivity.class);

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

        textNoAccount.setOnClickListener(view ->{
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        });

        loginButton.setOnClickListener(view -> {
            if(!(email.getText().toString().isEmpty()) && email.getError() == null) {
                auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            System.out.println("user logat cu succes");
                            startActivity(home);

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
                    }
                });

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser!= null){
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
        }
        updateUI(currentUser);

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
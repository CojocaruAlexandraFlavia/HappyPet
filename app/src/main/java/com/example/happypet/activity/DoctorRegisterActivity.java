package com.example.happypet.activity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.happypet.R;
import com.example.happypet.model.Doctor;
import com.example.happypet.model.Token;
import com.example.happypet.model.enums.PasswordStrength;
import com.example.happypet.model.view_model.TokenViewModel;
import com.example.happypet.model.view_model.UserViewModel;
import com.example.happypet.util.ApplicationImpl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import javax.inject.Inject;

public class DoctorRegisterActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private EditText tokenEditTExt, lastNameEditText, firstNameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private ProgressBar passwordStrengthBar;
    private ImageView profilePhotoView;
    private String uid;
    private Uri imageURI;
    int SELECT_PICTURE = 200;
    private FirebaseAuth auth;
    private FirebaseFirestore fstore;

    private FirebaseStorage storage;

    @Inject
    UserViewModel userViewModel;
    @Inject
    TokenViewModel tokenViewModel;

    private final String value = "doctorRegister";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_doctor_register);

        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        ApplicationImpl.getApp().getApplicationComponent().inject(this);


        lastNameEditText = findViewById(R.id.editLastName);
        firstNameEditText = findViewById(R.id.editFirstName);
        emailEditText = findViewById(R.id.edit_email);
        passwordEditText = findViewById(R.id.edit_password);
        Button registerButton = findViewById(R.id.registerButton);
        confirmPasswordEditText = findViewById(R.id.editConfirmPassword);
        passwordStrengthBar = findViewById(R.id.password_strength_bar_register);
        profilePhotoView = findViewById(R.id.profile_pic);
        tokenEditTExt =findViewById(R.id.edit_token);

        Button galleryButton = findViewById(R.id.button_galerie);
        Button cameraButton = findViewById(R.id.button_camera);


        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                PasswordStrength passwordStrength = PasswordStrength.calculate(charSequence.toString());
                int color = passwordStrength.color;
                String message = passwordStrength.message;
                passwordStrengthBar.setProgressTintList(ColorStateList.valueOf(color));
                System.out.println("color: " + color);
                System.out.println("message: " + message);
                switch (message){
                    case "WEAK":{
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            passwordStrengthBar.setProgress(25, true);
                        }
                        break;
                    }
                    case "MEDIUM": {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            passwordStrengthBar.setProgress(50, true);
                        }
                        break;
                    }
                    case "STRONG": {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            passwordStrengthBar.setProgress(75, true);
                        }
                        break;
                    }
                    case "VERY STRONG": {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            passwordStrengthBar.setProgress(100, true);
                        }
                        break;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        galleryButton.setOnClickListener(view -> galleryImageChooser());

        ActivityResultLauncher<Intent> takePhoto = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Bundle extras = Objects.requireNonNull(result.getData()).getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imageURI = getImageUri(getApplicationContext(), imageBitmap);

                    profilePhotoView.setImageBitmap(imageBitmap);
                });

        cameraButton.setOnClickListener(view -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            String[] PERMISSIONS = {Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions(this, PERMISSIONS, 0);

            takePhoto.launch(takePictureIntent);
        });

        registerButton.setOnClickListener(v -> {
            String token  = tokenEditTExt.getText().toString();
            String firstName = firstNameEditText.getText().toString();
            String lastName = lastNameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();
            String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
            AtomicBoolean validation = new AtomicBoolean(true);


            if(token.isEmpty()){
                tokenEditTExt.setError("Camp obligatoriu!");
                validation.set(false);
            }
            if(firstName.isEmpty()){
                firstNameEditText.setError("Camp obligatoriu");
                validation.set(false);
            }
            if(lastName.isEmpty()){
                lastNameEditText.setError("Camp obligatoriu");
                validation.set(false);
            }

            if(email.isEmpty()){
                emailEditText.setError("Camp obligatoriu");
                validation.set(false);
            }

            else if(!Pattern.compile(emailRegex).matcher(email).matches()){
                emailEditText.setError("Email invalid");
                validation.set(false);
            }
            if(password.isEmpty()){
                passwordEditText.setError("Camp obligatoriu");
                validation.set(false);
            }

            if(confirmPassword.isEmpty()){
                confirmPasswordEditText.setError("Camp obligatoriu");
                validation.set(false);
            } else if(!password.trim().equals(confirmPassword.trim())){
                confirmPasswordEditText.setError("Confirmare esuata");
                validation.set(false);
            }


            new Thread(() -> {
                String tokenVal = tokenEditTExt.getText().toString();
                Token t = tokenViewModel.findByTokenValue(tokenVal);

                if(t == null){
                    validation.set(false);
                }

                if(String.valueOf(validation.get()).equals("true")){
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, task -> {
                                if (task.isSuccessful()) {

                                    FirebaseUser user = auth.getCurrentUser();
                                    assert user != null;
                                    uid = user.getUid();
                                    if (null != imageURI) {
                                        StorageReference ref = storage.getReference().child("/images/" + user.getUid() +".jpg");
                                        ref.putFile(imageURI).addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().addOnSuccessListener(uri -> System.out.println("file location" + uri)));
                                    }
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(firstName + " " + lastName)
                                            .setPhotoUri(imageURI)
                                            .build();
                                    user.updateProfile(profileUpdates);


                                        new Thread(() -> {
                                            Log.d(value, "NewThread2");
                                            if(userViewModel == null){
                                                Log.d(value, "isNullSecondthread");
                                            }


                                            Doctor d = new Doctor();
                                            if(imageURI != null)
                                            {
                                                d.setPhotoPath(imageURI.toString());
                                            }
                                            d.setEmail(emailEditText.getText().toString());
                                            d.setFirstName(firstNameEditText.getText().toString());
                                            d.setLastName(lastNameEditText.getText().toString());
                                            assert t != null;
                                            d.setToken(t.getTokenValue());
                                            d.setLocationId(1);
                                            d.setPassword(BCrypt.hashpw(passwordEditText.getText().toString(), BCrypt.gensalt()));

                                            if(userViewModel == null){
                                                Log.d("userviewmodel", "e null");
                                            }

                                            userViewModel.insertDoctor(d);
                                            System.out.println("doctor inserat");

                                            fstore.collection("users").document(uid).set(d).addOnCompleteListener(task1 -> System.out.println("A mers"));

                                            Intent i = new Intent(DoctorRegisterActivity.this, LoginActivity.class);
                                            startActivity(i);


                                        }).start();


                                } else {
                                    System.out.println(Objects.requireNonNull(task.getException()).getMessage());
                                    Toast.makeText(DoctorRegisterActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            });

                }else{
                    this.runOnUiThread(()-> tokenEditTExt.setError("TOKEN INVALID"));

                }

            }).start();


        });
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        Bitmap OutImage = Bitmap.createScaledBitmap(inImage, 1000, 1000,true);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), OutImage, "Title", null);
        return Uri.parse(path);
    }



    private void galleryImageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_PICK);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {
                imageURI = data.getData();
                profilePhotoView.setImageURI(imageURI);

            }
        }
    }

}
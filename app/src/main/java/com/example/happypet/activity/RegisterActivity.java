package com.example.happypet.activity;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.happypet.R;
import com.example.happypet.model.Client;
import com.example.happypet.model.enums.PasswordStrength;
import com.example.happypet.model.view_model.UserViewModel;
import com.example.happypet.util.FilesUtils;
import com.example.happypet.util.MyApplication;
import com.example.happypet.util.PermissionUtils;

import org.apache.commons.io.FileUtils;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.inject.Inject;

public class RegisterActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private EditText lastNameEditText, firstNameEditText, emailEditText, passwordEditText, confirmPasswordEditText, phoneEditText;
    private ProgressBar passwordStrengthBar;
    private ImageView profilePhotoView, test;
    private String uploadedFilePath = "";

    private static final int TAKE_PHOTO = 1;
    private static final int CHOOSE_PHOTO_FROM_GALLERY = 2;

    @Inject
    UserViewModel userViewModel;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        MyApplication.getApp().getApplicationComponent().inject(this);

        lastNameEditText = findViewById(R.id.last_name_register);
        firstNameEditText = findViewById(R.id.first_name_register);
        emailEditText = findViewById(R.id.email_register);
        passwordEditText = findViewById(R.id.password_register);
        phoneEditText = findViewById(R.id.phone_register);
        Button registerButton = findViewById(R.id.button_create_account);
        confirmPasswordEditText = findViewById(R.id.confirm_password_register);
        passwordStrengthBar = findViewById(R.id.password_strength_bar_register);
        profilePhotoView = findViewById(R.id.profile_photo_register);
        Button addProfilePictureButton = findViewById(R.id.select_profile_picture_register);
        test = findViewById(R.id.test);
        Button cameraButton = findViewById(R.id.camera_regsiter);

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

        ActivityResultLauncher<Intent> getChosenPhoto = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Uri selected = Objects.requireNonNull(data).getData();
                        profilePhotoView.setImageURI(selected);

                        File file = new File(selected.getPath());//create path from uri
                        final String[] split = file.getPath().split(":");//split the path.
                        uploadedFilePath = split[1];

                        System.out.println("uploaded file:" + uploadedFilePath);
                        System.out.println("uri: " + selected);
                        System.out.println("uri path: " + selected.getPath());

                        String filePath = FilesUtils.getPath(getApplicationContext(), selected);
                        String fileName = FilesUtils.getFileName(selected);
                        System.out.println("file path: " + filePath);
                            new Thread(() -> {
                                try {
                                    insertInPrivateStorage(fileName, filePath);
                                    getFromInternalStorage(fileName);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }).start();
                        }
                    });

        addProfilePictureButton.setOnClickListener(view -> {

            PermissionUtils.requestPermission(this, 1,
                    Manifest.permission.READ_EXTERNAL_STORAGE, true);

//            ActivityCompat.requestPermissions(this, PERMISSIONS, 0);
            Intent gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            getChosenPhoto.launch(Intent.createChooser(gallery, "Selectati poza"));
        });

        ActivityResultLauncher<Intent> takePhoto = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Bundle extras = Objects.requireNonNull(result.getData()).getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    test.setImageBitmap(imageBitmap);
                });

        cameraButton.setOnClickListener(view -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePhoto.launch(takePictureIntent);
        });

        registerButton.setOnClickListener(v -> {
            String firstName = firstNameEditText.getText().toString();
            String lastName = lastNameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();
            String phoneNumber = phoneEditText.getText().toString();
            String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
            String phoneRegex = "^\\d{10}$";
            boolean validation = true;
            if(firstName.isEmpty()){
                firstNameEditText.setError("Camp obligatoriu");
                validation = false;
            }
            if(lastName.isEmpty()){
                lastNameEditText.setError("Camp obligatoriu");
                validation = false;
            }

            if(email.isEmpty()){
                emailEditText.setError("Camp obligatoriu");
                validation = false;
            }

            else if(!Pattern.compile(emailRegex).matcher(email).matches()){
                emailEditText.setError("Email invalid");
                validation = false;
            }
            if(password.isEmpty()){
                passwordEditText.setError("Camp obligatoriu");
                validation = false;
            }

            if(phoneNumber.isEmpty()) {
                phoneEditText.setError("Camp obligatoriu");
                validation = false;
            } else if(Pattern.compile(phoneRegex).matcher(phoneNumber).matches()){
                phoneEditText.setError("Numar de telefon invalid");
                validation = false;
            }

            if(confirmPassword.isEmpty()){
                confirmPasswordEditText.setError("Camp obligatoriu");
                validation = false;
            } else if(!password.trim().equals(confirmPassword.trim())){
                confirmPasswordEditText.setError("Confirmare esuata");
                validation = false;
            }
            if(validation){
                new Thread(() -> {
                    //userViewModel = new UserViewModel(this.getApplication());
                    boolean existingEmail = userViewModel.findUserByEmail(email);
                    new Thread(() -> {
                        if(existingEmail) {
                            runOnUiThread(() -> emailEditText.setError("Adresa de mail exista"));
                        }else{
                            Client client = new Client();
                            client.setPhoneNumber(phoneNumber);
                            client.setEmail(email);
                            client.setFirstName(firstName);
                            client.setLastName(lastName);
                            client.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
                            client.setPhotoPath(uploadedFilePath);
                            userViewModel.insertClient(client);
                            runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Cont creat cu succes!", Toast.LENGTH_SHORT).show());

                        }
                    }).start();
                }).start();
            }
        });

    }


    private void insertInPrivateStorage(String fileName, String filePath) throws IOException {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("Profile photos", Context.MODE_PRIVATE);
        File file = new File(directory, fileName);
        if (!file.exists()) {
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(file);
                fos.write(FileUtils.readFileToByteArray(file));
                //fos.flush();
                fos.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getFromInternalStorage(String fileName){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("Profile photos", Context.MODE_PRIVATE);
        File file = new File(directory, fileName);
        test.setImageDrawable(Drawable.createFromPath(file.toString()));
    }

}

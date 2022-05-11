package com.example.happypet.activity;


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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.happypet.R;
import com.example.happypet.model.Client;
import com.example.happypet.model.enums.PasswordStrength;
import com.example.happypet.model.view_model.UserViewModel;
import com.example.happypet.util.ApplicationImpl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;
import java.util.regex.Pattern;

import javax.inject.Inject;

public class RegisterActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private EditText lastNameEditText, firstNameEditText, emailEditText, passwordEditText, confirmPasswordEditText, phoneEditText;
    private ProgressBar passwordStrengthBar;
    private ImageView profilePhotoView;
    private String uid;
    private Button registerButton, cameraButton, galleryButton;
    private Uri imageURI;
    int SELECT_PICTURE = 200;
    private FirebaseAuth auth;
    private FirebaseFirestore fstore;

    private FirebaseStorage storage;

    @Inject
    UserViewModel userViewModel;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        ApplicationImpl.getApp().getApplicationComponent().inject(this);

        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        lastNameEditText = findViewById(R.id.editLastName);
        firstNameEditText = findViewById(R.id.editFirstName);
        emailEditText = findViewById(R.id.edit_email);
        passwordEditText = findViewById(R.id.edit_password);
        phoneEditText = findViewById(R.id.editPhoneNumber);
        registerButton = findViewById(R.id.registerButton);
        confirmPasswordEditText = findViewById(R.id.editConfirmPassword);
        passwordStrengthBar = findViewById(R.id.password_strength_bar_register);
        profilePhotoView = findViewById(R.id.profile_pic);


        galleryButton = findViewById(R.id.button_galerie);
        cameraButton = findViewById(R.id.button_camera);

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
//                System.out.println("color: " + color);
//                System.out.println("message: " + message);
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

//        ActivityResultLauncher<Intent> getChosenPhoto = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        Intent data = result.getData();
//                        Uri selected = Objects.requireNonNull(data).getData();
//                        profilePhotoView.setImageURI(selected);
//
//                        File file = new File(selected.getPath());//create path from uri
//                        final String[] split = file.getPath().split(":");//split the path.
//                        uploadedFilePath = split[1];
//
//                        System.out.println("uploaded file:" + uploadedFilePath);
//                        System.out.println("uri: " + selected);
//                        System.out.println("uri path: " + selected.getPath());
//
//                        String filePath = FilesUtils.getPath(getApplicationContext(), selected);
//                        String fileName = FilesUtils.getFileName(selected);
//                        System.out.println("file path: " + filePath);
//                            new Thread(() -> {
//                                try {
//                                    insertInPrivateStorage(fileName, filePath);
//                                    getFromInternalStorage(fileName);
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }).start();
//                        }
//                    });

        galleryButton.setOnClickListener(view -> {

           galleryImageChooser();
        });

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
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = auth.getCurrentUser();
                                assert user != null;
                                uid = user.getUid();
                                if (null != imageURI) {
                                    StorageReference ref = storage.getReference().child("/images/" + user.getUid() +".jpg");
                                    ref.putFile(imageURI).addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            System.out.println("file location" + uri);
                                        }
                                    }));

                                }

                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(firstName + " " + lastName)
                                                .setPhotoUri(imageURI)
                                                .build();
                                user.updateProfile(profileUpdates);

                                new Thread(() -> {
                                    //userViewModel = new UserViewModel(this.getApplication());

                                    new Thread(() -> {

                                        Client c = new Client();
                                        if(imageURI != null)
                                        {
                                            c.setPhotoPath(imageURI.toString());
                                        }
                                        c.setEmail(emailEditText.getText().toString());
                                        c.setFirstName(firstNameEditText.getText().toString());
                                        c.setLastName(lastNameEditText.getText().toString());
                                        c.setPhoneNumber(phoneEditText.getText().toString());
                                        c.setPassword(BCrypt.hashpw(passwordEditText.getText().toString(), BCrypt.gensalt()));
                                        userViewModel.insertClient(c);

//                                        fstore.collection("users").document(uid).set(c)
//                                                .addOnCompleteListener(task1 -> System.out.println("A mers"));
                                        fstore.collection("users").document(uid).set(c).addOnCompleteListener(task1 -> System.out.println("A mers"));
                                    }).start();
                                }).start();
                                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(i);
                            } else {
                                System.out.println(Objects.requireNonNull(task.getException()).getMessage());
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        Bitmap OutImage = Bitmap.createScaledBitmap(inImage, 1000, 1000,true);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), OutImage, "Title", null);
        return Uri.parse(path);
    }

    private void saveUserToFirebase(){ }

    private void galleryImageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //String uid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {
                imageURI = data.getData();
                profilePhotoView.setImageURI(imageURI);

            }
        }
    }

}

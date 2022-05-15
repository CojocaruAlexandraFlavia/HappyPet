package com.example.happypet.activity;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.happypet.R;
import com.example.happypet.model.Animal;
import com.example.happypet.model.Appointment;
import com.example.happypet.model.AppointmentType;
import com.example.happypet.model.Client;
import com.example.happypet.model.Doctor;
import com.example.happypet.model.Location;
import com.example.happypet.model.view_model.AnimalViewModel;
import com.example.happypet.model.view_model.AppointmentViewModel;
import com.example.happypet.model.view_model.LocationViewModel;
import com.example.happypet.model.view_model.UserViewModel;
import com.example.happypet.util.ApplicationImpl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class AddAppointmentActivity extends AppCompatActivity {

    private final Calendar calendar = Calendar.getInstance();
    private long selectedDoctorId = 0, doctorId;
    private long selectedAppointmentTypeId = 0;
    private long selectedPetId = 0;
    private List<Location> locations;
    //private List<Doctor> doctorsFromSelectedLocation;
    private List<AppointmentType> appointmentTypes;
    private List<Animal> clientPets;
    private List<String> filledIntervals;
    private Client client;
    private long savedAppointment;
    private final String notificationChannelId = "1";
    //private Spinner selectDoctorDropdown;
    private EditText appointmentDate;
    private LinearLayout availableIntervals;
    private final List<String> possibleAppointmentHours = Arrays.asList("09:00", "09:30", "10:00", "10:30", "11:00",
            "11:30", "12:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30");
    private Doctor doctor;
    private boolean validated = true;
    private Spinner availableHoursForDateDropdown;
    private TextView selectDateError;

    @Inject
    UserViewModel userViewModel;

    @Inject
    LocationViewModel locationViewModel;

    @Inject
    AppointmentViewModel appointmentViewModel;

    @Inject
    AnimalViewModel animalViewModel;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        ApplicationImpl.getApp().getApplicationComponent().inject(this);

        Button addAppointment = findViewById(R.id.button_add_appointment);
        //Spinner selectLocationDropdown = findViewById(R.id.select_location_dropdown);
        Spinner selectAppointmentTypeDropdown = findViewById(R.id.select_appointment_type_dropdown);
        Spinner selectPetDropdown = findViewById(R.id.select_animal_dropdown);
        availableHoursForDateDropdown = findViewById(R.id.available_hours);
        //selectDoctorDropdown = findViewById(R.id.select_doctor_dropdown);
        selectDateError = findViewById(R.id.select_date_error);
        appointmentDate = findViewById(R.id.select_date);

        doctorId = getIntent().getExtras().getLong("doctorId");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            new Thread(() -> {
                client = userViewModel.getClientByEmail(user.getEmail());
                clientPets = animalViewModel.getAnimalsForOwner(client.getClientId());

                List<String> petNames = clientPets.stream().map(Animal::getName).collect(Collectors.toList());
                petNames.add(0, "Selectati animal de companie");
                ArrayAdapter<String> petAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, petNames);
                petAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                this.runOnUiThread(() -> selectPetDropdown.setAdapter(petAdapter));
            }).start();
        }
//        }else{
//            new Thread(() -> {
//                client = userViewModel.getClientById(1);
//                clientPets = animalViewModel.getAnimalsForOwner(client.getClientId());
//                List<String> petNames = clientPets.stream().map(Animal::getName).collect(Collectors.toList());
//                ArrayAdapter<String> petAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, petNames);
//                this.runOnUiThread(() -> selectPetDropdown.setAdapter(petAdapter));
//            }).start();
//        }

        new Thread(() -> {
//            Appointment appointment = new Appointment();
//            appointment.setDate("12/04/2022 10:00");
//            appointment.setAppointmentTypeId(1);
//            appointment.setDoctorId(1);
//            appointment.setAnimalId(1);
//            appointmentViewModel.insertAppointment(appointment);
//
//            Client client1 = new Client();
//            client1.setLastName("C");
//            client1.setFirstName("a");
//            userViewModel.insertClient(client1);
//
//            Location nl = new Location();
//            nl.setAddress("Str. a");
//            nl.setCity("Paris");
//            locationViewModel.insertLocation(nl);
//            nl.setCity("Bucharest");
//            locationViewModel.insertLocation(nl);
//
//            Doctor doctor = new Doctor();
//            doctor.setEmail("doctor1@yahoo.com");
//            doctor.setFirstName("Alex");
//            doctor.setLastName("Alex");
//            doctor.setLocationId(1);
//            userViewModel.insertDoctor(doctor);
//
//            Doctor doctor1 = new Doctor();
//            doctor1.setEmail("doctor2@yahoo.com");
//            doctor1.setFirstName("Alex2");
//            doctor1.setLastName("Alex2");
//            doctor1.setLocationId(2);
//            userViewModel.insertDoctor(doctor1);
//
//            AppointmentType appointmentType1 = new AppointmentType();
//            appointmentType1.setName("Tuns");
//            appointmentType1.setPrice(50.25);
//            appointmentViewModel.insertAppointmentType(appointmentType1);
//
//            AppointmentType appointmentType2 = new AppointmentType();
//            appointmentType2.setName("Consultatie");
//            appointmentType2.setPrice(120.00);
//            appointmentViewModel.insertAppointmentType(appointmentType2);
//
//            Animal animal = new Animal();
//            animal.setName("Rex");
//            animal.setType(Animal.DOG);
//            animal.setOwnerId(1);
//            animalViewModel.insertAnimal(animal);
//
//            Animal animal1 = new Animal();
//            animal1.setName("Mitu");
//            animal1.setType(Animal.CAT);
//            animal1.setOwnerId(1);
//            animalViewModel.insertAnimal(animal1);

            doctor = userViewModel.getDoctorById(doctorId);

            appointmentTypes = appointmentViewModel.getAllAppointmentTypes();
            List<String> appointmentTypeNames = appointmentTypes.stream().map(AppointmentType::getName).collect(Collectors.toList());
            appointmentTypeNames.add(0, "Selectati tipul programarii");
            ArrayAdapter<String> appointmentTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, appointmentTypeNames);
            appointmentTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.runOnUiThread(() -> selectAppointmentTypeDropdown.setAdapter(appointmentTypeAdapter));
        }).start();

        selectAppointmentTypeDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i > 0) {
                    selectedAppointmentTypeId = appointmentTypes.get(i-1).getAppointmentTypeId();
                }else{
                    selectedPetId = 0;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        selectPetDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i > 0) {
                    selectedPetId = clientPets.get(i-1).getAnimalId();
                }else{
                    selectedPetId = 0;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        availableHoursForDateDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i > 0){
                    String dayAndHour = appointmentDate.getText().toString() + " " + ((TextView)view).getText();
                    appointmentDate.setText(dayAndHour);
                    appointmentDate.setError(null);
                }else if(appointmentDate.getText().toString().length() > 10 && i == 0){
                    String onlyDay = appointmentDate.getText().toString().substring(0, 11);
                    appointmentDate.setText(onlyDay);
                }
                selectDateError.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        addAppointment.setOnClickListener(v -> {
            validated = true;
            if(appointmentDate.getText().toString().length() > 0 && appointmentDate.getText().toString().length() < 12){
                System.out.println("date error");
                appointmentDate.setError("");
                String errorMessage = "Selectați ora programării!";
                selectDateError.setVisibility(View.VISIBLE);
                selectDateError.setText(errorMessage);
                selectDateError.setTextColor(Color.RED);
                appointmentDate.requestFocus();
                validated = false;
            }else if(appointmentDate.getText().toString().equals("")){
                System.out.println("date error2");
                String errorMessage = "Selectați data programării!";
                selectDateError.setVisibility(View.VISIBLE);
                selectDateError.setText(errorMessage);
                selectDateError.setTextColor(Color.RED);
                appointmentDate.setError("Selectati data programarii!");
                validated = false;
            }else{
                selectDateError.setVisibility(View.INVISIBLE);
            }
            if(selectedPetId == 0){
                System.out.println("pet error");
                TextView errorText = (TextView)selectPetDropdown.getSelectedView();
                errorText.setError("");
                errorText.setTextColor(Color.RED);
                errorText.setText(R.string.selectati_un_element_din_lista);
                validated = false;
            }
            if(selectedAppointmentTypeId == 0){
                System.out.println("type error");
                TextView errorText = (TextView)selectAppointmentTypeDropdown.getSelectedView();
                errorText.setError("");
                errorText.setTextColor(Color.RED);
                errorText.setText(R.string.selectati_un_tip_de_programare);
                validated = false;
            }
            if(validated){
                Appointment appointment = new Appointment();
                appointment.setAppointmentTypeId(selectedAppointmentTypeId);
                appointment.setAnimalId(selectedPetId);
                appointment.setDoctorId(doctorId);
                appointment.setDate(appointmentDate.getText().toString());
                createNotificationChannel();
                new Thread(() -> {
                    savedAppointment = appointmentViewModel.insertAppointment(appointment);
                    Intent intent = new Intent(this, SeeAppointmentActivity.class);
                    intent.putExtra("id", savedAppointment);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                    stackBuilder.addNextIntentWithParentStack(intent);
                   // stackBuilder.addParentStack(SeeAppointmentActivity.class);
                    PendingIntent resultPendingIntent =
                            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, notificationChannelId)
                            .setContentTitle("O noua programare adaugata!")
                            .setContentText("Programarea a fost inregistrata! Apasa aici pentru detalii")
                            .setSmallIcon(R.drawable.ic_notification)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setContentIntent(resultPendingIntent)
                            .setAutoCancel(true);
                    Notification notification = builder.build();
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                    notificationManager.notify(notification.number, notification);
                }).start();
            }

        });

        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,day);
            updateLabel();
        };

        appointmentDate.setOnClickListener(view ->
                new DatePickerDialog(AddAppointmentActivity.this, date,
                        calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show());
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateLabel(){
        String format ="dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.ROOT);
        String selectedDate = dateFormat.format(calendar.getTime());
        appointmentDate.setText(selectedDate);
        if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            TextView textView = new TextView(this);
            textView.setText(R.string.nu_poti_face_programari_in_weekend);
            this.runOnUiThread(() -> availableIntervals.addView(textView));
        }else{
            new Thread(() -> {
                this.runOnUiThread(() -> availableHoursForDateDropdown.setVisibility(View.VISIBLE));

                filledIntervals = appointmentViewModel.getAppointmentsForDay(selectedDate);
                List<String> availableIntervalsForDay = new ArrayList<>(possibleAppointmentHours);
                availableIntervalsForDay.removeAll(filledIntervals);

                availableIntervalsForDay.add(0, "Selectati ora programarii");
                ArrayAdapter<String> availableHoursAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, availableIntervalsForDay);
                availableHoursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                this.runOnUiThread(() -> availableHoursForDateDropdown.setAdapter(availableHoursAdapter));
            }).start();
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "New appointment";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(notificationChannelId, name, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

package com.example.happypet.activity;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
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
    private long selectedLocationId = 0, selectedDoctorId = 0, selectedAppointmentTypeId = 0, selectedPetId = 0;
    private List<Location> locations;
    private List<Doctor> doctorsFromSelectedLocation;
    private List<AppointmentType> appointmentTypes;
    private List<Animal> clientPets;
    private List<String> filledIntervals;
    private Client client;
    private long savedAppointment;
    private final String notificationChannelId = "1";
    private Spinner selectDoctorDropdown;
    private EditText appointmentDate;
    private LinearLayout availableIntervals;
    private final List<String> possibleAppointmentHours = Arrays.asList("09:00", "09:30", "10:00", "10:30", "11:00",
            "11:30", "12:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30");

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
        Spinner selectLocationDropdown = findViewById(R.id.select_location_dropdown);
        Spinner selectAppointmentTypeDropdown = findViewById(R.id.select_appointment_type_dropdown);
        Spinner selectPetDropdown = findViewById(R.id.select_animal_dropdown);
        selectDoctorDropdown = findViewById(R.id.select_doctor_dropdown);
        appointmentDate = findViewById(R.id.select_date);
        availableIntervals = findViewById(R.id.available_intervals);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            new Thread(() -> {
                client = userViewModel.getClientByEmail(user.getEmail());
                clientPets = animalViewModel.getAnimalsForOwner(client.getClientId());

                List<String> petNames = clientPets.stream().map(Animal::getName).collect(Collectors.toList());
                ArrayAdapter<String> petAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, petNames);
                this.runOnUiThread(() -> selectPetDropdown.setAdapter(petAdapter));

            }).start();
        }else{
            new Thread(() -> {
                client = userViewModel.getClientById(1);
                clientPets = animalViewModel.getAnimalsForOwner(client.getClientId());
                List<String> petNames = clientPets.stream().map(Animal::getName).collect(Collectors.toList());
                ArrayAdapter<String> petAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, petNames);
                this.runOnUiThread(() -> selectPetDropdown.setAdapter(petAdapter));
            }).start();
        }

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

            locations = locationViewModel.getAllLocations();
            List<String> locationNames = locations.stream().map(location -> location.getCity() + ", " + location.getAddress()).collect(Collectors.toList());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locationNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.runOnUiThread(() -> selectLocationDropdown.setAdapter(adapter));

            appointmentTypes = appointmentViewModel.getAllAppointmentTypes();
            List<String> appointmentTypeNames = appointmentTypes.stream().map(AppointmentType::getName).collect(Collectors.toList());
            ArrayAdapter<String> appointmentTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, appointmentTypeNames);
            appointmentTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.runOnUiThread(() -> selectAppointmentTypeDropdown.setAdapter(appointmentTypeAdapter));

            doctorsFromSelectedLocation = userViewModel.getALlDoctors();
            List<String> doctorsNames = doctorsFromSelectedLocation.stream().map(d -> d.getFirstName() + " " + d.getLastName()).collect(Collectors.toList());
            ArrayAdapter<String> doctorsAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, doctorsNames);
            doctorsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.runOnUiThread(() -> selectDoctorDropdown.setAdapter(doctorsAdapter));

        }).start();

        selectDoctorDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedDoctorId = doctorsFromSelectedLocation.get(i).getDoctorId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        selectAppointmentTypeDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedAppointmentTypeId = appointmentTypes.get(i).getAppointmentTypeId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        selectPetDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPetId = clientPets.get(i).getAnimalId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });


        selectLocationDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedLocationId = locations.get(i).getLocationId();
                //doctorsFromSelectedLocation = userViewModel.getDoctorsFromLocation(selectedLocationId);
                //System.out.println("doctor for location: " + doctorsFromSelectedLocation.size());
                new Thread(() -> {
                    doctorsFromSelectedLocation = userViewModel.getDoctorsFromLocation(selectedLocationId);
                    List<String> doctorsNames = doctorsFromSelectedLocation.stream().map(doctor -> doctor.getFirstName() + " " + doctor.getLastName()).collect(Collectors.toList());
                    ArrayAdapter<String> doctorsAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, doctorsNames);

                    doctorsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    runOnUiThread(() -> selectDoctorDropdown.setAdapter(doctorsAdapter));
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        addAppointment.setOnClickListener(v -> {
            Appointment appointment = new Appointment();
            appointment.setAppointmentTypeId(selectedAppointmentTypeId);
            appointment.setAnimalId(selectedPetId);
            appointment.setDoctorId(selectedDoctorId);
            appointment.setDate(appointmentDate.getText().toString());
            createNotificationChannel();
            new Thread(() -> {
                savedAppointment = appointmentViewModel.insertAppointment(appointment);
                Intent intent = new Intent(this, SeeAppointmentActivity.class);
                intent.putExtra("id", savedAppointment);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addNextIntentWithParentStack(intent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

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
                this.runOnUiThread(() -> availableIntervals.removeAllViews());
                filledIntervals = appointmentViewModel.getAppointmentsForDay(selectedDate);
                List<String> availableIntervalsForDay = new ArrayList<>(possibleAppointmentHours);
                availableIntervalsForDay.removeAll(filledIntervals);
                this.runOnUiThread(() -> availableIntervalsForDay.forEach(interval -> availableIntervals.addView(createViewForInterval(interval))));
            }).start();
        }
    }

    private TextView createViewForInterval(String hour){
        TextView textView = new TextView(this);
        textView.setText(hour);
        String dayAndHour = appointmentDate.getText().toString() + " " + textView.getText();
        textView.setOnClickListener(view -> appointmentDate.setText(dayAndHour));
        return textView;
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

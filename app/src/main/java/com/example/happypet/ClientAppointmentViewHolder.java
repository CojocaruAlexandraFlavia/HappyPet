package com.example.happypet;

import android.view.View;
import android.widget.TextView;

import com.example.happypet.R;

public class ClientAppointmentViewHolder {
    TextView appointmentDate, appointmentType, petType, petAge, doctorName, appointmentPrice, location;
    ClientAppointmentViewHolder(View view){
        appointmentDate = view.findViewById(R.id.dateTextView);
        doctorName = view.findViewById(R.id.doctorName);
        appointmentType = view.findViewById(R.id.typeTextView);
        petType = view.findViewById(R.id.petType);
        petAge = view.findViewById(R.id.petAge);
        appointmentPrice = view.findViewById(R.id.priceTextView);
        location = view.findViewById(R.id.appointmentLocation);
    }

}

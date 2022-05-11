package com.example.happypet;

import android.view.View;
import android.widget.TextView;

public class DoctorAppointmentViewHolder {

    TextView appointmentDate, appointmentType, petType, petAge, ownerName, appointmentPrice;
    DoctorAppointmentViewHolder(View view){
        appointmentDate = view.findViewById(R.id.dateTextView);
        appointmentType = view.findViewById(R.id.typeTextView);
        petType = view.findViewById(R.id.petType);
        petAge = view.findViewById(R.id.petAge);
        ownerName = view.findViewById(R.id.ownerName);
        appointmentPrice = view.findViewById(R.id.priceTextView);
    }


}

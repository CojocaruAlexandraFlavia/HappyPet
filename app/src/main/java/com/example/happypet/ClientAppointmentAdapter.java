package com.example.happypet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class ClientAppointmentAdapter extends ArrayAdapter<String> {

        Context context;
        ArrayList<String> appointmentDate;
        ArrayList<String> appointmentType;
        ArrayList<String> appointmentPrice;
        ArrayList<String> petTypes;
        ArrayList<String> petAges;
        ArrayList<String> doctorName;
        ArrayList<String> location;



public ClientAppointmentAdapter(Context context, ArrayList<String> petTypes, ArrayList<String> petAges, ArrayList<String> appointmentDate, ArrayList<String> appointmentType,ArrayList<String> appointmentPrice, ArrayList<String> doctorName, ArrayList<String> location) {
        super(context, R.layout.single_client_appointment_item, R.id.petType, petTypes);
        this.context = context;
        this.petTypes = petTypes;
        this.petAges = petAges;
        this.appointmentDate = appointmentDate;
        this.appointmentType = appointmentType;
        this.appointmentPrice = appointmentPrice;
        this.doctorName = doctorName;
        this.location = location;
        }

@SuppressLint("SetTextI18n")
@Override
public View getView(int position, View convertView, ViewGroup parent) {


        View singleItem = convertView;
        ClientAppointmentViewHolder holder = null;
        if(singleItem == null){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        singleItem = layoutInflater.inflate(R.layout.single_client_appointment_item, parent, false);
        holder = new ClientAppointmentViewHolder(singleItem);
        singleItem.setTag(holder);
        }else{
        holder = (ClientAppointmentViewHolder) singleItem.getTag();
        }

        holder.petType.setText("Numele animalului: " + petTypes.get(position));
        if(petAges.get(position).equals("1")){
        holder.petAge.setText(("Vârstă: " + petAges.get(position) + " an"));
        }else{
        holder.petAge.setText(("Vârstă: " + petAges.get(position) + " ani"));
        }
        holder.appointmentDate.setText("Data programării: " + appointmentDate.get(position));
        holder.appointmentType.setText("Tipul programării: " + appointmentType.get(position));
        holder.appointmentPrice.setText("Prețul programării: " + appointmentPrice.get(position) + " RON");
        holder.doctorName.setText("Doctor: Dr. " + doctorName.get(position));
        holder.location.setText("Locație:  " + location.get(position));

        singleItem.setOnClickListener(view ->{
        Toast.makeText(getContext(), "You clicked : " + petTypes.get(position), Toast.LENGTH_SHORT).show();
        });


        return singleItem;
        }
}

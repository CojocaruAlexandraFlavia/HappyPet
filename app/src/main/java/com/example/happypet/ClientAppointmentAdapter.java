package com.example.happypet;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.happypet.activity.MapsActivity;

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
        ArrayList<String> locationLatLng;



public ClientAppointmentAdapter(Context context, ArrayList<String> petTypes, ArrayList<String> petAges, ArrayList<String> appointmentDate, ArrayList<String> appointmentType,ArrayList<String> appointmentPrice, ArrayList<String> doctorName, ArrayList<String> location, ArrayList<String> locationLatLng) {
        super(context, R.layout.single_client_appointment_item, R.id.petType, petTypes);
        this.context = context;
        this.petTypes = petTypes;
        this.petAges = petAges;
        this.appointmentDate = appointmentDate;
        this.appointmentType = appointmentType;
        this.appointmentPrice = appointmentPrice;
        this.doctorName = doctorName;
        this.location = location;
        this.locationLatLng = locationLatLng;
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
        holder.viewLocation.setOnClickListener(view -> {
                Intent i = new Intent(context, MapsActivity.class);
                i.putExtra("LatLng", locationLatLng.get(position));
                i.putExtra("locationName", location.get(position));
                startActivity(context, i, null);

        });

        singleItem.setOnClickListener(view ->{
        Toast.makeText(getContext(), "You clicked : " + petTypes.get(position), Toast.LENGTH_SHORT).show();

        });


        return singleItem;
        }
}

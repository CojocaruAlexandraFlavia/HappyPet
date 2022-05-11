package com.example.happypet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class DoctorAppointmentAdapter extends ArrayAdapter<String> {

    Context context;
    ArrayList<String> appointmentDate;
    ArrayList<String> appointmentType;
    ArrayList<String> appointmentPrice;
    ArrayList<String> petTypes;
    ArrayList<String> petAges;
    ArrayList<String> ownerName;



    public DoctorAppointmentAdapter(Context context, ArrayList<String> petTypes, ArrayList<String> petAges, ArrayList<String> appointmentDate, ArrayList<String> appointmentType,ArrayList<String> appointmentPrice, ArrayList<String> ownerName) {
        super(context, R.layout.single_appointment_item, R.id.petType, petTypes);
        this.context = context;
        this.petTypes = petTypes;
        this.petAges = petAges;
        this.appointmentDate = appointmentDate;
        this.appointmentType = appointmentType;
        this.appointmentPrice = appointmentPrice;
        this.ownerName = ownerName;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View singleItem = convertView;
        DoctorAppointmentViewHolder holder = null;
        if(singleItem == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            singleItem = layoutInflater.inflate(R.layout.single_appointment_item, parent, false);
            holder = new DoctorAppointmentViewHolder(singleItem);
            singleItem.setTag(holder);
        }else{
            holder = (DoctorAppointmentViewHolder) singleItem.getTag();
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
        holder.ownerName.setText("Stăpân:  " + ownerName.get(position));

        singleItem.setOnClickListener(view ->{
            Toast.makeText(getContext(), "You clicked : " + petTypes.get(position), Toast.LENGTH_SHORT).show();
        });


        return singleItem;
    }
}

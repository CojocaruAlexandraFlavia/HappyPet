package com.example.happypet;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happypet.activity.DoctorProfileActivity;
import com.example.happypet.activity.HomeActivity;
import com.example.happypet.data.repository.LocationRepository;
import com.example.happypet.model.Doctor;

import java.util.ArrayList;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {
    private ArrayList<Doctor> doctorArrayList;
    private Context context;
    private LocationRepository locationRepository;

    // creating a constructor for our variables.
    public DoctorAdapter(ArrayList<Doctor> doctorArrayList, Context context) {
        this.doctorArrayList = doctorArrayList;
        this.context = context;

    }

    public void filterList(ArrayList<Doctor> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        doctorArrayList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DoctorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        Doctor doctor = doctorArrayList.get(position);
        holder.doctorNameTv.setText(doctor.getFullName());
        holder.doctorNameTv.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), DoctorProfileActivity.class);
            intent.putExtra("doctorId", doctor.getDoctorId());
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return doctorArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private TextView doctorNameTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            doctorNameTv = itemView.findViewById(R.id.idDoctorName);
        }
    }

}
package com.example.happypet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ProgramAdapter extends ArrayAdapter<String> {

    Context context;
    ArrayList<Integer> petImages;
    ArrayList<String> petNames;
    ArrayList<String> petAges;


    public ProgramAdapter(Context context, ArrayList<String> petNames, ArrayList<Integer> petImages, ArrayList<String> petAges) {
        super(context, R.layout.single_pet_item, R.id.petName, petNames);
        this.context = context;
        this.petImages = petImages;
        this.petNames = petNames;
        this.petAges = petAges;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {


        View singleItem = convertView;
        ProgramViewHolder holder = null;
        if(singleItem == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            singleItem = layoutInflater.inflate(R.layout.single_pet_item, parent, false);
            holder = new ProgramViewHolder(singleItem);
            singleItem.setTag(holder);
        }else{
            holder = (ProgramViewHolder) singleItem.getTag();
        }

        holder.petImage.setImageResource(petImages.get(position));
        holder.petName.setText("Nume: " + petNames.get(position));
        if(petAges.get(position).equals("1")){
            holder.petAge.setText(("Vârstă: " + petAges.get(position) + " an"));
        }else{
            holder.petAge.setText(("Vârstă: " + petAges.get(position) + " ani"));
        }


        singleItem.setOnClickListener(view ->{
            Toast.makeText(getContext(), "You clicked : " + petNames.get(position), Toast.LENGTH_SHORT).show();
        });


        return singleItem;
    }
}
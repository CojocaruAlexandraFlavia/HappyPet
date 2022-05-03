package com.example.happypet;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProgramViewHolder {
    ImageView petImage;
    TextView petName, petAge;
    ProgramViewHolder(View view){
        petImage = view.findViewById(R.id.petImageView);
        petName = view.findViewById(R.id.petName);
        petAge = view.findViewById(R.id.petAge);
    }

}

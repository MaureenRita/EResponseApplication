package com.example.finalyrera;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SafetyGuidelines extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_guidelines);

        // Fire Safety Guidelines
        TextView tvFireSafetyTitle = findViewById(R.id.tvFireSafetyTitle);
        TextView tvFireSafetyInstructions = findViewById(R.id.tvFireSafetyInstructions);
        ImageView imageViewFire = findViewById(R.id.imageViewFire);

        tvFireSafetyTitle.setText(R.string.fire_guidelines);
        tvFireSafetyInstructions.setText(R.string.fire_safety_instructions);
        imageViewFire.setImageResource(R.drawable.fire);

        // Floods Safety Guidelines
        TextView tvFloodsSafetyTitle = findViewById(R.id.tvFloodsSafetyTitle);
        TextView tvFloodsSafetyInstructions = findViewById(R.id.tvFloodsSafetyInstructions);
        ImageView imageViewFloods = findViewById(R.id.imageViewFloods);

        tvFloodsSafetyTitle.setText(R.string.floods_guidelines);
        tvFloodsSafetyInstructions.setText(R.string.floods_safety_instructions);
        imageViewFloods.setImageResource(R.drawable.floods);

        // Medical Emergency Guidelines
        TextView tvMedicalEmergencySafetyTitle = findViewById(R.id.tvMedicalEmergencySafetyTitle);
        TextView tvMedicalEmergencySafetyInstructions = findViewById(R.id.tvMedicalEmergencySafetyInstructions);
        ImageView imageViewMedicalEmergency = findViewById(R.id.imageViewMedicalEmergency);

        tvMedicalEmergencySafetyTitle.setText(R.string.medical_emergency_guidelines);
        tvMedicalEmergencySafetyInstructions.setText(R.string.medical_emergency_safety_instructions);
        imageViewMedicalEmergency.setImageResource(R.drawable.medical);

        // Car Accident Safety Guidelines
        TextView tvCarAccidentSafetyTitle = findViewById(R.id.tvCarAccidentSafetyTitle);
        TextView tvCarAccidentSafetyInstructions = findViewById(R.id.tvCarAccidentSafetyInstructions);
        ImageView imageViewCarAccident = findViewById(R.id.imageViewCarAccident);

        tvCarAccidentSafetyTitle.setText(R.string.car_accident_guidelines);
        tvCarAccidentSafetyInstructions.setText(R.string.car_accident_safety_instructions);
        imageViewCarAccident.setImageResource(R.drawable.caraccident);
    }
}

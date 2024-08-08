package com.example.finalyrera;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Contacts extends AppCompatActivity {

    private Button btnCallPolice, btnCallHospital, btnCallRedCross, btnCallTowTruck, btnSafetyGuidelines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        // Initialize buttons
        btnCallPolice = findViewById(R.id.btnCallPolice);
        btnCallHospital = findViewById(R.id.btnCallHospital);
        btnCallRedCross = findViewById(R.id.btnCallRedCross);
        btnCallTowTruck = findViewById(R.id.btnCallTowTruck);
        btnSafetyGuidelines = findViewById(R.id.btnSafetyGuidelines);

        // Set click listeners for buttons
        btnCallPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialPhoneNumber("999");
            }
        });

        btnCallHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialPhoneNumber("112");
            }
        });

        btnCallRedCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialPhoneNumber("1199");
            }
        });

        btnCallTowTruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialPhoneNumber("020-3319333");
            }
        });

        btnSafetyGuidelines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Safety Guidelines Activity
                Intent intent = new Intent(Contacts.this, SafetyGuidelines.class);
                startActivity(intent);
            }
        });
    }

    // Method to dial phone number
    private void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }
}
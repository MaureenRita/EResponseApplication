package com.example.finalyrera;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmergencyHistory extends AppCompatActivity {

    private LinearLayout linearLayoutIncidents;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_history);

        linearLayoutIncidents = findViewById(R.id.linearLayoutIncidents);
        databaseReference = FirebaseDatabase.getInstance().getReference("incidents");

        // Load and display all incidents from Firebase
        loadIncidentData();
    }

    private void loadIncidentData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Clear existing views
                    linearLayoutIncidents.removeAllViews();

                    // Iterate through all incidents
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Incident incident = snapshot.getValue(Incident.class);
                        if (incident != null) {
                            // Add view for each incident
                            addIncidentView(incident);
                        }
                    }
                } else {
                    Toast.makeText(EmergencyHistory.this, "No incidents found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EmergencyHistory.this, "Failed to load incidents: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addIncidentView(Incident incident) {
        // Inflate a new view for the incident
        LayoutInflater inflater = LayoutInflater.from(this);
        View incidentView = inflater.inflate(R.layout.item_incident, linearLayoutIncidents, false);

        // Find and set data in the inflated view
        TextView tvIncidentType = incidentView.findViewById(R.id.tvIncidentType);
        TextView tvLocation = incidentView.findViewById(R.id.tvLocation);
        TextView tvDescription = incidentView.findViewById(R.id.tvDescription);
        TextView tvIntensity = incidentView.findViewById(R.id.tvIntensity);
        ImageView ivPhoto = incidentView.findViewById(R.id.ivPhoto);

        tvIncidentType.setText("Type: " + incident.getIncidentType());
        tvLocation.setText("Location: " + incident.getLocation());
        tvDescription.setText("Description: " + incident.getDescription());
        tvIntensity.setText("Intensity: " + incident.getIntensity());

        // Load image using Glide
        Glide.with(this)
                .load(incident.getPhotoUrl())
                .into(ivPhoto);

        // Add the view to the LinearLayout
        linearLayoutIncidents.addView(incidentView);
    }
}

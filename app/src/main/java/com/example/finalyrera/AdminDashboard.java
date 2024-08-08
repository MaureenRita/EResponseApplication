package com.example.finalyrera;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboard extends AppCompatActivity {

    private TextView tvDispatchLog;
    private EditText etAmbulancesDispatch, etFiretrucksDispatch, etEstimatedTime;
    private Button btnDispatchResources, btnContactUser, btnEmergencyHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        initializeViews();
        setupButtonListeners();
    }

    private void initializeViews() {
        tvDispatchLog = findViewById(R.id.tvDispatchLog);
        etAmbulancesDispatch = findViewById(R.id.etAmbulancesDispatch);
        etFiretrucksDispatch = findViewById(R.id.etfiretrucksDispatch);
        etEstimatedTime = findViewById(R.id.etEstimatedTime);
        btnDispatchResources = findViewById(R.id.btnDispatchResources);
        btnContactUser = findViewById(R.id.btnContactUser);
        btnEmergencyHistory = findViewById(R.id.btnEmergencyHistory);
    }

    private void setupButtonListeners() {
        btnDispatchResources.setOnClickListener(v -> dispatchResources());
        btnContactUser.setOnClickListener(v -> contactUser());
        btnEmergencyHistory.setOnClickListener(v -> openEmergencyHistory());
    }

    private void dispatchResources() {
        String ambulancesStr = etAmbulancesDispatch.getText().toString();
        String firetrucksStr = etFiretrucksDispatch.getText().toString();
        String estimatedTimeStr = etEstimatedTime.getText().toString();

        if (ambulancesStr.isEmpty() || firetrucksStr.isEmpty() || estimatedTimeStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int ambulances = Integer.parseInt(ambulancesStr);
            int firetrucks = Integer.parseInt(firetrucksStr);
            int estimatedTime = Integer.parseInt(estimatedTimeStr);

            String dispatchMessage = String.format("Dispatched %d ambulances and %d firetrucks. ETA: %d minutes",
                    ambulances, firetrucks, estimatedTime);

            updateDispatchLog(dispatchMessage);
            sendFeedbackToUser(dispatchMessage);

            etAmbulancesDispatch.setText("");
            etFiretrucksDispatch.setText("");
            etEstimatedTime.setText("");
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input. Please enter valid numbers.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateDispatchLog(String message) {
        String currentLog = tvDispatchLog.getText().toString();
        tvDispatchLog.setText(message + "\n\n" + currentLog);
    }

    private void sendFeedbackToUser(String message) {
        // In a real app, you would send this message to the user
        // This could be done via FCM (Firebase Cloud Messaging) or your own messaging system
        Toast.makeText(this, "Feedback sent to user: " + message, Toast.LENGTH_LONG).show();
    }

    private void contactUser() {
        // In a real app, you'd implement a way to contact the user
        Toast.makeText(this, "Contacting user", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ContactUser.class);
        startActivity(intent);
    }

    private void openEmergencyHistory() {
        // In a real app, you'd open an activity to show emergency history
        Toast.makeText(this, "Opening emergency history", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, EmergencyHistory.class);
        startActivity(intent);
    }
}

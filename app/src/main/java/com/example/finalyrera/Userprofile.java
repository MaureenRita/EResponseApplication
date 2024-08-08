package com.example.finalyrera;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Userprofile extends AppCompatActivity {

    private ImageView profileImage;
    private TextView tvUserName, tvUserEmail;
    private Button btnEditProfile, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        profileImage = findViewById(R.id.profile_image);
        tvUserName = findViewById(R.id.tvUserName);
        tvUserEmail = findViewById(R.id.tvUserEmail);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnLogout = findViewById(R.id.btnLogout);

        // Load user data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String savedUserName = sharedPreferences.getString("username", "User Name");
        String savedUserEmail = sharedPreferences.getString("email", "user@example.com");

        tvUserName.setText(savedUserName);
        tvUserEmail.setText(savedUserEmail);

        btnEditProfile.setOnClickListener(v -> {
            // Navigate to Edit Profile Activity
            Intent intent = new Intent(Userprofile.this, Editprofile.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            // Handle logout logic
            Toast.makeText(Userprofile.this, "Logged out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Userprofile.this, UserLogin.class);
            startActivity(intent);
            finish(); // Finish the profile activity to prevent going back to it on back press
        });
    }
}

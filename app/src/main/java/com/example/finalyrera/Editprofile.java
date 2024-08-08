package com.example.finalyrera;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Editprofile extends AppCompatActivity {

    private EditText etUserName, etUserEmail;
    private Button btnSaveChanges;
    private SharedPreferences sharedPreferences; // Declare sharedPreferences here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        etUserName = findViewById(R.id.etUserName);
        etUserEmail = findViewById(R.id.etUserEmail);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Load existing values from SharedPreferences if available
        String currentUserName = sharedPreferences.getString("username", "");
        String currentUserEmail = sharedPreferences.getString("email", "");

        etUserName.setText(currentUserName);
        etUserEmail.setText(currentUserEmail);

        btnSaveChanges.setOnClickListener(v -> {
            // Get new values from EditTexts
            String newUserName = etUserName.getText().toString().trim();
            String newUserEmail = etUserEmail.getText().toString().trim();

            if (newUserName.isEmpty() || newUserEmail.isEmpty()) {
                Toast.makeText(Editprofile.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Save changes to SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", newUserName);
                editor.putString("email", newUserEmail);
                editor.apply();

                Toast.makeText(Editprofile.this, "Changes saved", Toast.LENGTH_SHORT).show();
                finish(); // Close the activity after saving changes
            }
        });
    }
}

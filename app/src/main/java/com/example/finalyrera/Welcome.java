package com.example.finalyrera;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class Welcome extends AppCompatActivity {

    private Button btncallforhelp, btnlocationsharing, btnreportemergency, btntalktous, btnProfile;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btncallforhelp = findViewById(R.id.btncallforhelp);
        btnlocationsharing = findViewById(R.id.btnlocationsharing);
        btntalktous = findViewById(R.id.btntalktous);
        btnreportemergency = findViewById(R.id.btnreportemergency);
        btnProfile = findViewById(R.id.btnProfile);

        btncallforhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Welcome.this, Contacts.class));
            }
        });

        btnlocationsharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Welcome.this, PanicButton.class));
            }
        });

        btntalktous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Welcome.this,Chat.class));
            }
        });

        btnreportemergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Welcome.this, Form.class));
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Welcome.this, Userprofile.class));
            }
        });
    }
}

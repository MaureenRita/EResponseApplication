package com.example.finalyrera;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserLogin extends AppCompatActivity {

    private static final String TAG = "UserLogin";

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvSignup, tvForgotPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Find views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignup = findViewById(R.id.tvSignup);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        // Ensure btnLogin, tvSignup, and tvForgotPassword are not null
        if (btnLogin == null || tvSignup == null || tvForgotPassword == null) {
            Log.e(TAG, "Button or TextView is null");
            return;
        }

        // Set up login button listener
        btnLogin.setOnClickListener(v -> loginUser());

        // Set up signup link listener
        tvSignup.setOnClickListener(v -> {
            Intent intent = new Intent(UserLogin.this, UserSignup.class);
            startActivity(intent);
        });

        // Set up forgot password link listener
        tvForgotPassword.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                etEmail.setError("Email is required to reset password");
                return;
            }
            resetPassword(email);
        });
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Navigate to the User Dashboard or any other activity
                            startActivity(new Intent(UserLogin.this, Welcome.class));
                            finish();
                        }
                    } else {
                        Toast.makeText(UserLogin.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Authentication failed", task.getException());
                    }
                });
    }

    private void resetPassword(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(UserLogin.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UserLogin.this, "Failed to send password reset email: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Failed to send password reset email", task.getException());
                    }
                });
    }
}

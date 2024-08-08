package com.example.finalyrera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class Form extends AppCompatActivity {

    private static final int REQUEST_CODE_SELECT_PHOTO = 1;

    private Spinner spinnerIncidentType;
    private EditText etLocation, etDescription;
    private SeekBar seekBarIntensity;
    private Button buttonSelectPhoto, btnSubmitForm;
    private ImageView ivSelectedPhoto;
    private Uri selectedImageUri;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        databaseReference = FirebaseDatabase.getInstance().getReference("incidents");
        storageReference = FirebaseStorage.getInstance().getReference("incident_photos");

        spinnerIncidentType = findViewById(R.id.spinnerIncidentType);
        etLocation = findViewById(R.id.etLocation);
        etDescription = findViewById(R.id.etDescription);
        seekBarIntensity = findViewById(R.id.seekBarIntensity);
        buttonSelectPhoto = findViewById(R.id.buttonSelectPhoto);
        ivSelectedPhoto = findViewById(R.id.ivSelectedPhoto);
        btnSubmitForm = findViewById(R.id.btnSubmitForm);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.incident_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIncidentType.setAdapter(adapter);

        buttonSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btnSubmitForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("latitude") && intent.hasExtra("longitude")) {
            double latitude = intent.getDoubleExtra("latitude", 0);
            double longitude = intent.getDoubleExtra("longitude", 0);
            String location = "Lat: " + latitude + ", Lng: " + longitude;
            etLocation.setText(location);
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    ivSelectedPhoto.setImageBitmap(bitmap);
                    ivSelectedPhoto.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void submitForm() {
        String incidentType = spinnerIncidentType.getSelectedItem().toString();
        String location = etLocation.getText().toString();
        String description = etDescription.getText().toString();
        int intensity = seekBarIntensity.getProgress();

        if (TextUtils.isEmpty(location) || TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedImageUri != null) {
            uploadPhotoAndSaveIncident(incidentType, location, description, intensity);
        } else {
            saveIncident(incidentType, location, description, intensity, null);
        }
    }

    private void uploadPhotoAndSaveIncident(final String incidentType, final String location, final String description, final int intensity) {
        final StorageReference photoRef = storageReference.child(System.currentTimeMillis() + ".jpg");

        photoRef.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String photoUrl = uri.toString();
                        saveIncident(incidentType, location, description, intensity, photoUrl);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Form.this, "Failed to upload photo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveIncident(String incidentType, String location, String description, int intensity, @Nullable String photoUrl) {
        String incidentId = databaseReference.push().getKey();
        Incident incident = new Incident(incidentId, incidentType, location, description, intensity, photoUrl);

        databaseReference.child(incidentId).setValue(incident).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Form.this, "Incident reported successfully.", Toast.LENGTH_SHORT).show();

                // Send the incident details using a LocalBroadcast
                Intent historyIntent = new Intent("com.example.finalyrera.SEND_INCIDENT");
                historyIntent.putExtra("incident", incident);
                LocalBroadcastManager.getInstance(Form.this).sendBroadcast(historyIntent);

                // Navigate back to the Welcome activity
                Intent welcomeIntent = new Intent(Form.this, Welcome.class);
                welcomeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(welcomeIntent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Form.this, "Failed to report incident", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

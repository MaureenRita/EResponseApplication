package com.example.finalyrera;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private TextView tvLocationInfo;
    private GoogleMap mMap;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_map);

        tvLocationInfo = findViewById(R.id.tvLocationInfo);
        location = getIntent().getStringExtra("location");

        if (location != null) {
            tvLocationInfo.setText("Location: " + location);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Parse the location string to get latitude and longitude
        String[] parts = location.split(",");
        if (parts.length == 2) {
            try {
                double lat = Double.parseDouble(parts[0].split(":")[1].trim());
                double lng = Double.parseDouble(parts[1].split(":")[1].trim());

                LatLng incidentLocation = new LatLng(lat, lng);

                // Add a marker for the incident location and move the camera
                mMap.addMarker(new MarkerOptions().position(incidentLocation).title("Incident Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(incidentLocation, 15));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                // Handle the error, maybe show a toast to the user
            }
        }
    }
}
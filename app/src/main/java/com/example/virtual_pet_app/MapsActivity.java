package com.example.virtual_pet_app;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button search, back;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String NAME = "name";
    public static final String HUNGER = "hunger";
    public static final String EXERCISE = "exercise";

    public int Hunger;
    public int Exercise;

    private LatLng lastLocation;
    private float distanceTravelled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                LatLng newPosition = new LatLng(latitude, longitude);
                float[] results = new float[1];
                if (lastLocation == null) {
                    lastLocation = newPosition;
                } else {
                    Location.distanceBetween(lastLocation.latitude, lastLocation.longitude,
                            newPosition.latitude, newPosition.longitude, results);
                    distanceTravelled += results[0];    // in meters
                    if (distanceTravelled > 50) {
                        while (distanceTravelled > 50) {
                            Exercise += 1;
                            distanceTravelled -= 50;
                        }
                    }
                }
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };
        search = (Button) findViewById(R.id.b_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFood();
            }
        });
        back = (Button) findViewById(R.id.b_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        //LatLng location = new LatLng(52.969344, -0.037614);
        LatLng location = lastLocation;

        googleMap.addMarker(new MarkerOptions().position(location));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 13));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(location)      // Sets the center of the map to location user
                .zoom(18)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(67.5f)                   // Sets the tilt of the camera to 10 degrees
                .build();                   // Creates a CameraPosition from the builder
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void SearchFood()
    {
        Random random = new Random();
        int newFood = random.nextInt(10);
       if(Hunger < newFood)
        {
            String text = "Found " + newFood + " Food.";
            Toast.makeText(MapsActivity.this, text, Toast.LENGTH_SHORT).show();
            Hunger -= newFood;
        }
        else
        {
            Hunger = 0;
            Toast.makeText(this, "Your pet is too full to eat!", Toast.LENGTH_SHORT).show();
        }
        saveData();
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(HUNGER, Hunger);

        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Hunger = sharedPreferences.getInt(HUNGER, 0);
    }
}

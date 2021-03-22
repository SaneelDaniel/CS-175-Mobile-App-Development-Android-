package edu.sjsu.android.location;

import androidx.appcompat.app.AppCompatActivity;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnShowLocation;
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnShowLocation = (findViewById(R.id.btnShowLocation));
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gps = new GPSTracker(MainActivity.this);
                Location location = gps.getLocation();
                if(gps.canGetLocation()){
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat " + latitude + "\nLong: " + longitude,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
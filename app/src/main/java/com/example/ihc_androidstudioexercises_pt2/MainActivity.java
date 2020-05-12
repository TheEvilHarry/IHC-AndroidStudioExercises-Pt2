package com.example.ihc_androidstudioexercises_pt2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor light;
    private GPSTracker gpsTracker;
    private Location location;
    private Sensor proximity;

    private TextView lightView;
    private TextView latitudeView;
    private TextView longitudeView;
    private TextView proximityView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lightView = (TextView) findViewById(R.id.light);
        proximityView = (TextView) findViewById(R.id.proximity);
        latitudeView = (TextView) findViewById(R.id.latitude);
        longitudeView = (TextView) findViewById(R.id.longitude);

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        gpsTracker = new GPSTracker(getApplicationContext());
        location = gpsTracker.getLocation();
        if(location != null){
            double lat = location.getLatitude();
            double longi = location.getLongitude();
            latitudeView.setText("LAT: " + lat);
            longitudeView.setText("LON: " + longi);

        }
        else{
            latitudeView.setText("null");
            longitudeView.setText("null");
        }


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(light != null){
            sensorManager.registerListener(MainActivity.this, light, SensorManager.SENSOR_DELAY_NORMAL);
        }

        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if(proximity != null){
            sensorManager.registerListener(MainActivity.this,proximity,SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;

        if(sensor.getType() == Sensor.TYPE_LIGHT)
        {
            lightView.setText(sensorEvent.values[0] + "");
        }
        else if(sensor.getType() == Sensor.TYPE_PROXIMITY){
            if(sensorEvent.values[0] < 1.5F){
                proximityView.setText("Too close!");
            }
            else{
                proximityView.setText("Far away");
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

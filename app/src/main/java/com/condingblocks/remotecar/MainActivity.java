package com.condingblocks.remotecar;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.condingblocks.remotecar.utils.SensorListner;

public class MainActivity extends AppCompatActivity {

    SensorManager sensorManager;
    public static final String TAG = "MainActivity";
    SensorListner sensorListner;
    Sensor accelSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

         accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if(accelSensor != null ){

            Log.d(TAG, "onCreate: " + accelSensor.getName());

        }else{
            Log.d(TAG, "onCreate: Sensor not created" );
        }

        sensorListner = new SensorListner();

    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener(sensorListner);
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onResume();
        registerSensor();

    }

    public void registerSensor(){
        sensorManager.registerListener(
                sensorListner,
                accelSensor,
                1000 * 10
        );
    }
}

package com.condingblocks.remotecar.utils;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import com.condingblocks.remotecar.MainActivity;
import com.condingblocks.remotecar.models.SensorDataModel;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.Socket;
import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.emitter.Emitter;


/**
 * Created by rishabhkhanna on 22/03/17.
 */

public class SensorListner implements SensorEventListener {
    public static final String TAG = "Sensor Listener";
    io.socket.client.Socket socket;
    public static SensorDataModel model;
    Gson gson;
    float x = 0.0f;
    float y = 0.0f;


    public SensorListner() {
        try {
            socket = IO.socket("https://car-remote.herokuapp.com/");
            Log.d(TAG, "SensorListner: init");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        model = new SensorDataModel();

        socket.on(io.socket.client.Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, "call: inside connect");
                socket.emit("foo" , "hii");
            }
        });


        Log.d(TAG, "SensorListner: constructor");
        socket.connect();
        Log.d(TAG, "SensorListner: called");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.values[0]  > 0 || event.values[0]  < -0 || event
                .values[1] > 0 || event.values[1] < -0) {
            x -= event.values[0];
            y += event.values[1];
            float z = event.values[2];
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//                Log.d(TAG, "onSensorChanged: x " + x);
//                Log.d(TAG, "onSensorChanged: y " + y);
//                Log.d(TAG, "onSensorChanged: z " + z);
            }

            model.setAccelX(x);
            model.setAccelY(y);
            model.setAccelZ(z);

            gson = new Gson();


            socket.emit("sensor", gson.toJson(model));

            MainActivity.changePosition();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

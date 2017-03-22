package com.condingblocks.remotecar.utils;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

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

    public SensorListner() {
        try {
            socket = IO.socket("http://172.16.98.147:8888/");
            Log.d(TAG, "SensorListner: init");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        socket.on(io.socket.client.Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, "call: inside connect");
                socket.emit("foo" , "hii");
                socket.disconnect();
            }
        });

        Log.d(TAG, "SensorListner: constructor");

        socket.connect();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            Log.d(TAG, "onSensorChanged: x" + event.values[0]);
            Log.d(TAG, "onSensorChanged: y " + event.values[1]);
            Log.d(TAG, "onSensorChanged: z" + event.values[2]);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

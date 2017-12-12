package com.condingblocks.pi_bot.views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.condingblocks.pi_bot.R;
import com.condingblocks.pi_bot.models.Direction;
import com.google.gson.Gson;

import java.net.URISyntaxException;
import java.util.ResourceBundle;

import io.socket.client.IO;
import io.socket.emitter.Emitter;

public class Controls extends AppCompatActivity implements View.OnClickListener {
    Button left;
    Button right;
    Button forward;
    Button backward;
    FloatingActionButton mapBtn;
    io.socket.client.Socket socket;
    public static final String TAG = "Controls";
    Gson gson;
    public static Double lat;
    public static Double longi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controls);
        socketConnection();
        left = (Button) findViewById(R.id.btnRight);
        right = (Button) findViewById(R.id.btnLeft);
        forward = (Button) findViewById(R.id.btnForward);
        backward = (Button) findViewById(R.id.btnBackward);
        mapBtn = (FloatingActionButton) findViewById(R.id.floating);
        gson = new Gson();
        getLocation();
        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "onTouch: " + event.toString());
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    disableRight();
                    disableForward();
                    disableBackward();
                    sendSocketData("left");
                    Log.d(TAG, "onTouch: Left down");
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    enableRight();
                    enableForward();
                    enableBackward();
                    sendSocketData("stop");
                }
                return false;
            }
        });

        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    disableLeft();
                    disableForward();
                    disableBackward();
                    sendSocketData("right");
                    Log.d(TAG, "onTouch: right down");

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    enableLeft();
                    enableForward();
                    enableBackward();
                    sendSocketData("stop");
                }
                return false;
            }
        });

        forward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    disableRight();
                    disableLeft();
                    disableBackward();
                    sendSocketData("forward");
                    Log.d(TAG, "onTouch: forward down");

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    enableRight();
                    enableLeft();
                    enableBackward();
                    sendSocketData("stop");
                    Log.d(TAG, "onTouch: forward stop");
                }
                return false;
            }
        });

        backward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    disableRight();
                    disableForward();
                    disableLeft();
                    sendSocketData("backward");
                    Log.d(TAG, "onTouch: backward down");

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    enableRight();
                    enableForward();
                    enableLeft();
                    sendSocketData("stop");
                    Log.d(TAG, "onTouch: forward stop");
                }
                return false;
            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lat == null){
                    Toast.makeText(Controls.this, "Not got the location till now", Toast.LENGTH_SHORT).show();
                }else {
                    Intent i = new Intent(Controls.this, MapsActivity.class);
                    startActivity(i);
                }

            }
        });


    }

    private void getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                100,
                10.0f,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        lat = location.getLatitude();
                        longi = location.getLongitude();
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
                }
        );
    }

    @Override
    public void onClick(View v) {

    }

    public void disableLeft(){
        left.setEnabled(false);
    }
    public void disableRight(){
        right.setEnabled(false);
    }
    public void disableForward(){
        forward.setEnabled(false);
    }
    public void disableBackward(){
        backward.setEnabled(false);
    }
    public void enableLeft(){
        left.setEnabled(true);
    }
    public void enableRight(){
        right.setEnabled(true);
    }
    public void enableForward(){
        forward.setEnabled(true);
    }
    public void enableBackward(){
        backward.setEnabled(true);
    }

    public void socketConnection(){
        try {
            socket = IO.socket("http://192.168.1.5:8888/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "SensorListner: constructor");
        if(socket  != null)
        socket.connect();
        Log.d(TAG, "SensorListner: called");


        socket.on(io.socket.client.Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, "call: inside connect");
                socket.emit("foo" , "hii");
            }
        });

        socket.on("location", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
//                Location l = new Gson().fromJson(String.valueOf(args) ,Location.class);
//                lat = l.getLatitude();
//                longi = l.getLongitude();
//                Log.d(TAG, "call: " + args);
            }
        });

    }

    public void sendSocketData(String direction){
        Direction thisDirection = new Direction(direction);
        String thisObject = gson.toJson(thisDirection);
        Log.d(TAG, "sendSocketData: sendData to server");
        socket.emit("control", thisObject);
    }

}

package com.condingblocks.remotecar.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.condingblocks.remotecar.R;
import com.condingblocks.remotecar.models.Direction;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.emitter.Emitter;

public class Controls extends AppCompatActivity implements View.OnClickListener {
    Button left;
    Button right;
    Button forward;
    Button backward;
    io.socket.client.Socket socket;
    public static final String TAG = "Controls";
    Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controls);
        socketConnection();
        left = (Button) findViewById(R.id.btnLeft);
        right = (Button) findViewById(R.id.btnRight);
        forward = (Button) findViewById(R.id.btnForward);
        backward = (Button) findViewById(R.id.btnBackward);
        gson = new Gson();

        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    disableRight();
                    disableForward();
                    disableBackward();
                    sendSocketData("left");
                    Log.d(TAG, "onTouch: Left down");

                }else if(event.getAction() == MotionEvent.ACTION_UP){
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
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    disableLeft();
                    disableForward();
                    disableBackward();
                    sendSocketData("right");
                    Log.d(TAG, "onTouch: right down");

                }else if(event.getAction() == MotionEvent.ACTION_UP){
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
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    disableRight();
                    disableLeft();
                    disableBackward();
                    sendSocketData("forward");
                    Log.d(TAG, "onTouch: forward down");

                }else if(event.getAction() == MotionEvent.ACTION_UP){
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
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    disableRight();
                    disableForward();
                    disableLeft();
                    sendSocketData("backward");
                    Log.d(TAG, "onTouch: backward down");

                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    enableRight();
                    enableForward();
                    enableLeft();
                    sendSocketData("stop");
                    Log.d(TAG, "onTouch: forward stop");
                }
                return false;
            }
        });




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
            socket = IO.socket("http://192.168.1.36:8888/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "SensorListner: constructor");
        socket.connect();
        Log.d(TAG, "SensorListner: called");


        socket.on(io.socket.client.Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, "call: inside connect");
                socket.emit("foo" , "hii");
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

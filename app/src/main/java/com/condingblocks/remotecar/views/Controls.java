package com.condingblocks.remotecar.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.condingblocks.remotecar.R;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controls);
        socketConnection();
        left = (Button) findViewById(R.id.btnLeft);
        right = (Button) findViewById(R.id.btnRight);
        forward = (Button) findViewById(R.id.btnForward);
        backward = (Button) findViewById(R.id.btnBackward);


        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    disableRight();
                    disableForward();
                    disableBackward();


                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    enableRight();
                    enableForward();
                    enableBackward();
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


                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    enableLeft();
                    enableForward();
                    enableBackward();
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


                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    enableRight();
                    enableLeft();
                    enableBackward();
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


                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    enableRight();
                    enableForward();
                    enableLeft();
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
            socket = IO.socket("https://car-remote.herokuapp.com/");
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
}

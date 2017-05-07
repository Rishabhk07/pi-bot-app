package com.condingblocks.remotecar;

import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.condingblocks.remotecar.utils.SensorListner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    SensorManager sensorManager;
    public static final String TAG = "MainActivity";
    public static SensorListner sensorListner = new SensorListner();
    public static ImageView imageIv;
    Display display;
    Point point;
    static int maxX;
    static int maxY;
    Button btnLeft;
    Button btnRight;

    public static boolean enableLeft = false;
    public static boolean enableRight = false;


    Sensor accelSensor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        display = getWindowManager().getDefaultDisplay();

        point = new Point();
        display.getSize(point);
        maxX = point.x;
        maxY = point.y;
        btnLeft = (Button) findViewById(R.id.btnDirection);
        btnRight = (Button) findViewById(R.id.btnAccel);
        imageIv = (ImageView) findViewById(R.id.circleIV);
        InputStream inputStream = null;
        try {
            inputStream = getAssets().open("Test.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String buf = "";
            while((buf = reader.readLine()) != null){
                sb.append(buf);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());



        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if(accelSensor != null ){

            Log.d(TAG, "onCreate: " + accelSensor.getName());

        }else{
            Log.d(TAG, "onCreate: Sensor not created" );
        }

        btnLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    enableLeft = true;
                    enableRight = false;
                    btnRight.setEnabled(false);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    enableLeft = false;
                    enableRight = false;
                    btnRight.setEnabled(true);
                }
                return false;
            }
        });

        btnRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    enableLeft = false;
                    enableRight = true;
                    btnLeft.setEnabled(false);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    enableLeft = false;
                    enableRight = false;
                    btnLeft.setEnabled(true);
                }
                return false;
            }
        });




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
                SensorManager.SENSOR_DELAY_GAME
        );
    }

    public static void changePosition(){
        float x = sensorListner.model.getAccelX();
        float y = sensorListner.model.getAccelY();


        if(x > maxX - 100){
            x = maxX-100;
        }else if (x < 1){
            x = 1;
        }

        if(y > maxY -400){
            y = maxY - 400;
        }else if (y < 0){
            y = 0;
        }


        imageIv.setX(x);
        imageIv.setY(y);
    }
}

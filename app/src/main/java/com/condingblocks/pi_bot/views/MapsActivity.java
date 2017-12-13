package com.condingblocks.pi_bot.views;

import android.content.Intent;
import android.location.LocationManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.condingblocks.pi_bot.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ResourceBundle;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FloatingActionButton shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        shareButton = (FloatingActionButton) findViewById(R.id.btnShare);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng wheelchair;
        if(Controls.lat != null && Controls.longi != null){
            wheelchair = new LatLng(Controls.lat,Controls.longi);
            mMap.addMarker(new MarkerOptions().position(wheelchair).title("your Wheelchair"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(wheelchair));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
        }

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://maps.google.com/maps?saddr=" + Controls.lat+","+Controls.longi;

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);

                sharingIntent.setType("text/plain");

                String string = "Here is location ";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT,string);
                sharingIntent.putExtra(Intent.EXTRA_TEXT,uri);
                startActivity(Intent.createChooser(sharingIntent,"share via"));
            }
        });
    }
}

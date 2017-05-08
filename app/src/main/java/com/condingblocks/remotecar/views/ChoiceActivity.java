package com.condingblocks.remotecar.views;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.condingblocks.remotecar.MainActivity;
import com.condingblocks.remotecar.R;

public class ChoiceActivity extends AppCompatActivity {
    CardView mountainCard;
    CardView roadCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        mountainCard = (CardView) findViewById(R.id.cardMountain);
        roadCard = (CardView) findViewById(R.id.cardRoad);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Pi Bot");
        actionBar.setIcon(R.mipmap.rasp);


        mountainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChoiceActivity.this , Controls.class);
                startActivity(i);
            }
        });


        roadCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChoiceActivity.this , MainActivity.class);
                startActivity(i);
            }
        });
    }
}

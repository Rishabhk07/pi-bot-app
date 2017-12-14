package com.condingblocks.pi_bot.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.condingblocks.pi_bot.R;

public class SignupActivity extends AppCompatActivity {

    Button signupBtn;
    EditText nameET;
    EditText emailEt;
    EditText passwordET;
    TextView loginLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupBtn = (Button) findViewById(R.id.signup_btn);
        nameET = (EditText) findViewById(R.id.name_signup);
        emailEt = (EditText) findViewById(R.id.email_signup);
        passwordET = (EditText) findViewById(R.id.password_signup);
        loginLink = (TextView) findViewById(R.id.login_link);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                signup();
            }
        });

    }
}

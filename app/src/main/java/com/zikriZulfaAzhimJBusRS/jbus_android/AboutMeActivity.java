package com.zikriZulfaAzhimJBusRS.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class AboutMeActivity extends AppCompatActivity {
    private TextView usernameTextView, emailTextView, initialTextView, balanceTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        usernameTextView = findViewById(R.id.username);
        emailTextView = findViewById(R.id.email);
        initialTextView = findViewById(R.id.initial);
        balanceTextView = findViewById(R.id.balance);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String username = extras.getString("USERNAME_KEY");
            String email = extras.getString("EMAIL_KEY");
            String initial = extras.getString("INITIAL_KEY");

            Log.d("Email", "Nilai email sebelum dikirim: " + email);
            Log.d("Initial", "Nilai initial sebelum dikirim: " + initial);
            usernameTextView.setText(username);
            emailTextView.setText(email);
            initialTextView.setText("Z");
            balanceTextView.setText("100000");

        }
    }
    }

package com.zikriZulfaAzhimJBusRS.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    private EditText usernameEditText, emailEditText;
    private Button registerButton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        usernameEditText = findViewById(R.id.Username);
        emailEditText = findViewById(R.id.Email);

        registerButton = findViewById(R.id.Sign_In_Button);
        registerButton.setOnClickListener(v -> {

            String username = usernameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String initial = getInitials(username);


            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);

            intent.putExtra("USERNAME_KEY", username);
            intent.putExtra("EMAIL_KEY", email);
            intent.putExtra("INITIAL_KEY", initial);

            startActivity(intent);
            finish();
        });
    }
    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    private String getInitials(String username) {
        StringBuilder initials = new StringBuilder();
        boolean makeNextCapital = true;

        if (username != null && !username.isEmpty()) {
            for (int i = 0; i < username.length(); i++) {
                if (makeNextCapital && Character.isLetter(username.charAt(i))) {
                    initials.append(Character.toUpperCase(username.charAt(i)));
                    makeNextCapital = false;
                } else if (Character.isWhitespace(username.charAt(i))) {
                    makeNextCapital = true;
                }
            }
        }
        return initials.toString();
    }
}
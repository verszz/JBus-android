package com.zikriZulfaAzhimJBusRS.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zikriZulfaAzhimJBusRS.jbus_android.model.Account;
import com.zikriZulfaAzhimJBusRS.jbus_android.model.BaseResponse;
import com.zikriZulfaAzhimJBusRS.jbus_android.request.BaseApiService;
import com.zikriZulfaAzhimJBusRS.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The type Register activity.
 */
public class RegisterActivity extends AppCompatActivity {
//    private EditText usernameEditText, emailEditText;
    private Button registerButton = null;
    private BaseApiService mApiService;
    private Context mContext;
    private EditText name, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
//        usernameEditText = findViewById(R.id.Username);
//        emailEditText = findViewById(R.id.Email);
        mContext = this;
        mApiService = UtilsApi.getApiService();
// sesuaikan dengan ID yang kalian buat di layout
        name = findViewById(R.id.Username);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        registerButton = findViewById(R.id.Sign_In_Button);
        registerButton.setOnClickListener(v -> {
            handleRegister();
            moveActivity(this, LoginActivity.class);
//            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
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

    /**
     * Handle register.
     */
    protected void handleRegister() {
// handling empty field
        String nameS = name.getText().toString();
        String emailS = email.getText().toString();
        String passwordS = password.getText().toString();
        if (nameS.isEmpty() || emailS.isEmpty() || passwordS.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        mApiService.register(nameS, emailS, passwordS).enqueue(new Callback<BaseResponse<Account>>() {
            @Override
            public void onResponse(Call<BaseResponse<Account>> call, Response<BaseResponse<Account>> response) {
// handle the potential 4xx & 5xx error
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<Account> res = response.body();
// if success finish this activity (back to login activity)
                if (res.success) finish();
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<BaseResponse<Account>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewToast(Context ctx, String message){
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

}
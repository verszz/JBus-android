package com.zikriZulfaAzhimJBusRS.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zikriZulfaAzhimJBusRS.jbus_android.model.Account;
import com.zikriZulfaAzhimJBusRS.jbus_android.model.BaseResponse;
import com.zikriZulfaAzhimJBusRS.jbus_android.request.BaseApiService;
import com.zikriZulfaAzhimJBusRS.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextView registerNow = null;
    private Button loginButton = null;
    private EditText email, password;
    private BaseApiService mApiService;
    private Context mContext;
    public static Account LoggedAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        mApiService = UtilsApi.getApiService();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registerNow = findViewById(R.id.register_now);
        loginButton = findViewById(R.id.login_button);
        registerNow.setOnClickListener(v -> {moveActivity(this, RegisterActivity.class);});
        loginButton.setOnClickListener(v -> {
            handleLogin();


            });
        getSupportActionBar().hide();

    }
    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }
    protected void handleLogin() {
// handling empty field
        String emailS = email.getText().toString();
        String passwordS = password.getText().toString();
        if (emailS.isEmpty() || passwordS.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        mApiService.login(emailS, passwordS).enqueue(new Callback<BaseResponse<Account>>() {
            @Override
            public void onResponse(Call<BaseResponse<Account>> call, Response<BaseResponse<Account>> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                BaseResponse<Account> res = response.body();

                if (res.success)
                    finish();
                LoggedAccount = res.payload;
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
                moveActivity(mContext, MainActivity.class);
            }
            @Override
            public void onFailure(Call<BaseResponse<Account>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


    private void viewToast(Context ctx, String message){
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }
}
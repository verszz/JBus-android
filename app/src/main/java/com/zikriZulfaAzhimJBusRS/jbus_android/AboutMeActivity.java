package com.zikriZulfaAzhimJBusRS.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

/**
 * The type About me activity.
 */
public class AboutMeActivity extends AppCompatActivity {
    /**
     * The constant LoggedAccount.
     */
    public static Account LoggedAccount;
    private BaseApiService mApiService;
    private Context mContext;
    private Button topUpButton, manageBusButton = null;
    private EditText topUp;
    private TextView usernameTextView, emailTextView, initialTextView, balanceTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        mContext = this;
        mApiService = UtilsApi.getApiService();
        getSupportActionBar().setTitle("About Me");

        LoggedAccount = LoginActivity.LoggedAccount;

        usernameTextView = findViewById(R.id.username);
        emailTextView = findViewById(R.id.email);
        initialTextView = findViewById(R.id.initial);
        balanceTextView = findViewById(R.id.balance);

        topUp = findViewById(R.id.topUp);

        topUpButton = findViewById(R.id.topUpButton);
        manageBusButton = findViewById(R.id.manageBusButton);



        if (LoginActivity.LoggedAccount != null) {
            usernameTextView.setText(LoginActivity.LoggedAccount.name);
            emailTextView.setText(LoginActivity.LoggedAccount.email);

            initialTextView.setText(getInitials(LoginActivity.LoggedAccount.name));

            if (LoginActivity.LoggedAccount.company != null) {
                // Already a renter
                TextView textView = findViewById(R.id.statusTextView);
                textView.setText("Anda sudah terdaftar sebagai Penyewa");

                TextView registerRenter = findViewById(R.id.registerRenter);
                registerRenter.setVisibility(View.GONE);

                Button manageBusButton = findViewById(R.id.manageBusButton);
                manageBusButton.setOnClickListener(v -> {
                    moveActivity(mContext, ManageBusActivity.class);
                });
            } else {
                // Not a renter
                TextView textView = findViewById(R.id.statusTextView);
                textView.setText("Anda belum terdaftar sebagai Penyewa");

                Button manageBusButton = findViewById(R.id.manageBusButton);
                manageBusButton.setVisibility(View.GONE);

                TextView registerCompany = findViewById(R.id.registerRenter);
                registerCompany.setOnClickListener(v -> {
                    moveActivity(mContext, RegisterRenterActivity.class);
                });
            }


        }
            topUpButton.setOnClickListener(v -> {
            handleTopUp();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Fetch updated account details from the server
        mApiService.getAccountbyId(LoggedAccount.id).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Account loggedAccount = response.body();
                    TextView balanceTextView =  findViewById(R.id.balance);
                    balanceTextView.setText("IDR " + String.valueOf(loggedAccount.balance));
                } else {
                    Toast.makeText(AboutMeActivity.this, "Failed to get account details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
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
     * Handle top up.
     */
    protected void handleTopUp() {
    String amountString = topUp.getText().toString();
    if (!amountString.isEmpty()) {
        double amount = Double.parseDouble(amountString);

        mApiService.topUp(LoginActivity.LoggedAccount.id, amount).enqueue(new Callback<BaseResponse<Double>>() {
            @Override
            public void onResponse(Call<BaseResponse<Double>> call, Response<BaseResponse<Double>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<Double> res = response.body();
                if (res.success) {
                    LoginActivity.LoggedAccount.balance += res.payload;
                    finish();
                    startActivity(getIntent());
                    Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Top-up failed: " + res.message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Double>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    } else {
        Toast.makeText(mContext, "Please enter the amount", Toast.LENGTH_SHORT).show();
    }
}
}

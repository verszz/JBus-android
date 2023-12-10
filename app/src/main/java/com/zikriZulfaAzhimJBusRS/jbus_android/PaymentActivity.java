package com.zikriZulfaAzhimJBusRS.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.zikriZulfaAzhimJBusRS.jbus_android.model.Payment;
import com.zikriZulfaAzhimJBusRS.jbus_android.request.BaseApiService;
import com.zikriZulfaAzhimJBusRS.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The type Payment activity.
 */
public class PaymentActivity extends AppCompatActivity {

    private ListView paymentListView;
    private PaymentArrayAdapter paymentAdapter;
    private BaseApiService mApiService;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        paymentListView = findViewById(R.id.payment_listView);
        paymentAdapter = new PaymentArrayAdapter(this, new ArrayList<>());
        paymentListView.setAdapter(paymentAdapter);

        mApiService = UtilsApi.getApiService();

        int buyerId = LoginActivity.LoggedAccount.id; // Ganti dengan cara sesuai untuk mendapatkan ID pembeli
        loadMyPayment(buyerId);
    }

    private void loadMyPayment(int buyerId) {
        mApiService.getMyPayment(buyerId).enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Payment> paymentList = response.body();
                    displayPaymentList(paymentList);
                } else {
                    Toast.makeText(PaymentActivity.this, "Gagal mendapatkan detail pembayaran", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {
                Toast.makeText(PaymentActivity.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayPaymentList(List<Payment> paymentList) {
        paymentAdapter.clear();
        paymentAdapter.addAll(paymentList);
    }
}
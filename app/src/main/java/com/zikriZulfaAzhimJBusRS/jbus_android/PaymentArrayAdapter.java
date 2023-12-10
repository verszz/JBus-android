package com.zikriZulfaAzhimJBusRS.jbus_android;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.zikriZulfaAzhimJBusRS.jbus_android.model.BaseResponse;
import com.zikriZulfaAzhimJBusRS.jbus_android.model.Bus;
import com.zikriZulfaAzhimJBusRS.jbus_android.model.Payment;
import com.zikriZulfaAzhimJBusRS.jbus_android.request.BaseApiService;
import com.zikriZulfaAzhimJBusRS.jbus_android.request.UtilsApi;

import java.util.List;

import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * The type Payment array adapter.
 */
public class PaymentArrayAdapter extends ArrayAdapter<Payment> {

    private Context mContext;
    private List<Payment> paymentList;
    private BaseApiService mApiService;
//    private List<Bus> busList;

    /**
     * Instantiates a new Payment array adapter.
     *
     * @param context the context
     * @param list    the list
     */
    public PaymentArrayAdapter(Context context, List<Payment> list) {
        super(context, 0, list);
        mContext = context;
        paymentList = list;
        mApiService = UtilsApi.getApiService();
//        busList = Response.body();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.payment_list_item, parent, false);
        }

        Payment currentPayment = paymentList.get(position);
//        String busName = findBusName(busList, currentPayment.busId);

        TextView paymentIdTextView = listItem.findViewById(R.id.payment_id);
        TextView amountTextView = listItem.findViewById(R.id.payment_amount);
        TextView dateTextView = listItem.findViewById(R.id.payment_date);
//        TextView busTextView = listItem.findViewById(R.id.payment_bus);

        // Set data to the respective TextViews
        paymentIdTextView.setText("Payment ID: " + currentPayment.id);
//        busTextView.setText("Bus: "+currentPayment.busId);
        amountTextView.setText("Bus Seat: " + currentPayment.busSeat);
        dateTextView.setText("Date: " + currentPayment.departureDate);

        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Payment clickedPayment = paymentList.get(position);
                showConfirmationDialog(clickedPayment);
            }
        });
        return listItem;
    }


    private void showConfirmationDialog(Payment payment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Konfirmasi Pembayaran")
                .setMessage("Informasi pembayaran:\nID: " + payment.id + "\nStatus: " + payment.status)
                .setPositiveButton("Lanjutkan Pembayaran", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Panggil API untuk meneruskan pembayaran dengan ID payment.id
                        callAcceptPaymentAPI(payment.id);
                    }
                })
                .setNegativeButton("Batalkan Pembayaran", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Panggil API untuk membatalkan pembayaran dengan ID payment.id
                        callCancelPaymentAPI(payment.id);
                    }
                })
                .show();
    }

    private void callAcceptPaymentAPI(int paymentId) {
        // Inisialisasi Retrofit dan service
         mApiService.acceptPayment(paymentId).enqueue(new Callback<BaseResponse<Payment>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Payment>> call, Response<BaseResponse<Payment>> response) {
                        if (response.isSuccessful()) {
                            BaseResponse<Payment> paymentResponse = response.body();
                            if (paymentResponse != null && paymentResponse.success) {
                                // Handle keberhasilan meneruskan pembayaran di sini
                                Toast.makeText(mContext, "Pembayaran berhasil dilanjutkan", Toast.LENGTH_SHORT).show();
                            } else {
                                // Handle jika terjadi kesalahan dalam meneruskan pembayaran
                                Toast.makeText(mContext, "Gagal melanjutkan pembayaran", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Handle jika terjadi kesalahan pada response dari API
                            Toast.makeText(mContext, "Terjadi kesalahan pada response API", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Payment>> call, Throwable t) {
                        // Handle jika terjadi kesalahan saat melakukan panggilan API
                        Toast.makeText(mContext, "Terjadi kesalahan saat melakukan panggilan API", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void callCancelPaymentAPI(int paymentId) {
        // Inisialisasi Retrofit dan service
        mApiService.cancelPayment(paymentId).enqueue(new Callback<BaseResponse<Payment>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Payment>> call, Response<BaseResponse<Payment>> response) {
                        if (response.isSuccessful()) {
                            BaseResponse<Payment> paymentResponse = response.body();
                            if (paymentResponse != null && paymentResponse.success) {
                                // Handle keberhasilan pembatalan pembayaran di sini
                                Toast.makeText(mContext, "Pembayaran berhasil dibatalkan", Toast.LENGTH_SHORT).show();
                            } else {
                                // Handle jika terjadi kesalahan dalam pembatalan pembayaran
                                Toast.makeText(mContext, "Gagal membatalkan pembayaran", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Handle jika terjadi kesalahan pada response dari API
                            Toast.makeText(mContext, "Terjadi kesalahan pada response API", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Payment>> call, Throwable t) {
                        // Handle jika terjadi kesalahan saat melakukan panggilan API
                        Toast.makeText(mContext, "Terjadi kesalahan saat melakukan panggilan API", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}

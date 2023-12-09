package com.zikriZulfaAzhimJBusRS.jbus_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.zikriZulfaAzhimJBusRS.jbus_android.model.BaseResponse;
import com.zikriZulfaAzhimJBusRS.jbus_android.model.Bus;
import com.zikriZulfaAzhimJBusRS.jbus_android.model.Schedule;
import com.zikriZulfaAzhimJBusRS.jbus_android.request.BaseApiService;
import com.zikriZulfaAzhimJBusRS.jbus_android.request.UtilsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteBusActivity extends AppCompatActivity {

    private BaseApiService mApiService;
    private Context mContext;
    private int busId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_bus);
        mContext = this;
        mApiService = UtilsApi.getApiService();

        busId = this.getIntent().getIntExtra("busId", -1); // -1 is the default value if busId is not found

        Button yesButton = this.findViewById(R.id.yes_button);
        Button noButton = this.findViewById(R.id.no_button);
        Log.e("DeleteBusActivity", "onCreate method is running");
        yesButton.setOnClickListener(v->{
            handleDelete();
        });
        noButton.setOnClickListener(v->{
            moveActivity(this, ManageBusActivity.class);
        });

    }

//    private void showDeleteDialog() {
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
//        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View dialogView = inflater.inflate(R.layout.activity_delete_bus, null);
//        dialogBuilder.setView(dialogView);
//
//        Button yesButton = dialogView.findViewById(R.id.yes_button);
//        Button noButton = dialogView.findViewById(R.id.no_button);
//
//        AlertDialog alertDialog = dialogBuilder.create();
//        alertDialog.show();
//
//        yesButton.setOnClickListener(v -> {
//            handleDelete();
//            alertDialog.dismiss(); // Tutup dialog setelah melakukan aksi hapus
//        });
//
//        noButton.setOnClickListener(v -> alertDialog.dismiss());
//    }

    protected void handleDelete() {
        mApiService.deleteBus(busId).enqueue(new Callback<BaseResponse<Bus>>() {
            @Override
            public void onResponse(Call<BaseResponse<Bus>> call, Response<BaseResponse<Bus>> response) {
                if (!response.isSuccessful()) return;

                moveActivity(mContext, ManageBusSchedule.class);
                Log.d("DeleteBusActivity", "onResponse method is called");

            }

            @Override
            public void onFailure(Call<BaseResponse<Bus>> call, Throwable t) {
                Log.d("DeleteBusActivity", "onFailure method is called");
            }
        });
    }

    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        mContext.startActivity(intent);
    }
}
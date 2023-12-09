package com.zikriZulfaAzhimJBusRS.jbus_android;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.zikriZulfaAzhimJBusRS.jbus_android.model.BaseResponse;
import com.zikriZulfaAzhimJBusRS.jbus_android.model.Bus;
import com.zikriZulfaAzhimJBusRS.jbus_android.model.Schedule;
import com.zikriZulfaAzhimJBusRS.jbus_android.request.BaseApiService;
import com.zikriZulfaAzhimJBusRS.jbus_android.request.UtilsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The type Personal bus array adapter.
 */
public class PersonalBusArrayAdapter extends ArrayAdapter<Bus> {

    private Context mContext;
    private int mResource;


    /**
     * Instantiates a new Personal bus array adapter.
     *
     * @param context the context
     * @param busList the bus list
     */
    public PersonalBusArrayAdapter(@NonNull Context context, int resource, List<Bus> busList) {
        super(context, resource, busList);
        mContext = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;


        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.personal_bus_view, parent, false);
        }

        Bus currentBus = getItem(position);
        TextView busNameTextView = listItemView.findViewById(R.id.busView);


        Button addSched = listItemView.findViewById(R.id.manage_button);
        Button deleteBus = listItemView.findViewById(R.id.delete_button);
        deleteBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int busId = currentBus.getId();
                Intent intent = new Intent(mContext, DeleteBusActivity.class);
                intent.putExtra("busId", busId); // Pass busId to DeleteBusActivity
                mContext.startActivity(intent);
            }

        });
        addSched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveActivity(mContext, ManageBusSchedule.class);
            }

        });



        if (currentBus != null) {
            busNameTextView.setText(currentBus.name);
            }

        return listItemView;
    }


    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        mContext.startActivity(intent);
    }

    @Override
    public Bus getItem(int position) {
        return super.getItem(position);
    }
}

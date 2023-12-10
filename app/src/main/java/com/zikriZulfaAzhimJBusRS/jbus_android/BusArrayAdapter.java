package com.zikriZulfaAzhimJBusRS.jbus_android;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zikriZulfaAzhimJBusRS.jbus_android.model.Bus;

import java.util.List;

/**
 * The type Bus array adapter.
 */
public class BusArrayAdapter extends ArrayAdapter<Bus> {
    private Context mContext;
    /**
     * The constant busid.
     */
    public static int busid;

    /**
     * Instantiates a new Bus array adapter.
     *
     * @param context the context
     * @param busList the bus list
     */
    public BusArrayAdapter(@NonNull Context context, List<Bus> busList) {
        super(context, 0, busList);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.bus_view, parent, false);
        }
        Bus currentBus = getItem(position);

        TextView busNameTextView = listItemView.findViewById(R.id.busName);
        TextView departureStationTextView = listItemView.findViewById(R.id.departure);
        TextView arrivalStationTextView = listItemView.findViewById(R.id.arrival);
        busNameTextView.setText(currentBus.name);
        departureStationTextView.setText("Departure: " + currentBus.departure);
        arrivalStationTextView.setText("Arrival: " + currentBus.arrival);

        Button bookButton = listItemView.findViewById(R.id.booking_button);

        bookButton.setOnClickListener(v -> {
            Log.d("ButtonClickListener", "Button clicked");
            Intent i = new Intent(mContext, BookingActivity.class);
            busid = currentBus.id;
            i.putExtra("busId", currentBus.id);
            mContext.startActivity(i);
        });


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

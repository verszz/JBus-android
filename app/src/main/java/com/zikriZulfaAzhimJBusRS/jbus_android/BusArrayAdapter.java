package com.zikriZulfaAzhimJBusRS.jbus_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zikriZulfaAzhimJBusRS.jbus_android.model.Bus;

import java.util.List;

public class BusArrayAdapter extends ArrayAdapter<Bus> {

    public BusArrayAdapter(@NonNull Context context, List<Bus> busList) {
        super(context, 0, busList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.bus_view, parent, false);
        }

        TextView busNameTextView = listItemView.findViewById(R.id.busName);
        TextView departureStationTextView = listItemView.findViewById(R.id.departure);
        TextView arrivalStationTextView = listItemView.findViewById(R.id.arrival);

        Bus currentBus = getItem(position);

        if (currentBus != null) {
            busNameTextView.setText(currentBus.name);
            departureStationTextView.setText("Departure: " + currentBus.departure);
            arrivalStationTextView.setText("Arrival: " + currentBus.arrival);
        }

        return listItemView;
    }
}

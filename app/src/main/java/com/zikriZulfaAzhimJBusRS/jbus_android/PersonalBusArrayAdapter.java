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

public class PersonalBusArrayAdapter extends ArrayAdapter<Bus> {

    public PersonalBusArrayAdapter(@NonNull Context context, List<Bus> busList) {
        super(context, 0, busList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.personal_bus_view, parent, false);
        }

        TextView busNameTextView = listItemView.findViewById(R.id.busName);

        Bus currentBus = getItem(position);

        if (currentBus != null) {
            busNameTextView.setText(currentBus.name);
            }

        return listItemView;
    }


}

package com.zikriZulfaAzhimJBusRS.jbus_android;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
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
 * The type Personal bus array adapter.
 */
public class PersonalBusArrayAdapter extends ArrayAdapter<Bus> {
    private Context mContext;


    /**
     * Instantiates a new Personal bus array adapter.
     *
     * @param context the context
     * @param busList the bus list
     */
    public PersonalBusArrayAdapter(@NonNull Context context, List<Bus> busList) {
        super(context, 0, busList);
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

        Button addSched = listItemView.findViewById(R.id.button3);
        addSched.setOnClickListener(v -> {
                moveActivity(mContext, ManageBusSchedule.class);
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
}

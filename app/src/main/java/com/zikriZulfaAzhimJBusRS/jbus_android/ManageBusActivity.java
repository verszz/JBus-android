package com.zikriZulfaAzhimJBusRS.jbus_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zikriZulfaAzhimJBusRS.jbus_android.model.Bus;
import com.zikriZulfaAzhimJBusRS.jbus_android.model.Schedule;
import com.zikriZulfaAzhimJBusRS.jbus_android.request.BaseApiService;
import com.zikriZulfaAzhimJBusRS.jbus_android.request.UtilsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The type Manage bus activity.
 */
public class ManageBusActivity extends AppCompatActivity {
    private BaseApiService mApiService;
    private Context mContext;
    private ListView myBusListView;
    /**
     * The Add sched.
     */
    ImageView addSched;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bus);

        getSupportActionBar().setTitle("Manage Bus");

        mContext = this;
        mApiService = UtilsApi.getApiService();
        myBusListView = this.findViewById(R.id.manageBus_listView);

        loadMyBus();



    }

    /**
     * Load my bus.
     */
    protected void loadMyBus() {
        mApiService.getMyBus(LoginActivity.LoggedAccount.id).enqueue(new Callback<List<Bus>>() {
            @Override
            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
                if (!response.isSuccessful()) return;

                List<Bus> myBusList = response.body();
                MyArrayAdapter adapter = new MyArrayAdapter(mContext, myBusList);
                myBusListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {

            }
        });
    }

    private class MyArrayAdapter extends ArrayAdapter<Bus> {

        /**
         * Instantiates a new My array adapter.
         *
         * @param context the context
         * @param objects the objects
         */
        public MyArrayAdapter(@NonNull Context context, @NonNull List<Bus> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View currentItemView = convertView;

            // of the recyclable view is null then inflate the custom layout for the same
            if (currentItemView == null) {
                currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.personal_bus_view, parent, false);
            }

            // get the position of the view from the ArrayAdapter
            Bus currentBus = getItem(position);

            // then according to the position of the view assign the desired TextView 1 for the same
            TextView busName = currentItemView.findViewById(R.id.busView);
            busName.setText(currentBus.name);

            // then according to the position of the view assign the desired TextView 2 for the same
            Button addSched = currentItemView.findViewById(R.id.manage_button);
            addSched.setOnClickListener(v->{
                Intent i = new Intent(mContext, ManageBusSchedule.class);
                i.putExtra("busId", currentBus.id);
                mContext.startActivity(i);
            });

            // then return the recyclable view
            return currentItemView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu_manage_bus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.add_button) {
            Intent intent = new Intent(mContext, AddBusActivity.class);
            intent.putExtra("type", "addBus");
            startActivity(intent);
            return true;
        } else return super.onOptionsItemSelected(item);
    }
}
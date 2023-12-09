package com.zikriZulfaAzhimJBusRS.jbus_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zikriZulfaAzhimJBusRS.jbus_android.model.Bus;
import com.zikriZulfaAzhimJBusRS.jbus_android.request.BaseApiService;
import com.zikriZulfaAzhimJBusRS.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    BusArrayAdapter obj = null;
    ListView list;
    private Button[] btns;
    private int currentPage = 0;
    private int pageSize = 7;
    private int listSize;
    private int noOfPages;
    private List<Bus> listBus = new ArrayList<>();
    private Button prevButton = null;
    private Button nextButton = null;
    private ListView busListView = null;
    private HorizontalScrollView pageScroll = null;
    private BaseApiService mApiService;
    private Context mContext;
    public static int selectedBookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().show();
        mContext = this;
        mApiService = UtilsApi.getApiService();
        list = findViewById(R.id.bus_listView);
        obj = new BusArrayAdapter(this, new ArrayList<>());
        list.setAdapter(obj);
        loadMyBus();


        prevButton = findViewById(R.id.prev_page);
        nextButton = findViewById(R.id.next_page);
        pageScroll = findViewById(R.id.page_number_scroll);
        //busListView = findViewById(R.id.bus_listView);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                position -= 1;
                if (position >= 0 && position < listBus.size()) {
                    Toast.makeText(getApplicationContext(), "Selected item at position: " + position, Toast.LENGTH_LONG).show();
                    selectedBookId = listBus.get(position).id;
                    Intent intent = new Intent(mContext, BookingActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Got wrong  ", Toast.LENGTH_LONG).show();                }
            }
        });

        listSize = 0; // Initialize listSize

        paginationFooter();
        goToPage(currentPage);

        prevButton.setOnClickListener(v -> {
            currentPage = currentPage != 0 ? currentPage - 1 : 0;
            goToPage(currentPage);
        });

        nextButton.setOnClickListener(v -> {
            currentPage = currentPage != noOfPages - 1 ? currentPage + 1 : currentPage;
            goToPage(currentPage);
        });
    }

    protected void loadMyBus() {
        mApiService.getMyBus(LoginActivity.LoggedAccount.id).enqueue(new Callback<List<Bus>>() {
            @Override
            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
                if (response.isSuccessful()) {
                    List<Bus> myBusList = response.body();
                    MyArrayAdapter adapter = new MyArrayAdapter(mContext, myBusList);
                    list.setAdapter(adapter);

                    listSize = myBusList.size(); // Update listSize with received bus list size
                    paginationFooter();
                    goToPage(currentPage);
                }
            }

            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {
                // Handle failure
            }
        });
    }

    private class MyArrayAdapter extends ArrayAdapter<Bus> {

        public MyArrayAdapter(@NonNull Context context, @NonNull List<Bus> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View currentItemView = convertView;

            if (currentItemView == null) {
                currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.bus_view, parent, false);
            }

            Bus currentBus = getItem(position);

            TextView busName = currentItemView.findViewById(R.id.busName);
            TextView departure = currentItemView.findViewById(R.id.departure);
            TextView arrival = currentItemView.findViewById(R.id.arrival);
            busName.setText(currentBus.name);
            departure.setText(currentBus.departure.stationName);
            arrival.setText(currentBus.arrival.stationName);

            return currentItemView;
        }
    }

    private void paginationFooter() {
        int val = listSize % pageSize;
        val = val == 0 ? 0 : 1;
        noOfPages = listSize / pageSize + val;
        LinearLayout ll = findViewById(R.id.btn_layout);
        ll.removeAllViews(); // Clear previous pagination buttons if any
        btns = new Button[noOfPages];

        if (noOfPages <= 6) {
            ((FrameLayout.LayoutParams) ll.getLayoutParams()).gravity = Gravity.CENTER;
        }

        for (int i = 0; i < noOfPages; i++) {
            btns[i] = new Button(this);
            btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btns[i].setText("" + (i + 1));
            btns[i].setTextColor(getResources().getColor(R.color.black));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(100, 100);
            ll.addView(btns[i], lp);

            final int j = i;
            btns[j].setOnClickListener(v -> {
                currentPage = j;
                goToPage(j);
            });
        }
    }

    private void goToPage(int index) {
        for (int i = 0; i < noOfPages; i++) {
            if (i == index) {
                btns[index].setBackgroundDrawable(getResources().getDrawable(R.drawable.circle));
                btns[i].setTextColor(getResources().getColor(android.R.color.white));
                scrollToItem(btns[index]);
                viewPaginatedList(listBus, currentPage);
            } else {
                btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
                btns[i].setTextColor(getResources().getColor(android.R.color.black));
            }
        }
    }

    private void scrollToItem(Button item) {
        int scrollX = item.getLeft() - (pageScroll.getWidth() - item.getWidth()) / 2;
        pageScroll.smoothScrollTo(scrollX, 0);
    }

    private void viewPaginatedList(List<Bus> listBus, int page) {
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, listBus.size());
        List<Bus> paginatedList = listBus.subList(startIndex, endIndex);

        obj.clear();
        obj.addAll(paginatedList);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.person_button) {
            String username = getIntent().getStringExtra("USERNAME_KEY");
            String email = getIntent().getStringExtra("EMAIL_KEY");

            Intent aboutMeIntent = new Intent(MainActivity.this, AboutMeActivity.class);
            aboutMeIntent.putExtra("USERNAME_KEY", username);
            aboutMeIntent.putExtra("EMAIL_KEY", email);

            startActivity(aboutMeIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}

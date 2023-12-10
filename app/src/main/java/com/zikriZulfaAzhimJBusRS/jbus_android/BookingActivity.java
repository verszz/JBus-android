package com.zikriZulfaAzhimJBusRS.jbus_android;

import static android.text.TextUtils.*;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zikriZulfaAzhimJBusRS.jbus_android.model.BaseResponse;
import com.zikriZulfaAzhimJBusRS.jbus_android.model.Bus;
import com.zikriZulfaAzhimJBusRS.jbus_android.model.Payment;
import com.zikriZulfaAzhimJBusRS.jbus_android.model.Schedule;
import com.zikriZulfaAzhimJBusRS.jbus_android.model.Station;
import com.zikriZulfaAzhimJBusRS.jbus_android.request.BaseApiService;
import com.zikriZulfaAzhimJBusRS.jbus_android.request.UtilsApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The type Booking activity.
 */
public class BookingActivity extends AppCompatActivity {
    private BaseApiService mApiService;
    private Bus detailedBus;

    private TextView busNameTextView;
    private TextView capacityTextView;
    private TextView priceTextView;
    private TextView facilitiesTextView;
    private TextView busTypeTextView;
    private TextView departureTextView;
    private TextView arrivalTextView;
    private Spinner scheduleSpinner;
    private Spinner seatSpinner;
    private Button makeBooking;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        // Initialize the ActionBar
        ActionBar actionBar = getSupportActionBar();
        // Set the title of the ActionBar
        if (actionBar != null) {
            actionBar.setTitle("Bus Detail");
        }

        // Extract bus ID from the intent
        int busId = getIntent().getIntExtra("busId", -1);
        if (busId != -1) {
            // Make the API call to get detailed information using busId
            getBusDetails(busId);
        } else {
            // Handle the case when busId is not found in the intent
            Toast.makeText(this, "Bus ID not found", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity
        }
        mContext = this;


        busNameTextView = findViewById(R.id.busName_Book);
        capacityTextView = findViewById(R.id.capacity_Book);
        priceTextView = findViewById(R.id.price_Book);
        facilitiesTextView = findViewById(R.id.facility_Book);
        busTypeTextView = findViewById(R.id.busType_Book);
        departureTextView = findViewById(R.id.departureStation_Book);
        arrivalTextView = findViewById(R.id.arrivalStation_Book);
        scheduleSpinner = findViewById(R.id.schedule_dropdown);
        seatSpinner = findViewById(R.id.seat_dropdown);
        makeBooking = findViewById(R.id.book_button);

        // Update UI components when detailedBus is available
        updateUIComponents();

        // Set a listener for scheduleSpinner item selection
        scheduleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Update seatSpinner based on the selected schedule
                updateSeatSpinner(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        makeBooking = findViewById(R.id.book_button);

        // Set a click listener for the "Booking" button
        makeBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleMakeBooking();
            }
        });
    }

    private void getBusDetails(int busId) {
        mApiService = UtilsApi.getApiService();

        Call<Bus> call = mApiService.getBusbyId(busId);
        call.enqueue(new Callback<Bus>() {
            @Override
            public void onResponse(Call<Bus> call, Response<Bus> response) {
                if (response.isSuccessful() && response.body() != null) {
                    detailedBus = response.body();
                    // Update UI with detailed bus information
                    updateUIComponents();
                } else {
                    Toast.makeText(BookingActivity.this, "Failed to get bus details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Bus> call, Throwable t) {
                Toast.makeText(BookingActivity.this, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateUIComponents() {
        // Check if detailedBus is not null before updating UI
        if (detailedBus != null) {


            // Update UI components with detailed bus information
            busNameTextView.setText(detailedBus.name);
            capacityTextView.setText(String.valueOf(detailedBus.capacity));

            // Format the Price object into a readable string
            String priceText =  "" + detailedBus.price.price;
            priceTextView.setText(priceText);

            // Convert the list of facilities into a readable string
//            String facilitiesText = join(", ", detailedBus.facilities);
//            facilitiesTextView.setText(facilitiesText);

            // Get the readable representation of the BusType enum
            String busTypeText = detailedBus.busType.toString();
            busTypeTextView.setText(busTypeText);

            // Format the Station objects into readable strings
//            String departureText = formatStationText(detailedBus.departure);
            departureTextView.setText(detailedBus.departure.stationName);

//            String arrivalText = formatStationText(detailedBus.arrival);
            arrivalTextView.setText(detailedBus.arrival.stationName);

            List<Schedule> schedules = detailedBus.schedules;

            // Create an ArrayList to store the formatted timestamps
            ArrayList<String> formattedTimestamps = new ArrayList<>();

            // Convert each Timestamp to a String representation and add it to the ArrayList
            for (Schedule schedule : schedules) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedTimestamp = dateFormat.format(schedule.departureSchedule);
                formattedTimestamps.add(formattedTimestamp);
            }

            // Create an ArrayAdapter using the formatted timestamps and a default spinner layout
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, formattedTimestamps);

            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Apply the adapter to the spinner
            scheduleSpinner.setAdapter(adapter);
        }
    }

    private String formatStationText(Station station) {
        if (station != null) {
            // Format the Station information into a readable string
            return station.stationName + " - " + station.city.toString();
        } else {
            return "N/A"; // or handle it differently based on your requirements
        }
    }

    private void updateSeatSpinner(int selectedPosition) {
        if (detailedBus != null && selectedPosition >= 0 && selectedPosition < detailedBus.schedules.size()) {
            // Get the selected schedule
            Schedule selectedSchedule = detailedBus.schedules.get(selectedPosition);

            // Get the seat availability map from the selected schedule
            Map<String, Boolean> seatAvailability = selectedSchedule.seatAvailability;

            // Filter seats with a value of TRUE
            List<String> availableSeats = new ArrayList<>();
            for (Map.Entry<String, Boolean> entry : seatAvailability.entrySet()) {
                if (entry.getValue()) {
                    availableSeats.add(entry.getKey());
                }
            }

            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, availableSeats);

            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Apply the adapter to the spinner
            seatSpinner.setAdapter(adapter);
        }
    }

    private void handleMakeBooking() {
        // Check if detailedBus and selected schedule are not null
        if (detailedBus != null && scheduleSpinner.getSelectedItemPosition() >= 0 && scheduleSpinner.getSelectedItemPosition() < detailedBus.schedules.size()) {
            // Get the selected schedule
            Schedule selectedSchedule = detailedBus.schedules.get(scheduleSpinner.getSelectedItemPosition());

            // Get the selected seat
            String selectedSeat = seatSpinner.getSelectedItem().toString();

            // Check if the seat is available
            if (selectedSchedule.seatAvailability.containsKey(selectedSeat) && selectedSchedule.seatAvailability.get(selectedSeat)) {
                // Make the API call to makeBooking
                makeBooking(selectedSchedule, selectedSeat);
            } else {
                // Handle the case when the selected seat is not available
                Toast.makeText(BookingActivity.this, "Selected seat is not available", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void makeBooking(Schedule selectedSchedule, String selectedSeat) {
        int buyerId = LoginActivity.LoggedAccount.id; // Change this to your actual logic for getting the buyerId
        int renterId = detailedBus.accountId; // Change this to your actual logic for getting the renterId
        int busId = detailedBus.id;
        String departureDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(selectedSchedule.departureSchedule);

        // Make the API call to makeBooking
        mApiService.makeBooking(buyerId, renterId, busId, Arrays.asList(selectedSeat), departureDate).enqueue(new Callback<BaseResponse<Payment>>() {
            @Override
            public void onResponse(Call<BaseResponse<Payment>> call, Response<BaseResponse<Payment>> response) {
                if (response.isSuccessful()) {
                    BaseResponse<Payment> bookingResponse = response.body();
                    if (bookingResponse != null && bookingResponse.success) {
                        // Handle the successful booking
                        Intent intent = new Intent(mContext, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(BookingActivity.this, "Booking successful", Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle the case when the booking was not successful
                        Toast.makeText(BookingActivity.this, bookingResponse.message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle the case when the API call was not successful
                    Toast.makeText(BookingActivity.this, "Failed to make a booking", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Payment>> call, Throwable t) {
                // Handle the case when the API call failed
                t.printStackTrace();
                Toast.makeText(BookingActivity.this, "2Problem with the server"+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

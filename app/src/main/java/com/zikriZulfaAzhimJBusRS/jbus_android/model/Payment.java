package com.zikriZulfaAzhimJBusRS.jbus_android.model;

import androidx.annotation.NonNull;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * The type Payment.
 */
public class Payment extends Invoice {
    /**
     * The Bus id.
     */
    public int busId;
    /**
     * The Departure date.
     */
    public Timestamp departureDate;
    /**
     * The Bus seat.
     */
    public List<String> busSeat;
    @NonNull
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss");
        return dateFormat.format(this.departureDate.getTime()) + "\t\t" +"Seat: "+busSeat+"Status: "+status;
    }

}


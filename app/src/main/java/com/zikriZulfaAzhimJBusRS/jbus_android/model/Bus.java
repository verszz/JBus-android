package com.zikriZulfaAzhimJBusRS.jbus_android.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Bus.
 */
public class Bus extends Serializable {
    /**
     * The Account id.
     */
    public int accountId;
    /**
     * The Name.
     */
    public String name;
    /**
     * The Facilities.
     */
    public List<Facility> facilities;
    /**
     * The Price.
     */
    public Price price;
    /**
     * The Capacity.
     */
    public int capacity;
    /**
     * The Bus type.
     */
    public BusType busType;
    /**
     * The Departure.
     */
    public Station departure;
    /**
     * The Arrival.
     */
    public Station arrival;
    /**
     * The Schedules.
     */
    public List<Schedule> schedules;

    /**
     * Sample bus list list.
     *
     * @param size the size
     * @return the list
     */
    public static List<Bus> sampleBusList(int size) {
        List<Bus> busList = new ArrayList<>();

        for (int i = 1; i <= size; i++) {
            Bus bus = new Bus();
            bus.name = "Bus " + i;
            busList.add(bus);
        }

        return busList;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}

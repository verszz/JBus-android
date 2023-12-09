package com.zikriZulfaAzhimJBusRS.jbus_android.model;

import java.util.List;

public class Payment {
    private int buyerId;
    private int renterId;
    private int busId;
    private List<String> busSeats;
    private String departureDate;

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }


}

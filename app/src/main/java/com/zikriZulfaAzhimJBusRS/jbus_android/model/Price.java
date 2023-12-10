package com.zikriZulfaAzhimJBusRS.jbus_android.model;

/**
 * The type Price.
 */
public class Price {
    /**
     * The Rebate.
     */
    public double rebate;
    /**
     * The Price.
     */
    public double price;
    private double amount;

    @Override
    public String toString() {
        return String.valueOf(amount);
    }
}

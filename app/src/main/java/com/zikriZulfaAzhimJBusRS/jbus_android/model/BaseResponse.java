package com.zikriZulfaAzhimJBusRS.jbus_android.model;

/**
 * The type Base response.
 *
 * @param <T> the type parameter
 */
public class BaseResponse<T> {
    /**
     * The Success.
     */
    public boolean success;
    /**
     * The Message.
     */
    public String message;
    /**
     * The Payload.
     */
    public T payload;

}

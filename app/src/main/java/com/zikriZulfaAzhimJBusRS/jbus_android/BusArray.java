package com.zikriZulfaAzhimJBusRS.jbus_android;

/**
 * The type Bus array.
 */
public class BusArray {
    // the resource ID for the imageView
    private int ivNumbersImageId;

    // TextView 1
    private String mNumberInDigit;

    // TextView 1
    private String mNumbersInText;

    /**
     * Instantiates a new Bus array.
     *
     * @param NumbersImageId the numbers image id
     * @param NumbersInDigit the numbers in digit
     * @param NumbersInText  the numbers in text
     */
// create constructor to set the values for all the parameters of the each single view
    public BusArray(int NumbersImageId, String NumbersInDigit, String NumbersInText) {
        ivNumbersImageId = NumbersImageId;
        mNumberInDigit = NumbersInDigit;
        mNumbersInText = NumbersInText;
    }

    /**
     * Gets numbers image id.
     *
     * @return the numbers image id
     */
// getter method for returning the ID of the imageview
    public int getNumbersImageId() {
        return ivNumbersImageId;
    }

    /**
     * Gets number in digit.
     *
     * @return the number in digit
     */
// getter method for returning the ID of the TextView 1
    public String getNumberInDigit() {
        return mNumberInDigit;
    }

    /**
     * Gets numbers in text.
     *
     * @return the numbers in text
     */
// getter method for returning the ID of the TextView 2
    public String getNumbersInText() {
        return mNumbersInText;
    }
}

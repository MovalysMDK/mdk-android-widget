package com.soprasteria.movalysmdk.widget.position.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Helper for the MDKPosition widget class.
 */
public class PositionHelper {

    /**
     * Constructor.
     */
    private PositionHelper()  {
        // nothing to do
    }

    /**
     * Rounds a double value with the given number of decimals.
     * @param value the value to round
     * @param decimalNumber the number of decimal to keep
     * @return the rounded double value
     */
    public static double round(double value, int decimalNumber) {
        if (decimalNumber < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(decimalNumber, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}

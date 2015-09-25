package com.soprasteria.movalysmdk.widget.position.filter;

import android.text.InputFilter;
import android.text.Spanned;

import com.soprasteria.movalysmdk.widget.core.helper.InputFilterHelper;

/**
 * Input filter for the MDKPosition widget.
 * Checks that the input value is in the correct range, with a number o*f decimal that does not exceed a given number
 */
public class PositionInputFilter implements InputFilter {

    /** the minimum input value. */
    private double min;

    /** the maximum input value. */
    private double max;

    /** the maximum number of decimals. */
    private int maxDecimals;

    /**
     * Constructor.
     * @param min the min value
     * @param max the max value
     * @param dec the max number of decimals
     */
    public PositionInputFilter(double min, double max, int dec) {
        this.min = min;
        this.max = max;
        this.maxDecimals = dec;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        String res = "";

        try {
            double input = Double.parseDouble(InputFilterHelper.getNewValue(source, start, end, dest, dstart, dend));
            if (isInRange(min, max, input) && hasCorrectDecimal(maxDecimals, input)) {
                return null;
            } else if (source.length() == 0) {
                res = dest.subSequence(dstart, dend).toString();
            }
        } catch (NumberFormatException nfe) {
            // nothing to do
        }

        return res;
    }

    /**
     * Returns true if c value is between a and b.
     * @param a the min value
     * @param b the max value
     * @param c the value to test
     * @return true if c value is between a and b
     */
    private boolean isInRange(double a, double b, double c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }

    /**
     * Returns true if input has a maximum of maxDecimals decimals.
     * @param maxDecimals the max number of decimals
     * @param input the value to test
     * @return true if input has a maximum of maxDecimals decimals
     */
    private boolean hasCorrectDecimal(int maxDecimals, double input) {
        String text = Double.toString(Math.abs(input));
        int integerPlaces = text.indexOf('.');
        int decimalPlaces = text.length() - integerPlaces - 1;

        return decimalPlaces <= maxDecimals;
    }
}

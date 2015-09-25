package com.soprasteria.movalysmdk.widget.core.helper;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Helper for the {@link InputFilter} implementations.
 */
public class InputFilterHelper {

    /**
     * returns the string being typed.
     * @param source the replacement string
     * @param start the start of the replacement in source
     * @param end the end of the replacement in source
     * @param dest the string were replacement will be done
     * @param dstart the starting position of the replacement
     * @param dend th ending position of he replacement
     * @return the new string
     */
    public static String getNewValue(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        String newValue;

        String destinationString = dest.toString();
        newValue = destinationString.substring(0, dstart) + source.subSequence(start, end) + destinationString.substring(dend);

        return newValue;
    }
}

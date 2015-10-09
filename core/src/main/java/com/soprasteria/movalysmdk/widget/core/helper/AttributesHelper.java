package com.soprasteria.movalysmdk.widget.core.helper;

import android.content.res.TypedArray;
import android.util.Log;

/**
 * Helper class for the attributes reading and setting to class.
 */
public class AttributesHelper {

    /** TAG for debug. */
    private static final String TAG = AttributesHelper.class.getSimpleName();

    /**
     * Constructor.
     */
    private AttributesHelper() {
        // nothing to do
    }

    /**
     * Returns the integer value parsed from a String attribute.
     * On any error, the default value is returned.
     * @param typedArray an array of attributes
     * @param attributeIndex the index of the attribute to look for
     * @param defaultValue the default value to return
     * @return the read value, or the given default if there was a problem
     */
    public static int getIntFromStringAttribute(TypedArray typedArray, int attributeIndex, int defaultValue) {
        String attrValue = typedArray.getString(attributeIndex);

        if (attrValue == null) {
            return defaultValue;
        }

        try {
            int result = Integer.valueOf(attrValue);
            return result;
        } catch (NumberFormatException e) {
            Log.e(TAG, "Could not parse attribute value", e);
            return defaultValue;
        }

    }

    /**
     * Returns the String value parsed from a String attribute.
     * On any error, the default value is returned.
     * @param typedArray an array of attributes
     * @param attributeIndex the index of the attribute to look for
     * @param defaultValue the default value to return
     * @return the read value, or the given default if there was a problem
     */
    public static String getStringFromStringAttribute(TypedArray typedArray, int attributeIndex, String defaultValue) {
        String attrValue = typedArray.getString(attributeIndex);

        if (attrValue == null) {
            return defaultValue;
        } else {
            return attrValue;
        }
    }

    /**
     * Returns the integer value parsed from a ResourceID attribute.
     * On any error, the default value is returned.
     * @param typedArray an array of attributes
     * @param attributeIndex the index of the attribute to look for
     * @param defaultValue the default value to return
     * @return the read value, or the given default if there was a problem
     */
    public static int getIntFromResourceAttribute(TypedArray typedArray, int attributeIndex, int defaultValue) {
        return typedArray.getResourceId(attributeIndex, defaultValue);
    }

}

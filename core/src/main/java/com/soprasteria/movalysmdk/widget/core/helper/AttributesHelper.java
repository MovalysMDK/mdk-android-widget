package com.soprasteria.movalysmdk.widget.core.helper;

import android.content.res.TypedArray;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
     * Gets the value from an attribute and converts it to the given type.
     * @param typedArray an array of attributes
     * @param attributeIndex the index of the attribute to look for
     * @param attrClass the class of the attribute to read
     * @param defaultValue the default value to return
     * @param <O> the class of the returned value
     * @return the read value, or the given default if there was a problem
     */
    public static <O> O getAttributeValue(TypedArray typedArray, int attributeIndex, Class attrClass, O defaultValue) {
        Object attrValue = null;

        if (attrClass.equals(String.class)) {
            attrValue = typedArray.getString(attributeIndex);
        } else {
            Log.e(TAG, "Cannot convert from " + attrClass.getSimpleName());
            return null;
        }

        if (attrValue == null) {
            return defaultValue;
        }

        O returnedValue = convert(attrValue, defaultValue.getClass());

        if (returnedValue == null) {
            returnedValue = defaultValue;
        }

        return returnedValue;
    }

    /**
     * Convert the given value to the O type.
     * @param value the value to convert
     * @param returnedClass the return class
     * @param <O> the type of the value to return
     * @return the converted value
     */
    private static <O> O convert(Object value, Class<?> returnedClass) {
        O returnedValue = null;

        String methodName = value.getClass().getSimpleName() + "To" + returnedClass.getSimpleName();

        methodName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);

        try {
            Method method = AttributesHelper.class.getDeclaredMethod(methodName, value.getClass());

            if (method != null) {
                returnedValue = (O) method.invoke(null, value);
            }
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "Convert method " + methodName + " does not exists.", e);
        } catch (InvocationTargetException e) {
            Log.e(TAG, "InvocationTargetException", e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "IllegalAccessException", e);
        }

        return returnedValue;
    }

    /**
     * converts a String value to an int.
     * @param valueToConvert the String value to convert
     * @return the int value
     */
    private static Integer stringToInteger(String valueToConvert) {
        return Integer.valueOf(valueToConvert);
    }

}

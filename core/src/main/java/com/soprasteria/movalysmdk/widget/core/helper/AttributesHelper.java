package com.soprasteria.movalysmdk.widget.core.helper;

import android.content.res.TypedArray;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AttributesHelper {

    public static <O> O getAttributeValue(TypedArray typedArray, int attributeIndex, Class attrClass, O defaultValue) {
        Object attrValue = null;

        if (attrClass.equals(String.class)) {
            attrValue = typedArray.getString(attributeIndex);
        } else {
            Log.e("AttributesHelper", "Cannot convert from " + attrClass.getSimpleName());
        }

        O returnedValue = convert(attrValue, defaultValue);

        return returnedValue;
    }

    private static <O> O convert(Object value, O defaultValue) {
        O returnedValue = null;

        if (value != null) {
            String methodName = value.getClass().getSimpleName() + "To" + defaultValue.getClass().getSimpleName();
            try {
                Method method = AttributesHelper.class.getMethod(methodName, value.getClass(), defaultValue.getClass());

                if (method != null) {
                    method.invoke(returnedValue, value);
                }
            } catch (NoSuchMethodException e) {
                Log.e("AttributesHelper", "Convert method " + methodName + " does not exists.");
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (returnedValue == null) {
            returnedValue = defaultValue;
        }

        return returnedValue;
    }

    private static Integer StringToInteger(String valueToConvert) {
        return Integer.valueOf(valueToConvert);
    }

}

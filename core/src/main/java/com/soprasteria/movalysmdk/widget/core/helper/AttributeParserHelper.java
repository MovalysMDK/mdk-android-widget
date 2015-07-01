package com.soprasteria.movalysmdk.widget.core.helper;

import android.util.AttributeSet;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper to parse attibute set from widget and get the right type.
 */
public class AttributeParserHelper {

    /**
     * Return a Map corresponding to the AttributeSet in parameter
     * @param attrs the AttributeSet to parse
     * @return a Map representing the AttributeSet in parameter
     */
    public static Map<Integer, Object> parseAttributeSet(AttributeSet attrs) {
        Map<Integer,Object> rMap = new HashMap<>();

        for (int i = 0; i < attrs.getAttributeCount(); i++) {

            int resId = attrs.getAttributeResourceValue(i, -1);
            boolean booleanValue = attrs.getAttributeBooleanValue(i, false);
            //int listResId = attrs.getAttributeListValue(i, null, -1);
            float floatValue = 0f;
            try {
                floatValue = attrs.getAttributeFloatValue(i, 0f);
            } catch (RuntimeException e) {
                Log.d("AttributeParserHelper", e.getMessage());
            }
            int intValue = attrs.getAttributeIntValue(i, -1);
            int unsignedIntValue = attrs.getAttributeUnsignedIntValue(i, -1);
            String value = attrs.getAttributeValue(i);

            if (resId != -1) {
                rMap.put(attrs.getAttributeNameResource(i), resId);
            }/* else if (listResId != -1) {
                rMap.put(attrs.getAttributeNameResource(i), listResId);
            } */else if (floatValue != 0f) {
                rMap.put(attrs.getAttributeNameResource(i), floatValue);
            } else if (unsignedIntValue != -1) {
                rMap.put(attrs.getAttributeNameResource(i), unsignedIntValue);
            } else if (intValue != -1) {
                rMap.put(attrs.getAttributeNameResource(i), intValue);
            } else if (booleanValue) {
                rMap.put(attrs.getAttributeNameResource(i), booleanValue);
            } else {
                rMap.put(attrs.getAttributeNameResource(i), value);
            }

        }

        return rMap;
    }
}

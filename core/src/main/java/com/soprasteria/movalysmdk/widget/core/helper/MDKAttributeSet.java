/**
 * Copyright (C) 2010 Sopra Steria Group (movalys.support@soprasteria.com)
 *
 * This file is part of Movalys MDK.
 * Movalys MDK is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Movalys MDK is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with Movalys MDK. If not, see <http://www.gnu.org/licenses/>.
 */
package com.soprasteria.movalysmdk.widget.core.helper;

import android.support.annotation.IdRes;
import android.util.AttributeSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Helper to parse attribute set from widget and get the right type.
 */
public class MDKAttributeSet {

    /**
     * Log tag.
     */
    //FIXME doit les clés de log ne devrait pas mettre MDK pour si retrouver
    public static final String LOG_TAG = "AttributeParserHelper";

    /**
     * Attribute map.
     */
    private final Map<Integer, String> attributeMap;

    /**
     * Create a MDKAttributeSet from AttributeSet.
     * @param attrs AttributeSet to parse
     */
    public MDKAttributeSet(AttributeSet attrs) {
        this.attributeMap = new HashMap<>();
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            this.attributeMap.put(attrs.getAttributeNameResource(i), attrs.getAttributeValue(i));
        }
    }

    /**
     * Return if the specifed key exists.
     * @param attr key to test
     * @return true if the key exists in MDKAttributeSet, false otherwise
     */
    public boolean containsKey(int attr) {
        return this.attributeMap.containsKey(attr);
    }

    /**
     * Get boolean value.
     * @param attr key of the value
     * @return a boolean at the specified key
     */
    public boolean getBoolean(int attr) {
        return Boolean.valueOf(this.attributeMap.get(attr));
    }

    /**
     * Set a boolean value.
     * @param attr key to set the value
     * @param value value to set
     */
    public void setBoolean(int attr, boolean value) {
        this.attributeMap.put(attr, String.valueOf(value));
    }

    /**
     * Get integer value.
     * @param attr key of the value
     * @return a integer at the specified key
     */
    public int getInteger(int attr) {
        return Integer.valueOf(this.attributeMap.get(attr));
    }
    /**
     * Set a integer value.
     * @param attr key to set the value
     * @param value value to set
     */
    public void setInteger(int attr, int value) {
        this.attributeMap.put(attr, String.valueOf(value));
    }
    /**
     * Get float value.
     * @param attr key of the value
     * @return a float at the specified key
     */
    public float getFloat(int attr) {
        return Float.valueOf(this.attributeMap.get(attr));
    }
    /**
     * Set a float value.
     * @param attr key to set the value
     * @param value value to set
     */
    public void setFloat(int attr, float value) {
        this.attributeMap.put(attr, String.valueOf(value));
    }
    /**
     * Get value.
     * @param attr key of the value
     * @return a String at the specified key
     */
    public String getValue(int attr) {
        return this.attributeMap.get(attr);
    }
    /**
     * Set a value.
     * @param attr key to set the value
     * @param value value to set
     */
    public void setValue(int attr, String value) {
        this.attributeMap.put(attr, value);
    }
    /**
     * Get resource value.
     * @param attr key of the value
     * @return a resource at the specified key
     */
    public int getResource(int attr) {
        return Integer.valueOf(this.attributeMap.get(attr).replace("@", ""));
    }
    /**
     * Set a resource value.
     * @param attr key to set the value
     * @param value value to set
     */
    public void setResource(int attr, @IdRes int value) {
        this.attributeMap.put(attr, String.valueOf("@"+value));
    }

    /**
     * Get the Set of keys.
     * @return a Set of all keys
     */
    public Set<Integer> keySet() {
        return this.attributeMap.keySet();
    }
}

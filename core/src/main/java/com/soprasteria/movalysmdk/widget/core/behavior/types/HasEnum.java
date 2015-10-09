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
package com.soprasteria.movalysmdk.widget.core.behavior.types;

/**
 * Adds enum behavior to a widget.
 * @param <T> Custom enum type
 */
public interface HasEnum<T extends Enum> {

    /**
     * Gets the value associated to a widget as an enum value.
     * @return the enum value
     */
    T getValueAsEnumValue();

    /**
     * Sets the value associated to a widget from an enum value.
     * @param value the enum value
     */
    void setValueFromEnum(T value);

    /**
     * Gets the name of the value associated to a widget as a string.
     * @return the string
     */
    String getValueAsString();

    /**
     * Sets the value associated to a widget from a string.
     * @param name the name of the resource without prefix
     */
    void setValueFromString(String name);

    /**
     * Gets the resource pointer of the value associated to a widget as an integer.
     * @return the resource pointer
     */
    int getValueAsId();

    /**
     * Sets the value associated to a widget from an integer resource pointer.
     * @param id the resource pointer
     */
    void setValueFromId(int id);
    
    /**
     * Gets the widget's prefix for resource names.
     * @return the resource name prefix
     */
    String getResourceNamePrefix();

    /**
     * Sets the widget's prefix for resource names.
     * @param prefix the resource name prefix
     */
    void setResourceNamePrefix(String prefix);

}

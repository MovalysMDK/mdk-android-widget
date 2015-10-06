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
package com.soprasteria.movalysmdk.widget.core.behavior;

/**
 * Adds enum behavior to a widget.
 * @param <T> Custom enum type
 */
public interface HasEnum<T extends Enum<T>> {

    /**
     *  Gets the enum value associated to a widget.
     * @return the enum
     */
    Enum<T> getEnumValue();

    /**
     * Sets the enum value associated to a widget.
     * @param value the enum
     */
    void setEnumValue(Enum<T> value);

    /**
     * Gets the widget's prefix for resource names.
     * @return the enum prefix
     */
    String getEnumPrefix();

    /**
     * Sets the widget's prefix for resource names.
     * @param prefix the enum prefix
     */
    void setEnumPrefix(String prefix);

}

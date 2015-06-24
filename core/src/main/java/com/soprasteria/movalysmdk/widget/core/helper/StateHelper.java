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

/**
 * Provide information on the MDK state for a widget.
 */
public class StateHelper {

    /**
     * Private constructor.
     */
    private StateHelper() {
        // private because utility class
    }

    /**
     * Return true if the attribute is actually known by the MDK drawable state.
     * @param state MDK drawable state
     * @param attr attribute
     * @return boolean true the attribute is found
     */
    //FIXME: reverse parameter order
    public static boolean hasState(int attr, int[] state) {
        for (int i: state) {
            if (i == attr) {
                return true;
            }
        }
        return false;
    }

}

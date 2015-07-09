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

import android.support.annotation.AttrRes;

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
     * Return true if state is included in the view states.
     * @param state searched state
     * @param viewStates view states
     * @return boolean true the state is found
     */
    public static boolean hasState(@AttrRes int state, int[] viewStates) {
        for (int i: viewStates) {
            if (i == state) {
                return true;
            }
        }
        return false;
    }

}

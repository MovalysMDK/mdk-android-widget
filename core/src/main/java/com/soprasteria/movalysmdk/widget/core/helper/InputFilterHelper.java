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

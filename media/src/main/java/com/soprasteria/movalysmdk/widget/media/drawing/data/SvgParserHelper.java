/**
 * Copyright (C) 2010 Sopra Steria Group (movalys.support@soprasteria.com)
 * <p/>
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
package com.soprasteria.movalysmdk.widget.media.drawing.data;


/**
 * SVG parser helper class.
 */
public class SvgParserHelper {

    /**
     * The svg string.
     */
    private String str;

    /**
     * Constructor.
     *
     * @param str the svg string
     */
    public SvgParserHelper(String str) {
        this.str = str;
    }

    /**
     * Gets the svg string length.
     *
     * @return the string length
     */
    public int getLength() {
        return this.str.length();
    }

    /**
     * Checks if svg string is empty.
     *
     * @return true if empty
     */
    public boolean isEmpty() {
        return str.isEmpty();
    }

    /**
     * Trims the svg string.
     */
    public void trim() {
        str = str.trim();
    }

    /**
     * Substrings the svg string.
     *
     * @param start start index
     */
    public void substring(int start) {
        str = str.substring(start);
    }

    /**
     * Substrings the svg string.
     *
     * @param start start index
     * @param end   end index
     */
    public void substring(int start, int end) {
        str = str.substring(start, end);
    }

    /**
     * Gets the character of svg string at specified index.
     *
     * @param index index of the char to get
     * @return the svg string character
     */
    public char charAt(int index) {
        return str.charAt(index);
    }

    /**
     * Parses the first integer found in the string, flushing the string to this position.
     *
     * @return the parsed integer.
     */
    public int consumeFirstIntFromString() {

        int index = 0;
        int result;

        str = str.trim();

        while (index < str.length() && (Character.isDigit(str.charAt(index)) || str.charAt(index) == '-')) {
            index++;
        }
        result = Integer.valueOf(str.substring(0, index));
        str = str.substring(index);

        return result;
    }
}

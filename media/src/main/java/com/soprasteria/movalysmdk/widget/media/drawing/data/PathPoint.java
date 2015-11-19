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
 * The path point model class.
 */
public class PathPoint {


    /**
     * The different point types.
     */
    public enum PointType {
        /** Moveto type. **/
        MOVETO,
        /** Lineto type. **/
        LINETO,
        /** Close type. **/
        CLOSE,
    }

    /** The point type. */
    PointType type;

    /** X coordinate of the point. */
    float x;
    /** Y coordinate of the point. */
    float y;

    /**
     * Constructor.
     * @param type point type
     * @param x x coordinate
     * @param y y coordinate
     */
    public PathPoint(PointType type, float x, float y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }
}
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

import android.graphics.Path.Direction;
import android.graphics.PointF;
import android.view.MotionEvent;

/**
 * Draws a rectangle element in the drawing view.
 */
public class RectangleElement extends DrawingElement {

    /**
     * Gets the starting point.
     * @return the starting point
     */
    public PointF getStartingPoint() {
        return startingPoint;
    }

    /**
     * Sets the starting point.
     * @param startingPoint the starting point
     */
    public void setStartingPoint(PointF startingPoint) {
        this.startingPoint = startingPoint;
    }

    /**
     * Gets the ending point.
     * @return the ending point
     */
    public PointF getEndingPoint() {
        return endingPoint;
    }

    /**
     * Sets the ending point.
     * @param endingPoint the ending point
     */
    public void setEndingPoint(PointF endingPoint) {
        this.endingPoint = endingPoint;
    }

    /**
     * Rectangle starting point.
     */
    private PointF startingPoint = new PointF();
    /**
     * Rectangle ending point.
     */
    private PointF endingPoint = new PointF();

    /**
     * Returns the drawing data to SVG format.
     */
    @Override
    public String toSvg() {
        StringBuilder svgStrBuilder = new StringBuilder();
        svgStrBuilder.append("<rect x=\"").append((int) Math.min(startingPoint.x, endingPoint.x))
                .append("\" y=\"").append((int) Math.min(startingPoint.y, endingPoint.y))
                .append("\" width=\"").append((int) Math.abs(endingPoint.x - startingPoint.x))
                .append("\" height=\"").append((int) Math.abs(endingPoint.y - startingPoint.y))
                .append("\" id=\"obj").append(id).append("\" ")
                .append(brush.toSvgString())
                .append("/>");

        return svgStrBuilder.toString();
    }

    @Override
    public UpdateResult onTouchEvent(MotionEvent event, float x, float y) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startingPoint.x = endingPoint.x = centroid.x = x;
                startingPoint.y = endingPoint.y = centroid.y = y;
                androidPath.addRect(startingPoint.x, startingPoint.y, endingPoint.x, endingPoint.y, Direction.CW);
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                endingPoint.x = x;
                endingPoint.y = y;
                update();
                break;
            default:
                break;
        }

        return UpdateResult.NONE;
    }

    @Override
    public void update() {
        // Update the centroid
        centroid.x = startingPoint.x + (endingPoint.x - startingPoint.x) / 2;
        centroid.y = startingPoint.y + (endingPoint.y - startingPoint.y) / 2;
        // Update the android path
        androidPath.reset();
        androidPath.addRect(Math.min(startingPoint.x, endingPoint.x),
                Math.min(startingPoint.y, endingPoint.y),
                Math.max(startingPoint.x, endingPoint.x),
                Math.max(startingPoint.y, endingPoint.y),
                Direction.CW);
    }
}

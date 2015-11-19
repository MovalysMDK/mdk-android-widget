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
 * Draws a circle in a DrawingView.
 **/
public class CircleElement extends DrawingElement {

    /**
     * Gets the radius.
     * @return the radius
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Sets the radius.
     * @param radius the radius
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }

    /**
     * Gets the center.
     * @return the center point.
     */
    public PointF getCenter() {
        return center;
    }

    /**
     * Sets the center.
     * @param center the center
     */
    public void setCenter(PointF center) {
        this.center = center;
    }

    /**
     * Center of the circle.
     */
    private PointF center = new PointF();
    /**
     * Radius of the circle.
     */
    private float radius;

    /**
     * Returns the drawing data to SVG format.
     */
    @Override
    public String toSvg() {
        StringBuilder svgStrBuilder = new StringBuilder();
        svgStrBuilder.append("<circle cx=\"").append((int) center.x)
                .append("\" cy=\"").append((int) center.y)
                .append("\" r=\"").append((int) radius)
                .append("\" id=\"obj").append(id).append("\" ")
                .append(brush.toSvgString())
                .append("/>");

        return svgStrBuilder.toString();
    }

    @Override
    public UpdateResult onTouchEvent(MotionEvent event, float x, float y) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                center.x = centroid.x = x;
                center.y = centroid.y = y;
                radius = 0.0f;
                androidPath.addCircle(center.x, center.y, radius, Direction.CW);
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                radius = (float) Math.sqrt((y - center.y) * (y - center.y) + (x - center.x) * (x - center.x));
                androidPath.reset();
                androidPath.addCircle(center.x, center.y, radius, Direction.CW);
                break;
            default:
                break;
        }

        return UpdateResult.NONE;
    }

    @Override
    public void update() {
        // Compute the centroid
        centroid.x = center.x;
        centroid.y = center.y;
        // Update the android path
        androidPath.reset();
        androidPath.addCircle(center.x, center.y, radius, Direction.CW);
    }
}

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
import android.graphics.RectF;
import android.view.MotionEvent;

/**
 * Draws an oval in the DrawingView.
 */
public class OvalElement extends DrawingElement {

    /**
     * Gets the oval.
     * @return the oval
     */
    public RectF getOval() {
        return oval;
    }

    /**
     * Sets the oval.
     * @param oval the oval.
     */
    public void setOval(RectF oval) {
        this.oval = oval;
    }

    /**
     * Oval's clipping rectangle.
     */
    private RectF oval = new RectF();

    @Override
    public String toSvg() {
        StringBuilder svgStrBuilder = new StringBuilder();
        svgStrBuilder.append("<ellipse cx=\"").append((int) (oval.left + (oval.right - oval.left) / 2))
                .append("\" cy=\"").append((int) (oval.top + (oval.bottom - oval.top) / 2))
                .append("\" rx=\"").append((int) Math.abs((oval.right - oval.left) / 2))
                .append("\" ry=\"").append((int) Math.abs((oval.bottom - oval.top) / 2))
                .append("\" id=\"obj").append(id).append("\" ")
                .append(brush.toSvgString())
                .append("/>");

        return svgStrBuilder.toString();
    }

    @Override
    public UpdateResult onTouchEvent(MotionEvent event, float x, float y) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oval.left = oval.right = centroid.x = x;
                oval.top = oval.bottom = centroid.y = y;
                androidPath.addOval(oval, Direction.CW);
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                oval.right = x;
                oval.bottom = y;
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
        centroid.x = oval.left + (oval.right - oval.left) / 2;
        centroid.y = oval.top + (oval.bottom - oval.top) / 2;
        // Update the android path
        androidPath.reset();
        androidPath.addOval(oval, Direction.CW);
    }
}

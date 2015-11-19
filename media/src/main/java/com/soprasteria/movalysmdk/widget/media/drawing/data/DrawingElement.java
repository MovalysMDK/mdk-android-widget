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

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.soprasteria.movalysmdk.widget.media.drawing.data.Brush.Style;

/**
 * Base class for elements that can be drawn in a DrawingView.
 */
public abstract class DrawingElement {

    /**
     * The update results type.
     */
    public enum UpdateResult {
        /**
         * None.
         **/
        NONE,
        /**
         * Ask for keyboard.
         **/
        ASK_FOR_KEYBOARD,
    }

    /**
     * Counter for object ids.
     **/
    private static int objIdCounter = 0;
    /**
     * Object id.
     */
    protected int id;
    /**
     * Brush to be used while drawing this data.
     */
    protected Brush brush = new Brush();
    /**
     * Android Path representing the drawing data.
     */
    protected Path androidPath = new Path();
    /**
     * Centroid of the data.
     */
    protected PointF centroid = new PointF();

    /**
     * Gets a new id, incrementing the counter.
     *
     * @return a new id.
     */
    protected static int getNewId() {
        return objIdCounter++;
    }

    /**
     * Sets the id counter to the specified value if possible.
     * @param id the new id counter value
     */
    protected static void trySetMaxId(int id) {
        objIdCounter = (objIdCounter < id) ? id : objIdCounter;
    }

    /**
     * Constructor.
     */
    protected DrawingElement() {
        this.id = getNewId();
    }

    /**
     * Returns the brush to be used while drawing this data.
     *
     * @return this element's brush
     */
    public Brush getBrush() {
        return brush;
    }

    /**
     * Set the brush to be used while drawing this data.
     *
     * @param brush the brush to set
     */
    public void setBrush(Brush brush) {
        this.brush = brush;
    }

    /**
     * Returns the android path representing this data.
     *
     * @return path to the data
     */
    public Path getAndroidPath() {
        return androidPath;
    }

    /**
     * Returns the centroid of the data.
     *
     * @return centroid of the data
     */
    public PointF getCentroid() {
        return centroid;
    }

    /**
     * Applies the style to the brush.
     *
     * @param style style to apply
     */
    public void applyStyle(String style) {
        Brush.fromSvgString(style, this.brush);
    }

    /**
     * Updates drawing data according to new user inputs.
     *
     * @param event the motion event
     * @param x X coordinate of the input
     * @param y Y coordinate of the input
     * @return an UpdateResult.
     */
    public abstract UpdateResult onTouchEvent(MotionEvent event, float x, float y);

    /**
     * Draw the current data to the canvas.
     *
     * @param canvas the canvas to draw in
     * @param selectedBrush the brush of selected elements
     * @param centroidBrush the brush of the centroid
     */
    public void draw(Canvas canvas, Brush selectedBrush, Brush centroidBrush) {

        if (selectedBrush != null) {
            // Draw the data with selection brush
            canvas.drawPath(androidPath, selectedBrush.getStrokePaint());
        } else {
            // Draw the data
            if (brush.getStyle() == Style.FILL) {
                canvas.drawPath(androidPath, brush.getFillPaint());
            } else {
                canvas.drawPath(androidPath, brush.getStrokePaint());
            }
        }
    }

    /**
     * Key events handler.
     *
     * @param keyCode the code of the key
     * @param event the key event
     * @param drawingArea the rectangle in which the event applies
     * @return a boolean indicating that the event has been handled correctly
     */
    public boolean onKeyDown(int keyCode, KeyEvent event, RectF drawingArea) {
        return true;
    }

    /**
     * Updates the centroid and android path.
     */
    public abstract void update();

    /**
     * Returns the drawing data in SVG format.
     *
     * @return a string containing the svg data
     */
    public abstract String toSvg();

    /**
     * Gets this element's id.
     * @return the id
     */
    protected int getId() {
        return id;
    }

    /**
     * Sets the element's id.
     * @param id the new id
     */
    protected void setId(int id) {
        this.id = id;
    }
}

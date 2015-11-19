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
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Writes text in a drawing view.
 */
public class TextElement extends DrawingElement {

    /**
     * Gets the coordiantes.
     * @return the coordinates.
     */
    public PointF getCoords() {
        return coords;
    }

    /**
     * Sets the coordinates.
     * @param coords the coordinates
     */
    public void setCoords(PointF coords) {
        this.coords = coords;
    }

    /**
     * Gets the string value.
     * @return the string value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the string value.
     * @param value the string value.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Coordinates of the text.
     */
    private PointF coords = new PointF();

    /**
     * Value of the string.
     */
    private String value = "";


    @Override
    public String toSvg() {
        StringBuilder svgStrBuilder = new StringBuilder();
        svgStrBuilder.append("<text x=\"").append((int) coords.x)
                .append("\" y=\"").append((int) coords.y)
                .append("\" id=\"obj").append(id).append("\" ")
                .append(brush.toSvgString())
                .append("\" font-size=\"").append(brush.getStrokePaint().getStrokeWidth()).append("\" ")
                .append(">")
                .append(value)
                .append("</text>");
        return svgStrBuilder.toString();
    }

    @Override
    public UpdateResult onTouchEvent(MotionEvent event, float x, float y) {

        UpdateResult result = UpdateResult.NONE;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                coords = new PointF(x, y);
                centroid = new PointF(x, y);
                break;

            case MotionEvent.ACTION_MOVE:
                coords.x = x;
                coords.y = y;
                break;

            case MotionEvent.ACTION_UP:
                coords.x = x;
                coords.y = y;
                update();
                result = UpdateResult.ASK_FOR_KEYBOARD;
                break;
            default:
                break;
        }

        return result;
    }

    @Override
    public void draw(Canvas canvas, Brush selectedBrush, Brush centroidBrush) {

        if (selectedBrush != null) {
            // Draw the data with selection brush
            canvas.drawText(value, coords.x, coords.y, selectedBrush.getFillPaint());
        } else {
            // Draw the data
            canvas.drawText(value, coords.x, coords.y, brush.getFillPaint());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event, RectF drawingArea) {

        if (event.getAction() == KeyEvent.ACTION_MULTIPLE) {
            // Add the caracter
            String oldValue = this.value;
            this.value += event.getCharacters();

            // Check if the text boundaries are not outside of the drawing area
            Rect textSize = new Rect();
            this.brush.getFillPaint().getTextBounds(this.value, 0, this.value.length(), textSize);
            if (this.coords.x + textSize.right > drawingArea.right || this.coords.y + textSize.bottom > drawingArea.bottom) {
                this.value = oldValue;
            }
        } else if (keyCode == KeyEvent.KEYCODE_DEL && this.value.length() > 0) {
            this.value = this.value.substring(0, value.length() - 1);
        } else {
            // Add the caracter
            String oldValue = this.value;
            this.value += (char) event.getUnicodeChar();

            // Check if the text boundaries are not outside of the drawing area
            Rect textSize = new Rect();
            this.brush.getFillPaint().getTextBounds(this.value, 0, this.value.length(), textSize);
            if (this.coords.x + textSize.right > drawingArea.right || this.coords.y + textSize.bottom > drawingArea.bottom) {
                this.value = oldValue;
            }
        }

        return true;
    }

    @Override
    public void update() {
        // Update the centroid
        centroid.x = coords.x;
        centroid.y = coords.y;
    }
}

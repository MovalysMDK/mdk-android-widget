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
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;

import com.soprasteria.movalysmdk.widget.media.drawing.data.Brush.Style;

import java.util.LinkedList;
import java.util.List;

/**
 * A marker element for drawing elements.
 */
public class MarkerElement {

    /**
     * Marker unit types.
     */
    public enum MarkerUnits {
        /** Stroke width. **/
        STROKE_WIDTH,
        /** User space on use. **/
        USER_SPACE_ON_USE,
    }

    /**
     * Gets the reference point.
     * @return the reference point
     */
    public PointF getRef() {
        return ref;
    }

    /**
     * Sets the reference point.
     * @param ref the reference point.
     */
    public void setRef(PointF ref) {
        this.ref = ref;
    }

    /**
     * Gets the marker's width.
     * @return the marker width
     */
    public float getMarkerWidth() {
        return markerWidth;
    }

    /**
     * Sets the marker width.
     * @param markerWidth the marker width
     */
    public void setMarkerWidth(float markerWidth) {
        this.markerWidth = markerWidth;
    }

    /**
     * Gets the marker height.
     * @return the marker's height
     */
    public float getMarkerHeight() {
        return markerHeight;
    }

    /**
     * Sets the marker's height.
     * @param markerHeight the marker height
     */
    public void setMarkerHeight(float markerHeight) {
        this.markerHeight = markerHeight;
    }

    /**
     * Gets the auto orientation.
     * @return true if auto oriented.
     */
    public boolean isAutoOrientation() {
        return isAutoOrientation;
    }

    /**
     * Sets the auto orientation.
     * @param isAutoOrientation the oauto rientation parameter
     */
    public void setAutoOrientation(boolean isAutoOrientation) {
        this.isAutoOrientation = isAutoOrientation;
    }

    /**
     * Gets the angle.
     * @return the angle
     */
    public float getAngle() {
        return angle;
    }

    /**
     * Sets the angle.
     * @param angle the angle
     */
    public void setAngle(float angle) {
        this.angle = angle;
    }

    /**
     * Gets the id.
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the style.
     * @return the style
     */
    public String getStyle() {
        return style;
    }

    /**
     * Sets the style.
     * @param style the style.
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * Gets the list of drawing elements.
     * @return the element list
     */
    public List<DrawingElement> getElements() {
        return elements;
    }

    /**
     * Gets the marker units.
     * @return the marker units
     */
    public MarkerUnits getMarkerUnits() {
        return markerUnits;
    }

    /**
     * Sets the marker units.
     * @param markerUnits the marker units
     */
    public void setMarkerUnits(MarkerUnits markerUnits) {
        this.markerUnits = markerUnits;
    }

    /**
     * the Marker units.
     */
    private MarkerUnits markerUnits = MarkerUnits.STROKE_WIDTH;

    /**
     * Reference point.
     */
    private PointF ref = new PointF();
    /**
     * Marker width.
     */
    private float markerWidth = 3;
    /**
     * Marker height.
     */
    private float markerHeight = 3;
    /**
     * Auto orientation flag.
     */
    private boolean isAutoOrientation = true;
    /**
     * Angle of the marker.
     */
    private float angle = 0;
    /**
     * Id of the marker.
     */
    private String id;
    /**
     * Style applied to the marker.
     */
    private String style;

    /**
     * List of drawing elements.
     */
    private List<DrawingElement> elements = new LinkedList<>();

    /**
     * Bounds of the marker.
     */
    private RectF boundingBox = new RectF(Float.MAX_VALUE, Float.MAX_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);

    /**
     * Object id counter.
     */
    private static int objIdCounter = 0;


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
    public MarkerElement() {
        this.id = "marker" + getNewId();
    }

    /**
     * Set the elements of the marker.
     * @param elements the elements list
     */
    public void setElements(List<DrawingElement> elements) {
        this.elements = elements;

        RectF tempRect = new RectF();
        for (DrawingElement drawingElement : elements) {
            drawingElement.getAndroidPath().computeBounds(tempRect, true);
            boundingBox.left = (boundingBox.left > tempRect.left) ? tempRect.left : boundingBox.left;
            boundingBox.right = (boundingBox.right < tempRect.right) ? tempRect.right : boundingBox.right;
            boundingBox.top = (boundingBox.top > tempRect.top) ? tempRect.top : boundingBox.top;
            boundingBox.bottom = (boundingBox.bottom < tempRect.bottom) ? tempRect.bottom : boundingBox.bottom;
        }
    }
    /**
     * Draw the current data to the canvas.
     *
     * @param canvas the canvas to draw in
     * @param refPoint the reference point
     * @param angle the angle to apply
     * @param brush the brush to draw with
     */
    public void draw(Canvas canvas, PointF refPoint, float angle, Brush brush) {

        brush.setStyle(Style.FILL);

        for (DrawingElement drawingElement : elements) {

            canvas.save();

            Matrix matrix = new Matrix();
            Matrix tempMatrix = new Matrix();

            tempMatrix.setTranslate(-ref.x, -ref.y);
            matrix.postConcat(tempMatrix);

            tempMatrix.setRotate(angle);
            matrix.postConcat(tempMatrix);

            tempMatrix.setTranslate(refPoint.x, refPoint.y);
            matrix.postConcat(tempMatrix);

            canvas.concat(matrix);
            drawingElement.setBrush(brush);
            drawingElement.draw(canvas, null, null);

            canvas.restore();
        }
    }


    /**
     * Returns the drawing data in SVG format.
     *
     * @return a string containing the svg data
     */
    public String toSvg() {
        StringBuilder svgStrBuilder = new StringBuilder();

        svgStrBuilder.append("<marker refX=\"").append((int) ref.x)
                .append("\" refY=\"").append((int) ref.y);

        if (markerUnits == MarkerUnits.STROKE_WIDTH) {
            svgStrBuilder.append("\" markerUnits=\"strokeWidth\" ");
        } else {
            svgStrBuilder.append("\" markerUnits=\"userSpaceOnUse\" ");
        }
        svgStrBuilder.append("markerHeight=\"").append((int) markerHeight)
                .append("\" markerWidth=\"").append((int) markerWidth);

        if (isAutoOrientation) {
            svgStrBuilder.append("\" orient=\"auto\" ");
        } else {
            svgStrBuilder.append("\" orient=\"")
                    .append(angle)
                    .append("\" ");
        }

        svgStrBuilder.append("id=\"").append(id).append("\" >");


        for (DrawingElement drawingElement : elements) {
            svgStrBuilder.append(drawingElement.toSvg());
        }

        svgStrBuilder.append("</marker>");

        return svgStrBuilder.toString();
    }
}

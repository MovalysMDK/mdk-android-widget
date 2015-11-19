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
import android.view.MotionEvent;

/**
 * Draws a straight line in the DrawingView.
 */
public class LineElement extends DrawingElement {

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
     * Gets the start marker.
     * @return the starting marker
     */
    public MarkerElement getMarkerStart() {
        return markerStart;
    }

    /**
     * Sets the start marker.
     * @param markerStart the starting marker
     */
    public void setMarkerStart(MarkerElement markerStart) {
        this.markerStart = markerStart;
    }

    /**
     * Gets the end marker.
     * @return the ending marker
     */
    public MarkerElement getMarkerEnd() {
        return markerEnd;
    }


    /**
     * Sets the end marker.
     * @param markerEnd the ending marker
     */
    public void setMarkerEnd(MarkerElement markerEnd) {
        this.markerEnd = markerEnd;
    }

    /**
     * Starting point of the line.
     */
    private PointF startingPoint = new PointF();
    /**
     * Ending point of the line.
     */
    private PointF endingPoint = new PointF();
    /**
     * Marker placed at the start of the line.
     */
    private MarkerElement markerStart = null;
    /**
     * Marker placed at the end of the line.
     */
    private MarkerElement markerEnd = null;


    /**
     * Convert the line to SVG data.
     */
    @Override
    public String toSvg() {
        StringBuilder svgStrBuilder = new StringBuilder();

        if (markerStart != null) {
            svgStrBuilder.append(markerStart.toSvg());
        }

        if (markerEnd != null) {
            svgStrBuilder.append(markerEnd.toSvg());
        }

        svgStrBuilder.append("<line x1=\"").append((int) startingPoint.x)
                .append("\" y1=\"").append((int) startingPoint.y)
                .append("\" x2=\"").append((int) endingPoint.x)
                .append("\" y2=\"").append((int) endingPoint.y)
                .append("\" id=\"obj").append(id).append("\" ");

        if (markerStart != null) {
            svgStrBuilder.append("marker-start=\"url(#")
                    .append(markerStart.getId())
                    .append(")\" ");
        }

        if (markerEnd != null) {
            svgStrBuilder.append("marker-end=\"url(#")
                    .append(markerEnd.getId())
                    .append(")\" ");
        }

        svgStrBuilder.append(brush.toSvgString())
                .append("/>");

        return svgStrBuilder.toString();
    }

    @Override
    public UpdateResult onTouchEvent(MotionEvent event, float x, float y) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Initiate the line
                startingPoint.x = endingPoint.x = centroid.x = x;
                startingPoint.y = endingPoint.y = centroid.y = y;
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
    public void draw(Canvas canvas, Brush selectedBrush, Brush centroidBrush) {

        // Compute the marker angle
        float angle = (float) Math.toDegrees(Math.atan2(endingPoint.y - startingPoint.y, endingPoint.x - startingPoint.x));

        if (selectedBrush != null) {
            // Draw the data with selection brush
            canvas.drawPath(androidPath, selectedBrush.getStrokePaint());

            // Draw the start marker
            if (markerStart != null) {
                markerStart.draw(canvas, startingPoint, angle, new Brush(selectedBrush));
            }

            // Draw the end marker
            if (markerEnd != null) {
                markerEnd.draw(canvas, endingPoint, angle, new Brush(selectedBrush));
            }

        } else {
            // Draw the line
            canvas.drawPath(androidPath, brush.getStrokePaint());

            // Draw the start marker
            if (markerStart != null) {
                markerStart.draw(canvas, startingPoint, angle, new Brush(this.brush));
            }

            // Draw the end marker
            if (markerEnd != null) {
                markerEnd.draw(canvas, endingPoint, angle, new Brush(this.brush));
            }
        }
    }

    @Override
    public void update() {
        // Compute the centroid
        centroid.x = (endingPoint.x - startingPoint.x) / 2 + startingPoint.x;
        centroid.y = (endingPoint.y - startingPoint.y) / 2 + startingPoint.y;
        // Update the android path
        androidPath.reset();
        androidPath.moveTo(startingPoint.x, startingPoint.y);
        androidPath.lineTo(endingPoint.x, endingPoint.y);
    }

    @Override
    public void applyStyle(String style) {
        Brush.fromSvgString(style, this.brush);

        if (this.markerStart != null) {
            this.markerStart.setStyle(style);
        }
        if (this.markerEnd != null) {
            this.markerEnd.setStyle(style);
        }
    }

    @Override
    public void setBrush(Brush brush) {
        this.brush = brush;

        if (this.markerStart != null) {
            this.markerStart.setStyle(this.brush.toSvgString());
            this.markerStart.setStyle(this.markerStart.getStyle().substring(7, this.markerStart.getStyle().length() - 1));
        }
        if (this.markerEnd != null) {
            this.markerEnd.setStyle(this.brush.toSvgString());
            this.markerEnd.setStyle(this.markerEnd.getStyle().substring(7, this.markerEnd.getStyle().length() - 1));
        }
    }
}

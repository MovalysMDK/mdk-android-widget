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

import android.view.MotionEvent;

import com.soprasteria.movalysmdk.widget.media.drawing.data.MarkerElement.MarkerUnits;

import java.util.LinkedList;
import java.util.List;

/**
 * Draws a straight line with an arrow shape at both ends in a DrawingView.
 */
public class DoubleArrowLineElement extends LineElement {

    @Override
    public void setBrush(Brush brush) {
        super.setBrush(brush);

        int scale = (int) brush.getStrokePaint().getStrokeWidth() / 3;

        this.setMarkerEnd(new MarkerElement());
        this.getMarkerEnd().setAutoOrientation(true);
        this.getMarkerEnd().setMarkerHeight(3 * scale);
        this.getMarkerEnd().setMarkerWidth(4 * scale);
        this.getMarkerEnd().getRef().x = 0;
        this.getMarkerEnd().getRef().y = 5 * scale;
        this.getMarkerEnd().setMarkerUnits(MarkerUnits.STROKE_WIDTH);

        HandFreeElement path = new HandFreeElement();
        MotionEvent motionEvent = MotionEvent.obtain(0, 0, MotionEvent.ACTION_DOWN, 0, 0, 0);
        path.onTouchEvent(motionEvent, 0, 0);
        motionEvent = MotionEvent.obtain(0, 0, MotionEvent.ACTION_MOVE, 10 * scale, 5 * scale, 0);
        path.onTouchEvent(motionEvent, 10 * scale, 5 * scale);
        motionEvent = MotionEvent.obtain(0, 0, MotionEvent.ACTION_MOVE, 0, 10 * scale, 0);
        path.onTouchEvent(motionEvent, 0, 10 * scale);
        motionEvent = MotionEvent.obtain(0, 0, MotionEvent.ACTION_MOVE, 0, 0, 0);
        path.onTouchEvent(motionEvent, 0, 0);

        List<DrawingElement> drawingElementList = new LinkedList<>();
        drawingElementList.add(path);

        this.getMarkerEnd().setElements(drawingElementList);

        this.getMarkerEnd().setStyle(this.brush.toSvgString());
        this.getMarkerEnd().setStyle(this.getMarkerEnd().getStyle().substring(7, this.getMarkerEnd().getStyle().length() - 1));

        // MarkerStart
        this.setMarkerStart(new MarkerElement());
        this.getMarkerStart().setAutoOrientation(true);
        this.getMarkerStart().setMarkerHeight(3 * scale);
        this.getMarkerStart().setMarkerWidth(4 * scale);
        this.getMarkerStart().getRef().x = 5 * scale;
        this.getMarkerStart().getRef().y = 5 * scale;
        this.getMarkerStart().setMarkerUnits(MarkerUnits.STROKE_WIDTH);

        path = new HandFreeElement();
        motionEvent = MotionEvent.obtain(0, 0, MotionEvent.ACTION_DOWN, 0, 5 * scale, 0);
        path.onTouchEvent(motionEvent, 0, 5 * scale);
        motionEvent = MotionEvent.obtain(0, 0, MotionEvent.ACTION_MOVE, 10 * scale, 0, 0);
        path.onTouchEvent(motionEvent, 10 * scale, 0);
        motionEvent = MotionEvent.obtain(0, 0, MotionEvent.ACTION_MOVE, 10 * scale, 10 * scale, 0);
        path.onTouchEvent(motionEvent, 10 * scale, 10 * scale);
        motionEvent = MotionEvent.obtain(0, 0, MotionEvent.ACTION_MOVE, 0, 5 * scale, 0);
        path.onTouchEvent(motionEvent, 0, 5 * scale);

        drawingElementList = new LinkedList<>();
        drawingElementList.add(path);

        this.getMarkerStart().setElements(drawingElementList);

        this.getMarkerStart().setStyle(this.brush.toSvgString());
        this.getMarkerStart().setStyle(this.getMarkerStart().getStyle().substring(7, this.getMarkerStart().getStyle().length() - 1));
    }
}

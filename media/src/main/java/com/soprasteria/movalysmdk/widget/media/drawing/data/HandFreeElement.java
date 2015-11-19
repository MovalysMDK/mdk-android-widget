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

import android.graphics.PointF;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Draws a path following the user's gesture in the DawingView.
 */
public class HandFreeElement extends DrawingElement {



    /**
     * Gets the list of points.
     * @return the list of points
     */
    public List<PathPoint> getPoints() {
        return points;
    }

    /**
     * Number of hand free data points.
     */

    private List<PathPoint> points = new ArrayList<>();

    /**
     * Converts the hand free data to SVG format.
     */
    @Override
    public String toSvg() {
        StringBuilder svgStrBuilder = new StringBuilder();
        svgStrBuilder.append("<path id=\"obj").append(id).append("\" ");

        // Convert android path to svg path
        svgStrBuilder.append("d=\"");
        for (PathPoint point : points) {
            switch (point.type) {
                case CLOSE:
                    svgStrBuilder.append(" Z");
                    break;
                case LINETO:
                    svgStrBuilder.append(" L ").append((int) point.x).append(" ").append((int) point.y);
                    break;
                case MOVETO:
                    svgStrBuilder.append(" M ").append((int) point.x).append(" ").append((int) point.y);
                    break;
                default:
                    break;
            }
        }
        svgStrBuilder.append("\" ");

        svgStrBuilder.append(brush.toSvgString())
                .append("/>");
        return svgStrBuilder.toString();
    }

    @Override
    public UpdateResult onTouchEvent(MotionEvent event, float x, float y) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                androidPath.moveTo(x, y);
                points.clear();
                points.add(new PathPoint(PathPoint.PointType.MOVETO, x, y));
                centroid = new PointF(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                androidPath.lineTo(x, y);
                points.add(new PathPoint(PathPoint.PointType.LINETO, x, y));
                centroid.x = ((points.size() - 1) * centroid.x + x) / points.size();
                centroid.y = ((points.size() - 1) * centroid.y + y) / points.size();
                break;
            default:
                break;
        }

        return UpdateResult.NONE;
    }

    @Override
    public void update() {
        androidPath.reset();
        if (!points.isEmpty()) {
            PathPoint firstPoint = points.get(0);
            centroid.x = firstPoint.x;
            centroid.y = firstPoint.y;

            int pointNumber = 0;
            for (PathPoint point : points) {
                // Update the android path
                switch (point.type) {
                    case CLOSE:
                        androidPath.close();
                        break;
                    case LINETO:
                        androidPath.lineTo(point.x, point.y);
                        break;
                    case MOVETO:
                        androidPath.moveTo(point.x, point.y);
                        break;
                    default:
                        continue;
                }

                // Compute the centroid
                centroid.x = (pointNumber * centroid.x + point.x) / (pointNumber + 1);
                centroid.y = (pointNumber * centroid.y + point.y) / (pointNumber + 1);
                pointNumber++;
            }
        }
    }
}

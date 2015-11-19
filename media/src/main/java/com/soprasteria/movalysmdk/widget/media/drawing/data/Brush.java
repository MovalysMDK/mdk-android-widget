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

import android.graphics.DashPathEffect;
import android.graphics.Paint;

import java.util.HashMap;
import java.util.Map;

/**
 * Representation of a brush to paint an SVG element.
 */
public class Brush {

    /**
     * Map containing the exhaustive list of CSS colors supported by the SVG standard.
     */
    private static final Map<String, Integer> colorKeywordMap;

    static {
        colorKeywordMap = new HashMap<>();
        colorKeywordMap.put("aliceblue", 0xFFF0F8FF);
        colorKeywordMap.put("antiquewhite", 0xFFFAEBD7);
        colorKeywordMap.put("aqua", 0xFF00FFFF);
        colorKeywordMap.put("aquamarine", 0xFF7FFFD4);
        colorKeywordMap.put("azure", 0xFFF0FFFF);
        colorKeywordMap.put("beige", 0xFFF5F5DC);
        colorKeywordMap.put("bisque", 0xFFFFE4C4);
        colorKeywordMap.put("black", 0xFF000000);
        colorKeywordMap.put("blanchedalmond", 0xFFFFEBCD);
        colorKeywordMap.put("blue", 0xFF0000FF);
        colorKeywordMap.put("blueviolet", 0xFF8A2BE2);
        colorKeywordMap.put("brown", 0xFFA52A2A);
        colorKeywordMap.put("burlywood", 0xFFDEB887);
        colorKeywordMap.put("cadetblue", 0xFF5F9EA0);
        colorKeywordMap.put("chartreuse", 0xFF7FFF00);
        colorKeywordMap.put("chocolate", 0xFFD2691E);
        colorKeywordMap.put("coral", 0xFFFF7F50);
        colorKeywordMap.put("cornflowerblue", 0xFF6495ED);
        colorKeywordMap.put("cornsilk", 0xFFFFF8DC);
        colorKeywordMap.put("crimson", 0xFFDC143C);
        colorKeywordMap.put("cyan", 0xFF00FFFF);
        colorKeywordMap.put("darkblue", 0xFF00008B);
        colorKeywordMap.put("darkcyan", 0xFF008B8B);
        colorKeywordMap.put("darkgoldenrod", 0xFFB8860B);
        colorKeywordMap.put("darkgray", 0xFFA9A9A9);
        colorKeywordMap.put("darkgreen", 0xFF006400);
        colorKeywordMap.put("darkgrey", 0xFFA9A9A9);
        colorKeywordMap.put("darkkhaki", 0xFFBDB76B);
        colorKeywordMap.put("darkmagenta", 0xFF8B008B);
        colorKeywordMap.put("darkolivegreen", 0xFF556B2F);
        colorKeywordMap.put("darkorange", 0xFFFF8C00);
        colorKeywordMap.put("darkorchid", 0xFF9932CC);
        colorKeywordMap.put("darkred", 0xFF8B0000);
        colorKeywordMap.put("darksalmon", 0xFFE9967A);
        colorKeywordMap.put("darkseagreen", 0xFF8FBC8F);
        colorKeywordMap.put("darkslateblue", 0xFF483D8B);
        colorKeywordMap.put("darkslategray", 0xFF2F4F4F);
        colorKeywordMap.put("darkslategrey", 0xFF2F4F4F);
        colorKeywordMap.put("darkturquoise", 0xFF00CED1);
        colorKeywordMap.put("darkviolet", 0xFF9400D3);
        colorKeywordMap.put("deeppink", 0xFFFF1493);
        colorKeywordMap.put("deepskyblue", 0xFF00BFFF);
        colorKeywordMap.put("dimgray", 0xFF696969);
        colorKeywordMap.put("dimgrey", 0xFF696969);
        colorKeywordMap.put("dodgerblue", 0xFF1E90FF);
        colorKeywordMap.put("firebrick", 0xFFB22222);
        colorKeywordMap.put("floralwhite", 0xFFFFFAF0);
        colorKeywordMap.put("forestgreen", 0xFF228B22);
        colorKeywordMap.put("fuchsia", 0xFFFF00FF);
        colorKeywordMap.put("gainsboro", 0xFFDCDCDC);
        colorKeywordMap.put("ghostwhite", 0xFFF8F8FF);
        colorKeywordMap.put("gold", 0xFFFFD700);
        colorKeywordMap.put("goldenrod", 0xFFDAA520);
        colorKeywordMap.put("gray", 0xFF808080);
        colorKeywordMap.put("grey", 0xFF808080);
        colorKeywordMap.put("green", 0xFF008000);
        colorKeywordMap.put("greenyellow", 0xFFADFF2F);
        colorKeywordMap.put("honeydew", 0xFFF0FFF0);
        colorKeywordMap.put("hotpink", 0xFFFF69B4);
        colorKeywordMap.put("indianred", 0xFFCD5C5C);
        colorKeywordMap.put("indigo", 0xFF4B0082);
        colorKeywordMap.put("ivory", 0xFFFFFFF0);
        colorKeywordMap.put("khaki", 0xFFF0E68C);
        colorKeywordMap.put("lavender", 0xFFE6E6FA);
        colorKeywordMap.put("lavenderblush", 0xFFFFF0F5);
        colorKeywordMap.put("lawngreen", 0xFF7CFC00);
        colorKeywordMap.put("lemonchiffon", 0xFFFFFACD);
        colorKeywordMap.put("lightblue", 0xFFADD8E6);
        colorKeywordMap.put("lightcoral", 0xFFF08080);
        colorKeywordMap.put("lightcyan", 0xFFE0FFFF);
        colorKeywordMap.put("lightgoldenrodyellow", 0xFFFAFAD2);
        colorKeywordMap.put("lightgray", 0xFFD3D3D3);
        colorKeywordMap.put("lightgreen", 0xFF90EE90);
        colorKeywordMap.put("lightgrey", 0xFFD3D3D3);
        colorKeywordMap.put("lightpink", 0xFFFFB6C1);
        colorKeywordMap.put("lightsalmon", 0xFFFFA07A);
        colorKeywordMap.put("lightseagreen", 0xFF20B2AA);
        colorKeywordMap.put("lightskyblue", 0xFF87CEFA);
        colorKeywordMap.put("lightslategray", 0xFF778899);
        colorKeywordMap.put("lightslategrey", 0xFF778899);
        colorKeywordMap.put("lightsteelblue", 0xFFB0C4DE);
        colorKeywordMap.put("lightyellow", 0xFFFFFFE0);
        colorKeywordMap.put("lime", 0xFF00FF00);
        colorKeywordMap.put("limegreen", 0xFF32CD32);
        colorKeywordMap.put("linen", 0xFFFAF0E6);
        colorKeywordMap.put("magenta", 0xFFFF00FF);
        colorKeywordMap.put("maroon", 0xFF800000);
        colorKeywordMap.put("mediumaquamarine", 0xFF66CDAA);
        colorKeywordMap.put("mediumblue", 0xFF0000CD);
        colorKeywordMap.put("mediumorchid", 0xFFBA55D3);
        colorKeywordMap.put("mediumpurple", 0xFF9370DB);
        colorKeywordMap.put("mediumseagreen", 0xFF3CB371);
        colorKeywordMap.put("mediumslateblue", 0xFF7B68EE);
        colorKeywordMap.put("mediumspringgreen", 0xFF00FA9A);
        colorKeywordMap.put("mediumturquoise", 0xFF48D1CC);
        colorKeywordMap.put("mediumvioletred", 0xFFC71585);
        colorKeywordMap.put("midnightblue", 0xFF191970);
        colorKeywordMap.put("mintcream", 0xFFF5FFFA);
        colorKeywordMap.put("mistyrose", 0xFFFFE4E1);
        colorKeywordMap.put("moccasin", 0xFFFFE4B5);
        colorKeywordMap.put("navajowhite", 0xFFFFDEAD);
        colorKeywordMap.put("navy", 0xFF000080);
        colorKeywordMap.put("oldlace", 0xFFFDF5E6);
        colorKeywordMap.put("olive", 0xFF808000);
        colorKeywordMap.put("olivedrab", 0xFF6B8E23);
        colorKeywordMap.put("orange", 0xFFFFA500);
        colorKeywordMap.put("orangered", 0xFFFF4500);
        colorKeywordMap.put("orchid", 0xFFDA70D6);
        colorKeywordMap.put("palegoldenrod", 0xFFEEE8AA);
        colorKeywordMap.put("palegreen", 0xFF98FB98);
        colorKeywordMap.put("paleturquoise", 0xFFAFEEEE);
        colorKeywordMap.put("palevioletred", 0xFFDB7093);
        colorKeywordMap.put("papayawhip", 0xFFFFEFD5);
        colorKeywordMap.put("peachpuff", 0xFFFFDAB9);
        colorKeywordMap.put("peru", 0xFFCD853F);
        colorKeywordMap.put("pink", 0xFFFFC0CB);
        colorKeywordMap.put("plum", 0xFFDDA0DD);
        colorKeywordMap.put("powderblue", 0xFFB0E0E6);
        colorKeywordMap.put("purple", 0xFF800080);
        colorKeywordMap.put("red", 0xFFFF0000);
        colorKeywordMap.put("rosybrown", 0xFFBC8F8F);
        colorKeywordMap.put("royalblue", 0xFF4169E1);
        colorKeywordMap.put("saddlebrown", 0xFF8B4513);
        colorKeywordMap.put("salmon", 0xFFFA8072);
        colorKeywordMap.put("sandybrown", 0xFFF4A460);
        colorKeywordMap.put("seagreen", 0xFF2E8B57);
        colorKeywordMap.put("seashell", 0xFFFFF5EE);
        colorKeywordMap.put("sienna", 0xFFA0522D);
        colorKeywordMap.put("silver", 0xFFC0C0C0);
        colorKeywordMap.put("skyblue", 0xFF87CEEB);
        colorKeywordMap.put("slateblue", 0xFF6A5ACD);
        colorKeywordMap.put("slategray", 0xFF708090);
        colorKeywordMap.put("slategrey", 0xFF708090);
        colorKeywordMap.put("snow", 0xFFFFFAFA);
        colorKeywordMap.put("springgreen", 0xFF00FF7F);
        colorKeywordMap.put("steelblue", 0xFF4682B4);
        colorKeywordMap.put("tan", 0xFFD2B48C);
        colorKeywordMap.put("teal", 0xFF008080);
        colorKeywordMap.put("thistle", 0xFFD8BFD8);
        colorKeywordMap.put("tomato", 0xFFFF6347);
        colorKeywordMap.put("turquoise", 0xFF40E0D0);
        colorKeywordMap.put("violet", 0xFFEE82EE);
        colorKeywordMap.put("wheat", 0xFFF5DEB3);
        colorKeywordMap.put("white", 0xFFFFFFFF);
        colorKeywordMap.put("whitesmoke", 0xFFF5F5F5);
        colorKeywordMap.put("yellow", 0xFFFFFF00);
        colorKeywordMap.put("yellowgreen", 0xFF9ACD32);
    }

    /** The style applied to this brush. */
    private Style style;
    /**The paint applied to the stroke of this brush. */
    private Paint strokePaint = new Paint();
    /**The paint applied to the fill of this brush. */
    private Paint fillPaint = new Paint();
    /**The intervals between the dashes of the stroke of this brush. */
    private float[] strokeDashIntervals;
    /**The intervals between the dashes of the fill of this brush. */
    private float[] fillDashIntervals;

    /**
     * Constructor.
     */
    public Brush() {
        strokePaint.setAntiAlias(true);
        fillPaint.setAntiAlias(true);
        strokePaint.setStyle(Paint.Style.STROKE);
        fillPaint.setStyle(Paint.Style.FILL);

        this.setColor(0xFF000000);
        this.setTextSize(12);
        this.setStrokeWidth(12);

        this.setStrokeJoin(Paint.Join.MITER);
        this.setStyle(Style.STROKE);
        this.setStrokeCap(Paint.Cap.BUTT);
    }

    /**
     * Copy constructor.
     * @param brush the brush to copy.
     */
    public Brush(Brush brush) {
        this.style = brush.getStyle();
        this.strokePaint = new Paint(brush.getStrokePaint());
        this.fillPaint = new Paint(brush.getFillPaint());
        if (brush.getStrokeDashIntervals() != null) {
            this.strokeDashIntervals = new float[brush.getStrokeDashIntervals().length];
            System.arraycopy(brush.getStrokeDashIntervals(), 0, this.strokeDashIntervals, 0, brush.getStrokeDashIntervals().length);
        }
        if (brush.getFillDashIntervals() != null) {
            this.fillDashIntervals = new float[brush.getFillDashIntervals().length];
            System.arraycopy(brush.getFillDashIntervals(), 0, this.fillDashIntervals, 0, brush.getFillDashIntervals().length);
        }
    }

    /**
     * Updates a brush based on a string definition.
     * @param styleStr svg string defining a style
     * @param brush the brush to apply the style to
     * @return the styled brush
     */
    public static Brush fromSvgString(String styleStr, Brush brush) {

        // Split into array of attributes
        String[] attributes = styleStr.split(";");

        for (String attribute : attributes) {
            // Split attribute name and attribute value
            String[] attrValue = attribute.split(":");
            // Trim data
            attrValue[0] = attrValue[0].trim();
            attrValue[1] = attrValue[1].trim();
            // Treat supported style attributes
            if ("fill".equals(attrValue[0])) {
                int resultingFill = parseSvgPaint(attrValue[1]);
                if (resultingFill == 0) {
                    brush.setStyle(Style.STROKE);
                } else {
                    brush.setStyle(Style.STROKE_AND_FILL);
                    brush.setColor(resultingFill);
                }
            } else if ("stroke".equals(attrValue[0])) {
                brush.setColor(parseSvgPaint(attrValue[1]));
            } else if ("stroke-width".equals(attrValue[0])) {
                brush.setStrokeWidth(Float.parseFloat(attrValue[1]));
            } else if ("stroke-dasharray".equals(attrValue[0])) {
                brush.setStrokeDashIntervals(parseSvgDashArray(attrValue[1]));
            }
        }

        return brush;
    }

    /**
     * Creates a new Brush from a string definition.
     * @param styleStr a svg string defining the brush
     * @return a new brush
     */
    public static Brush fromSvgString(String styleStr) {
        Brush brush = new Brush();
        return fromSvgString(styleStr, brush);
    }

    /**
     * Parses an SVG Paint attribute  (http://www.w3.org/TR/SVG/painting.html#SpecifyingPaint) and returns the color it actually represents .
     * @param color svg color of the paint
     * @return the color as integer
     */
    public static int parseSvgPaint(String color) {
        String newColor = color;
        if ("none".equals(newColor)) {
            // none
            return 0;
        } else if (newColor.startsWith("#")) {
            // #rgb and #rrggbb
            if (newColor.length() == 4) {
                // #rgb -> #rrggbb
                char r = newColor.charAt(1);
                char g = newColor.charAt(2);
                char b = newColor.charAt(3);
                newColor = "#" + r + r + g + g + b + b;
            }
            return (255 << 24) + Integer.parseInt(newColor.substring(1), 16);

        } else if (newColor.startsWith("rgb(")) {
            // rgb(r, g, b)
            newColor = newColor.substring(4, newColor.length() - 1);
            String[] rgbData = newColor.split(",");
            int r = Integer.parseInt(rgbData[0].trim());
            int g = Integer.parseInt(rgbData[1].trim());
            int b = Integer.parseInt(rgbData[2].trim());
            return (255 << 24) + (r << 16) + (g << 8) + b;

        } else {
            // color keywords
            if (colorKeywordMap.containsKey(newColor)) {
                return colorKeywordMap.get(newColor);
            }
        }
        // Default is black
        return 0xFF000000;
    }

    /**
     * Converts an integer color to the best matching SVG paint.
     * @param color the color as integer
     * @return an svg string representing the paint with the specified color
     */
    public static String wrapSvgPaint(int color) {
        if (colorKeywordMap.containsValue(color)) {
            for (Map.Entry<String, Integer> e : colorKeywordMap.entrySet()) {
                if (colorKeywordMap.get(e.getKey()) == color) {
                    return e.getKey();
                }
            }
        } else {
            String colorStr = String.format("%08X", 0xFFFFFFFF & color);
            colorStr = "#" + colorStr.substring(2);
            return colorStr;
        }

        return null;
    }

    /**
     * Parses an SVG dash-array attribute.
     * @param attributeStr a svg string representing the dash array
     * @return the array of dash intervals
     */
    private static float[] parseSvgDashArray(String attributeStr) {

        String[] dashArray = attributeStr.split(",");

        float[] intervals = new float[dashArray.length];

        for (int i = 0; i < dashArray.length; ++i) {
            intervals[i] = Float.parseFloat(dashArray[i].trim());
        }

        return intervals;
    }

    /**
     * Returns the android paint used for stroke drawing .
     * @return the stroke paint
     */
    public Paint getStrokePaint() {
        return this.strokePaint;
    }

    /**
     * Returns the android paint used for fill drawing.
     * @return the fill paint
     */
    public Paint getFillPaint() {
        return this.fillPaint;
    }

    /**
     * Sets the android color used for stroke drawing.
     * @param color the stroke color
     */
    public void setStrokeColor(int color) {
        strokePaint.setColor(color);
    }

    /**
     * Sets the android color used for fill drawing.
     * @param color the fill color
     */
    public void setFillColor(int color) {
        fillPaint.setColor(color);
    }

    /**
     * Sets the color for both stroke and fill drawings.
     * @param color the stroke and fill color
     */
    public final void setColor(int color) {
        setStrokeColor(color);
        setFillColor(color);
    }

    /**
     * Sets the android color used for stroke drawing.
     * @param textSize the text size in pixels
     */
    public void setStrokeTextSize(float textSize) {
        strokePaint.setTextSize(textSize);
    }

    /**
     * Sets the android color used for fill drawing.
     * @param textSize the text size in pixels
     */
    public void setFillTextSize(float textSize) {
        fillPaint.setTextSize(textSize);
    }

    /**
     * Sets the text size for both stroke and fill drawings.
     * @param textSize the text size in pixels
     */
    public final void setTextSize(float textSize) {
        setStrokeTextSize(textSize);
        setFillTextSize(textSize);
    }

    /**
     * Sets the width of lines drawn with stroke.
     * @param width width of the stroke
     */
    public void setStrokeWidth(float width) {
        strokePaint.setStrokeWidth(width);
    }

    /**
     * Sets the join type for stroke.
     * @param join the join type
     */
    public void setStrokeJoin(Paint.Join join) {
        strokePaint.setStrokeJoin(join);
    }

    /**
     * Sets the cap for stroke.
     * @param cap the cap of the stroke
     */
    public void setStrokeCap(Paint.Cap cap) {
        strokePaint.setStrokeCap(cap);
    }

    /**
     * Returns the stroke dash intervals.
     * @return array of the intervals
     */
    public float[] getStrokeDashIntervals() {
        if (this.strokeDashIntervals != null) {
            return this.strokeDashIntervals.clone();
        } else {
            return new float[]{};
        }
    }

    /**
     * Sets the stroke dash intervals (first is filled).
     * @param intervals array of the intervals
     */
    public void setStrokeDashIntervals(float[] intervals) {
        this.strokeDashIntervals = intervals.clone();
        this.strokePaint.setPathEffect(new DashPathEffect(intervals, 0));
    }

    /**
     * Returns the stroke dash intervals.
     * @return array of the intervals
     */
    public float[] getFillDashIntervals() {
        if (this.fillDashIntervals != null) {
            return this.fillDashIntervals.clone();
        } else {
            return new float[]{};
        }
    }

    /**
     * Sets the fill dash intervals (first is filled).
     * @param intervals array of the intervals
     */
    public void setFillDashIntervals(float[] intervals) {
        this.fillDashIntervals = intervals.clone();
        this.fillPaint.setPathEffect(new DashPathEffect(intervals, 0));
    }

    /**
     * Gets the style applied to this brush.
     * @return the brush style
     */
    public Style getStyle() {
        return this.style;
    }

    /**
     * Sets the style applied to this brush.
     * @param style the brush style
     */
    public void setStyle(Style style) {
        this.style = style;
    }

    /**
     * Converts the current Brush to an SVG style attribute.
     * @return the svg string representing this brush
     */
    public String toSvgString() {
        String colorStr;

        StringBuilder svgStrBuilder = new StringBuilder();
        svgStrBuilder.append("style=\"");

        // Stroke and fill
        if (this.getStyle() == Style.STROKE) {
            svgStrBuilder.append("fill:none");
            colorStr = wrapSvgPaint(this.strokePaint.getColor());
            svgStrBuilder.append(";stroke:").append(colorStr);

        } else if (this.getStyle() == Style.FILL) {
            colorStr = wrapSvgPaint(this.fillPaint.getColor());
            svgStrBuilder.append("fill:").append(colorStr);

        } else if (this.getStyle() == Style.STROKE_AND_FILL) {
            colorStr = wrapSvgPaint(this.fillPaint.getColor());
            svgStrBuilder.append("fill:").append(colorStr);
            colorStr = wrapSvgPaint(this.strokePaint.getColor());
            svgStrBuilder.append(";stroke:").append(colorStr);
        }

        // Stroke width
        svgStrBuilder.append(";stroke-width:").append((int) this.strokePaint.getStrokeWidth());

        // Stroke dash array
        if (this.strokeDashIntervals != null && this.strokeDashIntervals.length > 0) {
            svgStrBuilder.append(";stroke-dasharray:");
            for (int i = 0; i < this.strokeDashIntervals.length; ++i) {
                svgStrBuilder.append((int) this.strokeDashIntervals[i]);
                if (i != this.strokeDashIntervals.length - 1) {
                    svgStrBuilder.append(",");
                }
            }
        }

        svgStrBuilder.append("\"");

        return svgStrBuilder.toString();
    }

    /**
     * Enumeration listing possible styles of brush.
     */
    public enum Style {
        /** Stroke only. **/
        STROKE,
        /** Fill only. **/
        FILL,
        /** Stroke and fill. **/
        STROKE_AND_FILL,
    }
}

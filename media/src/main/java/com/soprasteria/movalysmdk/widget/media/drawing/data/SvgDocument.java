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

import android.util.Log;
import android.util.Xml;

import com.soprasteria.movalysmdk.widget.media.drawing.data.MarkerElement.MarkerUnits;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A model representation of an svg document.
 */
public class SvgDocument {

    /**
     * Namespace constant.
     */
    private static final String NAMESPACE = null;

    /**
     * style key word.
     */
    private static final String STYLE = "style";

    /**
     * Width of the svg.
     */
    private int mWidth;
    /**
     * Height of the svg.
     */
    private int mHeight;


    /**
     * Width of the viewbox of the svg.
     */
    private int mViewboxWidth;
    /**
     * Height of the viewbox pf the svg.
     */
    private int mViewboxHeight;


    /**
     * List of drawing elements.
     */
    private List<DrawingElement> mData;

    /**
     * Mp of markers.
     */
    private Map<String, MarkerElement> mMarkerMap;

    /**
     * Constructor.
     * @param width width of the svg canvas.
     * @param height height of the svg canvas.
     * @param viewboxWidth width of the viewbox of the svg canvas.
     * @param viewboxHeight height of the viewbox of the svg canvas.
     */
    public SvgDocument(int width, int height, int viewboxWidth, int viewboxHeight) {
        this.mWidth = width;
        this.mHeight = height;
        this.mViewboxWidth = viewboxWidth;
        this.mViewboxHeight = viewboxHeight;
        this.mData = new LinkedList<>();
        this.mMarkerMap = new HashMap<>();
    }

    /**
     * Constructor.
     * @param width width of the svg canvas.
     * @param height height of the svg canvas.
     * @param viewboxWidth width of the viewbox of the svg canvas.
     * @param viewboxHeight height of the viewbox of the svg canvas.
     * @param drawingData elements to draw
     */
    public SvgDocument(int width, int height, int viewboxWidth, int viewboxHeight, List<DrawingElement> drawingData) {
        this.mWidth = width;
        this.mHeight = height;
        this.mViewboxWidth = viewboxWidth;
        this.mViewboxHeight = viewboxHeight;
        this.mData = drawingData;
    }

    /**
     * Gets the width.
     * @return the width
     */
    public int getWidth() {
        return mViewboxWidth!=0?mViewboxWidth:mWidth;
    }

    /**
     * Sets the width.
     * @param mWidth the width
     */
    public void setWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    /**
     * Gets the height.
     * @return the height
     */
    public int getHeight() {
        return mViewboxHeight!=0?mViewboxHeight:mHeight;
    }

    /**
     * Sets the height.
     * @param mHeight the height
     */
    public void setHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    /**
     * Add element to the drawing elements list.
     * @param data element to add
     */
    public void addDrawingData(DrawingElement data) {
        mData.add(data);
    }

    /**
     * Gets the drawing elements list.
     * @return the drawing elements list
     */
    public List<DrawingElement> getDrawingData() {
        return this.mData;
    }

    /**
     * Add a marker to the marker map.
     * @param markerElement the marker to add
     */
    public void addMarker(MarkerElement markerElement) {
        this.mMarkerMap.put(markerElement.getId(), markerElement);
    }

    /**
     * Gets the marker at specified id key.
     * @param id the key of the marker to get
     * @return the marker with the specified id key
     */
    public MarkerElement getMarker(String id) {
        return this.mMarkerMap.get(id);
    }

    /**
     * Converts the svg document to a string representation.
     * @return a string representation of this svg document
     */
    public String toString() {

        StringBuilder svgStrBuilder = new StringBuilder();

        svgStrBuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
                .append("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" ")
                .append("width=\"").append(mWidth)
                .append("\" height=\"").append(mHeight);

        if(mViewboxHeight!= 0 && mViewboxWidth !=0){
            svgStrBuilder.append("\" viewbox=\"0 0 ").append(mViewboxWidth).append(" ").append(mViewboxHeight);
        }

        svgStrBuilder.append("\">");

        for (DrawingElement data : mData) {
            svgStrBuilder.append(data.toSvg());
        }

        svgStrBuilder.append("</svg>");

        return svgStrBuilder.toString();
    }

    /**
     * Parses the specified string to build an svg document.
     * @param svgStr the string to parse
     * @return a new svg document
     * @throws SVGException when unable to parse
     */
    public static SvgDocument parse(String svgStr) throws SVGException {

        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new StringReader(svgStr));
            parser.nextTag();

            return readSvgDocument(parser);
        }catch( XmlPullParserException | IOException e){
            Log.e("SVG parsing",e.getMessage(),e);
            throw new SVGException();
        }
    }

    /**
     * Parses the specified XML to build an svg document.
     * @param parser the parser
     * @return a new svg document
     * @throws IOException exception
     * @throws XmlPullParserException exception
     */
    private static SvgDocument readSvgDocument(XmlPullParser parser) throws IOException, XmlPullParserException {

        SvgDocument svgDocument;

        parser.require(XmlPullParser.START_TAG, NAMESPACE, "svg");

        int width = Integer.valueOf(parser.getAttributeValue(NAMESPACE, "width"));
        int height = Integer.valueOf(parser.getAttributeValue(NAMESPACE, "height"));

        String viewbox = parser.getAttributeValue(NAMESPACE, "viewbox");
        int viewboxWidth = 0;
        int viewboxHeight = 0;
        if(viewbox != null && !viewbox.isEmpty()){
            viewboxWidth = Integer.parseInt(viewbox.substring(4, viewbox.indexOf(" ", 4)));
            viewboxHeight = Integer.parseInt(viewbox.substring(viewbox.lastIndexOf(" ")+1));
        }

        svgDocument = new SvgDocument(width, height,viewboxWidth,viewboxHeight);

        readTags(parser, svgDocument);

        return svgDocument;
    }

    /**
     * Reads the tags of a parser and and puts them in the specified document.
     * @param parser the parser
     * @param svgDocument the svg document
     * @throws IOException exception
     * @throws XmlPullParserException exception
     */
    private static void readTags(XmlPullParser parser, SvgDocument svgDocument) throws XmlPullParserException, IOException {

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String tagName = parser.getName();

            switch (tagName) {
                case "line":
                    svgDocument.addDrawingData(readLine(parser, svgDocument));
                    break;
                case "path":
                    svgDocument.addDrawingData(readPath(parser));
                    break;
                case "circle":
                    svgDocument.addDrawingData(readCircle(parser));
                    break;
                case "ellipse":
                    svgDocument.addDrawingData(readEllipse(parser));
                    break;
                case "rect":
                    svgDocument.addDrawingData(readRectangle(parser));
                    break;
                case "text":
                    svgDocument.addDrawingData(readText(parser));
                    break;
                case "marker":
                    svgDocument.addMarker(readMarker(parser));
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
    }

    /**
     * Reads an element with the parser to put into the document.
     * @param parser the parser
     * @param svgDocument the svg document
     * @return a new drawing element
     * @throws XmlPullParserException exception
     * @throws IOException exception
     */
    private static DrawingElement readLine(XmlPullParser parser, SvgDocument svgDocument) throws XmlPullParserException, IOException {

        LineElement data = new LineElement();

        parser.require(XmlPullParser.START_TAG, NAMESPACE, "line");
        data.getStartingPoint().x = Integer.valueOf(parser.getAttributeValue(NAMESPACE, "x1"));
        data.getStartingPoint().y = Integer.valueOf(parser.getAttributeValue(NAMESPACE, "y1"));
        data.getEndingPoint().x = Integer.valueOf(parser.getAttributeValue(NAMESPACE, "x2"));
        data.getEndingPoint().y = Integer.valueOf(parser.getAttributeValue(NAMESPACE, "y2"));
        // Parse marker start and end
        if (parser.getAttributeValue(NAMESPACE, "marker-start") != null) {
            data.setMarkerStart(svgDocument.getMarker(parseFuncIRI(parser.getAttributeValue(NAMESPACE, "marker-start"))));
        }
        if (parser.getAttributeValue(NAMESPACE, "marker-end") != null) {
            data.setMarkerEnd(svgDocument.getMarker(parseFuncIRI(parser.getAttributeValue(NAMESPACE, "marker-end"))));
        }

        // Update the centroid and android path of the object
        data.update();

        if (parser.getAttributeValue(NAMESPACE, STYLE) != null) {
            data.setBrush(Brush.fromSvgString(parser.getAttributeValue(NAMESPACE, STYLE)));
        }
        // Skip the end tag
        parser.next();
        return data;
    }

    /**
     * Reads an element with the parser to put into the document.
     * @param parser the parser
     * @return a new drawing element
     * @throws XmlPullParserException exception
     * @throws IOException exception
     */
    private static DrawingElement readCircle(XmlPullParser parser) throws XmlPullParserException, IOException {

        CircleElement data = new CircleElement();

        parser.require(XmlPullParser.START_TAG, NAMESPACE, "circle");
        data.getCenter().x = Integer.valueOf(parser.getAttributeValue(NAMESPACE, "cx"));
        data.getCenter().y = Integer.valueOf(parser.getAttributeValue(NAMESPACE, "cy"));
        data.setRadius(Integer.valueOf(parser.getAttributeValue(NAMESPACE, "r")));

        // Update the centroid and android path of the object
        data.update();

        if (parser.getAttributeValue(NAMESPACE, STYLE) != null) {
            data.setBrush(Brush.fromSvgString(parser.getAttributeValue(NAMESPACE, STYLE)));
        }
        // Skip the end tag
        parser.next();
        return data;
    }

    /**
     * Reads an element with the parser to put into the document.
     * @param parser the parser
     * @return a new drawing element
     * @throws XmlPullParserException exception
     * @throws IOException exception
     */
    private static DrawingElement readEllipse(XmlPullParser parser) throws XmlPullParserException, IOException {

        OvalElement data = new OvalElement();

        parser.require(XmlPullParser.START_TAG, NAMESPACE, "ellipse");
        int cx = Integer.valueOf(parser.getAttributeValue(NAMESPACE, "cx"));
        int cy = Integer.valueOf(parser.getAttributeValue(NAMESPACE, "cy"));
        int rx = Integer.valueOf(parser.getAttributeValue(NAMESPACE, "rx"));
        int ry = Integer.valueOf(parser.getAttributeValue(NAMESPACE, "ry"));

        data.getOval().left = cx - rx;
        data.getOval().right = cx + rx;
        data.getOval().top = cy - ry;
        data.getOval().bottom = cy + ry;

        // Update the centroid and android path of the object
        data.update();

        if (parser.getAttributeValue(NAMESPACE, STYLE) != null) {
            data.setBrush(Brush.fromSvgString(parser.getAttributeValue(NAMESPACE, STYLE)));
        }
        // Skip the end tag
        parser.next();
        return data;
    }

    /**
     * Reads an element with the parser to put into the document.
     * @param parser the parser
     * @return a new drawing element
     * @throws XmlPullParserException exception
     * @throws IOException exception
     */
    private static DrawingElement readRectangle(XmlPullParser parser) throws XmlPullParserException, IOException {

        RectangleElement data = new RectangleElement();

        parser.require(XmlPullParser.START_TAG, NAMESPACE, "rect");
        int x = Integer.valueOf(parser.getAttributeValue(NAMESPACE, "x"));
        int y = Integer.valueOf(parser.getAttributeValue(NAMESPACE, "y"));
        int width = Integer.valueOf(parser.getAttributeValue(NAMESPACE, "width"));
        int height = Integer.valueOf(parser.getAttributeValue(NAMESPACE, "height"));

        data.getStartingPoint().x = x;
        data.getEndingPoint().x = x + width;
        data.getStartingPoint().y = y;
        data.getEndingPoint().y = y + height;

        // Update the centroid and android path of the object
        data.update();

        if (parser.getAttributeValue(NAMESPACE, STYLE) != null) {
            data.setBrush(Brush.fromSvgString(parser.getAttributeValue(NAMESPACE, STYLE)));
        }
        // Skip the end tag
        parser.next();
        return data;
    }

    /**
     * Reads an element with the parser to put into the document.
     * @param parser the parser
     * @return a new drawing element
     * @throws XmlPullParserException exception
     * @throws IOException exception
     */
    private static DrawingElement readText(XmlPullParser parser) throws XmlPullParserException, IOException {

        TextElement data = new TextElement();

        parser.require(XmlPullParser.START_TAG, NAMESPACE, "text");
        data.getCoords().x = Integer.valueOf(parser.getAttributeValue(NAMESPACE, "x"));
        data.getCoords().y = Integer.valueOf(parser.getAttributeValue(NAMESPACE, "y"));

        // Update the centroid and android path of the object
        data.update();

        if (parser.getAttributeValue(NAMESPACE, STYLE) != null) {
            Brush brush = new Brush();
            Brush.fromSvgString(parser.getAttributeValue(NAMESPACE, STYLE), brush);

            data.setBrush(brush);
        }
        // Go to the text tag and get the text
        if (parser.next() != XmlPullParser.END_TAG) {
            data.setValue(parser.getText());

            // Skip the end tag
            parser.next();
        }

        return data;
    }

    /**
     * Reads an element with the parser to put into the document.
     * @param parser the parser
     * @return a new drawing element
     * @throws XmlPullParserException exception
     * @throws IOException exception
     */
    private static MarkerElement readMarker(XmlPullParser parser) throws XmlPullParserException, IOException {

        MarkerElement marker = new MarkerElement();

        parser.require(XmlPullParser.START_TAG, NAMESPACE, "marker");
        marker.getRef().x = Integer.valueOf(parser.getAttributeValue(NAMESPACE, "refX"));
        marker.getRef().y = Integer.valueOf(parser.getAttributeValue(NAMESPACE, "refY"));
        if ("strokeWidth".equals(parser.getAttributeValue(NAMESPACE, "markerUnits"))) {
            marker.setMarkerUnits(MarkerUnits.STROKE_WIDTH);
        } else {
            marker.setMarkerUnits(MarkerUnits.USER_SPACE_ON_USE);
        }
        marker.setMarkerWidth(Integer.valueOf(parser.getAttributeValue(NAMESPACE, "markerWidth")));
        marker.setMarkerHeight(Integer.valueOf(parser.getAttributeValue(NAMESPACE, "markerHeight")));
        if ("auto".equals(parser.getAttributeValue(NAMESPACE, "orient"))) {
            marker.setAutoOrientation(true);
        } else {
            marker.setAutoOrientation(false);
            marker.setAngle(Integer.valueOf(parser.getAttributeValue(NAMESPACE, "orient")));
        }
        marker.setId(parser.getAttributeValue(NAMESPACE, "id"));
        marker.setStyle(parser.getAttributeValue(NAMESPACE, STYLE));

        // Parse the marker contained data
        SvgDocument tempSvgDocument = new SvgDocument(0,0,0,0);
        readTags(parser, tempSvgDocument);
        marker.setElements(tempSvgDocument.mData);

        return marker;
    }

    /**
     * Reads an element with the parser to put into the document.
     * @param parser the parser
     * @return a new drawing element
     * @throws XmlPullParserException exception
     * @throws IOException exception
     */
    private static DrawingElement readPath(XmlPullParser parser) throws XmlPullParserException, IOException {

        int x = 0;
        int y = 0;
        HandFreeElement data = new HandFreeElement();

        parser.require(XmlPullParser.START_TAG, NAMESPACE, "path");
        data.getPoints().clear();
        SvgParserHelper dParser = new SvgParserHelper(parser.getAttributeValue(NAMESPACE, "d"));

        while (!dParser.isEmpty()) {
            dParser.trim();
            switch (dParser.charAt(0)) {
                case 'M':
                    dParser.substring(1);
                    x = dParser.consumeFirstIntFromString();
                    y = dParser.consumeFirstIntFromString();
                    data.getPoints().add(new PathPoint(PathPoint.PointType.MOVETO, x, y));
                    break;
                case 'm':
                    dParser.substring(1);
                    x += dParser.consumeFirstIntFromString();
                    y += dParser.consumeFirstIntFromString();
                    data.getPoints().add(new PathPoint(PathPoint.PointType.MOVETO, x, y));
                    break;
                case 'Z':
                case 'z':
                    dParser.substring(1);
                    data.getPoints().add(new PathPoint(PathPoint.PointType.CLOSE, x, y));
                    break;
                case 'L':
                    dParser.substring(1);
                    x = dParser.consumeFirstIntFromString();
                    y = dParser.consumeFirstIntFromString();
                    data.getPoints().add(new PathPoint(PathPoint.PointType.LINETO, x, y));
                    break;
                case 'l':
                    dParser.substring(1);
                    x += dParser.consumeFirstIntFromString();
                    y += dParser.consumeFirstIntFromString();
                    data.getPoints().add(new PathPoint(PathPoint.PointType.LINETO, x, y));
                    break;
                case 'H':
                    dParser.substring(1);
                    x = dParser.consumeFirstIntFromString();
                    data.getPoints().add(new PathPoint(PathPoint.PointType.LINETO, x, y));
                    break;
                case 'h':
                    dParser.substring(1);
                    x += dParser.consumeFirstIntFromString();
                    data.getPoints().add(new PathPoint(PathPoint.PointType.LINETO, x, y));
                    break;
                case 'V':
                    dParser.substring(1);
                    y = dParser.consumeFirstIntFromString();
                    data.getPoints().add(new PathPoint(PathPoint.PointType.LINETO, x, y));
                    break;
                case 'v':
                    dParser.substring(1);
                    y += dParser.consumeFirstIntFromString();
                    data.getPoints().add(new PathPoint(PathPoint.PointType.LINETO, x, y));
                    break;
                default:
                    break;
            }
        }

        // Update the centroid and android path of the object
        data.update();
        // Parse style
        if (parser.getAttributeValue(NAMESPACE, STYLE) != null) {
            data.setBrush(Brush.fromSvgString(parser.getAttributeValue(NAMESPACE, STYLE)));
        }
        // Skip the end tag
        parser.next();

        return data;
    }

    /**
     * Skip to next element.
     * @param parser the parser
     * @throws XmlPullParserException exception
     * @throws IOException exception
     */
    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Parses the iri in a string.
     * @param funcIRI the string to parse
     * @return a valid iri
     */
    private static String parseFuncIRI(String funcIRI) {

        String iri = funcIRI.trim();
        if (iri.startsWith("url(#") && iri.endsWith(")")) {
            // Valid FuncIRI
            return iri.substring(5, iri.length() - 1);
        }
        // Fallback case
        return "";
    }

    /**
     * Exception thrown when parsing svg.
     */
    private static class SVGException extends Exception {
    }
}


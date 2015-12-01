/**
 * Copyright (C) 2010 Sopra Steria Group (movalys.support@soprasteria.com)
 *
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
package com.soprasteria.movalysmdk.widget.media;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Picture;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Helper class to create scaled bitmap with SVG layer.
 */
public abstract class BitmapHelper {

    /**
     * Private constructor.
     */
    private BitmapHelper(){
        //Empty constructor.
    }

    /** Tag for debugger. **/
    private static final String TAG = "BitmapHelper";

    /**
     * Creates a bitmap with the mediaURI and, if applicable, the svg layer.
     * @param context the context to open the uri
     * @param bitmapUri uri of the file containing the bitmap
     * @param svgString string representation of the svg layer to apply, facultative
     * @param size the size of the new bitmap
     * @return a rasterized bitmap of the source image and the svg layer.
     * @throws IOException when the file cannot be opened
     */
    public static Bitmap createViewBitmap(Context context, Uri bitmapUri, @Nullable String svgString, int size) throws IOException{

        int orientation = getBitmapOrientation(context, bitmapUri);

        Bitmap bmp = scaleBitmap(context, bitmapUri, size);

        //Rotate bitmap if needed
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
            bmp = rotateBitmap(bmp, 90);
        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
            bmp = rotateBitmap(bmp, 180);
        }else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
            bmp = rotateBitmap(bmp, 270);
        }

        //Draw svg if present
        if(svgString!=null) {
            Canvas canvas = new Canvas(bmp);
            try {
                SVG svg = SVG.getFromString(svgString);
                canvas.drawBitmap(ThumbnailUtils.extractThumbnail(picture2Bitmap(svg.renderToPicture()),canvas.getWidth(),canvas.getHeight()),0,0,null);
            } catch (SVGParseException e) {
                Log.w(TAG, "Error parsing SVG: \n" + svgString, e);
            }
        }

        return bmp;
    }

    /**
     * Transforms a Picture in a Bitmap.
     * @param picture the Picture to transform
     * @return a Bitmap
     */
    private static Bitmap picture2Bitmap(Picture picture) {
        PictureDrawable pd = new PictureDrawable(picture);
        Bitmap bitmap = Bitmap.createBitmap(pd.getIntrinsicWidth(), pd.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawPicture(pd.getPicture());
        return bitmap;
    }

    /**
     * Scales a bitmap to stay under a certain size.
     * @param context the context to open the uri
     * @param bitmapUri uri of the file containing the bitmap
     * @param maxSize the max size of the bitmap
     * @return a scaled bitmap
     * @throws FileNotFoundException when the file cannot be opened
     */
    public static Bitmap scaleBitmap(Context context, Uri bitmapUri, int maxSize) throws FileNotFoundException{
        // Decode image size
        Point size = calculateBitmapSize(context,bitmapUri);

        // Find the correct scale value. It should be the power of 2.
        int scale = 1;
        while(size.x / scale / 2 >= maxSize &&
                size.y / scale / 2 >= maxSize) {
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;

        Bitmap extractedBmp = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(bitmapUri), null, o2);

        Bitmap bmp = extractedBmp.copy(Bitmap.Config.ARGB_8888,true);
        extractedBmp.recycle();
        extractedBmp = null;

        return bmp;
    }

    /**
     * Scales a bitmap to stay under a certain size.
     * @param context the context to open the uri
     * @param drawableRes drawable resource containing the bitmap
     * @param maxSize the max size of the bitmap
     * @return a scaled bitmap
     */
    public static Bitmap scaleBitmap(Context context, int drawableRes, int maxSize){
        // Decode image size

        Drawable bitmapDrawable = ContextCompat.getDrawable(context,drawableRes);
        Point size = new Point(bitmapDrawable.getIntrinsicWidth(),bitmapDrawable.getIntrinsicHeight());

        // Find the correct scale value. It should be the power of 2.
        int scale = 1;
        while(size.x / scale / 2 >= maxSize &&
                size.y / scale / 2 >= maxSize) {
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;

        Bitmap extractedBmp = BitmapFactory.decodeResource(context.getResources(), drawableRes, o2);

        Bitmap bmp = extractedBmp.copy(Bitmap.Config.ARGB_8888,true);
        extractedBmp.recycle();
        extractedBmp = null;

        return bmp;
    }

    /**
     * Gets the orientation of a bitmap based on the EXIF info of the file it's stored in.
     * @param context the context to open the uri
     * @param bitmapUri uri of the file containing the bitmap
     * @return an integer representing the orientation of the bitmap
     * @throws IOException when the file cannot be opened
     */
    private static int getBitmapOrientation(Context context, Uri bitmapUri) throws IOException{
        int orientation = 0;

        if (bitmapUri != null){
            String filePath = "";
            if("content".equals(bitmapUri.getScheme())) {
                Cursor cursor = context.getContentResolver().query(bitmapUri, new String[] { android.provider.MediaStore.Images.ImageColumns.DATA }, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    filePath = cursor.getString(0);
                    cursor.close();
                }
            } else {
                filePath = bitmapUri.getPath();
            }

            ExifInterface ei = new ExifInterface(filePath);
            orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        }

        return orientation;
    }

    /**
     * Rotates  bitmap by an angle.
     * @param source the source bitmap
     * @param angle the angle to rotate
     * @return a rotated bitmap
     */
    private static Bitmap rotateBitmap(Bitmap source, float angle){

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    /**
     * Calculates the size (width, height) of a bitmap stored in a file.
     * @param context the context to open the uri
     * @param bitmapUri uri of the file containing the bitmap
     * @return a point where X is width and Y is height, representing the image size
     * @throws FileNotFoundException when the file cannot be opened
     */
    public static Point calculateBitmapSize(Context context, Uri bitmapUri) throws FileNotFoundException {

        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(context.getContentResolver().openInputStream(bitmapUri), null, o);

        return new Point(o.outWidth,o.outHeight);
    }
}

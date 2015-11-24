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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

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
     * @return a rasterized bitmap of the source image and the svg layer.
     */
    public static Bitmap createViewBitmap(Context context, Uri bitmapUri, @Nullable String svgString){
        try {
            ExifInterface ei = new ExifInterface(bitmapUri.getPath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(context.getContentResolver().openInputStream(bitmapUri), null, o);

            // The new size we want to scale to
            final int requiredSize=1024;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= requiredSize &&
                    o.outHeight / scale / 2 >= requiredSize) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;

            Bitmap extractedBmp = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(bitmapUri), null, o2);

            Bitmap bmp = extractedBmp.copy(Bitmap.Config.ARGB_8888,true);
            extractedBmp.recycle();
            extractedBmp = null;

            //Rotate bitmap if needed
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                bmp = rotateBitmap(bmp, 90);

            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                bmp = rotateBitmap(bmp, 180);
            }

            //Draw svg if present
            if(svgString!=null) {
                Canvas canvas = new Canvas(bmp);
                SVG svg = SVG.getFromString(svgString);

                canvas.drawPicture(svg.renderToPicture());
            }

            return bmp;
        } catch (IOException e) {
            Log.w(TAG, "Error trying to access file: " + bitmapUri, e);
        } catch (SVGParseException e) {
            Log.w(TAG, "Error parsing SVG: \n" + svgString, e);
        }
        return null;
    }

    /**
     * Rotates  bitmap by an angle.
     * @param source the source bitmap
     * @param angle the angle to rotate
     * @return a rotated bitmap
     */
    public static Bitmap rotateBitmap(Bitmap source, float angle){

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}

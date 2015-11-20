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
package com.soprasteria.movalysmdk.widget.core.helper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


/**
 * Helper class for MDKPresenterView.
 */
public abstract class MDKPresenterHelper {

    /**
     * Constructor.
     */
    private MDKPresenterHelper() {
        //not called
    }

    /**
     * Method to set random color using a specific array of color.
     * By default, the color come from material design color.
     *
     * @param mdkPresenterViewTitle The titleView of the MDKPresenterView
     * @param title                 The text of text view (not use in this case)
     */
    public static void generateColor(View mdkPresenterViewTitle, String title) {
        if (title != null) {
            setColor(mdkPresenterViewTitle, calculateColor(title));
        }
    }

    /**
     * Method to set a specific color on the drawable background of the titleView.
     *
     * @param mdkPresenterViewTitle The titleView of the MDKPresenterView
     * @param color                 The color resource
     */
    public static void setColor(View mdkPresenterViewTitle, int color) {
        if (mdkPresenterViewTitle != null) {
            mdkPresenterViewTitle.getBackground().setColorFilter(color, PorterDuff.Mode.SRC);
        }
    }

    /**
     * Method to transform image into rounded image.
     *
     * @param bmp    The BitMap to transform
     * @param radius The radius of the image
     * @return the rounded BitMap
     */
    public static Bitmap getRoundedBitmap(Bitmap bmp, int radius) {
        if (bmp != null && radius != 0) {
            Bitmap sbmp;
            if (bmp.getWidth() != radius || bmp.getHeight() != radius) {
                sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
            } else {
                sbmp = bmp;
            }
            Bitmap output = Bitmap.createBitmap(sbmp.getWidth(),
                    sbmp.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());
            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
            paint.setDither(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.parseColor("#BAB399"));
            canvas.drawCircle(sbmp.getWidth() / 2f + 0.7f, sbmp.getHeight() / 2f + 0.7f,sbmp.getWidth() / 2f + 0.1f, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(sbmp, rect, rect, paint);
            return output;
        }
        return null;
    }

    /**
     * Method to manage the cross animation between titleView and imageView.
     *
     * @param titleView The view of the textView
     * @param imageView The view of the imageView
     */
    public static void crossFading(View titleView, View imageView) {
        Animation out = AnimationUtils.loadAnimation(titleView.getContext(), android.R.anim.fade_out);
        Animation in = AnimationUtils.loadAnimation(imageView.getContext(), android.R.anim.fade_in);
        titleView.startAnimation(out);
        imageView.startAnimation(in);
        titleView.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
    }

    /**
     * Method to convert string into hex code color.
     *
     * @param title The string in the Item
     * @return the color int
     */
    private static int calculateColorBase(String title) {
        String opacity = "#ff";
        String hexColor = String.format(opacity + "%06X", 0xeeeeee & title.hashCode());
        return Color.parseColor(hexColor);
    }

    /**
     * Method to get material color from calculateColorBase.
     *
     * @param title The string in the Item
     * @return the material color int
     */
    private static int calculateColor(String title) {
        ShapeDrawable drawable = new ShapeDrawable(new RectShape());
        drawable.getPaint().setColor(calculateColorBase(title));
        drawable.setIntrinsicHeight(2);
        drawable.setIntrinsicWidth(2);
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        Palette palette = Palette.generate(bitmap);
        return palette.getVibrantColor(0xff00bcd4);
    }
}

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

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.soprasteria.movalysmdk.widget.core.R;

import java.util.Random;

/**
 * Helper class for MDKPresenterView.
 */
public abstract class MDKPresenterHelper {
    /**
     * The random generator for color catalogue.
     */
    private static final Random randomGenerator = new Random();

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
        Resources res = mdkPresenterViewTitle.getContext().getResources();
        int[] colors = res.getIntArray(R.array.MDKPresenterMaterialColor);
        setColor(mdkPresenterViewTitle, colors[randomGenerator.nextInt(colors.length)]);
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
            canvas.drawCircle(sbmp.getWidth() / 2 + 0.7f, sbmp.getHeight() / 2 + 0.7f,
                    sbmp.getWidth() / 2 + 0.1f, paint);
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
}

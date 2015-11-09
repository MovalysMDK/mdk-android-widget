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
package com.soprasteria.movalysmdk.widget.basic;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.basic.model.MDKPresenter;
import com.soprasteria.movalysmdk.widget.core.helper.MDKPresenterHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * MDKPresenterView.
 */
public class MDKPresenterView extends RelativeLayout {

    /**
     * A random list of color.
     */
    private List<String> catalogue = new ArrayList<>();

    /**
     * The title textView.
     */
    private TextView titleView;

    /**
     * The imageView.
     */
    private ImageView imageView;
    /**
     * The image Uri.
     */
    private Uri imageUri;

    /**
     * Constructor.
     *
     * @param context the context
     */
    public MDKPresenterView(Context context) {
        super(context);
        if (!isInEditMode()) {
            init(null);
        }
    }

    /**
     * Constructor.
     *
     * @param context the context
     * @param attrs   the attributes
     */
    public MDKPresenterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(attrs);
        }
    }

    /**
     * Constructor.
     *
     * @param context  the context
     * @param attrs    the attributes
     * @param defStyle the def style
     */
    public MDKPresenterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) {
            init(attrs);
        }
    }

    /**
     * Inflation on initialization.
     *
     * @param attrs the attributes
     */
    private void init(AttributeSet attrs) {

        /* Get attrs style */
        TypedArray typedArrayComponent = this.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKPresenterViewComponent);
        float titleSize = typedArrayComponent.getDimension(R.styleable.MDKCommons_MDKPresenterViewComponent_title_size, 18);
        int titleColor = typedArrayComponent.getColor(R.styleable.MDKCommons_MDKPresenterViewComponent_title_color, -1);
        Drawable titleBackground = typedArrayComponent.getDrawable(R.styleable.MDKCommons_MDKPresenterViewComponent_title_background);

        /* Inflate views */
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        inflater.inflate(R.layout.mdkwidget_presenter_layout, this);
        titleView = (TextView) this.findViewById(R.id.component_title);
        imageView = (ImageView) this.findViewById(R.id.component_image);

        /* Init TextView */
        if (titleView != null) {
            titleView.setTextColor(titleColor);
            titleView.setTextSize(titleSize);
            if (titleBackground != null) {
                titleView.setBackgroundDrawable(titleBackground);
            }
        }

        /* Color catalogue */
        catalogueGenerator();
        MDKPresenterHelper.setRandomColor(this.titleView, catalogue);

        /* Recycling */
        typedArrayComponent.recycle();
    }

    /**
     * To set the title.
     *
     * @param title the title to set into titleView
     */
    public void setTitle(String title) {
        if (titleView != null) {
            titleView.setText(title);
        }
    }


    /**
     * To set the MDKPresenter in the MDKPresenterView.
     *
     * @param presenter the MDKPresenter created by the user
     */
    public void setPresenter(MDKPresenter presenter) {
        if (presenter != null) {
            this.setTitle(presenter.getString());
            this.setImage(presenter.getUri());
        }
    }

    /**
     * The list of material base colors.
     */
    private void catalogueGenerator() {
        catalogue.add(0, "#F44336");
        catalogue.add(1, "#673AB7");
        catalogue.add(2, "#03A9F4");
        catalogue.add(3, "#4CAF50");
        catalogue.add(4, "#FFEB3B");
        catalogue.add(5, "#FF5722");
        catalogue.add(6, "#607D8B");
        catalogue.add(7, "#E91E63");
        catalogue.add(8, "#3F51B5");
        catalogue.add(9, "#00BCD4");
        catalogue.add(10, "#8BC34A");
        catalogue.add(11, "#FFC107");
        catalogue.add(12, "#795548");
        catalogue.add(13, "#9C27B0");
        catalogue.add(14, "#2196F3");
        catalogue.add(15, "#009688");
        catalogue.add(16, "#CDDC39");
        catalogue.add(17, "#FF9800");
        catalogue.add(18, "#9E9E9E");
    }

    /**
     * Method to set the image located by the Uri.
     *
     * @param uri The Uri set in the MDKPresenter
     */
    private void setImage(Uri uri) {
        if (uri != null && imageView != null) {
            this.imageUri = uri;
            ViewTreeObserver vto = this.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    if (getWidth() != 0 && getHeight() != 0) {
                        updateImageView();
                    }
                }
            });
        }
    }

    /**
     * Updates the imageView with the imageUri.
     */
    private void updateImageView() {
        if (imageView != null) {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        Bitmap bmp = ThumbnailUtils.extractThumbnail(MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri), getHeight(), getWidth());
                        if (bmp != null) {
                            imageView.setImageBitmap(MDKPresenterHelper.getRoundedBitmap(bmp, getWidth()));
                            MDKPresenterHelper.crossFading(titleView, imageView);
                        }
                    } catch (IOException e) {
                        Log.w(this.getClass().getSimpleName(), "Error trying to access file: " + imageUri, e);
                    }
                }
            });
        }
    }

}

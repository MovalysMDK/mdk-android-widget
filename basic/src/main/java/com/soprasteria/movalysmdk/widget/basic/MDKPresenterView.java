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
import java.lang.ref.WeakReference;

/**
 * MDKPresenterView.
 */
public class MDKPresenterView extends RelativeLayout {

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
        this.titleView = (new WeakReference<>((TextView) this.findViewById(R.id.component_title))).get();
        this.imageView = (new WeakReference<>((ImageView) this.findViewById(R.id.component_image))).get();

        /* Init TextView */
        if (this.titleView != null) {
            this.titleView.setTextColor(titleColor);
            this.titleView.setTextSize(titleSize);
            if (titleBackground != null) {
                this.titleView.setBackgroundDrawable(titleBackground);
            } else {
                this.titleView.setBackgroundResource(R.drawable.mdk_circle);
            }
        }

        /* Recycling */
        typedArrayComponent.recycle();
    }

    /**
     * To set the title.
     *
     * @param title the title to set into titleView
     */
    public void setTitle(String title) {
        if (this.titleView != null) {
            this.titleView.setText(title);
            MDKPresenterHelper.generateColor(this.titleView, title);
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
     * Method to set the image located by the Uri.
     *
     * @param uri The Uri set in the MDKPresenter
     */
    private void setImage(Uri uri) {
        if (uri != null && this.imageView != null) {
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
        if (this.imageView != null) {
            this.imageView.post(new Runnable() {
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

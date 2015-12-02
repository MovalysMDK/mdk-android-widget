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
package com.soprasteria.movalysmdk.widget.media;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.AttributeSet;

import com.soprasteria.movalysmdk.widget.core.MDKBaseRichWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasChangeListener;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.behavior.types.HasMedia;
import com.soprasteria.movalysmdk.widget.core.helper.AttributesHelper;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;

/**
 * Rich widget representing a media picker,
 * and including by default the label and the error component.
 */
public class MDKRichMedia extends MDKBaseRichWidget<MDKMedia> implements HasValidator, HasMedia, HasChangeListener {

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     */
    public MDKRichMedia(Context context, AttributeSet attrs) {
        super(R.layout.mdkwidget_media_edit_label, R.layout.mdkwidget_media_edit, context, attrs);

        initDedicatedAttributes(attrs);
    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     * @param defStyleAttr defStyleAttr
     */
    public MDKRichMedia(Context context, AttributeSet attrs, int defStyleAttr) {
        super(R.layout.mdkwidget_media_edit_label, R.layout.mdkwidget_media_edit, context, attrs, defStyleAttr);

        initDedicatedAttributes(attrs);
    }

    /**
     * Initialize MDK widget class variables with layout attributes of the Rich component.
     * @param attrs Array of attributes of the MDK widget
     */
    private void initDedicatedAttributes(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKMediaComponent);

        //set inner widget width and height
        int hdp = typedArray.getDimensionPixelSize(R.styleable.MDKCommons_thumbnail_height, 0);
        int wdp = typedArray.getDimensionPixelSize(R.styleable.MDKCommons_thumbnail_width, 0);
        if (hdp != 0 && wdp != 0) {
            getInnerWidget().getLayoutParams().width = wdp;
            getInnerWidget().getLayoutParams().height = hdp;
            getInnerWidget().requestLayout();
        }

        getInnerWidget().setUseMDK(typedArray.getBoolean(R.styleable.MDKCommons_MDKMediaComponent_use_mdk, true));

        switch (AttributesHelper.getIntFromIntAttribute(typedArray, R.styleable.MDKCommons_MDKMediaComponent_media_type, MDKMedia.TYPE_PHOTO)) {
            case MDKMedia.TYPE_PHOTO:
                setMediaType(MDKMedia.TYPE_PHOTO);
                break;
            case MDKMedia.TYPE_VIDEO:
                setMediaType(MDKMedia.TYPE_VIDEO);
                break;
            case MDKMedia.TYPE_FILE:
                setMediaType(MDKMedia.TYPE_FILE);
                break;
            default:
                break;
        }

        setPlaceholder(AttributesHelper.getIntFromResourceAttribute(typedArray, R.styleable.MDKCommons_MDKMediaComponent_placeholder, 0));

        typedArray.recycle();
    }

    @Override
    public Uri getMediaUri() {
        return getInnerWidget().getMediaUri();
    }

    @Override
    public void setMediaUri(Uri uri) {
        getInnerWidget().setMediaUri(uri);
    }

    @Override
    public int getMediaType() {
        return getInnerWidget().getMediaType();
    }

    @Override
    public void setMediaType(@MDKMedia.MediaType int type) {
        if (!this.isInEditMode()) {
            getInnerWidget().setMediaType(type);
        }
    }

    @Override
    public void setPlaceholder(int drawableRes) {
        getInnerWidget().setPlaceholder(drawableRes);
    }

    @Override
    public int getPlaceholder() {
        return getInnerWidget().getPlaceholder();
    }

    @Override
    public String getModifiedPhotoSvg() {
        return getInnerWidget().getModifiedPhotoSvg();
    }

    @Override
    public void setModifiedPhotoSvg(String svg) {
        getInnerWidget().setModifiedPhotoSvg(svg);
    }

    @Override
    public void registerChangeListener(ChangeListener listener) {
        getInnerWidget().registerChangeListener(listener);
    }

    @Override
    public void unregisterChangeListener(ChangeListener listener) {
        getInnerWidget().unregisterChangeListener(listener);
    }

    @Override
    public void updateMedia(Uri uri, String svg) {
        getInnerWidget().updateMedia(uri, svg);
    }
}

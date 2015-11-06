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
package com.soprasteria.movalysmdk.widget.basic;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.basic.model.MDKPresenter;

/**
 * MDKPresenterView.
 */
public class MDKPresenterView extends RelativeLayout {

    /**
     * The title textView.
     */
    private TextView titleView;


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
     * @param attrs the android context
     */
    private void init(AttributeSet attrs) {

        /* Get attrs style */
        TypedArray typedArrayComponent = this.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKPresenterViewComponent);
        float titleSize = typedArrayComponent.getDimension(R.styleable.MDKCommons_MDKPresenterViewComponent_title_size, 18);
        int titleColor = typedArrayComponent.getColor(R.styleable.MDKCommons_MDKPresenterViewComponent_title_color, -1);

        /* Inflate views */
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        inflater.inflate(R.layout.mdkwidget_presenter_layout, this);
        titleView = (TextView) this.findViewById(R.id.component_internal_title);

        /* Init TextView */
        if (titleView != null) {
            titleView.setTextColor(titleColor);
            titleView.setTextSize(titleSize);
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
        if (titleView != null) {
            titleView.setText(title);
        }
    }

    /**
     * To get the first letter of a string in uppercase.
     *
     * @param title the string used to get the first letter in uppercase
     * @return the first letter in uppercase
     */
    public String getFirstLetterToUpper(String title) {
        if (title != null) {
            return title.substring(0, 1).toUpperCase();
        }
        return null;
    }

    /**
     * To set the title with getFirstLetterToUpper method.
     *
     * @param title the string used to get the first letter in uppercase and set into titleView
     */
    public void setTitleFirstLetterToUpper(String title) {
        String newTitle = this.getFirstLetterToUpper(title);
        if (title != null) {
            this.setTitle(newTitle);
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
        }
    }
}

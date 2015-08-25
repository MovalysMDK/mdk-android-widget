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

import com.soprasteria.movalysmdk.widget.core.MDKBaseRichSeekBarWidget;
import com.soprasteria.movalysmdk.widget.core.MDKRestorableWidget;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasChangeListener;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasSeekBar;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;

/**
 * Rich widget representing a seek bar component, conforming to the Material Design guidelines,
 * and including by default the error component.
 * @param <T> The class of the widget to encapsulate*
 */
public class MDKRichSeekBar <T extends MDKWidget & MDKRestorableWidget & HasValidator & HasDelegate & HasSeekBar & HasChangeListener> extends MDKBaseRichSeekBarWidget<T> {

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     */
    public MDKRichSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     * @param defStyleAttr defStyleAttr
     */
    public MDKRichSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Called by the constructor.
     * @param context the context of the view
     * @param attrs xml attributes
     */
    protected void init(Context context, AttributeSet attrs) {
        int layoutWithLabelId, layoutWithoutLabelId;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKSeekBarComponent);
        // parse widget attribute to get values

        layoutWithLabelId = R.layout.mdkwidget_seekbar_edit_label;
        layoutWithoutLabelId = R.layout.mdkwidget_seekbar_edit;

        typedArray.recycle();

        super.init(context, attrs, layoutWithLabelId, layoutWithoutLabelId);
    }
}

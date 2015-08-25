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
package com.soprasteria.movalysmdk.widget.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;
import android.view.View;

import com.soprasteria.movalysmdk.widget.core.behavior.HasChangeListener;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasSeekBar;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;

/**
 * Base implementation of the rich mdk widget for widget that uses uri.
 * @param <T> the inner widget type
 */
public class MDKBaseRichSeekBarWidget<T extends MDKWidget & MDKRestorableWidget & HasValidator & HasDelegate & HasSeekBar & HasChangeListener> extends MDKBaseRichWidget implements HasChangeListener, HasSeekBar {

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     */
    public MDKBaseRichSeekBarWidget(@LayoutRes int layoutWithLabelId, @LayoutRes int layoutWithoutLabelId, Context context, AttributeSet attrs) {
        super(layoutWithLabelId, layoutWithoutLabelId, context, attrs);

        initDedicatedAttributes(attrs);
    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     * @param defStyleAttr the style
     */
    public MDKBaseRichSeekBarWidget(@LayoutRes int layoutWithLabelId, @LayoutRes int layoutWithoutLabelId, Context context, AttributeSet attrs, @StyleableRes int defStyleAttr) {
        super(layoutWithLabelId, layoutWithoutLabelId, context, attrs, defStyleAttr);

        initDedicatedAttributes(attrs);
    }

    /**
     * Getter for the inner widget of the rich widget.
     * @return the inner widget
     */
    public T getInnerWidget()   {
        return (T) super.getInnerWidget();
    }

    /**
     * Initialize MDK widget class variables with layout attributes of the Rich component.
     * @param attrs Array of attributes of the MDK widget
     */
    private void initDedicatedAttributes(AttributeSet attrs){

        // Retrieve attributes of the Seek Bar widget in order to initialize MDK widget class variables.
        TypedArray typedArrayCustom = this.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKSeekBarComponent);

        String maxValueStr = typedArrayCustom.getString(R.styleable.MDKCommons_MDKSeekBarComponent_maxSeekBarValue);
        if (maxValueStr != null) {
            int seekBarMaxValue = Integer.parseInt(maxValueStr);
            setSeekBarMaxValue(seekBarMaxValue);
        }

        String initalValueStr = typedArrayCustom.getString(R.styleable.MDKCommons_MDKSeekBarComponent_initialSeekBarValue);
        if (initalValueStr != null) {
            int seekBarValue = Integer.parseInt(initalValueStr);
            this.setSeekBarValue(seekBarValue);
            this.setSeekProgress(seekBarValue);

        }

        typedArrayCustom.recycle();
    }

    @Override
    public void registerChangeListener(ChangeListener listener) {
        this.getInnerWidget().registerChangeListener(listener);
    }

    @Override
    public void unregisterChangeListener(ChangeListener listener) {
        this.getInnerWidget().unregisterChangeListener(listener);
    }

    /**
     * Override the default android setEnable on view and
     * call the inner component setEnable.
     * @param enabled Enable or not the view
     */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        ((View) this.getInnerWidget()).setEnabled(enabled);
    }

    @Override
    public int getSeekBarValue() {
        return this.getInnerWidget().getSeekBarValue();
    }

    @Override
    public void setSeekBarValue(int seekBarValue) {
        this.getInnerWidget().setSeekBarValue(seekBarValue);
    }

    @Override
    public int getSeekBarMaxValue() {
        return this.getInnerWidget().getSeekBarMaxValue();
    }

    @Override
    public void setSeekBarMaxValue(int seekBarMaxValue) {
        this.getInnerWidget().setSeekBarMaxValue(seekBarMaxValue);
    }

    @Override
    public void setSeekProgress(int value) {
        this.getInnerWidget().setSeekProgress(value);
    }
}

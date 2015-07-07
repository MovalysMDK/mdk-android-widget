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
import android.util.AttributeSet;
import android.view.View;

import com.soprasteria.movalysmdk.widget.core.behavior.HasChangeListener;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;

import java.util.Date;

/**
 * MDK Base Rich Date Widget.
 * <p>Override the base class to refactor code for methods :</p>
 * <ul>
 *     <li>getValidator</li>
 *     <li>validate</li>
 *     <li>setEnable</li>
 * </ul>
 * <p>Add a validator behavior on the base rich widget</p>
 * @param <T> the inner widget type
 */
public class MDKBaseRichDateWidget<T extends MDKWidget & MDKRestorableWidget & HasValidator & HasDate & HasChangeListener> extends MDKBaseRichWidget<T> implements HasValidator, HasDate, HasChangeListener {

    /**
     * Constructor.
     * @param layoutWithLabelId Id of layoutWithLabel
     * @param layoutWithoutLabelId Id of layoutWithoutLabel
     * @param context Context
     * @param attrs Collection of attributes
     */
    public MDKBaseRichDateWidget(int layoutWithLabelId, int layoutWithoutLabelId, Context context, AttributeSet attrs) {
        super(layoutWithLabelId, layoutWithoutLabelId, context, attrs);
    }

    /**
     * Constructor.
     * @param layoutWithLabelId Id of layoutWithLabel
     * @param layoutWithoutLabelId Id of layoutWithoutLabel
     * @param context Context
     * @param attrs Collection of attributes
     * @param defStyleAttr Attribute in the current theme referencing a style resource
     */
    public MDKBaseRichDateWidget(int layoutWithLabelId, int layoutWithoutLabelId, Context context, AttributeSet attrs, int defStyleAttr) {
        super(layoutWithLabelId, layoutWithoutLabelId, context, attrs, defStyleAttr);
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public boolean validate() {
        return this.getInnerWidget().validate();
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
    public Date getDate() {
        return this.getInnerWidget().getDate();
    }

    @Override
    public void setDate(Date date) {
        this.getInnerWidget().setDate(date);
    }

    @Override
    public void registerChangeListener(ChangeListener listener) {
        this.getInnerWidget().registerChangeListener(listener);
    }
}

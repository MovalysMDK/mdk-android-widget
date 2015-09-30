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

import com.soprasteria.movalysmdk.widget.core.behavior.HasChangeListener;
import com.soprasteria.movalysmdk.widget.core.behavior.types.HasDate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;

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
public class MDKBaseRichDateWidget<T extends MDKWidget & HasValidator & HasDate & HasChangeListener & HasDelegate> extends MDKBaseRichWidget<T> implements HasValidator, HasDate, HasChangeListener {

    /**
     * Constructor.
     * @param layoutWithLabelId Id of layoutWithLabel
     * @param layoutWithoutLabelId Id of layoutWithoutLabel
     * @param context Context
     * @param attrs Collection of attributes
     */
    public MDKBaseRichDateWidget(@LayoutRes int layoutWithLabelId, @LayoutRes int layoutWithoutLabelId, Context context, AttributeSet attrs) {
        super(layoutWithLabelId, layoutWithoutLabelId, context, attrs);
        init(context, attrs);
    }

    /**
     * Constructor.
     * @param layoutWithLabelId Id of layoutWithLabel
     * @param layoutWithoutLabelId Id of layoutWithoutLabel
     * @param context Context
     * @param attrs Collection of attributes
     * @param defStyleAttr Attribute in the current theme referencing a style resource
     */
    public MDKBaseRichDateWidget(@LayoutRes int layoutWithLabelId, @LayoutRes int layoutWithoutLabelId, Context context, AttributeSet attrs, @StyleableRes int defStyleAttr) {
        super(layoutWithLabelId, layoutWithoutLabelId, context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    public boolean validate(@EnumFormFieldValidator.EnumValidationMode int validationMode) {
        return this.getInnerWidget().validate(validationMode);
    }

    /**
     * Validate the component with default ON_USER validation mode.
     * <p>Use the validation of the inner widget</p>
     * @return true if the widget is valid, false otherwise
     */
    @Override
    public boolean validate() {
        return this.getInnerWidget().validate(EnumFormFieldValidator.VALIDATE);
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
    public void setDateHint(String dateHint) {
        getInnerWidget().setDateHint(dateHint);
    }

    @Override
    public void setTimeHint(String timeHint) {
        getInnerWidget().setTimeHint(timeHint);

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
     * Initialization.
     * @param context the context
     * @param attrs attributes
     */
    private final void init(Context context, AttributeSet attrs){
        //getInnerWidget().setDateHint(attrs.getS)
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKDateTimePickerComponent);
        String dateHint = typedArray.getString(R.styleable.MDKCommons_MDKDateTimePickerComponent_dateHint);
        if (dateHint == null) {
            dateHint = context.getString(R.string.default_date_hint_text);
        }
        String timeHint = typedArray.getString(R.styleable.MDKCommons_MDKDateTimePickerComponent_timeHint);
        if (timeHint == null) {
            timeHint = context.getString(R.string.default_time_hint_text);
        }

        getInnerWidget().setDateHint(dateHint);
        getInnerWidget().setTimeHint(timeHint);

        typedArray.recycle();

    }
}

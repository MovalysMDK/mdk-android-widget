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
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import com.soprasteria.movalysmdk.widget.basic.delegate.MDKDateTimePickerWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKTechnicalInnerWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKTechnicalWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasChangeListener;
import com.soprasteria.movalysmdk.widget.core.behavior.types.HasDate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasHints;
import com.soprasteria.movalysmdk.widget.core.behavior.HasLabel;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;

import java.util.Date;

/**
 * Base widget able to render a date picker, or a time picker.
 * <p>This widget has several use possibilities :</p>
 * <ul>
 * <li>used alone : by default, it will represent a date picker. by using the "mode" attribute, you can specify wether you want a date, or a time picker.</li>
 * <li>used with the addition of an other TextView : in this case, the current component acts as the master component,
 * and the TextView as the slave component. You can link the TextView to the MDKDateTime by setting on the MDKDateTime
 * one of the following attributes :
 * <ul>
 * <li>dateTextViewId : the MDKDateTime will act as a time picker, and the TextView referenced in dateTextViewId will act as a date picker</li>
 * <li>timeTextViewId : the MDKDateTime will act as a date picker, and the TextView referenced in dateTextViewId will act as a time picker</li>
 * </ul>
 * </li>
 * </ul>
 * <p>Other optional attributes :</p>
 * <ul>
 * <li>dateFormat : specify a custom format that will be used to display the date. The accepted format is the one of <a href="http://developer.android.com/reference/java/text/SimpleDateFormat.html">SimpleDateFormat</a></li>
 * <li>timeFormat : specify a custom format that will be used to display the time. The accepted format is the one of <a href="http://developer.android.com/reference/java/text/SimpleDateFormat.html">SimpleDateFormat</a></li>
 * </ul>
 */
public class MDKDateTime extends MDKTintedTextView implements MDKWidget, HasValidator, HasDate, HasDelegate, HasLabel, HasChangeListener, HasHints, View.OnClickListener {

    /**
     * Widget delegate that handles all the widget logic.
     */
    protected MDKDateTimePickerWidgetDelegate mdkWidgetDelegate;

    /**
     * Constructor.
     *
     * @param context the context
     * @param attrs   attributes
     */
    public MDKDateTime(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Constructor.
     *
     * @param context      the context
     * @param attrs        attributes
     * @param defStyleAttr the style
     */
    public MDKDateTime(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Instanciates the widget delegate.
     *
     * @param context the context
     * @param attrs   attributes
     */
    private final void init(Context context, AttributeSet attrs) {

        // Create the widget delegate
        mdkWidgetDelegate = new MDKDateTimePickerWidgetDelegate(this, attrs);
    }

    @Override
    public int[] getValidators() {
        return new int[]{R.string.mdkvalidator_datetime_class, R.string.mdkvalidator_datetimerange_class};
    }

    /**
     * Returns the widget date and time according the chosen picking mode.
     *
     * @return date the current date
     */
    @Override
    public Date getDate() {
        return this.mdkWidgetDelegate.getDisplayedDate();
    }

    /**
     * Sets the displayed date.
     *
     * @param date the new date
     */
    @Override
    public void setDate(Date date) {
        this.mdkWidgetDelegate.setDisplayedDate(date);
    }

    @Override
    public void setDateHint(String dateHint) {
        mdkWidgetDelegate.setDateHint(dateHint);
    }

    @Override
    public void setTimeHint(String timeHint) {
        mdkWidgetDelegate.setTimeHint(timeHint);

    }

    /**
     * Sets the displayed time.
     *
     * @param time the new time
     */
    public void setTime(Date time) {
        this.mdkWidgetDelegate.setDisplayedTime(time);
    }

    /**
     * Get max date (in Date format).
     *
     * @return Date the max date
     */
    public Date getMax() {
        return this.mdkWidgetDelegate.getMax();
    }

    /**
     * Set the max date.
     *
     * @param maxDate the new max date
     */
    public void setMax(Date maxDate) {
        this.mdkWidgetDelegate.setMax(maxDate);
    }

    /**
     * Get min date (in Date format).
     *
     * @return Date the min date
     */
    public Date getMin() {
        return this.mdkWidgetDelegate.getMin();
    }

    /**
     * Set the new min date (in Date format).
     *
     * @param minDate the new min date
     */
    public void setMin(Date minDate) {
        this.mdkWidgetDelegate.setMin(minDate);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * Overridden in order to add post initialisation.
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            this.mdkWidgetDelegate.onAttachedToWindow();
            final View clearButton = this.mdkWidgetDelegate.getClearButton();
            if (clearButton != null) {
                clearButton.setOnClickListener(this);
            }
        }
    }

    /* technical delegate methods */

    @Override
    public MDKTechnicalWidgetDelegate getTechnicalWidgetDelegate() {
        return this.mdkWidgetDelegate;
    }

    @Override
    public MDKTechnicalInnerWidgetDelegate getTechnicalInnerWidgetDelegate() {
        return this.mdkWidgetDelegate;
    }

    @Override
    public MDKWidgetDelegate getMDKWidgetDelegate() {
        return this.mdkWidgetDelegate;
    }

    /* rich selector methods */

    @Override
    public int[] superOnCreateDrawableState(int extraSpace) {
        return super.onCreateDrawableState(extraSpace);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        if (this.mdkWidgetDelegate != null) {
            return this.mdkWidgetDelegate.superOnCreateDrawableState(extraSpace);
        } else {
            // first called in the super constructor
            return super.onCreateDrawableState(extraSpace);
        }
    }

    @Override
    public void callMergeDrawableStates(int[] baseState, int[] additionalState) {
        mergeDrawableStates(baseState, additionalState);
    }

    /* delegate accelerator methods */

    @Override
    public void setMandatory(boolean mandatory) {
        this.mdkWidgetDelegate.setMandatory(mandatory);
    }

    @Override
    public boolean isMandatory() {
        return this.mdkWidgetDelegate.isMandatory();
    }

    @Override
    public void setEditable(boolean editable) {
        this.mdkWidgetDelegate.setEditable(editable);

        final View clearButton = this.mdkWidgetDelegate.getClearButton();
        if (clearButton != null) {
            if (editable) {
                clearButton.setVisibility(View.VISIBLE);
            } else {
                clearButton.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public boolean isEditable() {
        return this.mdkWidgetDelegate.isEditable();
    }

    @Override
    public void addError(MDKMessages error) {
        this.mdkWidgetDelegate.addError(error);
    }

    @Override
    public void setError(CharSequence error) {
        this.mdkWidgetDelegate.setError(error);
    }

    @Override
    public void clearError() {
        this.mdkWidgetDelegate.clearError();
    }

    @Override
    public CharSequence getLabel() {
        return this.mdkWidgetDelegate.getLabel();
    }

    @Override
    public void setLabel(CharSequence label) {
        this.mdkWidgetDelegate.setLabel(label);
    }

    @Override
    public boolean validate(@EnumFormFieldValidator.EnumValidationMode int validationMode) {
        return this.getMDKWidgetDelegate().validate(true, validationMode);
    }

    @Override
    public boolean validate() {
        return this.getMDKWidgetDelegate().validate(true, EnumFormFieldValidator.VALIDATE);
    }

    @Override
    public Object getValueToValidate() {
        return getDate();
    }

    @Override
    public void registerChangeListener(ChangeListener listener) {
        this.mdkWidgetDelegate.registerChangeListener(listener);
    }

    @Override
    public void unregisterChangeListener(ChangeListener listener) {
        this.mdkWidgetDelegate.unregisterChangeListener(listener);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.mdkWidgetDelegate.setEnabled(enabled);
    }

    /* save / restore */

    @Override
    public Parcelable onSaveInstanceState() {
        // Save the android view instance state
        Parcelable state = super.onSaveInstanceState();

        // Save the MDKWidgetDelegate instance state
        state = this.mdkWidgetDelegate.onSaveInstanceState(state);

        return state;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        // Restore the MDKWidgetDelegate instance state
        Parcelable innerState = this.mdkWidgetDelegate.onRestoreInstanceState(this, state);

        // Restore the android view instance state
        super.onRestoreInstanceState(innerState);
    }

    @Override
    public CharSequence[] getHints() {
        return this.mdkWidgetDelegate.getHints();
    }

    @Override
    public void setHints(CharSequence[] hints) {
        this.mdkWidgetDelegate.setHints(hints);
    }


    /**
     * Called when the view is attached to a window.
     */
    @Override
    public void onClick(View v) {
        int error = 0;

        if (v.getId() == this.mdkWidgetDelegate.getClearButtonId()) {
            this.mdkWidgetDelegate.clearDate();
        }

        if (error != 0) {
            this.mdkWidgetDelegate.setError(getResources().getString(error));
        }
    }

}

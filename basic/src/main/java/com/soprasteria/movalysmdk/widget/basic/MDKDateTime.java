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
import android.os.Parcelable;
import android.util.AttributeSet;

import com.soprasteria.movalysmdk.widget.basic.delegate.MDKDateTimePickerWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKRestorableWidget;
import com.soprasteria.movalysmdk.widget.core.MDKTechnicalWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasChangeListener;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.helper.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;

import java.util.Date;
import java.util.List;

/**
 * Base widget able to render a date picker, or a time picker.
 * <p>This widget has several use possibilities :</p>
 * <ul>
 *     <li>used alone : by default, it will represent a date picker. by using the "mode" attribute, you can specify wether you want a date, or a time picker.</li>
 *     <li>used with the addition of an other TextView : in this case, the current component acts as the master component,
 *          and the TextView as the slave component. You can link the TextView to the MDKDateTime by setting on the MDKDateTime
 *          one of the following attributes :
 *          <ul>
 *              <li>dateTextViewId : the MDKDateTime will act as a time picker, and the TextView referenced in dateTextViewId will act as a date picker</li>
 *              <li>timeTextViewId : the MDKDateTime will act as a date picker, and the TextView referenced in dateTextViewId will act as a time picker</li>
 *          </ul>
 *     </li>
 * </ul>
 * <p>Other optional attributes :</p>
 * <ul>
 *     <li>dateFormat : specify a custom format that will be used to display the date. The accepted format is the one of <a href="http://developer.android.com/reference/java/text/SimpleDateFormat.html">SimpleDateFormat</a></li>
 *     <li>timeFormat : specify a custom format that will be used to display the time. The accepted format is the one of <a href="http://developer.android.com/reference/java/text/SimpleDateFormat.html">SimpleDateFormat</a></li>
 * </ul>
 */
public class MDKDateTime extends MDKTintedTextView implements MDKWidget, MDKRestorableWidget, HasValidator, HasDate, HasDelegate, HasChangeListener {

    /** Widget delegate that handles all the widget logic. */
    protected MDKDateTimePickerWidgetDelegate mdkWidgetDelegate;

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     */
    public MDKDateTime(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
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
     * @param context the context
     * @param attrs attributes
     */
    private final void init(Context context, AttributeSet attrs) {

        // Create the widget delegate
        mdkWidgetDelegate = new MDKDateTimePickerWidgetDelegate(this, attrs);
    }

    /**
     * {@inheritDoc}
     *
     * Overridden in order to add post initialisation.
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            this.mdkWidgetDelegate.onAttachedToWindow();
        }
    }

    @Override
    public MDKWidgetDelegate getMDKWidgetDelegate() {
        return this.mdkWidgetDelegate;
    }

    @Override
    public int[] getValidators() {
        return new int[] {R.string.mdkwidget_mdkdatetime_validator_class, R.string.mdkwidget_mdkdatetimerange_validator_class};
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
    public MDKTechnicalWidgetDelegate getTechnicalWidgeDelegate() {
        return this.mdkWidgetDelegate;
    }

    @Override
    public void setMandatory(boolean mandatory) {
        this.mdkWidgetDelegate.setMandatory(mandatory);
    }

    @Override
    public boolean isMandatory() {
        return this.mdkWidgetDelegate.isMandatory();
    }

    @Override
    public void setUniqueId(int parentId) {
        this.mdkWidgetDelegate.setUniqueId(parentId);
    }

    @Override
    public int getUniqueId() {
        return this.mdkWidgetDelegate.getUniqueId();
    }

    @Override
    public int[] superOnCreateDrawableState(int extraSpace) {
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
    public Parcelable superOnSaveInstanceState() {
        return onSaveInstanceState();
    }

    @Override
    public void superOnRestoreInstanceState(Parcelable state) {
        onRestoreInstanceState(state);
    }

    /**
     * Returns the widget date and time according the chosen picking mode.
     * @return date the current date
     */
    @Override
    public Date getDate() {
        return this.mdkWidgetDelegate.getDisplayedDate();
    }

    /**
     * Sets the displayed date.
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
     * @param time the new time
     */
    public void setTime(Date time) {
        this.mdkWidgetDelegate.setDisplayedTime(time);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.mdkWidgetDelegate.setEnabled(enabled);
    }

    @Override
    public void registerChangeListener(ChangeListener listener) {
        this.mdkWidgetDelegate.registerChangeListener(listener);
    }

    @Override
    public void unregisterChangeListener(ChangeListener listener) {
        this.mdkWidgetDelegate.unregisterChangeListener(listener);
    }

    /**
     * Get max date (in Date format).
     * @return Date the max date
     */
    public Date getMax() {
        return this.mdkWidgetDelegate.getMax();
    }

    /**
     * Set the max date.
     * @param maxDate the new max date
     */
    public void setMax(Date maxDate) {
        this.mdkWidgetDelegate.setMax(maxDate);
    }

    /**
     * Get min date (in Date format).
     * @return Date the min date
     */
    public Date getMin() {
        return this.mdkWidgetDelegate.getMin();
    }

    /**
     * Set the new min date (in Date format).
     * @param minDate the new min date
     */
    public void setMin(Date minDate) {
        this.mdkWidgetDelegate.setMin(minDate);
    }

    @Override
    public void setRichSelectors(List<String> richSelectors) {
        this.getMDKWidgetDelegate().setRichSelectors(richSelectors);
    }
}

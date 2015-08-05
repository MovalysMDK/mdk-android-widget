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
import android.support.annotation.IdRes;
import android.util.AttributeSet;

import com.soprasteria.movalysmdk.widget.basic.delegate.MDKDateTimePickerWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKRestorableWidget;
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
    protected MDKDateTimePickerWidgetDelegate mdkDateTimePickerWidgetDelegate;

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
        mdkDateTimePickerWidgetDelegate = new MDKDateTimePickerWidgetDelegate(this, attrs);
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
            this.mdkDateTimePickerWidgetDelegate.onAttachedToWindow();
        }
    }

    @Override
    public MDKWidgetDelegate getMDKWidgetDelegate() {
        return this.mdkDateTimePickerWidgetDelegate;
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
        return this.getMDKWidgetDelegate().validate(true, EnumFormFieldValidator.ON_USER);
    }

    @Override
    public void setRootViewId( @IdRes int rootId) {
        this.mdkDateTimePickerWidgetDelegate.setRootViewId(rootId);
    }

    @Override
    public void setLabelViewId( @IdRes int labelId) {
        this.mdkDateTimePickerWidgetDelegate.setLabelViewId(labelId);
    }

    @Override
    public void setHelperViewId( @IdRes int helperId) {
        this.mdkDateTimePickerWidgetDelegate.setHelperViewId(helperId);
    }

    @Override
    public void setErrorViewId( @IdRes int errorId) {
        this.mdkDateTimePickerWidgetDelegate.setErrorViewId(errorId);
    }

    @Override
    public void setUseRootIdOnlyForError(boolean useRootIdOnlyForError) {
        this.mdkDateTimePickerWidgetDelegate.setUseRootIdOnlyForError(useRootIdOnlyForError);
    }

    @Override
    public void addError(MDKMessages error) {
        this.mdkDateTimePickerWidgetDelegate.addError(error);
    }

    @Override
    public void setError(CharSequence error) {
        this.mdkDateTimePickerWidgetDelegate.setError(error);
    }

    @Override
    public void clearError() {
        this.mdkDateTimePickerWidgetDelegate.clearError();
    }

    @Override
    public void setMandatory(boolean mandatory) {
        this.mdkDateTimePickerWidgetDelegate.setMandatory(mandatory);
    }

    @Override
    public boolean isMandatory() {
        return this.mdkDateTimePickerWidgetDelegate.isMandatory();
    }

    @Override
    public void setUniqueId(int parentId) {
        this.mdkDateTimePickerWidgetDelegate.setUniqueId(parentId);
    }

    @Override
    public int getUniqueId() {
        return this.mdkDateTimePickerWidgetDelegate.getUniqueId();
    }

    @Override
    public int[] superOnCreateDrawableState(int extraSpace) {
        if (this.mdkDateTimePickerWidgetDelegate != null) {
            return this.mdkDateTimePickerWidgetDelegate.superOnCreateDrawableState(extraSpace);
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
        return this.mdkDateTimePickerWidgetDelegate.getDisplayedDate();
    }

    /**
     * Sets the displayed date.
     * @param date the new date
     */
    @Override
    public void setDate(Date date) {
        this.mdkDateTimePickerWidgetDelegate.setDisplayedDate(date);
    }

    @Override
    public void setDateHint(String dateHint) {
        mdkDateTimePickerWidgetDelegate.setDateHint(dateHint);
    }

    @Override
    public void setTimeHint(String timeHint) {
        mdkDateTimePickerWidgetDelegate.setTimeHint(timeHint);

    }

    /**
     * Sets the displayed time.
     * @param time the new time
     */
    public void setTime(Date time) {
        this.mdkDateTimePickerWidgetDelegate.setDisplayedTime(time);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.mdkDateTimePickerWidgetDelegate.setEnabled(enabled);
    }

    @Override
    public void registerChangeListener(ChangeListener listener) {
        this.mdkDateTimePickerWidgetDelegate.registerChangeListener(listener);
    }

    @Override
    public void unregisterChangeListener(ChangeListener listener) {
        this.mdkDateTimePickerWidgetDelegate.unregisterChangeListener(listener);
    }

    /**
     * Get max date (in Date format).
     * @return Date the max date
     */
    public Date getMax() {
        return this.mdkDateTimePickerWidgetDelegate.getMax();
    }

    /**
     * Set the max date.
     * @param maxDate the new max date
     */
    public void setMax(Date maxDate) {
        this.mdkDateTimePickerWidgetDelegate.setMax(maxDate);
    }

    /**
     * Get min date (in Date format).
     * @return Date the min date
     */
    public Date getMin() {
        return this.mdkDateTimePickerWidgetDelegate.getMin();
    }

    /**
     * Set the new min date (in Date format).
     * @param minDate the new min date
     */
    public void setMin(Date minDate) {
        this.mdkDateTimePickerWidgetDelegate.setMin(minDate);
    }



}

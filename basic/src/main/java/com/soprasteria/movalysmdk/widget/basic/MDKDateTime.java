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

import com.soprasteria.movalysmdk.widget.core.MDKRestorableWidget;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKDateTimePickerWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.error.MDKError;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;

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
public class MDKDateTime extends TintedTextView implements MDKWidget, MDKRestorableWidget, HasValidator {

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
    public FormFieldValidator getValidator() {
        return this.mdkDateTimePickerWidgetDelegate.getValidator();
    }

    @Override
    public boolean validate() {
        boolean bValid = true;
        MDKError error = null;

        FormFieldValidator validator = this.mdkDateTimePickerWidgetDelegate.getValidator();

        if (this.mdkDateTimePickerWidgetDelegate.getDateTimePickerMode() == MDKDateTimePickerWidgetDelegate.DateTimePickerMode.DATE_TIME_PICKER
                || this.mdkDateTimePickerWidgetDelegate.getDateTimePickerMode() == MDKDateTimePickerWidgetDelegate.DateTimePickerMode.DATE_PICKER) {
            error = validator.validate(this.getDate(), this.mdkDateTimePickerWidgetDelegate.isMandatory(), this.getContext());
        }
        if (this.mdkDateTimePickerWidgetDelegate.getDateTimePickerMode() == MDKDateTimePickerWidgetDelegate.DateTimePickerMode.DATE_TIME_PICKER
                || this.mdkDateTimePickerWidgetDelegate.getDateTimePickerMode() == MDKDateTimePickerWidgetDelegate.DateTimePickerMode.TIME_PICKER) {
            error = error != null ? error : validator.validate(this.getTime(), this.mdkDateTimePickerWidgetDelegate.isMandatory(), this.getContext());
        }
        if (error == null) {
            this.clearError();
            bValid = true;
        } else {
            this.setError(error);
            bValid = false;
        }
        this.mdkDateTimePickerWidgetDelegate.setValid(bValid);
        return bValid;
    }

    @Override
    public void setRootViewId(int rootId) {
        this.mdkDateTimePickerWidgetDelegate.setRootViewId(rootId);
    }

    @Override
    public void setLabelViewId(int labelId) {
        this.mdkDateTimePickerWidgetDelegate.setLabelViewId(labelId);
    }

    @Override
    public void setHelperViewId(int helperId) {
        this.mdkDateTimePickerWidgetDelegate.setHelperViewId(helperId);
    }

    @Override
    public void setErrorViewId(int errorId) {
        this.mdkDateTimePickerWidgetDelegate.setErrorViewId(errorId);
    }

    @Override
    public void setUseRootIdOnlyForError(boolean useRootIdOnlyForError) {
        this.mdkDateTimePickerWidgetDelegate.setUseRootIdOnlyForError(useRootIdOnlyForError);
    }

    @Override
    public void setError(MDKError error) {
        this.mdkDateTimePickerWidgetDelegate.setError(error);
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
     * Sets the displayed Date.
     * @param date the new date
     */
    public void setDate(Date date) {
        this.mdkDateTimePickerWidgetDelegate.setDisplayedDate(date);
    }

    /**
     * Returns the displayed Date.
     * @return date the current date
     */
    public Date getDate() {
        return this.mdkDateTimePickerWidgetDelegate.getDisplayedDate();
    }

    /**
     * Sets the displayed Time.
     * @param time the new time
     */
    public void setTime(Date time) {
        this.mdkDateTimePickerWidgetDelegate.setDisplayedTime(time);
    }

    /**
     * Returns the displayed Time.
     * @return time the current time
     */
    public Date getTime() {
        return this.mdkDateTimePickerWidgetDelegate.getDisplayedTime();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.mdkDateTimePickerWidgetDelegate.setEnabled(enabled);
    }
}

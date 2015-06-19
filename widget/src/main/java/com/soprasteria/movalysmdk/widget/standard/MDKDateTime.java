package com.soprasteria.movalysmdk.widget.standard;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.base.delegate.HasMdkDelegate;
import com.soprasteria.movalysmdk.widget.base.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.base.delegate.MDKDateTimePickerWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKInnerWidget;
import com.soprasteria.movalysmdk.widget.core.MDKRestoreWidget;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.error.MDKError;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;

import java.util.Date;

/**
 * Base widget able to render a date picker, or a time picker.
 * This widget has several use possibilities :
 * - used alone : by default, it will represent a date picker.
 *   by using the "mode" attribute, you can specify wether you want a date, or a time picker.
 * - used with the addition of an other TextView : in this case, the current component acts as the master component,
 *   and the TextView as the slave component. You can link the TextView to the MDKDateTime by setting on the MDKDateTime
 *   one of the following attributes :
 *   - dateTextViewId : the MDKDateTime will act as a time picker, and the TextView referenced in dateTextViewId will act as a date picker
 *   - timeTextViewId : the MDKDateTime will act as a date picker, and the TextView referenced in dateTextViewId will act as a time picker
 *
 * Other optional attributes :
 * - dateFormat : specify a custom format that will be used to display the date. The accepted format is the one of <a href="http://developer.android.com/reference/java/text/SimpleDateFormat.html">SimpleDateFormat</a>
 * - timeFormat : specify a custom format that will be used to display the time. The accepted format is the one of <a href="http://developer.android.com/reference/java/text/SimpleDateFormat.html">SimpleDateFormat</a>
 */
public class MDKDateTime extends TextView implements MDKInnerWidget, MDKRestoreWidget, HasValidator, HasMdkDelegate {

    /** Widget delegate that handles all the widget logic. */
    protected MDKDateTimePickerWidgetDelegate mdkDateTimePickerWidgetDelegate;

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     */
    public MDKDateTime(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     * @param style the style
     */
    public MDKDateTime(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);

        init(context, attrs);
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
     * Overriden in order to add post initialisation actions.
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
            error = validator.validate(this.getDate(), this.mdkDateTimePickerWidgetDelegate.isMandatory());
        }
        if (this.mdkDateTimePickerWidgetDelegate.getDateTimePickerMode() == MDKDateTimePickerWidgetDelegate.DateTimePickerMode.DATE_TIME_PICKER
                || this.mdkDateTimePickerWidgetDelegate.getDateTimePickerMode() == MDKDateTimePickerWidgetDelegate.DateTimePickerMode.TIME_PICKER) {
            error = error != null ? error : validator.validate(this.getTime(), this.mdkDateTimePickerWidgetDelegate.isMandatory());
        }
        if (error == null) {
            this.setMDKError(null);
            bValid = true;
        } else {
            this.setMDKError(error);
            bValid = false;
        }
        this.mdkDateTimePickerWidgetDelegate.setValid(bValid);
        return bValid;
    }

    @Override
    public void setRootId(int rootId) {
        this.mdkDateTimePickerWidgetDelegate.setRootId(rootId);
    }

    @Override
    public void setLabelId(int labelId) {
        this.mdkDateTimePickerWidgetDelegate.setLabelId(labelId);
    }

    @Override
    public void setHelperId(int helperId) {
        this.mdkDateTimePickerWidgetDelegate.setHelperId(helperId);
    }

    @Override
    public void setErrorId(int errorId) {
        this.mdkDateTimePickerWidgetDelegate.setErrorId(errorId);
    }

    @Override
    public void setUseRootIdOnlyForError(boolean useRootIdOnlyForError) {
        this.mdkDateTimePickerWidgetDelegate.setUseRootIdOnlyForError(useRootIdOnlyForError);
    }

    @Override
    public void setMDKError(MDKError error) {
        this.mdkDateTimePickerWidgetDelegate.setMDKError(error);
    }

    @Override
    public void setError(CharSequence error) {
        this.mdkDateTimePickerWidgetDelegate.setError(error);
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

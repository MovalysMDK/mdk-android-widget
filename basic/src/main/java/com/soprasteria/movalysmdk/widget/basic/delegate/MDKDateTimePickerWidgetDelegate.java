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
package com.soprasteria.movalysmdk.widget.basic.delegate;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.soprasteria.movalysmdk.widget.basic.R;
import com.soprasteria.movalysmdk.widget.basic.model.MDKDate;
import com.soprasteria.movalysmdk.widget.core.MDKBaseWidget;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKChangeListenerDelegate;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.helper.MDKAttributeSet;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Delegate for the MDKDateTime widget.
 * This delegate implements the core concepts of the component : it handles the fact that the MDKDateTime
 * widget can be used alone, or with the addition of an other TextView.
 * In this later case the MDKDateTime will act
 * as the master component (as it hosts this delegate), and the TextView will act as the slave component.
 * The MDKDateTime chooses to be either a date or a time picker, and the slave will have the other role
 */
public class MDKDateTimePickerWidgetDelegate extends MDKWidgetDelegate implements MDKBaseWidget, View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    /**
     * empty string comparator.
     */
    public static final String EMPTY_STRING = "";

    /**
     * notify change listeners.
     */
    private MDKChangeListenerDelegate mdkListenerDelegate;

    /**
     * MDKDateTime active mode enumeration.
     */
    @IntDef({DATE_PICKER, TIME_PICKER, DATE_TIME_PICKER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DateTimeMode {
    }

    /**
     * DATE_PICKER.
     */
    public static final int DATE_PICKER = 0;
    /**
     * TIME_PICKER.
     */
    public static final int TIME_PICKER = 1;
    /**
     * DATE_TIME_PICKER.
     */
    public static final int DATE_TIME_PICKER = 2;

    /**
     * Mode in which the widget is.
     */
    private int dateTimePickerMode;
    /**
     * ID of the date TextView.
     */
    private int dateViewId;
    /**
     * ID of the time TextView.
     */
    private int timeViewId;

    /**
     * Date hint.
     */
    private String dateHint;
    /**
     * Time hint.
     */
    private String timeHint;
    /**
     * Cached reference of the date view.
     */
    private WeakReference<View> cachedDateView;
    /**
     * Cached reference of the time view.
     */
    private WeakReference<View> cachedTimeView;


    /**
     * Formatter used to display the date.
     */
    private DateFormat dateFormatter;
    /**
     * Formatter used to display the time.
     */
    private DateFormat timeFormatter;
    /**
     * If true, the time input is in 24H format.
     */
    private boolean is24HourFormat;

    /**
     * True if the component is enabled.
     */
    private boolean enabled = true;

    /**
     * Handle processing on min date and time.
     */
    private MDKDate minMDKDate = new MDKDate();
    /**
     * Handle processing on max date and time.
     */
    private MDKDate maxMDKDate = new MDKDate();
    /**
     * Handle processing on date and time.
     */
    private final MDKDate mdkDate = new MDKDate();
    /**
     * clear button identifier.
     */
    private int clearButtonId;

    /**
     * Enum for extraction (time or date).
     */
    @IntDef({DATE_EXTRACTION, TIME_EXTRACTION})
    @Retention(RetentionPolicy.SOURCE)
    @interface EnumKindOfExtraction {
    }

    /**
     * Indicates that the widget will be displaying a date.
     */
    public static final int DATE_EXTRACTION = 1;

    /**
     * Indicates that the widget will be displaying a time.
     */
    public static final int TIME_EXTRACTION = 2;

    /**
     * Constructor.
     *
     * @param view  the view
     * @param attrs attributes
     */
    public MDKDateTimePickerWidgetDelegate(View view, AttributeSet attrs) {

        super(view, attrs);

        this.mdkListenerDelegate = new MDKChangeListenerDelegate();

        // DateTimePicker specific fields parsing
        TypedArray typedArray = view.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKDateTimePickerComponent);

        int modeAttr = typedArray.getInt(R.styleable.MDKCommons_MDKDateTimePickerComponent_mode, -1);
        dateViewId = typedArray.getResourceId(R.styleable.MDKCommons_MDKDateTimePickerComponent_dateTextViewId, 0);
        timeViewId = typedArray.getResourceId(R.styleable.MDKCommons_MDKDateTimePickerComponent_timeTextViewId, 0);
        String dateFormatPattern = typedArray.getString(R.styleable.MDKCommons_MDKDateTimePickerComponent_dateFormat);
        String timeFormatPattern = typedArray.getString(R.styleable.MDKCommons_MDKDateTimePickerComponent_timeFormat);

        dateHint = typedArray.getString(R.styleable.MDKCommons_MDKDateTimePickerComponent_dateHint);
        if (dateHint == null) {
            dateHint = view.getContext().getString(R.string.default_date_hint_text);
        }
        timeHint = typedArray.getString(R.styleable.MDKCommons_MDKDateTimePickerComponent_timeHint);
        if (timeHint == null) {
            timeHint = view.getContext().getString(R.string.default_time_hint_text);
        }
        String minString = typedArray.getString(R.styleable.MDKCommons_MDKDateTimePickerComponent_min);
        String maxString = typedArray.getString(R.styleable.MDKCommons_MDKDateTimePickerComponent_max);

        clearButtonId = this.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons).getResourceId(R.styleable.MDKCommons_clearButtonViewId, 0);

        typedArray.recycle();

        setMode(view, modeAttr);

        //TODO : MDK-546
        // Initialize currently selected date and date formatter
        this.dateFormatter = createDateFormat(dateFormatPattern, DATE_EXTRACTION, view.getContext());
        this.timeFormatter = createDateFormat(timeFormatPattern, TIME_EXTRACTION, view.getContext());

        if (minString != null) {
            //TODO : MDK-546
            this.minMDKDate.setDate(minString, this.dateFormatter);
        }

        if (maxString != null) {
            this.maxMDKDate.setDate(maxString, this.dateFormatter);
        }

        //TODO : MDK-546
        this.setAttributeMap(new MDKAttributeSet(attrs));

        if (timeFormatPattern == null) {
            this.is24HourFormat = android.text.format.DateFormat.is24HourFormat(view.getContext());
        }
    }

    /**
     * Sets the processing mode of the view based on the given attributes.
     *
     * @param view     the view being processed
     * @param modeAttr the mode set in the layout attributes
     */
    private void setMode(View view, int modeAttr) {
        if (modeAttr != -1) {
            // If the mode attribute has been set, the handled values are :
            //  - date : it is a date picker
            //  - time : it is a time picker
            // All other cases are error case (dateTextView and timeTextView attributes should not
            // be set).
            if (modeAttr == DATE_PICKER) {
                dateTimePickerMode = DATE_PICKER;
                this.dateViewId = view.getId();
                this.cachedDateView = new WeakReference<>(view);
            } else if (modeAttr == TIME_PICKER) {
                dateTimePickerMode = TIME_PICKER;
                this.timeViewId = view.getId();
                this.cachedTimeView = new WeakReference<>(view);
            } else {
                // Default case : Date Picker
                dateTimePickerMode = DATE_PICKER;
                this.dateViewId = view.getId();
                this.cachedDateView = new WeakReference<>(view);
            }
        } else if (dateViewId != 0) {
            // If a date view attribute has been set, we are in the case in which the master widget
            // handles the time, and the slave component handles the date.
            dateTimePickerMode = DATE_TIME_PICKER;
            // The current view is the time view
            timeViewId = view.getId();
            this.cachedTimeView = new WeakReference<>(view);
        } else if (timeViewId != 0) {
            // If a time view attribute has been set, we are in the case in which the master widget
            // handles the date, and the slave component handles the time.
            dateTimePickerMode = DATE_TIME_PICKER;
            // The current view is the date view
            dateViewId = view.getId();
            this.cachedDateView = new WeakReference<>(view);
        } else {
            // Default case : Date Picker
            dateTimePickerMode = DATE_PICKER;
            this.dateViewId = view.getId();
            this.cachedDateView = new WeakReference<>(view);
        }
    }

    /**
     * Extract date or time format from pattern.
     *
     * @param pattern        the pattern.
     * @param context        the android context.
     * @param extractionType the kind of extraction (EnumKindOfExtraction)
     * @return formattedDate the extracted date/time;
     */
    private DateFormat createDateFormat(String pattern, @EnumKindOfExtraction int extractionType, Context context) {
        /** Output. */
        DateFormat formattedDateOrTime = null;
        // Initialize currently selected date and date formatter
        if (pattern != null) {
            formattedDateOrTime = new SimpleDateFormat(pattern);
        } else {

            if (extractionType == TIME_EXTRACTION) {
                if (context.getString(R.string.application_date_format) != null && !context.getString(R.string.application_date_format).equals(EMPTY_STRING)) {
                    formattedDateOrTime = new SimpleDateFormat(context.getString(R.string.application_date_format));
                } else {
                    formattedDateOrTime = android.text.format.DateFormat.getTimeFormat(context);
                }
            } else {
                if (context.getString(R.string.application_time_format) != null && !context.getString(R.string.application_time_format).equals(EMPTY_STRING)) {
                    formattedDateOrTime = new SimpleDateFormat(context.getString(R.string.application_time_format));
                } else {
                    formattedDateOrTime = android.text.format.DateFormat.getDateFormat(context);
                }
            }
        }
        return formattedDateOrTime;
    }

    /**
     * Updates the date and time views.
     */
    private void updateShownDateTime() {
        if (dateViewId != 0) {
            // Update date value
            if (this.mdkDate.getDateState() == MDKDate.DATE_TIME_NOT_NULL ||
                    this.mdkDate.getDateState() == MDKDate.TIME_NULL) {
                getDateTextView().setText(this.dateFormatter.format(this.mdkDate.getDateOrTime()));
            } else {
                getDateTextView().setText(dateHint);
            }
        }
        if (timeViewId != 0) {
            // Update time value
            if (this.mdkDate.getDateState() == MDKDate.DATE_TIME_NOT_NULL ||
                    this.mdkDate.getDateState() == MDKDate.DATE_NULL) {
                getTimeTextView().setText(this.timeFormatter.format(this.mdkDate.getDateOrTime()));
            } else {
                getTimeTextView().setText(timeHint);
            }
        }
    }

    /**
     * Register a ChangeListener in delegate.
     *
     * @param listener the ChangeListener to register
     */
    public void registerChangeListener(ChangeListener listener) {
        this.mdkListenerDelegate.registerChangeListener(listener);
    }

    /**
     * Unregisters a ChangeListener in delegate.
     *
     * @param listener the ChangeListener to unregister
     */
    public void unregisterChangeListener(ChangeListener listener) {
        this.mdkListenerDelegate.unregisterChangeListener(listener);
    }

    /**
     * Called by the view on the onAttachedToWindow event.
     * Initialize the click listeners and updates the date and time views
     */
    public void onAttachedToWindow() {
        // Initialize the date component
        if (dateViewId != 0) {
            // Handle click events on components
            getDateTextView().setOnClickListener(this);
        }
        // Initialize the time component
        if (timeViewId != 0) {
            // Handle click events on components
            getTimeTextView().setOnClickListener(this);
        }
        if (clearButtonId != 0) {
            // Handle click events on components
            getClearButton().setOnClickListener(this);
        }

        // Set initial date and time values
        updateShownDateTime();
    }

    /**
     * Handles the click on the date view or the time view in order to show the right dialog.
     *
     * @param view the view
     */
    @Override
    public void onClick(View view) {
        // If disabled, don't permit to edit the date or time
        if (enabled && !isReadonly()) {
            if (view.getId() == this.dateViewId) {
                // Display the date picker dialog
                Calendar calendar = Calendar.getInstance();
                if (this.mdkDate.getDateState() == MDKDate.DATE_NULL ||
                        this.mdkDate.getDateState() == MDKDate.DATE_TIME_NULL) {
                    this.mdkDate.setDate(new Date());
                }
                calendar.setTime(this.mdkDate.getDateOrTime());
                DatePickerDialog dateDialog = new DatePickerDialog(view.getContext(), this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dateDialog.setOnDismissListener(null);

                // Display dialog
                dateDialog.show();
            } else if (view.getId() == this.timeViewId) {
                // Display the time picker dialog
                Calendar calendar = Calendar.getInstance();
                if (this.mdkDate.getDateState() == MDKDate.TIME_NULL ||
                        this.mdkDate.getDateState() == MDKDate.DATE_TIME_NULL) {
                    this.mdkDate.setTime(new Date());
                }
                calendar.setTime(this.mdkDate.getDateOrTime());
                TimePickerDialog timeDialog = new TimePickerDialog(view.getContext(), this,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        this.is24HourFormat);

                // Display dialog
                timeDialog.show();
            }
            if (view.getId() == this.getClearButtonId()) {
                this.clearDate();
            }
        }
    }

    /**
     * Callback in order to retrieve the selected date from the dialog.
     *
     * @param view        the view
     * @param year        the new year
     * @param monthOfYear the new month
     * @param dayOfMonth  th new day
     */
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.mdkDate.getDateOrTime());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        this.mdkDate.setDate(calendar.getTime());

        if (view.isShown()) {
            updateShownDateTime();
            this.mdkListenerDelegate.notifyListeners();
        }
    }

    /**
     * Callback in order to retrieve the selected time from the dialog.
     *
     * @param view      the view
     * @param hourOfDay the new hour
     * @param minute    the new minute
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.mdkDate.getDateOrTime());
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        this.mdkDate.setTime(calendar.getTime());

        if (view.isShown()) {
            updateShownDateTime();
            this.mdkListenerDelegate.notifyListeners();
        }
    }

    /**
     * Returns the date TextView, if it exists.
     *
     * @return foundDateView th found date TextView
     */
    private TextView getDateTextView() {

        View foundDateView = null;
        // In time picker mode there is no date view
        if (dateTimePickerMode != TIME_PICKER) {
            // Try to reuse the cached view
            if (this.cachedDateView != null) {
                foundDateView = this.cachedDateView.get();
            }
            // if there is no valid cached view, try to get it
            if (foundDateView == null) {
                foundDateView = reverseFindViewById(this.dateViewId);
                this.cachedDateView = new WeakReference<View>(foundDateView);
            }
        }

        return (TextView) foundDateView;
    }

    /**
     * Returns the time TextView, if it exists.
     *
     * @return foundTimeView the found time TextView if exists
     */
    private TextView getTimeTextView() {

        View foundTimeView = null;
        // In date picker mode there is no time view
        if (dateTimePickerMode != DATE_PICKER) {
            // Try to reuse the cached view
            if (this.cachedTimeView != null) {
                foundTimeView = this.cachedTimeView.get();
            }
            // if there is no valid cached view, try to get it
            if (foundTimeView == null) {
                foundTimeView = reverseFindViewById(this.timeViewId);
                this.cachedTimeView = new WeakReference<View>(foundTimeView);
            }
        }

        return (TextView) foundTimeView;
    }

    /**
     * Sets the Date displayed by the widget.
     *
     * @param date the new date
     */
    public void setDisplayedDate(Date date) {
        if (date != null) {
            this.mdkDate.setDate(date);
        }
        updateShownDateTime();
    }

    /**
     * Return the displayed date and time of the widget.
     *
     * @return Date object according picking mode
     */
    public Date getDisplayedDate() {
        return mdkDate.getDateOrTime();

    }

    /**
     * Sets the Time displayed by the widget.
     *
     * @param time the new time
     */
    public void setDisplayedTime(Date time) {
        if (time != null) {
            this.mdkDate.setTime(time);
        }

        updateShownDateTime();
    }

    /**
     * Returns the mode in which the widget is.
     *
     * @return dateTimePickerMode the dateTimePickerMode
     */
    @DateTimeMode
    public int getDateTimePickerMode() {
        return dateTimePickerMode;
    }

    /**
     * True if the widget is enabled.
     *
     * @return enabled enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the enabled flag.
     *
     * @param enabled the new flag
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        /* clear button setEnable */
        setEnabledView(this.getClearButton(), enabled);

        // Propagates the enabled flag to the slave view, if any.
        // Note: Only propagate to slave view, calling setEnabled on master view will result in
        // an infinite loop !
        View masterView = this.valueObject.getView();
        if (masterView != null) {
            if (this.dateViewId != 0 && this.dateViewId != masterView.getId()) {
                getDateTextView().setEnabled(enabled);
            }
            if (this.timeViewId != 0 && this.timeViewId != masterView.getId()) {
                getTimeTextView().setEnabled(enabled);
            }
        }
    }


    /**
     * Get min date (in Date format).
     *
     * @return Date the min date
     */
    public Date getMin() {
        return minMDKDate.getDateOrTime();
    }

    /**
     * Set the min date.
     *
     * @param minDate the new min date
     */
    public void setMin(Date minDate) {
        this.minMDKDate.setDate(minDate.toString(), dateFormatter);
    }

    /**
     * Get max date (in Date format).
     *
     * @return Date th max date
     */
    public Date getMax() {
        return minMDKDate.getDateOrTime();
    }

    /**
     * Set the max date.
     *
     * @param maxDate the new max date
     */
    public void setMax(Date maxDate) {
        this.minMDKDate.setDate(maxDate.toString(), dateFormatter);
    }

    /**
     * Setter date hint.
     *
     * @param dateHint the new date hint
     */
    public void setDateHint(String dateHint) {
        this.dateHint = dateHint;
    }

    /**
     * Setter time hint.
     *
     * @param timeHint the new time hint
     */
    public void setTimeHint(String timeHint) {
        this.timeHint = timeHint;
    }

    @Override
    public Parcelable onSaveInstanceState(Parcelable superState) {

        // Save the android view instance state
        Parcelable state = super.onSaveInstanceState(superState);

        // Save the mdkDate
        Bundle bundle = new Bundle();
        bundle.putParcelable("state", state);
        bundle.putInt("dateState", this.mdkDate.getDateState());
        bundle.putSerializable("date", this.mdkDate.getDateOrTime());
        bundle.putString("dateHint", this.dateHint);
        bundle.putString("timeHint", this.timeHint);

        return bundle;
    }

    @Override
    public Parcelable onRestoreInstanceState(View view, Parcelable state) {
        Bundle bundle = (Bundle) state;

        // Restore the mdkDate
        Parcelable parcelable = bundle.getParcelable("state");
        final Date date = (Date) bundle.getSerializable("date");

        if (date != null) {
            int dateTimeState = bundle.getInt("dateState");
            if (dateTimeState == 0 || dateTimeState == 3) {
                this.mdkDate.setDate(date);
            }
            if (dateTimeState == 0 || dateTimeState == 2) {
                this.mdkDate.setTime(date);
            }
        }

        String hint = bundle.getString("dateHint");
        if (hint != null) {
            this.dateHint = hint;
        }
        hint = bundle.getString("timeHint");
        if (hint != null) {
            this.timeHint = hint;
        }

        // Restore the android view instance state
        return super.onRestoreInstanceState(view, parcelable);
    }

    /**
     * Returns the hints of the widget as an array.
     *
     * @return the hints as an array
     */
    public CharSequence[] getHints() {
        CharSequence[] hints = new CharSequence[2];

        hints[0] = this.dateHint;
        hints[1] = this.timeHint;

        return hints;
    }

    /**
     * Sets the hints of the widget.
     *
     * @param hints the array of hints to set
     */
    public void setHints(CharSequence[] hints) {
        if (hints.length < 2) {
            Log.e(this.getClass().getSimpleName(), "The method should receive an array with two elements");
            return;
        }

        this.dateHint = hints[0].toString();
        this.timeHint = hints[1].toString();

        if (this.cachedTimeView != null) {
            TextView time = (TextView) this.cachedTimeView.get();
            if (time != null) {
                time.setHint(this.timeHint);
            }
        }
        if (this.cachedDateView != null) {
            TextView date = (TextView) this.cachedDateView.get();
            if (date != null) {
                date.setHint(this.dateHint);
            }
        }

        updateShownDateTime();
    }

    /**
     * Returns the identifier of the clear button.
     *
     * @return the identifier of the clear button
     */
    public int getClearButtonId() {
        return this.clearButtonId;
    }

    /**
     * Returns the clear button.
     *
     * @return the clear button
     */
    public View getClearButton() {
        if (this.clearButtonId != 0) {
            return reverseFindViewById(this.clearButtonId);
        }
        return null;
    }

    /**
     * Sets the Date to null.
     */
    public void clearDate() {
        this.mdkDate.clearDateState();
        updateShownDateTime();
    }

    /**
     * set the enable status on a view.
     *
     * @param view      the view to set
     * @param isEnabled true if the view should be enabled
     */
    private void setEnabledView(View view, boolean isEnabled) {
        if (view != null) {
            view.setEnabled(isEnabled);
        }
    }

    /**
     * Get the value to validate, depend on the dateTimePickerMode.
     *
     * @return the value to validate
     */
    public Object getValueToValidate() {
        if (dateTimePickerMode == DATE_PICKER) {
            return this.mdkDate.getDate();
        } else if (dateTimePickerMode == TIME_PICKER) {
            return this.mdkDate.getTime();
        } else {
            return this.mdkDate.getDateTime();
        }
    }
}

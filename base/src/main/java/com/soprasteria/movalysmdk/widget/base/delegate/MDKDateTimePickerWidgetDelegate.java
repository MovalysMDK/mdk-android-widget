package com.soprasteria.movalysmdk.widget.base.delegate;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.soprasteria.movalysmdk.widget.base.R;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;

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
public class MDKDateTimePickerWidgetDelegate extends MDKWidgetDelegate implements MDKWidget, View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    /** Key used in the "mode" XML attribute in order to tell the MDKDateTime to act as a date picker */
    private static final String DATE_PICKER_MODE = "date";
    /** Key used in the "mode" XML attribute in order to tell the MDKDateTime to act as a time picker */
    private static final String TIME_PICKER_MODE = "time";

    private static final String NULL_DATE_TEXT = "--/--/----";
    private static final String NULL_TIME_TEXT = "--:--";

    /** MDKDateTime active mode enumeration */
    public enum DateTimePickerMode {
        DATE_PICKER,
        TIME_PICKER,
        DATE_TIME_PICKER,
    }

    /** Mode in which the widget is */
    private DateTimePickerMode dateTimePickerMode;
    /** ID of the date TextView */
    private int dateViewId;
    /** ID of the time TextView */
    private int timeViewId;
    /** Cached reference of the date view */
    private WeakReference<View> cachedDateView;
    /** Cached reference of the time view */
    private WeakReference<View> cachedTimeView;

    /** Date currently displayed */
    private Date displayedDate;
    /** Time currently displayed */
    private Date displayedTime;
    /** Formatter used to display the date */
    private DateFormat dateFormatter;
    /** Formatter used to display the time */
    private DateFormat timeFormatter;
    /** If true, the time input is in 24H format */
    private boolean is24HourFormat;
    /** String used when the date is null */
    private String nullDateText = NULL_DATE_TEXT;
    /** String used when the time is null */
    private String nullTimeText = NULL_TIME_TEXT;

    /** True if the component is enabled */
    private boolean enabled = true;

    /**
     * Constructor.
     * @param view
     * @param attrs
     */
    public MDKDateTimePickerWidgetDelegate(View view, AttributeSet attrs) {

        super(view, attrs);

        // DateTimePicker specific fields parsing
        TypedArray typedArray = view.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKDateTimePickerComponent);

        String modeAttr = typedArray.getString(R.styleable.MDKCommons_MDKDateTimePickerComponent_mode);
        dateViewId = typedArray.getResourceId(R.styleable.MDKCommons_MDKDateTimePickerComponent_dateTextViewId, 0);
        timeViewId = typedArray.getResourceId(R.styleable.MDKCommons_MDKDateTimePickerComponent_timeTextViewId, 0);
        String dateFormatPattern = typedArray.getString(R.styleable.MDKCommons_MDKDateTimePickerComponent_dateFormat);
        String timeFormatPattern = typedArray.getString(R.styleable.MDKCommons_MDKDateTimePickerComponent_timeFormat);

        typedArray.recycle();

        if (modeAttr != null) {
            // If the mode attribute has been set, the handled values are :
            //  - date : it is a date picker
            //  - time : it is a time picker
            // All other cases are error case (dateTextView and timeTextView attributes should not
            // be set).
            if (modeAttr.equals(DATE_PICKER_MODE)) {
                dateTimePickerMode = DateTimePickerMode.DATE_PICKER;
                this.dateViewId = view.getId();
                this.cachedDateView = new WeakReference<View>(view);
            } else if (modeAttr.equals(TIME_PICKER_MODE)) {
                dateTimePickerMode = DateTimePickerMode.TIME_PICKER;
                this.timeViewId = view.getId();
                this.cachedTimeView = new WeakReference<View>(view);
            } else {
                // Default case : Date Picker
                dateTimePickerMode = DateTimePickerMode.DATE_PICKER;
                this.dateViewId = view.getId();
                this.cachedDateView = new WeakReference<View>(view);
            }
        } else if (dateViewId != 0) {
            // If a date view attribute has been set, we are in the case in which the master widget
            // handles the time, and the slave component handles the date.
            dateTimePickerMode = DateTimePickerMode.DATE_TIME_PICKER;
            // The current view is the time view
            timeViewId = view.getId();
            this.cachedTimeView = new WeakReference<View>(view);
        } else if (timeViewId != 0) {
            // If a time view attribute has been set, we are in the case in which the master widget
            // handles the date, and the slave component handles the time.
            dateTimePickerMode = DateTimePickerMode.DATE_TIME_PICKER;
            // The current view is the date view
            dateViewId = view.getId();
            this.cachedDateView = new WeakReference<View>(view);
        } else {
            // Default case : Date Picker
            dateTimePickerMode = DateTimePickerMode.DATE_PICKER;
            this.dateViewId = view.getId();
            this.cachedDateView = new WeakReference<View>(view);
        }

        // Initialize currently selected date and date formatter
        if (dateFormatPattern != null) {
            this.dateFormatter = new SimpleDateFormat(dateFormatPattern);
        } else {
            this.dateFormatter = android.text.format.DateFormat.getDateFormat(view.getContext());
        }
        if (timeFormatPattern != null) {
            this.timeFormatter = new SimpleDateFormat(timeFormatPattern);
        } else {
            this.timeFormatter = android.text.format.DateFormat.getTimeFormat(view.getContext());
            this.is24HourFormat = android.text.format.DateFormat.is24HourFormat(view.getContext());
        }
    }

    /**
     * Updates the date and time views.
     */
    private void updateShownDateTime() {
        if (dateViewId != 0) {
            // Update date value
            if (displayedDate != null) {
                getDateTextView().setText(this.dateFormatter.format(displayedDate));
            } else {
                getDateTextView().setText(nullDateText);
            }
        }
        if (timeViewId != 0) {
            // Update time value
            if (displayedTime != null) {
                getTimeTextView().setText(this.timeFormatter.format(displayedTime));
            } else {
                getTimeTextView().setText(nullTimeText);
            }
        }
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

        // Set initial date and time values
        updateShownDateTime();
    }

    /**
     * Handles the click on the date view or the time view in order to show the right dialog.
     * @param view
     */
    @Override
    public void onClick(View view) {
        // If disabled, don't permit to edit the date or time
        if (enabled) {
            if (view.getId() == this.dateViewId) {
                // Display the date picker dialog
                Calendar calendar = Calendar.getInstance();
                if (displayedDate == null) {
                    displayedDate = new Date();
                }
                calendar.setTime(displayedDate);
                DatePickerDialog dateDialog = new DatePickerDialog(view.getContext(), this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                // Display dialog
                dateDialog.show();
            } else if (view.getId() == this.timeViewId) {
                // Display the time picker dialog
                Calendar calendar = Calendar.getInstance();
                if (displayedTime == null) {
                    displayedTime = new Date();
                }
                calendar.setTime(displayedTime);
                TimePickerDialog timeDialog = new TimePickerDialog(view.getContext(), this,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        this.is24HourFormat);
                // Display dialog
                timeDialog.show();
            }
        }
    }

    /**
     * Callback in order to retrieve the selected date from the dialog.
     * @param view
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     */
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(displayedDate);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        displayedDate = calendar.getTime();

        updateShownDateTime();
    }

    /**
     * Callback in order to retrieve the selected time from the dialog.
     * @param view
     * @param hourOfDay
     * @param minute
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(displayedTime);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        displayedTime = calendar.getTime();

        updateShownDateTime();
    }

    /**
     * Returns the date TextView, if it exists.
     * @return
     */
    private TextView getDateTextView() {

        View foundDateView = null;
        // In time picker mode there is no date view
        if (dateTimePickerMode != DateTimePickerMode.TIME_PICKER) {
            // Try to reuse the cached view
            if (this.cachedDateView != null) {
                foundDateView = this.cachedDateView.get();
            }
            // if there is no valid cached view, try to get it
            if (foundDateView == null) {
                View rootView = findRootView(true);
                if (rootView != null) {
                    foundDateView = rootView.findViewById(this.dateViewId);
                    this.cachedDateView = new WeakReference<View>(foundDateView);
                }
            }
        }

        return (TextView)foundDateView;
    }

    /**
     * Returns the time TextView, if it exists.
     * @return
     */
    private TextView getTimeTextView() {

        View foundTimeView = null;
        // In date picker mode there is no time view
        if (dateTimePickerMode != DateTimePickerMode.DATE_PICKER) {
            // Try to reuse the cached view
            if (this.cachedTimeView != null) {
                foundTimeView = this.cachedTimeView.get();
            }
            // if there is no valid cached view, try to get it
            if (foundTimeView == null) {
                View rootView = findRootView(true);
                if (rootView != null) {
                    foundTimeView = rootView.findViewById(this.timeViewId);
                    this.cachedTimeView = new WeakReference<View>(foundTimeView);
                }
            }
        }

        return (TextView)foundTimeView;
    }

    /**
     * Sets the Date displayed by the widget.
     * @param date
     */
    public void setDisplayedDate(Date date) {
        this.displayedDate = (Date) date.clone();
    }

    /**
     * Returns the Date displayed by the widget.
     */
    public Date getDisplayedDate() {
        return (Date) this.displayedDate.clone();
    }

    /**
     * Sets the Time displayed by the widget.
     * @param time
     */
    public void setDisplayedTime(Date time) {
        this.displayedTime = (Date) time.clone();
    }

    /**
     * Returns the Time displayed by the widget.
     * @return
     */
    public Date getDisplayedTime() {
        return (Date) this.displayedTime.clone();
    }

    /** Returns the mode in which the widget is */
    public DateTimePickerMode getDateTimePickerMode() {
        return dateTimePickerMode;
    }

    /** True if the widget is enabled */
    public boolean isEnabled() {
        return enabled;
    }

    /** Sets the enabled flag */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        // Propagates the enabled flag to the slave view, if any.
        // Note: Only propagate to slave view, calling setEnabled on master view will result in
        // an infinite loop !
        View masterView = this.weakView.get();
        if (masterView != null) {
            if (this.dateViewId != 0 && this.dateViewId != masterView.getId()) {
                getDateTextView().setEnabled(enabled);
            }
            if (this.timeViewId != 0 && this.timeViewId != masterView.getId()) {
                getTimeTextView().setEnabled(enabled);
            }
        }
    }
}

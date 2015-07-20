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
package com.soprasteria.movalysmdk.widget.basic.model;

import android.support.annotation.IntDef;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Class container to manage MDKDateTime information.
 * <p>It can provides the user's widget Date but also informs on several states according date and time values settings:</p>
 * <ul>
 *      <li>1: The date and time have been both set.</li>
 *      <li>2: The date and time are both not set./li>
 *      <li>3: The date is not set.</li>
 *      <li>4: The time is not set.</li>
 * </ul>
 *
 */
public class MDKDate {

    /**
     * Log tag.
     */
    public static final String LOG_TAG = "MDKDate";

    /** The date and time are not null. */
    public static final int DATE_TIME_NOT_NULL = 0;

    /** The date and time are null. */
    public static final int DATE_TIME_NULL = 1;

    /** The date is null. */
    public static final int DATE_NULL = 2;

    /** The time is null. */
    public static final int TIME_NULL = 3;

    /** Current user's date displayed. */
    private Calendar dateDisplayed ;

    /** Current user's time displayed. */
    private Calendar timeDisplayed ;

    /**
     * Setter.
     * @param stringDate the date in string format
     * @param dateFormat the format pattern
     */
    public void setDate(String stringDate, DateFormat dateFormat) {
        try {
            Date tmpDate = dateFormat.parse(stringDate);
            setDate(tmpDate);
        } catch (ParseException e) {
            Log.e(LOG_TAG, "Parsing date failure", e);
        }
    }

    /**
     * Set the user's date.
     * @param date date to set as displayed.
     */
    public void setDate(Date date) {
        this.dateDisplayed = Calendar.getInstance();
        this.dateDisplayed.setTime(date);
        this.dateDisplayed.set(Calendar.HOUR, 0);
        this.dateDisplayed.set(Calendar.MINUTE, 0);
        this.dateDisplayed.set(Calendar.SECOND, 0);
        this.dateDisplayed.set(Calendar.MILLISECOND, 0);
    }

    /**
     * Set the user's time.
     * @param time time to set as displayed.
     */
    public void setTime(Date time) {
        this.timeDisplayed = Calendar.getInstance();
        this.timeDisplayed.setTime(time);
        this.timeDisplayed.set(Calendar.YEAR, 0);
        this.timeDisplayed.set(Calendar.MONTH, 0);
        this.timeDisplayed.set(Calendar.DAY_OF_MONTH, 0);
    }

    /**
     * Get the current user's date and time selected.
     * @return date according user's fields
     */
    public Date getDate(){

        Date dateToReturn = null ;
        if ( this.dateDisplayed != null || this.timeDisplayed != null ) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);

            if ( this.dateDisplayed != null ) {
                cal.set(Calendar.YEAR, dateDisplayed.get(Calendar.YEAR));
                cal.set(Calendar.MONTH, dateDisplayed.get(Calendar.MONTH));
                cal.set(Calendar.DAY_OF_MONTH, dateDisplayed.get(Calendar.DAY_OF_MONTH));
            }

            if ( this.timeDisplayed != null ) {
                cal.set(Calendar.HOUR_OF_DAY, timeDisplayed.get(Calendar.HOUR_OF_DAY));
                cal.set(Calendar.MINUTE, timeDisplayed.get(Calendar.MINUTE));
            }

            dateToReturn = cal.getTime();
        }
        return dateToReturn ;
    }

    /**
     * Define the list of possible MDKDate states.
     */
    @IntDef({DATE_TIME_NOT_NULL, DATE_TIME_NULL, DATE_NULL, TIME_NULL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DateState {
        // nothing in the annotation interface.
    }

    /**
     * Get the component state.
     * @return state of the date and time components
     */
    public int getDateState(){

        if (this.dateDisplayed == null && this.timeDisplayed == null){
            return this.DATE_TIME_NULL;
        }

        if (this.dateDisplayed == null){
            return this.DATE_NULL;
        }

        if (this.timeDisplayed == null){
            return this.TIME_NULL;
        }

        return DATE_TIME_NOT_NULL;
    }

    /**
     * Check if the MDKDate is before.
     * @param dateToCompare the date to compare
     * @return true if the date is before other else
     */
    public boolean beforeDate(MDKDate dateToCompare) {
        return this.getDate().before(dateToCompare.getDate());
    }

    /**
     * Check if the MDKDate is after.
     * @param dateToCompare the date to compare
     * @return true if the date is after other else
     */
    public boolean afterDate(MDKDate dateToCompare) {
        return this.getDate().after(dateToCompare.getDate());
    }
}

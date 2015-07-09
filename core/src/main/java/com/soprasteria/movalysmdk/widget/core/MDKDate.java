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

import android.support.annotation.IntDef;

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

    //FIXME SMA : cette classe ne me semble pas dans le bon package à voir avec LMI

    /** The date and time are not null. */
    public static final int DATE_TIME_NOT_NULL = 0;

    /** The date and time are null. */
    public static final int DATE_TIME_NULL = 1;

    /** The date is null. */
    public static final int DATE_NULL = 2;

    /** The time is null. */
    public static final int TIME_NULL = 3;

    /** Current user's date displayed. */
    private Date dateDisplayed = null;

    /** Current user's time displayed. */
    private Date timeDisplayed = null;

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
            e.printStackTrace();
            // TODO mettre la bonne exception
        }

    }

    /**
     * Set the user's date.
     * @param mdkDateDate date to set as displayed.
     */
    public void setDate(Date mdkDateDate) {
        this.dateDisplayed = (Date) mdkDateDate.clone();
    }

    /**
     * Set the user's time.
     * @param mdkDateTime time to set as displayed.
     */
    public void setTime(Date mdkDateTime) {
        this.timeDisplayed = (Date) mdkDateTime.clone();
    }

    /**
     * Get the current user's date and time selected.
     * @return date according user's fields
     */
    public Date getDate(){
        Date dateToReturn= null;

        if (this.dateDisplayed != null) {
            dateToReturn = (Date) this.dateDisplayed.clone();
        }

        if (this.timeDisplayed != null) {
            dateToReturn = (Date) this.timeDisplayed.clone();
        }

        if (this.dateDisplayed != null && timeDisplayed != null){
            Calendar dateCalendar = Calendar.getInstance();
            dateCalendar.setTime(this.dateDisplayed);

            Calendar timeCalendar = Calendar.getInstance();
            timeCalendar.setTime(this.timeDisplayed);

            dateCalendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY));
            dateCalendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
            dateCalendar.set(Calendar.SECOND, timeCalendar.get(Calendar.SECOND));

            dateToReturn = dateCalendar.getTime();
        }

        return dateToReturn;
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

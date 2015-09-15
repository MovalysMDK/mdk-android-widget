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
package com.soprasteria.movalysmdk.espresso.action;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;

import com.soprasteria.movalysmdk.widget.basic.MDKDateTime;
import com.soprasteria.movalysmdk.widget.core.MDKBaseRichDateWidget;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.util.Calendar;

/**
 * Action to manipulate MdkRichDateWidgets.
 */
public class MdkRichDateTimeAction {

    /**
     * Constructor.
     */
    private MdkRichDateTimeAction() {
        // private because helper class.
    }

    /**
     * Set date on mdkRichDateWidget.
     * @param year year
     * @param monthOfYear month
     * @param dayOfMonth day
     * @return ViewAction
     */
    public static ViewAction setDate(final int year, final int monthOfYear, final int dayOfMonth) {
        return new SetDateViewAction(year, monthOfYear, dayOfMonth);
    }

    /**
     * Set date on mdkRichDateWidget.
     * @param hour year
     * @param minute month
     * @return ViewAction
     */
    public static ViewAction setTime(final int hour, final int minute) {
        return new SetTimeViewAction(hour, minute);
    }

    /**
     * Set datetime on mdkRichDateWidget.
     * @param year year
     * @param monthOfYear month
     * @param dayOfMonth day
     * @param hour year
     * @param minute month
     * @return ViewAction
     */
    public static ViewAction setDateTime(final int year, final int monthOfYear, final int dayOfMonth, final int hour, final int minute) {
        return new SetDateTimeViewAction(year, monthOfYear, dayOfMonth, hour, minute);
    }

    /**
     * SetTime action for MDKRichDateTimeWidget.
     */
    private static class SetTimeViewAction implements ViewAction {

        /**
         * hour.
         */
        private final int hour ;

        /**
         * minute.
         */
        private final int minute ;

        /**
         * Constructor.
         * @param hour hour.
         * @param minute minute.
         */
        public SetTimeViewAction(int hour, int minute) {
            this.hour = hour;
            this.minute = minute;
        }

        @Override
        public void perform(UiController uiController, View view) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.MINUTE, minute);

            MDKDateTime dateTimeView;

            if (view instanceof MDKBaseRichDateWidget) {
                MDKBaseRichDateWidget dateTimeWidget = (MDKBaseRichDateWidget) view;
                dateTimeView = (MDKDateTime) dateTimeWidget.getInnerWidget();
            } else {
                dateTimeView = (MDKDateTime) view;
            }

            dateTimeView.setTime(cal.getTime());
        }

        @Override
        public String getDescription() {
            return "set time";
        }

        @Override
        public Matcher<View> getConstraints() {
            return Matchers.allOf(
                    Matchers.anyOf(ViewMatchers.isAssignableFrom(MDKBaseRichDateWidget.class), ViewMatchers.isAssignableFrom(MDKDateTime.class)),
                    ViewMatchers.isDisplayed());
        }
    }

    /**
     * SetDate action on MDKRichDateTime widget.
     */
    private static class SetDateViewAction implements ViewAction {

        /**
         * Year.
         */
        private final int year;

        /**
         * Month of year.
         */
        private final int monthOfYear;

        /**
         * Day of month.
         */
        private final int dayOfMonth;

        /**
         * Constructor.
         * @param year year.
         * @param monthOfYear month of year.
         * @param dayOfMonth day of month.
         */
        public SetDateViewAction(int year, int monthOfYear, int dayOfMonth) {
            this.year = year;
            this.monthOfYear = monthOfYear;
            this.dayOfMonth = dayOfMonth;
        }

        @Override
        public void perform(UiController uiController, View view) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, monthOfYear -1);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            MDKDateTime dateTimeView;

            if (view instanceof MDKBaseRichDateWidget) {
                MDKBaseRichDateWidget dateTimeWidget = (MDKBaseRichDateWidget) view;
                dateTimeView = (MDKDateTime) dateTimeWidget.getInnerWidget();
            } else {
                dateTimeView = (MDKDateTime) view;
            }

            dateTimeView.setDate(cal.getTime());
        }

        @Override
        public String getDescription() {
            return "set date";
        }

        @Override
        public Matcher<View> getConstraints() {
            return Matchers.allOf(
                    Matchers.anyOf(ViewMatchers.isAssignableFrom(MDKBaseRichDateWidget.class), ViewMatchers.isAssignableFrom(MDKDateTime.class)),
                    ViewMatchers.isDisplayed());
        }
    }

    /**
     * SetDateTime action for MDKRichDateTimeWidget.
     */
    private static class SetDateTimeViewAction implements ViewAction {

        /**
         * hour.
         */
        private final int hour ;

        /**
         * minute.
         */
        private final int minute ;

        /**
         * Year.
         */
        private final int year;

        /**
         * Month of year.
         */
        private final int monthOfYear;

        /**
         * Day of month.
         */
        private final int dayOfMonth;

        /**
         * Constructor.
         * @param year year.
         * @param monthOfYear month of year.
         * @param dayOfMonth day of month.
         * @param hour hour.
         * @param minute minute.
         */
        public SetDateTimeViewAction(int year, int monthOfYear, int dayOfMonth, int hour, int minute) {
            this.hour = hour;
            this.minute = minute;
            this.year = year;
            this.monthOfYear = monthOfYear;
            this.dayOfMonth = dayOfMonth;
        }

        @Override
        public void perform(UiController uiController, View view) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.MINUTE, minute);
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, monthOfYear - 1);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            MDKDateTime dateTimeView;

            if (view instanceof MDKBaseRichDateWidget) {
                MDKBaseRichDateWidget dateTimeWidget = (MDKBaseRichDateWidget) view;
                dateTimeView = (MDKDateTime) dateTimeWidget.getInnerWidget();
            } else {
                dateTimeView = (MDKDateTime) view;
            }

            dateTimeView.setDate(cal.getTime());
            dateTimeView.setTime(cal.getTime());
        }

        @Override
        public String getDescription() {
            return "set date time";
        }

        @Override
        public Matcher<View> getConstraints() {
            return Matchers.allOf(
                    Matchers.anyOf(ViewMatchers.isAssignableFrom(MDKBaseRichDateWidget.class), ViewMatchers.isAssignableFrom(MDKDateTime.class)),
                    ViewMatchers.isDisplayed());
        }
    }
}

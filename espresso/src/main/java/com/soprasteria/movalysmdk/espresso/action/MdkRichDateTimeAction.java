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
        return new ViewAction() {

            @Override
            public void perform(UiController uiController, View view) {
                MDKBaseRichDateWidget dateTimeWidget = (MDKBaseRichDateWidget)view;

                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(0);
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear-1);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                MDKDateTime dateTimeView = (MDKDateTime) dateTimeWidget.getInnerWidget();
                dateTimeView.setDate(cal.getTime());
            }

            @Override
            public String getDescription() {
                return "set date";
            }

            @Override
            public Matcher<View> getConstraints() {
                return Matchers.allOf(
                        ViewMatchers.isAssignableFrom(MDKBaseRichDateWidget.class),
                        ViewMatchers.isDisplayed());
            }
        };
    }

    /**
     * Set date on mdkRichDateWidget.
     * @param hour year
     * @param minute month
     * @return ViewAction
     */
    public static ViewAction setTime(final int hour, final int minute) {
        return new ViewAction() {

            @Override
            public void perform(UiController uiController, View view) {
                MDKBaseRichDateWidget dateTimeWidget = (MDKBaseRichDateWidget)view;

                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(0);
                cal.set(Calendar.HOUR_OF_DAY, hour);
                cal.set(Calendar.MINUTE, minute);

                MDKDateTime dateTimeView = (MDKDateTime) dateTimeWidget.getInnerWidget();
                dateTimeView.setTime(cal.getTime());
            }

            @Override
            public String getDescription() {
                return "set time";
            }

            @Override
            public Matcher<View> getConstraints() {
                return Matchers.allOf(
                        ViewMatchers.isAssignableFrom(MDKBaseRichDateWidget.class),
                        ViewMatchers.isDisplayed());
            }
        };
    }
}

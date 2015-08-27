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
package com.soprasteria.movalysmdk.widget.sample;

import android.support.test.espresso.contrib.PickerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.soprasteria.movalysmdk.espresso.action.SpoonScreenshotAction;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.soprasteria.movalysmdk.espresso.action.MdkRichDateTimeAction.setDate;
import static com.soprasteria.movalysmdk.espresso.action.MdkRichDateTimeAction.setTime;
import static com.soprasteria.movalysmdk.espresso.matcher.MdkDateMatchers.withDate;
import static com.soprasteria.movalysmdk.espresso.matcher.MdkDateMatchers.withDateTime;
import static com.soprasteria.movalysmdk.espresso.matcher.MdkDateMatchers.withTime;
import static com.soprasteria.movalysmdk.espresso.matcher.MdkViewMatchers.withConcatText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Tests for MdkRichDate et MdkRichDateTime widgets.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class DateTest {

    /**
     *  Rule to initialize DateActivity.
     */
    @Rule
    public ActivityTestRule<DateActivity> mActivityRule = new ActivityTestRule<>(DateActivity.class);

    /**
     * Test empty value.
     */
    @Test
    public void testNotFilledDate1() {
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // click validate button
        onView(withId(R.id.validateButton)).perform(click());

        // Take screenshot
        SpoonScreenshotAction.perform("richdatetime_emptyvalue");

        // check error
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichDateTime_withLabelAndMandatory))))
                .check(matches(withConcatText(R.string.fortyTwoTextFormater_prefix, R.string.mdkvalidator_mandatory_error_invalid)));
    }

    /**
     * Test with a valid date time value.
     */
    @Test
    public void testValidTimeDate() {
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // Update time
        onView(withId(R.id.mdkRichDateTime_withLabelAndMandatory)).perform(setDate(2015, 2, 2));
        onView(withId(R.id.mdkRichDateTime_withLabelAndMandatory)).perform(setTime(10,30));
        onView(withId(R.id.mdkRichDateTime_withLabelAndMandatory)).check(matches(withDateTime(2015, 2, 2, 10, 30)));

        // click validate button
        onView(withId(R.id.validateButton)).perform(click());

        // Take screenshot
        SpoonScreenshotAction.perform("richdatetime_validvalue");

        // check no error
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichDateTime_withLabelAndMandatory))))
                .check(matches(withText("")));

        // Update time only
        onView(withId(R.id.mdkRichDateTime_withLabelAndMandatory)).perform(setTime(12,40));
        onView(withId(R.id.mdkRichDateTime_withLabelAndMandatory)).check(matches(withDateTime(2015, 2, 2, 12, 40)));

        // click validate button
        onView(withId(R.id.validateButton)).perform(click());

        // check no error
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichDateTime_withLabelAndMandatory))))
                .check(matches(withText("")));
    }

    /**
     * Test with a valid date value.
     */
    @Test
    public void testValidDate() {
        // update date
        onView(withId(R.id.mdkRichDate_withLabelAndMandatory)).perform(click());

        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).check(matches(isDisplayed()));
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2010, 4, 23));
        onView(withId(android.R.id.button1)).perform(click());

        // click validate button
        onView(withId(R.id.validateButton)).perform(click());

        // check date
        onView(withId(R.id.mdkRichDate_withLabelAndMandatory)).check(matches(withDate(2010, 4, 23)));

        // check no error
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichDate_withLabelAndMandatory))))
                .check(matches(withText("")));
    }

    /**
     * Test with a valid time value.
     */
    @Test
    public void testValidTime() {
        // update time
        onView(withId(R.id.mdkRichTime_withLabelAndMandatory)).perform(click());

        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).check(matches(isDisplayed()));
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(4, 23));
        onView(withId(android.R.id.button1)).perform(click());

        // click validate button
        onView(withId(R.id.validateButton)).perform(click());

        // check time
        onView(withId(R.id.mdkRichTime_withLabelAndMandatory)).check(matches(withTime(4, 23)));

        // check no error
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichTime_withLabelAndMandatory))))
                .check(matches(withText("")));
    }

    /**
     * Test with a valid date time value but not in range acceptable value.
     */
    @Test
    public void testNotValidDateRange() {
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // Update time
        onView(withId(R.id.mdkRichDateTime_withLabelAndMandatory)).perform(setDate(2015, 2, 2));
        onView(withId(R.id.mdkRichDateTime_withLabelAndMandatory)).perform(setTime(10,30));

        // click validate button
        onView(withId(R.id.validateButton)).perform(click());

        // Take screenshot
        SpoonScreenshotAction.perform("richdatetime_validvalue");

        // check no error
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichDateTime_withLabelAndMandatory))))
                .check(matches(withText("")));

        // Update time only
        onView(withId(R.id.mdkRichDateTime_withLabelAndMandatory)).perform(setTime(12, 40));

        // click validate button
        onView(withId(R.id.validateButton)).perform(click());

        // check no error
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichDateTime_withLabelAndMandatory))))
                .check(matches(withText("")));
    }

    /**
     * Test datehint and timehint.
     */
    @Test
    public void testHints() {
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // click validate button
        onView(withId(R.id.validateButton)).perform(click());

        // Take screenshot
        SpoonScreenshotAction.perform("richdatetime_hints");

        // Check hint date value
        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.mdkRichDateTime_withLabelAndMandatoryAndHints))))
                .check(matches(withText(R.string.dateHint)));

        // Check hint time value
        onView(allOf(withId(R.id.component_internal_time), isDescendantOfA(withId(R.id.mdkRichDateTime_withLabelAndMandatoryAndHints))))
                .check(matches(withText(R.string.timeHint)));
    }
}

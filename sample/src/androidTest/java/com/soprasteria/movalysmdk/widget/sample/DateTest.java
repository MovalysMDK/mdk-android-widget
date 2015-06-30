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

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.soprasteria.movalysmdk.widget.test.actions.SpoonScreenshotAction;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import static com.soprasteria.movalysmdk.widget.test.matchers.MdkViewMatchers.withConcatText;
import static com.soprasteria.movalysmdk.widget.test.actions.MdkRichDateTimeAction.setDate;
import static com.soprasteria.movalysmdk.widget.test.actions.MdkRichDateTimeAction.setTime;

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
                .check(matches(withConcatText(R.string.fortyTwoTextFormater_prefix, R.string.mdkwidget_mandatory_error)));
    }

    /**
     * Test with a valid date time value.
     */
    @Test
    public void testValidDate() {
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
        onView(withId(R.id.mdkRichDateTime_withLabelAndMandatory)).perform(setTime(12,40));

        // click validate button
        onView(withId(R.id.validateButton)).perform(click());

        // check no error
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichDateTime_withLabelAndMandatory))))
                .check(matches(withText("")));
    }
}

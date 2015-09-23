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

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.soprasteria.movalysmdk.espresso.action.OrientationChangeAction.orientationPortrait;
import static com.soprasteria.movalysmdk.espresso.matcher.MdkViewMatchers.withConcatText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Non regression testing class for custom MDK Position widget.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class PositionTest {

    /**
     * Activity used for this tests.
     */
    @Rule
    public ActivityTestRule<PositionActivity> mActivityRule = new ActivityTestRule<>(PositionActivity.class);

    /**
      * Check MDK Position widget behaviour when this one is mandatory or not.
      */
    @Test
    public void testMandatory() {
        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // check map button not clickable
        onView(allOf(withId(R.id.component_mapButton), isDescendantOfA(withId(R.id.mdkRichPosition_locationWithLabelAndError))))
                .check(matches(not(isEnabled())));
        // check location button is clickable
        onView(allOf(withId(R.id.component_positionButton), isDescendantOfA(withId(R.id.mdkRichPosition_locationWithLabelAndError))))
                .check(matches(isEnabled()));

        // click validate button
        onView(withId(R.id.validateButton)).perform(click());

        // check mandatory error
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichPosition_locationWithLabelAndError))))
                .check(matches(withConcatText(R.string.test_fortyTwoTextFormater_prefix, R.string.test_mdkvalidator_position_error_validation)));

        // remove mandatory option on widget
        onView(withId(R.id.mandatoryButton)).perform(click());

        // click validate button
        onView(withId(R.id.validateButton)).perform(click());

        // check no error
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichPosition_locationWithLabelAndError))))
                .check(matches(withText(isEmptyOrNullString())));
    }

    /**
     * test the validity of the widget after clicking on the location button.
     */
    @Test
    public void testValidate() {
        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // check that latitude and longitude are empty
        onView(allOf(withId(R.id.component_internal_latitude), isDescendantOfA(withId(R.id.mdkRichPosition_locationWithLabelAndError))))
                .check(matches(withText(isEmptyOrNullString())));
        onView(allOf(withId(R.id.component_internal_longitude), isDescendantOfA(withId(R.id.mdkRichPosition_locationWithLabelAndError))))
                .check(matches(withText(isEmptyOrNullString())));

        // click on position button
        onView(allOf(withId(R.id.component_positionButton), isDescendantOfA(withId(R.id.mdkRichPosition_locationWithLabelAndError)))).perform(click());

        // TODO: should test the values, but does not work on emulator...
    }

    /**
     * Test the validity of the widget after text input.
     */
    @Test
    public void testFill() {
        // fill latitude and longitude
        onView(allOf(withId(R.id.component_internal_latitude), isDescendantOfA(withId(R.id.mdkRichPosition_locationWithLabelAndError))))
                .perform(typeText("1.5"));
        onView(allOf(withId(R.id.component_internal_longitude), isDescendantOfA(withId(R.id.mdkRichPosition_locationWithLabelAndError))))
                .perform(typeText("2.4"));

        onView(isRoot()).perform(orientationPortrait());

        // check that latitude and longitude are filled
        onView(allOf(withId(R.id.component_internal_latitude), isDescendantOfA(withId(R.id.mdkRichPosition_locationWithLabelAndError))))
                .check(matches(withText("1.5")));
        onView(allOf(withId(R.id.component_internal_longitude), isDescendantOfA(withId(R.id.mdkRichPosition_locationWithLabelAndError))))
                .check(matches(withText("2.4")));
    }

    /**
     * Test that the component is disabled by the button.
     */
    @Test
    public void testDisable() {
        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // Disable widgets
        onView(withId(R.id.enableButton)).perform(click());

        // Check widgets are disabled
        onView(withId(R.id.mdkPosition_withErrorAndCommandOutside)).check(matches(not(isEnabled())));

        // Re enabled widget
        onView(withId(R.id.enableButton)).perform(click());

        // Check widgets are enabled
        onView(withId(R.id.mdkPosition_withErrorAndCommandOutside)).check(matches(isEnabled()));
    }
}

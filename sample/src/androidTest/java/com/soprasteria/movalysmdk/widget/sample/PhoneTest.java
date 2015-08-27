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

import com.soprasteria.movalysmdk.espresso.action.SpoonScreenshotAction;
import com.soprasteria.movalysmdk.widget.basic.MDKPhone;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.soprasteria.movalysmdk.espresso.action.OrientationChangeAction.orientationLandscape;
import static com.soprasteria.movalysmdk.espresso.action.OrientationChangeAction.orientationPortrait;
import static com.soprasteria.movalysmdk.espresso.matcher.MdkViewMatchers.withConcatHint;
import static com.soprasteria.movalysmdk.espresso.matcher.MdkViewMatchers.withConcatText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Non regression testing class for custom MDK phone widget.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class PhoneTest {

    /**
     * Activity used for this tests.
     */
    @Rule
    public ActivityTestRule<PhoneActivity> mActivityRule = new ActivityTestRule<>(PhoneActivity.class);

    /**
     * Check MDK phone widget behaviour with invalid phone format.
     */
    @Test
    public void testInvalidPhone() {
        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // Write invalid phone
        onView(withId(R.id.mdkPhone_withErrorAndCommandOutside)).perform(typeText("wrong format"));

        // Check send button state
        onView(withId(R.id.buttonCall)).check(matches(not(isEnabled())));

        // click validate button
        onView(withId(R.id.validateButton)).perform(click());

        // check error
        onView(withId(R.id.checkbox_errorText)).check(matches(withConcatText(
                R.string.fortyTwoTextFormater_prefix, R.string.mdkvalidator_phone_error_invalid)));

        SpoonScreenshotAction.perform("phone_invalidphone_errorstate");

        onView(isRoot()).perform(orientationLandscape());

        SpoonScreenshotAction.perform("phone_invalidphone_errorstate_landscape");

        onView(withId(R.id.checkbox_errorText))
                .check(matches(withConcatText(R.string.fortyTwoTextFormater_prefix, R.string.mdkvalidator_phone_error_invalid)));

        onView(isRoot()).perform(orientationPortrait());

        onView(withId(R.id.checkbox_errorText))
                .check(matches(withConcatText(R.string.fortyTwoTextFormater_prefix, R.string.mdkvalidator_phone_error_invalid)));
    }

    /**
     * Check MDK phone widget behaviour with valid phone format.
     */
    @Test
    public void testValidPhone() {
        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // Write a valid phone address
        onView(withId(R.id.mdkPhone_withErrorAndCommandOutside)).perform(typeText("0240389090"));
        // Check send button state
        onView(withId(R.id.buttonCall)).check(matches(isEnabled()));

        // click validate button
        onView(withId(R.id.validateButton)).check(matches(isEnabled()));
        onView(withId(R.id.validateButton)).perform(click());

        // get value and check
        CharSequence text = ((MDKPhone) mActivityRule.getActivity().findViewById(R.id.mdkPhone_withErrorAndCommandOutside)).getText();
        assertThat("get equal set", "0240389090".equals(text.toString()));

        // check no error
        onView(withId(R.id.checkbox_errorText)).check(matches(withText(isEmptyOrNullString())));
    }

    /**
     * Check MDK phone widget behaviour when this one is enabled and disabled.
     */
    @Test
    public void testDisabledPhone() {
        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // Disable widgets
        onView(withId(R.id.enableButton)).perform(click());

        // Check widgets are disabled
        onView(withId(R.id.mdkPhone_withErrorAndCommandOutside)).check(matches(not(isEnabled())));

        // Re enabled widget
        onView(withId(R.id.enableButton)).perform(click());

        // Check widgets are enabled
        onView(withId(R.id.mdkPhone_withErrorAndCommandOutside)).check(matches(isEnabled()));
    }


    /**
     * Check MDK phone widget behaviour when this one is mandatory or not.
     */
    @Test
    public void testMandatoryPhone() {
        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // Check widgets are mandatory
        onView(withId(R.id.mdkPhone_withErrorAndCommandOutside))
                .check(matches(withConcatHint(R.string.testHintText, R.string.mandatory_char)));

        // remove mandatory option on widget
        onView(withId(R.id.mandatoryButton)).perform(click());

        // Check widgets are no more mandatory
        onView(withId(R.id.mdkPhone_withErrorAndCommandOutside))
                .check(matches(withHint(R.string.testHintText)));
    }
}

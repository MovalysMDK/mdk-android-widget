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

import com.soprasteria.movalysmdk.widget.basic.MDKEmail;
import com.soprasteria.movalysmdk.widget.test.espresso.actions.SpoonScreenshotAction;

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
import static com.soprasteria.movalysmdk.widget.test.espresso.actions.OrientationChangeAction.orientationLandscape;
import static com.soprasteria.movalysmdk.widget.test.espresso.actions.OrientationChangeAction.orientationPortrait;
import static com.soprasteria.movalysmdk.widget.test.espresso.matchers.MdkViewMatchers.withConcatHint;
import static com.soprasteria.movalysmdk.widget.test.espresso.matchers.MdkViewMatchers.withConcatText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Non regression testing class for custom MDK email widget.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class EmailTest {

    /**
     * Activity used for this tests.
     */
    @Rule
    public ActivityTestRule<EmailActivity> mActivityRule = new ActivityTestRule<>(EmailActivity.class);

    /**
     * Check MDK email widget behaviour with invalid email format.
     */
    @Test
    public void testInvalidEmail() {
        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // Write invalid email
        onView(withId(R.id.mdkEmail_withErrorAndCommandOutside)).perform(typeText("wrong format"));

        // Check send button state
        onView(withId(R.id.buttonSend)).check(matches(not(isEnabled())));

        // click validate button
        onView(withId(R.id.validateButton)).perform(click());

        // check error
        onView(withId(R.id.errorText)).check(matches(withConcatText(
                R.string.fortyTwoTextFormater_prefix, R.string.mdkwidget_email_error)));

        SpoonScreenshotAction.perform("email_invalidemail_errorstate");

        onView(isRoot()).perform(orientationLandscape());

        SpoonScreenshotAction.perform("email_invalidemail_errorstate_landscape");

        onView(withId(R.id.errorText))
                .check(matches(withConcatText(R.string.fortyTwoTextFormater_prefix, R.string.mdkwidget_email_error)));

        onView(isRoot()).perform(orientationPortrait());

        onView(withId(R.id.errorText))
                .check(matches(withConcatText(R.string.fortyTwoTextFormater_prefix, R.string.mdkwidget_email_error)));
    }

    /**
     * Check MDK email widget behaviour with valid email format.
     */
    @Test
    public void testValidEmail() {
        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // Write a valid email address
        onView(withId(R.id.mdkEmail_withErrorAndCommandOutside)).perform(typeText("myemail@soprasteria.com"));
        // Check send button state
        onView(withId(R.id.buttonSend)).check(matches(isEnabled()));

        // click validate button
        onView(withId(R.id.validateButton)).check(matches(isEnabled()));
        onView(withId(R.id.validateButton)).perform(click());

        // get value and check
        CharSequence text = ((MDKEmail) mActivityRule.getActivity().findViewById(R.id.mdkEmail_withErrorAndCommandOutside)).getText();
        assertThat("get equal set", "myemail@soprasteria.com".equals(text.toString()));

        // check no error
        onView(withId(R.id.errorText)).check(matches(withText(isEmptyOrNullString())));
    }

    /**
     * Check MDK email widget behaviour when this one is enabled and disabled.
     */
    @Test
    public void testDisabledEmail() {
        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // Disable widgets
        onView(withId(R.id.enableButton)).perform(click());

        // Check widgets are disabled
        onView(withId(R.id.mdkEmail_withErrorAndCommandOutside)).check(matches(not(isEnabled())));

        // Re enabled widget
        onView(withId(R.id.enableButton)).perform(click());

        // Check widgets are enabled
        onView(withId(R.id.mdkEmail_withErrorAndCommandOutside)).check(matches(isEnabled()));
    }


    /**
     * Check MDK email widget behaviour when this one is mandatory or not.
     */
    @Test
    public void testMandatoryEmail() {
        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // Check widgets are mandatory
        onView(withId(R.id.mdkEmail_withErrorAndCommandOutside))
                .check(matches(withConcatHint(R.string.testHintText, R.string.mandatory_char)));

        // remove mandatory option on widget
        onView(withId(R.id.mandatoryButton)).perform(click());

        // Check widgets are no more mandatory
        onView(withId(R.id.mdkEmail_withErrorAndCommandOutside))
                .check(matches(withHint(R.string.testHintText)));
    }
}

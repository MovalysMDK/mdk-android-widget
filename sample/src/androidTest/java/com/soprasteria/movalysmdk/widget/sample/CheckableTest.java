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

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
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
 * Non regression testing class for custom Mcheckedkable widget.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class CheckableTest {

    /**
     * ActiCheckused for this tests.
     */
    @Rule
    public ActivityTestRule<CheckableActivity> mActivityRule = new ActivityTestRule<>(CheckableActivity.class);

    /**
     * Check MDK checkable widget behaviour with invalid checkable format.
     */
    @Test
    public void testCheckable() {
        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // Check enabled status of the view
        onView(withId(R.id.mdkCheckbox_withErrorAndCommandOutside)).check(matches(not(isChecked())));

        // Click the view
        onView(withId(R.id.mdkCheckbox_withErrorAndCommandOutside)).perform(click());

        onView(withId(R.id.mdkCheckbox_withErrorAndCommandOutside)).check(matches(isChecked()));

        SpoonScreenshotAction.perform("checkable_checked_status");

        onView(isRoot()).perform(orientationLandscape());

        SpoonScreenshotAction.perform("checkable_checked_status_landscape");
    }

    /**
     * Check MDK checkable widget behaviour when this one is enabled and disabled.
     */
    @Test
    public void testDisabledCheckable() {
        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // Disable widgets
        onView(withId(R.id.enableButton)).perform(click());

        // Check widgets are disabled
        onView(withId(R.id.mdkCheckbox_withErrorAndCommandOutside)).check(matches(not(isEnabled())));

        // Re enabled widget
        onView(withId(R.id.enableButton)).perform(click());

        // Check widgets are enabled
        onView(withId(R.id.mdkCheckbox_withErrorAndCommandOutside)).check(matches(isEnabled()));
    }
}

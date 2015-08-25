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

import com.soprasteria.movalysmdk.espresso.action.SpoonScreenshotAction;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.soprasteria.movalysmdk.espresso.action.OrientationChangeAction.orientationLandscape;
import static com.soprasteria.movalysmdk.espresso.action.OrientationChangeAction.orientationPortrait;
import static com.soprasteria.movalysmdk.espresso.matcher.MdkViewMatchers.withConcatText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Tests for MdkRichseekbar widget.
 */
public class SeekBarTest {

    /**
     * Activity used for this tests.
     */
    @Rule
    public ActivityTestRule<SeekBarActivity> mActivityRule = new ActivityTestRule<>(SeekBarActivity.class);


    /**
     * Check MDK seekbar widget behaviour with invalid email format.
     */
    @Test
    public void testInvalidSeekBar() {

    // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // Swipe bar to obtain an invalid value
        onView(withId(R.id.mdkRichSeekBar_withLabelAndError)).perform(swipeRight());

        // check error

        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichSeekBar_withLabelAndError))))
                .check(matches(withConcatText(R.string.fortyTwoTextFormater_prefix, R.string.seekbar_maxSeekBarValue_error_20)));

        SpoonScreenshotAction.perform("seekbar_invalidseekbar_errorstate");

        onView(isRoot()).perform(orientationLandscape());

        SpoonScreenshotAction.perform("seekbar_invalidseekbar_errorstate_landscape");

        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichSeekBar_withLabelAndError))))
                .check(matches(withConcatText(R.string.fortyTwoTextFormater_prefix, R.string.seekbar_maxSeekBarValue_error_20)));

        onView(isRoot()).perform(orientationPortrait());

        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichSeekBar_withLabelAndError))))
                .check(matches(withConcatText(R.string.fortyTwoTextFormater_prefix, R.string.seekbar_maxSeekBarValue_error_20)));

    }

    /**
     * Check MDK seekbar widget behaviour when this one is enabled and disabled.
     */
    @Test
    public void testDisabledSeekbar() {
        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // Disable widgets
        onView(withId(R.id.enableButton)).perform(click());

        // Check widgets are disabled
        onView(withId(R.id.mdkRichSeekBar_withLabelAndError)).check(matches(not(isEnabled())));

        // Re enabled widget
        onView(withId(R.id.enableButton)).perform(click());

        // Check widgets are enabled
        onView(withId(R.id.mdkRichSeekBar_withLabelAndError)).check(matches(isEnabled()));
    }
}

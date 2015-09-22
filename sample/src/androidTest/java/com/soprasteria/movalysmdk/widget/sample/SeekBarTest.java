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

import com.soprasteria.movalysmdk.espresso.action.MdkSeekBarAction;
import com.soprasteria.movalysmdk.espresso.action.SpoonScreenshotAction;
import com.soprasteria.movalysmdk.espresso.matcher.MdkSeekbarMatchers;
import com.soprasteria.movalysmdk.widget.sample.factor.AbstractCommandWidgetTest;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
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
public class SeekBarTest extends AbstractCommandWidgetTest {

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
                .check(matches(withConcatText(R.string.test_fortyTwoTextFormater_prefix, R.string.test_seekbar_maxSeekBarValue_error_20)));

        SpoonScreenshotAction.perform("seekbar_invalidseekbar_errorstate");

        onView(isRoot()).perform(orientationLandscape());

        SpoonScreenshotAction.perform("seekbar_invalidseekbar_errorstate_landscape");

        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichSeekBar_withLabelAndError))))
                .check(matches(withConcatText(R.string.test_fortyTwoTextFormater_prefix, R.string.test_seekbar_maxSeekBarValue_error_20)));

        onView(isRoot()).perform(orientationPortrait());

        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichSeekBar_withLabelAndError))))
                .check(matches(withConcatText(R.string.test_fortyTwoTextFormater_prefix, R.string.test_seekbar_maxSeekBarValue_error_20)));

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
        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.mdkRichSeekBar_withLabelAndError)))).check(matches(not(isEnabled())));
        onView(allOf(withId(R.id.component_seekbar_edittext), isDescendantOfA(withId(R.id.mdkRichSeekBar_withLabelAndError)))).check(matches(not(isEnabled())));

        // Re enable widget
        onView(withId(R.id.enableButton)).perform(click());

        // Check widgets are enabled
        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.mdkRichSeekBar_withLabelAndError)))).check(matches(isEnabled()));
        onView(allOf(withId(R.id.component_seekbar_edittext), isDescendantOfA(withId(R.id.mdkRichSeekBar_withLabelAndError)))).check(matches(isEnabled()));

        testDisableOutsideWidget(R.id.mdkRichSeekBar_withLabelAndError);
    }

    /**
     * Check MDK seekbar returned value is the actual expected value
     */
    @Test
    public void testSeekbarValue(){
        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        //by seekbar input
        onView(withId(R.id.mdkRichSeekBar_withLabelAndError)).perform(MdkSeekBarAction.setMDKRichSeekbarProgress(50))
                .check(matches(MdkSeekbarMatchers.mdkRichSeekbarWithProgress(50)));

        onView(withId(R.id.mdkSeekBar_withErrorAndCommandOutside)).perform(MdkSeekBarAction.setMDKSeekbarProgress(42))
                .check(matches(MdkSeekbarMatchers.mdkSeekbarWithProgress(42)));


        //by edittext input
        onView(allOf(withId(R.id.component_seekbar_edittext), isDescendantOfA(withId(R.id.mdkRichSeekBar_withLabelAndError)))).perform(clearText(), typeText("1"));//caret goes before zero, so we only have to type the 1 to test 10
        //Log.d("TEST", String.valueOf(((MDKRichSeekBar) mActivityRule.getActivity().findViewById(R.id.mdkRichSeekBar_withLabelAndError)).getSeekBarValue()));
        onView(withId(R.id.mdkRichSeekBar_withLabelAndError)).check(matches(MdkSeekbarMatchers.mdkRichSeekbarWithProgress(10)));

        onView(allOf(withId(R.id.component_seekbar_edittext), isDescendantOfA(withId(R.id.mdkRichSeekBar_withLabelAndError)))).perform(clearText(),typeText("hello"));
        onView(withId(R.id.mdkRichSeekBar_withLabelAndError)).check(matches(MdkSeekbarMatchers.mdkRichSeekbarWithProgress(0)));

    }

    /**
     * Check MDK seekbar returns value in the min/max range
     */
    @Test
    public void testSeekbarValueRange(){
        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        //by seekbar input
        onView(withId(R.id.mdkRichSeekBar_min_42)).perform(MdkSeekBarAction.setMDKRichSeekbarProgress(50))
                .check(matches(MdkSeekbarMatchers.mdkRichSeekbarWithProgress(50)));

        onView(withId(R.id.mdkRichSeekBar_min_42)).perform(MdkSeekBarAction.setMDKRichSeekbarProgress(25))
                .check(matches(MdkSeekbarMatchers.mdkRichSeekbarWithProgress(42)));

        onView(withId(R.id.mdkRichSeekBar_withLabelAndError)).perform(MdkSeekBarAction.setMDKRichSeekbarProgress(-1))
                .check(matches(MdkSeekbarMatchers.mdkRichSeekbarWithProgress(0)));

        onView(withId(R.id.mdkRichSeekBar_min_42)).perform(MdkSeekBarAction.setMDKRichSeekbarProgress(150))
                .check(matches(MdkSeekbarMatchers.mdkRichSeekbarWithProgress(72)));

        onView(withId(R.id.mdkRichSeekBar_withLabelAndError)).perform(MdkSeekBarAction.setMDKRichSeekbarProgress(150))
                .check(matches(MdkSeekbarMatchers.mdkRichSeekbarWithProgress(100)));


        //by edittext input

        //TODO: TO BE FIXED - espresso doesn't match R.id.component_seekbarEditText inside R.id.mdkRichSeekBar_min_42: espresso bug?
        /*
        onView(allOf(withId(R.id.component_seekbar_edittext), isDescendantOfA(withId(R.id.mdkRichSeekBar_min_42)))).perform(typeText("5"));//caret goes before zero, so we only have to type the 5 to test 50
        onView(withId(R.id.mdkRichSeekBar_min_42)).check(matches(MdkSeekbarMatchers.mdkRichSeekbarWithProgress(50)));

        onView(allOf(withId(R.id.component_seekbar_edittext), isDescendantOfA(withId(R.id.mdkRichSeekBar_min_42)))).perform(clearText(), typeText("1"));//caret goes before zero, so we only have to type the 1 to test 10
        onView(withId(R.id.mdkRichSeekBar_min_42)).check(matches(MdkSeekbarMatchers.mdkRichSeekbarWithProgress(42)));

        onView(allOf(withId(R.id.component_seekbar_edittext), isDescendantOfA(withId(R.id.mdkRichSeekBar_min_42)))).perform(clearText(), typeText("100"));
        onView(withId(R.id.mdkRichSeekBar_min_42)).check(matches(MdkSeekbarMatchers.mdkRichSeekbarWithProgress(72)));
        */

        onView(allOf(withId(R.id.component_seekbar_edittext), isDescendantOfA(withId(R.id.mdkRichSeekBar_withLabelAndError)))).perform(clearText(), typeText("150"));
        onView(withId(R.id.mdkRichSeekBar_withLabelAndError)).check(matches(MdkSeekbarMatchers.mdkRichSeekbarWithProgress(100)));

    }

    @Override
    protected ActivityTestRule getActivity() {
        return mActivityRule;
    }



}

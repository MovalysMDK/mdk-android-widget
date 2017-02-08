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

import android.os.Build;
import android.support.test.espresso.action.ViewActions;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.soprasteria.movalysmdk.espresso.action.MdkSeekBarAction;
import com.soprasteria.movalysmdk.espresso.action.SpoonScreenshotAction;
import com.soprasteria.movalysmdk.espresso.matcher.MdkSeekbarMatchers;
import com.soprasteria.movalysmdk.widget.sample.factor.AbstractCommandWidgetTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
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
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SeekBarTest extends AbstractCommandWidgetTest {

    /**
     * Constructor.
     */
    public SeekBarTest() {
        super(SeekBarActivity.class);
    }

    /**
     * Add Permission used for this tests.
     */
    @Before
    public void grantWritePermission() {
        // In M+, trying to call a number will trigger a runtime dialog. Make sure
        // the permission is granted before running this test.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + getTargetContext().getPackageName()
                            + " android.permission.READ_EXTERNAL_STORAGE");
            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + getTargetContext().getPackageName()
                            + " android.permission.WRITE_INTERNAL_STORAGE");

            getInstrumentation().getUiAutomation().executeShellCommand(
                    "mkdir /storage/emulated/0/app_spoon-screenshots");
            getInstrumentation().getUiAutomation().executeShellCommand(
                    "mkdir /storage/emulated/0/app_spoon-screenshots/" + getTargetContext().getPackageName() + ".SeekBarTest");
            getInstrumentation().getUiAutomation().executeShellCommand(
                    "mkdir /storage/emulated/0/app_spoon-screenshots/" + getTargetContext().getPackageName() + ".SeekBarTest/testInvalidSeekBar");
        }
    }

    /**
     * Check MDK seekbar widget behaviour with invalid seekbar format.
     */
    @Test
    public void testInvalidSeekBar() {

    // Assertion that activity result is not null, nominal case
        assertThat(getActivityRule().getActivity(), is(notNullValue()));

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
        assertThat(getActivityRule().getActivity(), is(notNullValue()));

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

        this.getEnabledScenario().testDisableOutsideWidget(R.id.mdkRichSeekBar_withLabelAndError);
    }

    /**
     * Check MDK seekbar returned value is the actual expected value.
     */
    @Test
    public void testSeekbarValue(){
        // Assertion that activity result is not null, nominal case
        assertThat(getActivityRule().getActivity(), is(notNullValue()));

        //by seekbar input
        onView(withId(R.id.mdkRichSeekBar_withLabelAndError)).perform(MdkSeekBarAction.setMDKRichSeekbarProgress(50))
                .check(matches(MdkSeekbarMatchers.mdkRichSeekbarWithProgress(50)));

        onView(withId(R.id.mdkSeekBar_withErrorAndCommandOutside)).perform(MdkSeekBarAction.setMDKSeekbarProgress(42))
                .check(matches(MdkSeekbarMatchers.mdkSeekbarWithProgress(42)));


        //by edittext input
        onView(allOf(withId(R.id.component_seekbar_edittext), isDescendantOfA(withId(R.id.mdkRichSeekBar_withLabelAndError)))).perform(clearText(), typeText("10"));
        onView(withId(R.id.mdkRichSeekBar_withLabelAndError)).check(matches(MdkSeekbarMatchers.mdkRichSeekbarWithProgress(10)));

        onView(allOf(withId(R.id.component_seekbar_edittext), isDescendantOfA(withId(R.id.mdkRichSeekBar_withLabelAndError)))).perform(clearText(),typeText("hello"), pressImeActionButton());
        onView(withId(R.id.mdkRichSeekBar_withLabelAndError)).check(matches(MdkSeekbarMatchers.mdkRichSeekbarWithProgress(0)));

    }

    /**
     * Check MDK seekbar returns value in the min/max range.
     */
    @Test
    public void testSeekbarValueRange(){
        // Assertion that activity result is not null, nominal case
        assertThat(getActivityRule().getActivity(), is(notNullValue()));

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

        onView(allOf(withId(R.id.component_seekbar_edittext), isDescendantOfA(withId(R.id.mdkRichSeekBar_withLabelAndError)))).perform(clearText(), typeText("150"), pressImeActionButton());
        onView(withId(R.id.mdkRichSeekBar_withLabelAndError)).check(matches(MdkSeekbarMatchers.mdkRichSeekbarWithProgress(100)));

        onView(allOf(withId(R.id.component_seekbar_edittext), isDescendantOfA(withId(R.id.mdkRichSeekBar_min_42)))).perform(ViewActions.actionWithAssertions(ViewActions.scrollTo()));

        onView(allOf(withId(R.id.component_seekbar_edittext), isDescendantOfA(withId(R.id.mdkRichSeekBar_min_42)))).perform(clearText(), typeText("0"), pressImeActionButton());
        onView(withId(R.id.mdkRichSeekBar_min_42)).check(matches(MdkSeekbarMatchers.mdkRichSeekbarWithProgress(42)));

        onView(allOf(withId(R.id.component_seekbar_edittext), isDescendantOfA(withId(R.id.mdkRichSeekBar_min_42)))).perform(clearText(), typeText("100"), pressImeActionButton());
        onView(withId(R.id.mdkRichSeekBar_min_42)).check(matches(MdkSeekbarMatchers.mdkRichSeekbarWithProgress(72)));

    }

    /**
     * Check MDK seekbar displays value in correct format.
     */
    @Test
    public void testSeekbarFormatter() {
        // Assertion that activity result is not null, nominal case
        assertThat(getActivityRule().getActivity(), is(notNullValue()));

        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.mdkRichSeekBar_different_formatter)))).perform(ViewActions.actionWithAssertions(ViewActions.scrollTo()));

        //by seekbar input
        onView(withId(R.id.mdkRichSeekBar_different_formatter)).perform(MdkSeekBarAction.setMDKRichSeekbarProgress(5));
        onView(withId(R.id.mdkRichSeekBar_different_formatter)).check(matches(MdkSeekbarMatchers.mdkRichSeekbarWithDisplayedValue("Friday")));
    }

}

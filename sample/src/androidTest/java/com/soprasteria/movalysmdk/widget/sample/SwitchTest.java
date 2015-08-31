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
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.soprasteria.movalysmdk.espresso.action.DelayScrollToAction.delayScrollTo;
import static com.soprasteria.movalysmdk.espresso.action.OrientationChangeAction.orientationLandscape;
import static com.soprasteria.movalysmdk.espresso.action.OrientationChangeAction.orientationPortrait;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Non regression testing class for custom Mcheckedkable widget.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SwitchTest {

    /**
     * Activity used for this tests.
     */
    @Rule
    public ActivityTestRule<SwitchActivity> mActivityRule = new ActivityTestRule<>(SwitchActivity.class);

    /**
     * Check MDK switch widget behaviour with invalid checkable format.
     */
    @Test
    public void testSwitch() {
        testCheckable(R.id.mdkSwitch_withErrorAndCommandOutside, false, 3);
    }

    /**
     * Check MDK rich switch widget behaviour with invalid checkable format.
     */
    @Test
    public void testRichSwitch() {
        testCheckable(R.id.mdkRichSwitch_withLabelAndError, true, 4);
    }

    /**
     * Tests a checkable with the given identifier.
     * @param viewId the checkable identifier
     * @param isRich true if the component being tested is a Rich component
     * @param testCaseNumber the test case number
     */
    private void testCheckable(int viewId, boolean isRich, int testCaseNumber) {
        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // scroll to the tested view
        onView(withId(viewId)).perform(delayScrollTo());

        // Check not checked status of the view
        if (!isRich) {
            onView(withId(viewId)).check(matches(not(isChecked())));
        } else {
            onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(viewId)))).check(matches(not(isChecked())));
        }

        // Click the view
        onView(withId(viewId)).perform(click());

        // Check checked status of the view
        if (!isRich) {
            onView(withId(viewId)).check(matches(isChecked()));
        } else {
            onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(viewId)))).check(matches(isChecked()));
        }

        SpoonScreenshotAction.perform("checkable_checked_status" + testCaseNumber);

        /* ------ orientation  change -------- */

        onView(isRoot()).perform(orientationLandscape());

        // screenshot
        SpoonScreenshotAction.perform("checkable_checked_status_landscape" + testCaseNumber);

        // scroll to the tested view
        onView(withId(viewId)).perform(delayScrollTo());

        // check that the widget is still checked
        if (!isRich) {
            onView(withId(viewId)).check(matches(isChecked()));
        } else {
            onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(viewId)))).check(matches(isChecked()));
        }

        // Click the view
        onView(withId(viewId)).perform(scrollTo(), click());

        // check that the widget is not checked
        if (!isRich) {
            onView(withId(viewId)).check(matches(not(isChecked())));
        } else {
            onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(viewId)))).check(matches(not(isChecked())));
        }

        /* ------ orientation  change -------- */

        onView(isRoot()).perform(orientationPortrait());

        // scroll to the tested view
        onView(withId(viewId)).perform(delayScrollTo());

        // check that the widget is still not checked
        if (!isRich) {
            onView(withId(viewId)).check(matches(not(isChecked())));
        } else {
            onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(viewId)))).check(matches(not(isChecked())));
        }
    }

    /**
     * Check MDK switch widget behaviour when this one is enabled and disabled.
     */
    @Test
    public void testDisabledSwitch() {
        testDisabledCheckable(R.id.mdkSwitch_withErrorAndCommandOutside, false, 3);
    }

    /**
     * Check MDK rich switch widget behaviour when this one is enabled and disabled.
     */
    @Test
    public void testDisabledRichSwitch() {
        testDisabledCheckable(R.id.mdkRichSwitch_withLabelAndError, true, 4);
    }

    /**
     * Tests a checkable with the given identifier.
     * @param viewId the checkable identifier
     * @param isRich true if the component being tested is a Rich component
     * @param testCaseNumber the test case number
     */
    private void testDisabledCheckable(int viewId, boolean isRich, int testCaseNumber) {
        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // scroll to the tested view
        onView(withId(viewId)).perform(delayScrollTo());

        // check that the widget is not checked
        if (!isRich) {
            onView(withId(viewId)).check(matches(not(isChecked())));
        } else {
            onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(viewId)))).check(matches(not(isChecked())));
        }

        // Disable widgets
        onView(withId(R.id.enableButton)).perform(scrollTo(), click());

        // scroll to the tested view
        onView(withId(viewId)).perform(delayScrollTo());

        // Check widgets are disabled
        onView(withId(viewId)).check(matches(not(isEnabled())));

        // check that the widget is not checked
        if (!isRich) {
            onView(withId(viewId)).check(matches(not(isChecked())));
        } else {
            onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(viewId)))).check(matches(not(isChecked())));
        }

        /* ------ orientation  change -------- */

        onView(isRoot()).perform(orientationLandscape());

        // scroll to the tested view
        onView(withId(viewId)).perform(delayScrollTo());

        // Check widgets are disabled
        onView(withId(viewId)).check(matches(not(isEnabled())));

        // check that the widget is not checked
        if (!isRich) {
            onView(withId(viewId)).check(matches(not(isChecked())));
        } else {
            onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(viewId)))).check(matches(not(isChecked())));
        }

        /* ------ orientation  change -------- */

        onView(isRoot()).perform(orientationPortrait());

        // Click the view
        onView(withId(viewId)).perform(scrollTo(), click());

        // check that the widget is not checked
        if (!isRich) {
            onView(withId(viewId)).check(matches(not(isChecked())));
        } else {
            onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(viewId)))).check(matches(not(isChecked())));
        }

        // FIXME : ne marche pas
      /*  // Re enabled widget
        onView(withId(R.id.enableButton)).perform(scrollTo(), click());

        // scroll to the tested view
        onView(withId(viewId)).perform(delayScrollTo());

        // Check widgets are enabled
        onView(withId(viewId)).check(matches(isEnabled()));

        // Click the view
        onView(withId(viewId)).perform(scrollTo(), click());

        // check that the widget is not checked
        if (!isRich) {
            onView(withId(viewId)).check(matches(isChecked()));
        } else {
            onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(viewId)))).check(matches(isChecked()));
        }*/
    }

    /**
     * Check MDK switch widget behaviour when this one is checked and disabled.
     */
    @Test
    public void testDisabledCheckedSwitch() {
        testDisabledCheckedCheckable(R.id.mdkSwitch_withErrorAndCommandOutside, false, 3);
    }

    /**
     * Check MDK rich switch widget behaviour when this one is checked and disabled.
     */
    @Test
    public void testDisabledCheckedRichSwitch() {
        testDisabledCheckedCheckable(R.id.mdkRichSwitch_withLabelAndError, true, 4);
    }

    /**
     * Tests a checkable with the given identifier.
     * @param viewId the checkable identifier
     * @param isRich true if the component being tested is a Rich component
     * @param testCaseNumber the test case number
     */
    private void testDisabledCheckedCheckable(int viewId, boolean isRich, int testCaseNumber) {
        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // check that the widget is not checked
        onView(withId(viewId)).check(matches(not(isChecked())));

        // Click the view
        onView(withId(viewId)).perform(scrollTo(), click());

        // check that the widget is not checked
        if (!isRich) {
            onView(withId(viewId)).check(matches(isChecked()));
        } else {
            onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(viewId)))).check(matches(isChecked()));
        }

        // Disable widgets
        onView(withId(R.id.enableButton)).perform(scrollTo(), click());

        // Check widgets are disabled
        onView(withId(viewId)).check(matches(not(isEnabled())));

        // check that the widget is not checked
        if (!isRich) {
            onView(withId(viewId)).check(matches(isChecked()));
        } else {
            onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(viewId)))).check(matches(isChecked()));
        }

        /* ------ orientation  change -------- */

        onView(isRoot()).perform(orientationLandscape());

        // scroll to the tested view
        onView(withId(viewId)).perform(delayScrollTo());

        // Check widgets are disabled
        onView(withId(viewId)).check(matches(not(isEnabled())));

        // check that the widget is not checked
        if (!isRich) {
            onView(withId(viewId)).check(matches(isChecked()));
        } else {
            onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(viewId)))).check(matches(isChecked()));
        }

        /* ------ orientation  change -------- */

        onView(isRoot()).perform(orientationPortrait());

        // Click the view
        onView(withId(viewId)).perform(scrollTo(), click());

        // check that the widget is not checked
        if (!isRich) {
            onView(withId(viewId)).check(matches(isChecked()));
        } else {
            onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(viewId)))).check(matches(isChecked()));
        }

        // FIXME : ne marche pas
        /*// Re enabled widget
        onView(withId(R.id.enableButton)).perform(scrollTo(), click());

        // scroll to the tested view
        onView(withId(viewId)).perform(delayScrollTo());

        // Check widgets are enabled
        onView(withId(viewId)).check(matches(isEnabled()));

        // Click the view
        onView(withId(viewId)).perform(scrollTo(), click());

        // check that the widget is not checked
        if (!isRich) {
            onView(withId(viewId)).check(matches(not(isChecked())));
        } else {
            onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(viewId)))).check(matches(not(isChecked())));
        }*/
    }
}

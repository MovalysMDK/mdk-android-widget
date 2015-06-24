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
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.notNullValue;

import static com.soprasteria.movalysmdk.widget.test.actions.OrientationChangeAction.orientationLandscape;
import static com.soprasteria.movalysmdk.widget.test.actions.OrientationChangeAction.orientationPortrait;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RichEmailTest {

    @Rule
    public ActivityTestRule<EmailActivity> mActivityRule = new ActivityTestRule<>(EmailActivity.class);

    @Test
    public void testInvalidEmail() {
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // write invalid email
        onView(withId(R.id.mdkRichEmail_withLabelAndError)).perform(typeText("wrong format"));

        // click validate button
        onView(withId(R.id.validateButton)).perform(click());

        // check error
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichEmail_withLabelAndError))))
                .check(matches(withText("42 /!\\ Invalid email value")));

        SpoonScreenshotAction.perform("rightemail_invalidemail_errorstate");

        onView(isRoot()).perform(orientationLandscape());

        SpoonScreenshotAction.perform("rightemail_invalidemail_errorstate_landscape");

        //onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.view))))
        //        .check(matches(withText("42 /!\\ invalid email value")));

        onView(isRoot()).perform(orientationPortrait());

        //onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.view))))
        //        .check(matches(withText("42 /!\\ invalid email value")));
    }

    @Test
    public void testValidEmail() {

        // write valid email
        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.mdkRichEmail_withLabelAndError))))
                .perform(clearText());
        onView(withId(R.id.mdkRichEmail_withLabelAndError)).perform(typeText("contact@gmail.com"));

        // click validate button
        onView(withId(R.id.validateButton)).perform(click());

        // check no error
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichEmail_withLabelAndError))))
                .check(matches(withText(isEmptyOrNullString())));
    }
}

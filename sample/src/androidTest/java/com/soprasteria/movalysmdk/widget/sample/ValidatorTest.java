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
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.soprasteria.movalysmdk.espresso.action.SpoonScreenshotAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.soprasteria.movalysmdk.espresso.action.DelayScrollToAction.delayScrollTo;
import static com.soprasteria.movalysmdk.espresso.matcher.MdkViewMatchers.withConcatText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Test Custom validator on RichEditText widget.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ValidatorTest {

    /**
     * Activity to test.
     */
    @Rule
    public ActivityTestRule<ValidatorActivity> mActivityRule = new ActivityTestRule<>(ValidatorActivity.class);

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
                            + " android.permission.WRITE_INTERNAL_STORAGE");
        }
    }

    /**
     * Test empty value.
     */
    @Test
    public void testNotFilled() {
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // click validate button
        onView(withId(R.id.validateButton)).perform(click());

        // Take screenshot
        SpoonScreenshotAction.perform("customvalidator_emptyvalue");

        // check error
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichText_withCustomValidator))))
                .check(matches(withConcatText(R.string.test_fortyTwoTextFormater_prefix, R.string.test_mdkvalidator_mandatory_error_invalid)));

        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichText_nomandatory_withCustomValidator))))
                .check(matches(withText(isEmptyOrNullString())));
    }

    /**
     * Test valid value.
     */
    @Test
    public void testValidEntry() {
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // enter valid text (only characters)
        onView(withId(R.id.mdkRichText_withCustomValidator)).perform(typeText("valid entry"));
        onView(withId(R.id.mdkRichText_nomandatory_withCustomValidator)).perform(typeText("valid entry"));

        // click validate button
        onView(withId(R.id.validateButton)).perform(click());

        // Take screenshot
        SpoonScreenshotAction.perform("customvalidator_validvalue");

        // check no error
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichText_withCustomValidator))))
                .check(matches(withText(isEmptyOrNullString())));
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichText_nomandatory_withCustomValidator))))
                .check(matches(withText(isEmptyOrNullString())));
    }

    /**
     * Test invalid value.
     */
    @Test
    public void testInvalidEntry() {
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // enter valid text (only characters)
        onView(withId(R.id.mdkRichText_withCustomValidator)).perform(typeText("invalid entry 23"));
        onView(withId(R.id.mdkRichText_nomandatory_withCustomValidator)).perform(typeText("invalid 45 entry"));

        // click validate button
        onView(withId(R.id.validateButton)).perform(click());

        // Take screenshot
        SpoonScreenshotAction.perform("customvalidator_invalidvalue");

        // check no error
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichText_withCustomValidator))))
                .check(matches(withConcatText(R.string.test_fortyTwoTextFormater_prefix, R.string.test_no_number_allowed)));
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichText_nomandatory_withCustomValidator))))
                .check(matches(withConcatText(R.string.test_fortyTwoTextFormater_prefix, R.string.test_no_number_allowed)));
    }

    /**
     * Test checkbox value.
     */
    @Test
    public void testCheckbox() {
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // click validate button
        onView(withId(R.id.validateButton)).perform(click());

        // Take screenshot
        SpoonScreenshotAction.perform("customvalidator_validcheckbox");

        String error = mActivityRule.getActivity().getString(R.string.test_fortyTwoTextFormater_prefix)
            + mActivityRule.getActivity().getString(R.string.test_checkable_error) + " " + mActivityRule.getActivity().getString(R.string.test_true_text);

        // check no error
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichCheckbox_trueValidation))))
                .check(matches(withText(error)));
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichCheckbox_falseValidation))))
                .check(matches(withText(R.string.test_empty_string)));

        // click the checkboxes
        onView(withId(R.id.mdkRichCheckbox_falseValidation)).perform(delayScrollTo());

        onView(withId(R.id.mdkRichCheckbox_trueValidation)).perform(click());
        onView(withId(R.id.mdkRichCheckbox_falseValidation)).perform(click());

        // click validate button
        onView(withId(R.id.validateButton)).perform(delayScrollTo());
        onView(withId(R.id.validateButton)).perform(click());

        // Take screenshot
        SpoonScreenshotAction.perform("customvalidator_invalidcheckbox");

        error = mActivityRule.getActivity().getString(R.string.test_fortyTwoTextFormater_prefix)
                + mActivityRule.getActivity().getString(R.string.test_checkable_error) + " " + mActivityRule.getActivity().getString(R.string.test_false_text);

        // check no error
        onView(withId(R.id.mdkRichCheckbox_falseValidation)).perform(delayScrollTo());

        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichCheckbox_trueValidation))))
                .check(matches(withText(R.string.empty_string)));
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichCheckbox_falseValidation))))
                .check(matches(withText(error)));
    }

    /**
     * Test switch value.
     */
    @Test
    public void testSwitch() {
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // click validate button
        onView(withId(R.id.validateButton)).perform(click());

        // Take screenshot
        SpoonScreenshotAction.perform("customvalidator_validswitch");

        String error = mActivityRule.getActivity().getString(R.string.test_fortyTwoTextFormater_prefix)
                + mActivityRule.getActivity().getString(R.string.test_checkable_error) + " " + mActivityRule.getActivity().getString(R.string.test_true_text);

        // check no error
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichSwitch_trueValidation))))
                .check(matches(withText(error)));

        // click the switches
        onView(withId(R.id.mdkRichSwitch_trueValidation)).perform(ViewActions.actionWithAssertions(scrollTo()));

        onView(withId(R.id.mdkRichSwitch_trueValidation)).perform(click());

        // click validate button
        onView(withId(R.id.validateButton)).perform(ViewActions.actionWithAssertions(scrollTo()));
        onView(withId(R.id.validateButton)).perform(click());

        // Take screenshot
        SpoonScreenshotAction.perform("customvalidator_invalidswitch");

        // check no error
        onView(withId(R.id.mdkRichSwitch_trueValidation)).perform(delayScrollTo());

        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichSwitch_trueValidation))))
                .check(matches(withText(R.string.test_empty_string)));
    }

}

/**
 * Copyright (C) 2010 Sopra Steria Group (movalys.support@soprasteria.com)
 * <p/>
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

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.CloseKeyboardAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import com.soprasteria.movalysmdk.widget.test.espresso.actions.CustomScrollToAction;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.soprasteria.movalysmdk.widget.test.espresso.actions.CloseSoftKeyboardDelayAction.closeSoftKeyboardDelay;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import static com.soprasteria.movalysmdk.widget.test.espresso.matchers.MdkViewMatchers.withConcatText;
import static com.soprasteria.movalysmdk.widget.test.espresso.actions.CloseSoftKeyboardDelayAction.closeSoftKeyboardDelay;

/**
 * Non regression testing class for custom MDK EditText widget
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RichEditTextTest {

    /**
     * Rule to initialize EditTextActivity.
     */
    @Rule
    public ActivityTestRule<EditTextActivity> mActivityRule = new ActivityTestRule<>(EditTextActivity.class);

    /**
     * <p> Layout's Widget: MDKEditText with custom layout, label, hint.</p>
     * <p> 1 - Check label visibility to invisible.</p>
     * <p> 2 - Write text.</p>
     * <p> 3 - Check label visibility to visible.</p>
     */
    @Test
    public void testWithCustomLayout() {

        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // Scroll screen position to validate button
        onView(withId(is(R.id.validateButton))).perform(scrollTo());

        //Check that label is not visible yet
        onView(allOf(withId(R.id.component_label), isDescendantOfA(withId(R.id.mdkRichEditText_withCustomLayout))))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));

        // write text into RichEditText component
        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.mdkRichEditText_withCustomLayout))))
                .perform(typeText("Input text hides hint and label shows down"), closeSoftKeyboardDelay());

        // The label is now visible
        onView(allOf(withId(R.id.component_label), isDescendantOfA(withId(R.id.mdkRichEditText_withCustomLayout))))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    /**
     * <p> Layout's Widget: MDKEditText with style and a button to fill/clear text.</p>
     * <p> 0 - Check label visibility to INVISIBLE. </p>
     * <p> 1 - Click on "Remplir" button to automatically filled the field out.</p>
     * <p> 2 - Check label visibility to VISIBLE. </p>
     * <p> 3 - Click on "Vider" button to automatically reset the field.</p>
     * <p> 4 - Check label visibility to INVISIBLE.</p>
     */
    @Test
    public void testFillClear() {

        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // The label is now invisible
        onView(allOf(withId(R.id.component_label), isDescendantOfA(withId(R.id.mdkRichEditText_withCustomLayoutAndButton))))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));

        // click on "Remplir" button to automatically fill out the RichEditText component with "Hello"
        onView(withId(R.id.richedittext_button_remplir_effacer)).perform(click());

        // Check that "Hello" is well written into the RichEditText component
        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.mdkRichEditText_withCustomLayoutAndButton))))
                .check(matches(withText(R.string.hello_world)));

        // The label is now visible
        onView(allOf(withId(R.id.component_label), isDescendantOfA(withId(R.id.mdkRichEditText_withCustomLayoutAndButton))))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        // Re-click on "Vider" button to automatically reset the RichEditText component text
        onView(withId(R.id.richedittext_button_remplir_effacer)).perform(click());

        // The label is now invisible
        onView(allOf(withId(R.id.component_label), isDescendantOfA(withId(R.id.mdkRichEditText_withCustomLayoutAndButton))))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));

        // Check that the RichEditText component text is now empty
        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.mdkRichEditText_withCustomLayoutAndButton))))
                .check(matches(withText(R.string.empty_string)));

    }

    /**
     * <p>Layout's Widget: MDKRichEditText with label, mandatory, no hint.</p>
     * <p>1 - Click on "Validate" button to check that an error is raised when the mandatory field is empty and (*) info displaying. </p>
     * <p>2 - Click on "Validate" to check that no error is raised when the mandatory field is not empty.</p>
     * <p>3 - Click on "Mandatory" to change the field's mandatory attribute and (*) info displaying.</p>
     * <p>4 - Click on "Validate" button to check that no error is raised when the field is empty.</p>
     */
    @Test
    public void testWithLabelAndMandatory() {

        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // Check that a mandatory MDKEditText raises an error after clicking on validate without to have filled it out with text
        onView(withId(R.id.validateButton)).perform(click());

        // Check that hint value is the label name with the (*) for mandatory use
        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.mdkRichEditText_withLabelAndMandatory))))
                .check(matches(withHint("My label (*)")));

        // The error message is displayed
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichEditText_withLabelAndMandatory))))
                .check(matches(withConcatText(R.string.fortyTwoTextFormater_prefix, R.string.mdkwidget_mandatory_error)));

        // Write message into MDKRichEditText component
        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.mdkRichEditText_withLabelAndMandatory))))
                .perform(typeText("Input text"), closeSoftKeyboardDelay());

        // The label is now visible
        onView(allOf(withId(R.id.component_label), isDescendantOfA(withId(R.id.mdkRichEditText_withLabelAndMandatory))))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        // Check that hint value is the label name with the (*) for mandatory use
        onView(allOf(withId(R.id.component_label), isDescendantOfA(withId(R.id.mdkRichEditText_withLabelAndMandatory))))
                .check(matches(withText("My label")));

        // Use CustomScrollToAction to manage problem with Espresso (it does not take account of padding during scrollTo action).
        onView(withId(R.id.validateButton)).perform(ViewActions.actionWithAssertions(new CustomScrollToAction()), click());

        // Check that the MDKEditText component raises no error after validating it
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichEditText_withLabelAndMandatory))))
                .check(matches(withText(R.string.empty_string)));

        // Make MDKEditText not mandatory
        onView(withId(R.id.mandatoryButton)).perform(ViewActions.actionWithAssertions(new CustomScrollToAction()), click());

        // Empty the MDKEditText field
        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.mdkRichEditText_withLabelAndMandatory))))
                .perform(clearText(), closeSoftKeyboardDelay());

        // Check that hint value is the label name without the (*) for mandatory use
        onView(allOf(withId(R.id.component_label), isDescendantOfA(withId(R.id.mdkRichEditText_withLabelAndMandatory))))
                .check(matches(withText("My label")));

        // Make MDKEditText not mandatory
        onView(withId(R.id.validateButton)).perform(ViewActions.actionWithAssertions(new CustomScrollToAction()), click());

        // Check that the MDKEditText component raises no error after validating it
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichEditText_withLabelAndMandatory))))
                .check(matches(withText(R.string.empty_string)));

        // Reset mandatory state to true for later tests
        onView(withId(R.id.mandatoryButton)).perform(ViewActions.actionWithAssertions(new CustomScrollToAction()), click());

    }

    /**
     *  <p>Layout's Widget: MDKRichEditText without label and hint.</p>
     *  <p>1 - Check that the label and hint are not declared.</p>
     *  <p>2 - Despite the lacks of label and hint, MDKRichEditText is still writable.</p>
     *  <p>3 - Label still nonexistent.</p>
     */
    @Test
    public void testWithoutLabelAndHint() {

        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // Scroll screen position to validate button
        onView(withId(is(R.id.mdkRichEditText_withoutLabelAndHint))).perform(scrollTo());

        // Check that label does not exist
        onView(allOf(withId(R.id.component_label), isDescendantOfA(withId(R.id.mdkRichEditText_withoutLabelAndHint))))
                .check(doesNotExist());

        // Check that no hint are declared
        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.mdkRichEditText_withoutLabelAndHint))))
                .check(matches(withHint(R.string.empty_string)));

        // Write text into editText
        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.mdkRichEditText_withoutLabelAndHint))))
                .perform(typeText("Text is still writable and no label shows up"), closeSoftKeyboardDelay());

        // Check text into editText
        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.mdkRichEditText_withoutLabelAndHint))))
                .check(matches(withText("Text is still writable and no label shows up")));

        // Check that label does not exist
        onView(allOf(withId(R.id.component_label), isDescendantOfA(withId(R.id.mdkRichEditText_withoutLabelAndHint))))
                .check(doesNotExist());
    }

    /**
     * <p>Layout's Widget: MDKRichEditText no label, mandatory, with hint.</p>
     * <p>1 - Click on "Validate" button to check that an error is raised when the mandatory field is empty and (*) info displaying. </p>
     * <p>2 - Click on "Validate" to check that no error is raised when the mandatory field is not empty.</p>
     * <p>3 - Click on "Mandatory" to change the field's mandatory attribute and (*) info displaying.</p>
     * <p>4 - Click on "Validate" button to check that no error is raised when the field is empty.</p>
     */
    @Test
    public void testWithoutLabelButHint() {

        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // Scroll screen position to the label
        onView(withId(is(R.id.mdkRichEditText_withoutLabelButHint))).perform(scrollTo());

        // Check that label does not exist
        onView(allOf(withId(R.id.component_label), isDescendantOfA(withId(R.id.mdkRichEditText_withoutLabelButHint))))
                .check(doesNotExist());

        // Check that a mandatory MDKEditText raises an error after clicking on validate without to have filled it out with text
        onView(withId(R.id.validateButton)).perform(ViewActions.actionWithAssertions(new CustomScrollToAction()), click());

        // Scroll screen position to the label
        onView(withId(is(R.id.mdkRichEditText_withoutLabelButHint))).perform(scrollTo());

        // Check that the label got the (*) for mandatory use
        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.mdkRichEditText_withoutLabelButHint))))
                .check(matches(withHint("My hint (*)")));

        // The error message is displayed
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichEditText_withoutLabelButHint))))
                .check(matches(withConcatText(R.string.mdkwidget_mandatory_error, R.string.mdkwidget_mandatory_error)));

        // Write message into MDKRichEditText component
        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.mdkRichEditText_withoutLabelButHint))))
                .perform(typeText("Input user's text"), closeSoftKeyboardDelay());

        // Use CustomScrollToAction to manage problem with Espresso (it does not take account of padding during scrollTo action).
        onView(withId(R.id.validateButton)).perform(ViewActions.actionWithAssertions(new CustomScrollToAction()), click());

        // Scroll screen position to the label
        onView(withId(is(R.id.mdkRichEditText_withoutLabelButHint))).perform(scrollTo());

        // Check that the MDKEditText component raises no error after validating it
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichEditText_withoutLabelButHint))))
                .check(matches(withText(R.string.empty_string)));

        // Make MDKEditText not mandatory
        onView(withId(R.id.mandatoryButton)).perform(ViewActions.actionWithAssertions(new CustomScrollToAction()), click());

        // Scroll screen position to the label
        onView(withId(is(R.id.mdkRichEditText_withoutLabelButHint))).perform(scrollTo());

        // Empty the MDKEditText field
        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.mdkRichEditText_withoutLabelButHint))))
                .perform(clearText(), closeSoftKeyboardDelay());

        // Check that the label got no (*) for mandatory use
        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.mdkRichEditText_withoutLabelButHint))))
                .check(matches(withHint("My hint")));

        // Make MDKEditText not mandatory
        onView(withId(R.id.validateButton)).perform(ViewActions.actionWithAssertions(new CustomScrollToAction()), click());

        // Scroll screen position to the label
        onView(withId(is(R.id.mdkRichEditText_withoutLabelButHint))).perform(scrollTo());

        // Check that the MDKEditText component raises no error after validating it
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichEditText_withoutLabelButHint))))
                .check(matches(withText(R.string.empty_string)));
    }
}

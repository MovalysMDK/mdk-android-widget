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

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.soprasteria.movalysmdk.widget.sample.factor.AbstractCommandWidgetTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Non regression testing class for custom MDK EditText widget.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RichEditTextTest extends AbstractCommandWidgetTest {

    /**
     * Rule to initialize EditTextActivity.
     */
    @Rule
    public ActivityTestRule<EditTextActivity> mActivityRule = new ActivityTestRule<>(EditTextActivity.class);

    @Override
    protected ActivityTestRule getActivity() {
        return mActivityRule;
    }

    /**
     * Check MDK text widget behaviour with invalid text format.
     */
    @Test
    public void testInvalidText() {

        testTextEntryOutsideWidget(
                mActivityRule.getActivity().getString(R.string.test_empty_string),
                new int[]{R.string.test_mdkvalidator_mandatory_error_invalid},
                R.id.mdkEditText_withExternalLabelAndSharedError,
                0,
                R.id.sharedError,
                false
        );
    }

    /**
     * Check MDK text widget behaviour with valid text format.
     */
    @Test
    public void testValidText() {

        testTextEntryOutsideWidget(
                mActivityRule.getActivity().getString(R.string.test_hello_world),
                new int[]{R.string.test_empty_string},
                R.id.mdkEditText_withExternalLabelAndSharedError,
                0,
                R.id.sharedError,
                true
        );
    }

    /**
     * Check MDK rich text widget behaviour with valid text format.
     */
    @Test
    public void testRichTextWithLabelValidEntry() {
        testTextEntryRichWidget(
                mActivityRule.getActivity().getString(R.string.test_hello_world),
                new int[]{R.string.test_empty_string},
                R.id.mdkRichEditText_withLabelAndMandatory,
                0,
                true
        );
    }

    /**
     * Check MDK rich text widget behaviour with invalid text format.
     */
    @Test
    public void testRichTextWithLabelInvalidEntry() {
        testTextEntryRichWidget(
                mActivityRule.getActivity().getString(R.string.test_empty_string),
                new int[]{R.string.test_fortyTwoTextFormater_prefix, R.string.test_mdkvalidator_mandatory_error_invalid},
                R.id.mdkRichEditText_withLabelAndMandatory,
                0,
                false
        );
    }

    /**
     * Check MDK rich text with custom layout widget behaviour with valid text format.
     */
    @Test
    public void testRichTextWithCustomLayoutValidEntry() {
        testTextEntryRichWidget(
                mActivityRule.getActivity().getString(R.string.test_hello_world),
                new int[]{R.string.test_empty_string},
                R.id.mdkRichEditText_withCustomLayout,
                0,
                true
        );
    }

    /**
     * Check MDK rich text with custom layout widget behaviour with invalid text format.
     */
    @Test
    public void testRichTextWithCustomLayoutInvalidEntry() {
        testTextEntryRichWidget(
                mActivityRule.getActivity().getString(R.string.test_empty_string),
                new int[]{R.string.test_fortyTwoTextFormater_prefix, R.string.test_mdkvalidator_mandatory_error_invalid},
                R.id.mdkRichEditText_withCustomLayout,
                0,
                false
        );
    }

    /**
     * Check MDK rich edit with helper widget behaviour with valid entry.
     */
    @Test
    public void testRichEditWithHelperValidEntry() {
        testTextEntryRichWidget(
                mActivityRule.getActivity().getString(R.string.test_hello_world),
                new int[]{R.string.test_edit_rich_helper_text},
                R.id.mdkRichEditText_withHelper,
                0,
                true
        );
    }

    /**
     * Check MDK rich edit with helper widget behaviour with invalid entry.
     */
    @Test
    public void testRichEmailWithHelperInvalidEntry() {
        testTextEntryRichWidget(
                mActivityRule.getActivity().getString(R.string.test_empty_string),
                new int[]{R.string.test_edit_rich_helper_text},
                R.id.mdkRichEditText_withHelper,
                0,
                false
        );
    }

    /**
     * Check MDK rich text widget disability behaviour toggle.
     */
    @Test
    public void testDisableRichWidget() {
        testDisableRichWidget(R.id.mdkRichEditText_withLabelAndMandatory);
    }

    /**
     * Check MDK rich text with outside error widget disability behaviour toggle.
     */
    @Test
    public void testDisableOutsideWidget() {
        testDisableOutsideWidget(R.id.mdkEditText_withExternalLabelAndSharedError);
    }

    /**
     * Check MDK rich text widget mandatory behaviour toggle.
     */
    @Test
    public void testMandatoryRichWidget() {
        testMandatoryRichWidget(R.id.mdkRichEditText_withLabelAndMandatory, R.string.test_edit_text_label);
    }

    /**
     * Check MDK rich text with outside error widget mandatory behaviour toggle.
     */
    @Test
    public void testMandatoryOutsideWidget() {
        testMandatoryOutsideWidget(R.id.mdkEditText_withExternalLabelAndSharedError, R.string.test_edit_text_label);
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
                .check(matches(withText(R.string.test_hello_world)));

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
                .check(matches(withText(R.string.test_empty_string)));

    }

}

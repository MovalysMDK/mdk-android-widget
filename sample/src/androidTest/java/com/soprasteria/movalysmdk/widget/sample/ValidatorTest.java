package com.soprasteria.movalysmdk.widget.sample;

import android.support.test.rule.ActivityTestRule;

import com.soprasteria.movalysmdk.widget.test.espresso.actions.SpoonScreenshotAction;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.soprasteria.movalysmdk.widget.test.espresso.matchers.MdkViewMatchers.withConcatText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Test Custom validator on RichEditText widget.
 */
public class ValidatorTest {

    /**
     * Activity to test.
     */
    @Rule
    public ActivityTestRule<ValidatorActivity> mActivityRule = new ActivityTestRule<>(ValidatorActivity.class);

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
                .check(matches(withConcatText(R.string.fortyTwoTextFormater_prefix, R.string.mdkwidget_mandatory_error)));

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
                .check(matches(withConcatText(R.string.fortyTwoTextFormater_prefix, R.string.no_number_allowed)));
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichText_nomandatory_withCustomValidator))))
                .check(matches(withConcatText(R.string.fortyTwoTextFormater_prefix, R.string.no_number_allowed)));
    }

}

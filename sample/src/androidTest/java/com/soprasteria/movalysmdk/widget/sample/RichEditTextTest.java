package com.soprasteria.movalysmdk.widget.sample;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RichEditTextTest {

    @Rule
    public ActivityTestRule<EditTextActivity> mActivityRule = new ActivityTestRule<>(EditTextActivity.class);

    @Test
    public void RichEditTextTest() {

        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // write text into RichEditText component
        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.test_edit_style))))
                .perform(typeText("Input text hides hint and label show down"));

        // click on "Remplir" button to automatically fill out the RichEditText component with "Hello"
        onView(withId(R.id.richedittext_button_remplir_effacer)).perform(click());

        // Check that "Hello" is well written into the RichEditText component
        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.test_edit_style_2))))
                .check(matches(withText(R.string.hello_world)));

        // Re-click on "Vider" button to automatically reset the RichEditText component text
        onView(withId(R.id.richedittext_button_remplir_effacer)).perform(click());

        // Check that the RichEditText component text is now empty
        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.test_edit_style_2))))
                .check(matches(withText(R.string.empty_string)));

        // Check that a mandatory MDKEditText raises an error after clicking on validate without to have filled it out with text
        onView(withId(R.id.validateButton)).perform(click());


       // onView(withId(R.id.test_case_1)).check(matches(withText("42 /!\\ Must de filled")));
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.test_case_1))))
               .check(matches(withText("42 /!\\ Must be filled")));

        // Write message into MDKRichEditText component
        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.test_case_1))))
                .perform(typeText("Input text"));

        // Perform click on validation button
        onView(withId(R.id.validateButton)).perform(click());

        // Check that the MDKEditText component raises no error after validating it
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.test_case_1))))
              .check(matches(withText(R.string.empty_string)));


    }

}

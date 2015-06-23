package com.soprasteria.movalysmdk.widget.sample;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.CloseKeyboardAction;
import android.support.test.espresso.action.GeneralClickAction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Tap;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.util.HumanReadables;
import android.support.test.espresso.util.TreeIterables;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import com.soprasteria.movalysmdk.widget.test.actions.CustomScrollToAction;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RichEditTextTest {

    @Rule
    public ActivityTestRule<EditTextActivity> mActivityRule = new ActivityTestRule<>(EditTextActivity.class);

    @Test
    public void RichEditTextTest() {

        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        onView(withId(is(R.id.validateButton))).perform(scrollTo());

        // write text into RichEditText component
        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.mdkRichEditText_withCustomLayout))))
                .perform(typeText("Input text hides hint and label show down"), closeSoftKeyboard());


        // click on "Remplir" button to automatically fill out the RichEditText component with "Hello"
        onView(withId(R.id.richedittext_button_remplir_effacer)).perform(click());

        // Check that "Hello" is well written into the RichEditText component
        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.mdkRichEditText_withCustomLayoutAndButton))))
                .check(matches(withText(R.string.hello_world)));

        // Re-click on "Vider" button to automatically reset the RichEditText component text
        onView(withId(R.id.richedittext_button_remplir_effacer)).perform(click());

        // Check that the RichEditText component text is now empty
        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.mdkRichEditText_withCustomLayoutAndButton))))
                .check(matches(withText(R.string.empty_string)));

        // Check that a mandatory MDKEditText raises an error after clicking on validate without to have filled it out with text
        onView(withId(R.id.validateButton)).perform(click());



        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichEditText_withLabelAndMandatory))))
               .check(matches(withText("42 /!\\ Must be filled")));

        // Write message into MDKRichEditText component
        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.mdkRichEditText_withLabelAndMandatory))))
                .perform(typeText("Input text"), closeSoftKeyboard());

        //onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.mdkRichEditText_withCustomLayoutAndButton)))).perform(scrollTo());

        // TODO remove CustomcrollToAction if there is no paddind in xml file
        // Use CustomScrollToAction to managa problem with Espresso (it does not take account of padding during scrollTo action).
        onView(withId(R.id.validateButton)).perform(ViewActions.actionWithAssertions(new CustomScrollToAction()), click());

        // Check that the MDKEditText component raises no error after validating it
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichEditText_withLabelAndMandatory))))
              .check(matches(withText(R.string.empty_string)));

    }


    /**
     * Custom closeSoftKeyboard method.
     * Workaround to manage time delay to close softkeyboard bug.
     * @return ViewAction the view action
     */
    public static ViewAction closeSoftKeyboard() {
        return new ViewAction() {
            /**
             * The delay time to allow the soft keyboard to dismiss.
             */
            private static final long KEYBOARD_DISMISSAL_DELAY_MILLIS = 1000L;

            /**
             * The real {@link CloseKeyboardAction} instance.
             */
            private final ViewAction mCloseSoftKeyboard = new CloseKeyboardAction();

            @Override
            public Matcher<View> getConstraints() {
                return mCloseSoftKeyboard.getConstraints();
            }

            @Override
            public String getDescription() {
                return mCloseSoftKeyboard.getDescription();
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                mCloseSoftKeyboard.perform(uiController, view);
                uiController.loopMainThreadForAtLeast(KEYBOARD_DISMISSAL_DELAY_MILLIS);
            }
        };
    }
}

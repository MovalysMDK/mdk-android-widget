package com.soprasteria.movalysmdk.widget.sample.factor;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.soprasteria.movalysmdk.espresso.action.SpoonScreenshotAction;
import com.soprasteria.movalysmdk.widget.sample.R;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.soprasteria.movalysmdk.espresso.action.DelayScrollToAction.delayScrollTo;
import static com.soprasteria.movalysmdk.espresso.action.OrientationChangeAction.orientationLandscape;
import static com.soprasteria.movalysmdk.espresso.action.OrientationChangeAction.orientationPortrait;
import static com.soprasteria.movalysmdk.espresso.matcher.MdkViewMatchers.withConcatHint;
import static com.soprasteria.movalysmdk.espresso.matcher.MdkViewMatchers.withConcatText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Abstract implementation for Text base widget.
 */
public abstract class AbstractCommandTextTest {

    /**
     * Abstract method.
     * This class return the test activity.
     * @return the ActivityTestRule to test
     */
    protected abstract ActivityTestRule getActivity();


    /**
     * Method use to execute AbstractCommandTextTest#testEntrySenarioBasicWithRotation with a widget outside a RichWidget.
     * @param textInput the text input
     * @param errorMessages the error message reference
     * @param inputView  the input view reference
     * @param commandView the command view reference
     * @param errorView the error view reference
     * @param validEntry true if the input is a valid entry, false otherwise
     */
    public void testEntryOutsideWidget(String textInput, int[] errorMessages, @IdRes int inputView, @IdRes int commandView, @IdRes int errorView, boolean validEntry) {

        testEntrySenarioBasicWithRotation(
                textInput,
                errorMessages,
                withId(inputView),
                withId(commandView),
                withId(errorView),
                validEntry
        );

    }

    /**
     * Method use to execute AbstractCommandTextTest#testEntrySenarioBasicWithRotation with a RichWidget.
     * @param textInput the text input
     * @param errorMessages the error message reference
     * @param richWidgetView the rich widget reference
     * @param commandView the command view reference
     * @param validEntry true if the input is a valid entry, false otherwise
     */
    public void testEntryRichWidget(String textInput, int[] errorMessages, @IdRes int richWidgetView, @IdRes int commandView, boolean validEntry) {

        testEntrySenarioBasicWithRotation(
                textInput,
                errorMessages,
                allOf(withId(R.id.component_internal), isDescendantOfA(withId(richWidgetView))),
                commandView!=0?allOf(withId(commandView), isDescendantOfA(withId(richWidgetView))):null,
                allOf(withId(R.id.component_error), isDescendantOfA(withId(richWidgetView))),
                validEntry
        );

    }

    /**
     * Method use to execute AbstractCommandTextTest#testEntrySenarioBasicWithRotation with a widget outside a RichWidget.
     * @param textInput the text input
     * @param errorMessages the error message reference as a int[]
     * @param inputView  the input as Matcher&lt;view&gt;
     * @param commandView the command as Matcher&lt;view&gt;
     * @param errorView the error as Matcher&lt;view&gt;
     * @param validEntry true if the input is a valid entry, false otherwise
     */
    public void testEntrySenarioBasicWithRotation(String textInput, int[] errorMessages, Matcher<View> inputView, Matcher<View> commandView, Matcher<View> errorView, boolean validEntry) {
        ActivityTestRule mActivityRule = this.getActivity();

        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // Make scroll to
        onView(inputView).perform(ViewActions.actionWithAssertions(delayScrollTo()));

        // Write invalid email
        onView(inputView).perform(typeText(textInput));

        // Check send button state
        if (commandView != null) {
            if (validEntry) {
                onView(commandView).check(matches(
                        isEnabled()
                ));
            } else {
                onView(commandView).check(matches(
                        not(isEnabled())
                ));
            }
        }

        // click validate button
        onView(withId(R.id.validateButton)).perform(ViewActions.actionWithAssertions(delayScrollTo()), click());

        // Make scroll to
        onView(inputView).perform(ViewActions.actionWithAssertions(delayScrollTo()));

        // check error
        onView(errorView).check(matches(withConcatText(errorMessages)));

        // get value and check
        onView(inputView).check(matches(withText(textInput)));

        SpoonScreenshotAction.perform("email_invalidemail_errorstate");

        onView(isRoot()).perform(orientationLandscape());

        // Make scroll to
        onView(inputView).perform(ViewActions.actionWithAssertions(delayScrollTo()));

        // get value and check
        onView(inputView).check(matches(withText(textInput)));
        // Check send button state
        if (commandView != null) {
            if (validEntry) {
                onView(commandView).check(matches(
                        isEnabled()
                ));
            } else {
                onView(commandView).check(matches(
                        not(isEnabled())
                ));
            }
        }

        SpoonScreenshotAction.perform("email_invalidemail_errorstate_landscape");

        onView(errorView)
                .check(matches(withConcatText(errorMessages)));

        onView(isRoot()).perform(orientationPortrait());

        // Make scroll to
        onView(inputView).perform(ViewActions.actionWithAssertions(delayScrollTo()));

        // get value and check
        onView(inputView).check(matches(withText(textInput)));

        // get value and check
        onView(inputView).check(matches(withText(textInput)));
        // Check send button state
        if (commandView != null) {
            if (validEntry) {
                onView(commandView).check(matches(
                        isEnabled()
                ));
            } else {
                onView(commandView).check(matches(
                        not(isEnabled())
                ));
            }
        }

        onView(errorView)
                .check(matches(withConcatText(errorMessages)));
    }

    /**
     * Test the disable senario for widget outside RichWidget.
     * @param inputView the input view reference
     */
    public void testDisableOutsideWidget(int inputView) {
        testDisableSenarioBasicWithRotation(
                withId(inputView)
        );
    }

    /**
     * Test the disable senario for a RichWidget.
     * @param richWidgetView the input view reference
     */
    public void testDisableRichWidget(int richWidgetView) {
        testDisableSenarioBasicWithRotation(
                allOf(withId(R.id.component_internal), isDescendantOfA(withId(richWidgetView)))
        );
    }

    /**
     * Test the disable senario for a widget.
     * @param inputView the input view as Matcher&lt;View&gt;
     */
    public void testDisableSenarioBasicWithRotation(Matcher<View> inputView) {
        ActivityTestRule mActivityRule = this.getActivity();

        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // Disable widgets
        onView(withId(R.id.enableButton)).perform(click());

        // Check widgets are disabled
        onView(inputView).check(matches(not(isEnabled())));

        onView(isRoot()).perform(orientationLandscape());

        // Check widgets are disabled after rotate
        onView(inputView).check(matches(not(isEnabled())));

        // Re enabled widget
        onView(withId(R.id.enableButton)).perform(click());

        // Check widgets are enabled
        onView(inputView).check(matches(isEnabled()));

    }

    /**
     * Test the mandatory senario for widget outside RichWidget.
     * @param inputView the input view reference
     * @param stringRef the string reference to test
     */
    public void testMandatoryOutsideWidget(@IdRes int inputView, @StringRes int stringRef) {
        testMandatorywidget(
                withId(inputView),
                stringRef
        );
    }

    /**
     * Test the mandatory senario for widget as RichWidget.
     * @param inputView the input view reference
     * @param stringRef the string reference to test
     */
    public void testMandatoryRichWidget(@IdRes int inputView, @StringRes int stringRef) {
        testMandatorywidget(
                allOf(withId(R.id.component_internal), isDescendantOfA(withId(inputView))),
                stringRef
        );
    }

    /**
     * Test the mandatory senario for a widget.
     * @param inputView the input view as Matcher&lt;View&gt;
     * @param stringRef the string reference
     */
    public void testMandatorywidget(Matcher<View> inputView, @StringRes int stringRef) {
        ActivityTestRule mActivityRule = this.getActivity();

        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // Check widgets are mandatory
        onView(inputView)
                .check(matches(withConcatHint(stringRef, R.string.mdkrichselector_mandatory_label_char)));

        // remove mandatory option on widget
        onView(withId(R.id.mandatoryButton)).perform(click());

        // Check widgets are no more mandatory
        onView(inputView)
                .check(matches(withHint(stringRef)));
    }

}

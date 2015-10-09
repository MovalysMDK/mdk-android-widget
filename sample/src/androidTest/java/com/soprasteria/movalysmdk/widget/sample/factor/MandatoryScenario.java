package com.soprasteria.movalysmdk.widget.sample.factor;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.soprasteria.movalysmdk.widget.core.behavior.HasHint;
import com.soprasteria.movalysmdk.widget.core.behavior.HasLabel;
import com.soprasteria.movalysmdk.widget.sample.R;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.soprasteria.movalysmdk.espresso.matcher.MdkViewMatchers.withConcatHint;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Mandatory scenario testing class.
 * @param <T> class of the activity to test
 */
public class MandatoryScenario<T extends AppCompatActivity> extends AbstractScenario<T> {

    /**
     * Constructor.
     * @param mActivityRules the linked ActivityTestRule
     */
    public MandatoryScenario(ActivityTestRule<T> mActivityRules) {
        super(mActivityRules);
    }

    /**
     * Test the mandatory scenario for widget outside RichWidget.
     * @param inputView the input view reference
     * @param stringRef the string reference to test
     */
    public void testMandatoryOutsideWidget(@IdRes int inputView, @StringRes int stringRef) {
        testMandatoryWidget(
                withId(inputView),
                stringRef
        );
    }

    /**
     * Test the mandatory scenario for widget as RichWidget.
     * @param inputView the input view reference
     * @param stringRef the string reference to test
     */
    public void testMandatoryRichWidget(@IdRes int inputView, @StringRes int stringRef) {
        ActivityTestRule mActivityRule = this.getActivity();

        View view = mActivityRule.getActivity().findViewById(inputView);

        if (view instanceof HasHint) {
            testMandatoryWidget(
                    allOf(withId(R.id.component_internal), isDescendantOfA(withId(inputView))),
                    stringRef
            );
        } else if (view instanceof HasLabel) {
            testMandatoryWidget(
                    allOf(withId(R.id.component_label), isDescendantOfA(withId(inputView))),
                    stringRef
            );
        }
    }

    /**
     * Test the mandatory scenario for a widget.
     * @param inputView the input view as Matcher&lt;View&gt;
     * @param stringRef the string reference
     */
    public void testMandatoryWidget(Matcher<View> inputView, @StringRes int stringRef) {
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

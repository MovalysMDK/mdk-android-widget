package com.soprasteria.movalysmdk.widget.sample.factor;

import android.support.test.rule.ActivityTestRule;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.soprasteria.movalysmdk.widget.sample.R;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.soprasteria.movalysmdk.espresso.action.OrientationChangeAction.orientationLandscape;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Enable scenario testing class.
 * @param <T> class of the activity to test
 */
public class EnabledScenario<T extends AppCompatActivity> extends AbstractScenario<T> {

    /**
     * Constructor.
     * @param mActivityRule the linked ActivityTestRule
     */
    public EnabledScenario(ActivityTestRule<T> mActivityRule) {
        super(mActivityRule);
    }

    /**
     * Test the disable scenario for widget outside RichWidget.
     * @param inputView the input view reference
     */
    public void testDisableOutsideWidget(int inputView) {
        testDisableScenarioBasicWithRotation(
                withId(inputView)
        );
    }

    /**
     * Test the disable scenario for a RichWidget.
     * @param richWidgetView the input view reference
     */
    public void testDisableRichWidget(int richWidgetView) {
        testDisableScenarioBasicWithRotation(
                allOf(withId(R.id.component_internal), isDescendantOfA(withId(richWidgetView)))
        );
    }

    /**
     * Test the disable scenario for a widget.
     * @param inputView the input view as Matcher&lt;View&gt;
     */
    public void testDisableScenarioBasicWithRotation(Matcher<View> inputView) {
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
}

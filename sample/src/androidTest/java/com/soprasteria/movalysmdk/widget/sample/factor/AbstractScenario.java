package com.soprasteria.movalysmdk.widget.sample.factor;

import android.support.test.rule.ActivityTestRule;
import android.support.v7.app.AppCompatActivity;

import org.junit.Rule;

/**
 * Abstract scenario class.
 * @param <T> class of the activity to test
 */
public abstract class AbstractScenario<T extends AppCompatActivity> {

    /**
     * Rule to initialize EditTextActivity.
     */
    @Rule
    public ActivityTestRule<T> mActivityRule;

    /**
     * Constructor.
     * @param mActivityRule the linked ActivityTestRule
     */
    public AbstractScenario(ActivityTestRule<T> mActivityRule) {
        this.mActivityRule = mActivityRule;
    }

    /**
     * Abstract method.
     * This class return the test activity.
     * @return the ActivityTestRule to test
     */
    protected ActivityTestRule getActivity() {
        return this.mActivityRule;
    }

}

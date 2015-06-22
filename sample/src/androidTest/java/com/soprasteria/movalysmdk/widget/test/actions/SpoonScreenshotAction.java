package com.soprasteria.movalysmdk.widget.test.actions;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import com.squareup.spoon.Spoon;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsAnything;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;

/**
 * Espresso action to take screenshot.
 * <p>Spoon library is used to take the screenshot</p>
 */
public class SpoonScreenshotAction implements ViewAction {

    private final String tag;
    private final String testClass;
    private final String testMethod;

    /**
     * Constructor.
     * @param tag the tag
     * @param testClass the test class
     * @param testMethod the test method
     */
    public SpoonScreenshotAction(String tag, String testClass, String testMethod) {
        this.tag = tag;
        this.testClass = testClass;
        this.testMethod = testMethod;
    }

    @Override
    public Matcher<View> getConstraints() {
        return new IsAnything<View>();
    }

    @Override
    public String getDescription() {
        return "Taking a screenshot using spoon.";
    }

    @Override
    public void perform(UiController uiController, View view) {
        Spoon.screenshot(getActivity(view), tag, testClass, testMethod);
    }

    /**
     * Take screenshot.
     * <p>This must be called directly from your test method</p>
     * @param tag Unique tag to further identify the screenshot. Must match [a-zA-Z0-9_-]+.
     */
    public static void perform(String tag) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        String testClass = trace[3].getClassName();
        String testMethod = trace[3].getMethodName();
        onView(isRoot()).perform(new SpoonScreenshotAction(tag, testClass, testMethod));
    }

    /**
     * Get activity from view.
     * @param view view
     * @return activity linked to the view
     */
    private static Activity getActivity(View view) {
        Context context = view.getContext();
        while (!(context instanceof Activity)) {
            if (context instanceof ContextWrapper) {
                context = ((ContextWrapper) context).getBaseContext();
            } else {
                throw new IllegalStateException("Got a context of class "
                        + context.getClass()
                        + " and I don't know how to get the Activity from it");
            }
        }
        return (Activity) context;
    }
}

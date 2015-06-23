package com.soprasteria.movalysmdk.widget.test.actions;

import android.graphics.Rect;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.espresso.util.HumanReadables;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

/**
 * CustomScrollToAction class definition. Only used for test.
 * The displaying at least is changed from 90 to 1% for test.
 * A time delay has been added.
 * This class seems only necessary if we have a big view
 */
public class CustomScrollToAction implements ViewAction {

    private static final String TAG = CustomScrollToAction.class.getSimpleName();

    /**
     * Default constructor.
     */
    public CustomScrollToAction() {
    }

    /**
     * Constraints getter.
     * @return Matcher<View> Matching views
     */
    public Matcher<View> getConstraints() {
        return Matchers.allOf(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE), ViewMatchers.isDescendantOfA(Matchers.anyOf(ViewMatchers.isAssignableFrom(ScrollView.class), ViewMatchers.isAssignableFrom(HorizontalScrollView.class))));
    }

    /**
     * Perform scroll action to the view given as parameter.
     * @param uiController the ui controller
     * @param view the view to show (scroll to it)
     */
    public void perform(UiController uiController, View view) {
        if (ViewMatchers.isDisplayingAtLeast(1).matches(view)) {
            Log.i(TAG, "View is already displayed. Returning.");
        } else {
            Rect rect = new Rect();
            view.getDrawingRect(rect);
            rect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            if (!view.requestRectangleOnScreen(rect, true)) {
                Log.w(TAG, "Scrolling to view was requested, but none of the parents scrolled.");
            }

            // TODO check if it is necessary
            uiController.loopMainThreadForAtLeast(5000);

            if (!ViewMatchers.isDisplayingAtLeast(1).matches(view)) {
                throw (new PerformException.Builder()).withActionDescription(this.getDescription()).withViewDescription(HumanReadables.describe(view)).withCause(new RuntimeException("Scrolling to view was attempted, but the view is not displayed")).build();
            }
        }
    }

    /**
     * Description of the action.
     * @return String the description
     */
    public String getDescription() {
        return "scroll to";
    }
}

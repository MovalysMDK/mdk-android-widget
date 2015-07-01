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
package com.soprasteria.movalysmdk.widget.test.espresso.actions;

import android.graphics.Rect;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
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
 * <p>The displaying at least is changed from 90 to 1% for test.
 * A time delay has been added.
 * This class seems only necessary if we have a big view.</p>
 */
public class DelayScrollToAction implements ViewAction {

    /**
     * Log tag.
     */
    private static final String LOG_TAG = "DelayScrollToAction";

    /**
     * Default constructor.
     */
    private DelayScrollToAction() {
    }

    /**
     * Add a delay to the scroll action.
     * @return ViewAction
     */
    public static ViewAction delayScrollTo() {
        return ViewActions.actionWithAssertions(new DelayScrollToAction());
    }

    /**
     * Constraints getter.
     * @return Matcher<View> Matching views
     */
    @Override
    public Matcher<View> getConstraints() {
        return Matchers.allOf(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE), ViewMatchers.isDescendantOfA(Matchers.anyOf(ViewMatchers.isAssignableFrom(ScrollView.class), ViewMatchers.isAssignableFrom(HorizontalScrollView.class))));
    }

    /**
     * Perform scroll action to the view given as parameter.
     * @param uiController the ui controller
     * @param view the view to show (scroll to it)
     */
    @Override
    public void perform(UiController uiController, View view) {
        if (ViewMatchers.isDisplayingAtLeast(1).matches(view)) {
            Log.i(LOG_TAG, "View is already displayed. Returning.");
        } else {
            Rect rect = new Rect();
            view.getDrawingRect(rect);
            rect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            if (!view.requestRectangleOnScreen(rect, true)) {
                Log.w(LOG_TAG, "Scrolling to view was requested, but none of the parents scrolled.");
            }

            // TODO check if it is necessary
            uiController.loopMainThreadForAtLeast(2000);

            if (!ViewMatchers.isDisplayingAtLeast(1).matches(view)) {
                throw (new PerformException.Builder()).withActionDescription(this.getDescription()).withViewDescription(HumanReadables.describe(view)).withCause(new RuntimeException("Scrolling to view was attempted, but the view is not displayed")).build();
            }
        }
    }

    /**
     * Description of the action.
     * @return String the description
     */
    @Override
    public String getDescription() {
        return "scroll to";
    }
}

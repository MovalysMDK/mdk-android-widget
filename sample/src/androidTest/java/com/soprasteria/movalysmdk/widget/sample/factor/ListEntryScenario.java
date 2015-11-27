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
package com.soprasteria.movalysmdk.widget.sample.factor;

import android.support.annotation.IdRes;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.soprasteria.movalysmdk.widget.sample.AbstractFixedListActivity;
import com.soprasteria.movalysmdk.widget.sample.R;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.soprasteria.movalysmdk.espresso.action.MdkRecyclerViewAction.clickChildViewWithId;
import static com.soprasteria.movalysmdk.espresso.action.OrientationChangeAction.orientationLandscape;
import static com.soprasteria.movalysmdk.espresso.action.OrientationChangeAction.orientationPortrait;
import static com.soprasteria.movalysmdk.espresso.matcher.MdkRecyclerViewMatchers.withFixedListSize;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * List entry scenario testing class.
 * @param <T> class of the activity to test
 */
public class ListEntryScenario<T extends AppCompatActivity> extends AbstractScenario<T> {

    /** order of the Validate button in the resources array. */
    private static final int VALID = 0;

    /** order of the Cancel button in the resources array. */
    private static final int CANCEL = 1;

    /** order of the Delete button in the resources array. */
    private static final int DELETE = 2;

    /**
     * Constructor.
     * @param mActivityRule the linked ActivityTestRule
     */
    public ListEntryScenario(ActivityTestRule<T> mActivityRule) {
        super(mActivityRule);
    }

    /**
     * Method use to execute AbstractCommandWidgetTest#testEntryScenarioBasicWithRotation with a text widget outside a RichWidget.
     * @param textInput the text input
     * @param errorMessages the error message reference
     * @param listView  the input view reference
     * @param inputView the detail input view reference
     * @param addButtonView the add button view reference
     * @param errorView the error view reference
     */
    public void testTextEntryOutsideWidget(String textInput, int[] errorMessages, @IdRes int listView, @IdRes int inputView, @IdRes int addButtonView, @IdRes int errorView) {
        testListEntryScenarioBasicWithRotation(
                typeText(textInput),
                errorMessages,
                withId(listView),
                withId(inputView),
                new int [] {R.string.fixedlist_addDialog_validate, R.string.fixedlist_addDialog_cancel, R.id.delete_item},
                addButtonView != 0 ? withId(addButtonView) : null,
                withId(errorView)
        );
    }

    /**
     * Method use to execute AbstractCommandWidgetTest#testEntryScenarioBasicWithRotation with a text RichWidget.
     * @param textInput the text input
     * @param errorMessages the error message reference
     * @param richWidgetView the rich widget reference
     * @param inputView the detail input view reference
     * @param addButtonView the add button view reference
     */
    public void testTextEntryRichWidget(String textInput, int[] errorMessages, @IdRes int richWidgetView, @IdRes int inputView, @IdRes int addButtonView) {
        testListEntryScenarioBasicWithRotation(
                typeText(textInput),
                errorMessages,
                allOf(withId(R.id.component_internal), isDescendantOfA(withId(richWidgetView))),
                withId(inputView),
                new int [] {R.string.fixedlist_addDialog_validate, R.string.fixedlist_addDialog_cancel, R.id.delete_item},
                addButtonView != 0 ? allOf(withId(addButtonView), isDescendantOfA(withId(richWidgetView))) : null,
                allOf(withId(R.id.component_error), isDescendantOfA(withId(richWidgetView)))
                );
    }

    /**
     * Method use to execute AbstractCommandWidgetTest#testEntryScenarioBasicWithRotation with a widget outside a RichWidget.
     * @param action the action to perform on the view
     * @param errorMessages the error message reference as a int[]
     * @param listView the list view as Matcher&lt;view&gt;
     * @param inputView the detail input view as Matcher&lt;view&gt;
     * @param actionButtons the validate, cancel and delete buttons identifiers
     * @param commandView the command as Matcher&lt;view&gt;
     * @param errorView the error as Matcher&lt;view&gt;
     */
    public void testListEntryScenarioBasicWithRotation(
            ViewAction action, int[] errorMessages, Matcher<View> listView,
            Matcher<View> inputView, int[] actionButtons, Matcher<View> commandView, Matcher<View> errorView) {
        ActivityTestRule mActivityRule = this.getActivity();

        // Assertion that activity result is not null, nominal case
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // list should be empty
        onView(listView).check(ViewAssertions.matches(withFixedListSize(((AbstractFixedListActivity) mActivityRule.getActivity()).getDataset().length)));

        // click validate button
        onView(withId(R.id.validateButton)).perform(ViewActions.actionWithAssertions(scrollTo()), click());

        // change orientation to landscape
        onView(isRoot()).perform(orientationLandscape());

        // change orientation to portrait
        onView(isRoot()).perform(orientationPortrait());

        // click the add button
        onView(commandView).perform(scrollTo(), click());

        // check that the input view (ie the dialog) is displayed
        onView(inputView).check(matches(isDisplayed()));

        // input value
        onView(inputView).perform(action);

        // click the cancel button
        onView(withText(actionButtons[CANCEL])).perform(click());

        // list should be empty
        onView(listView).check(ViewAssertions.matches(withFixedListSize(((AbstractFixedListActivity) mActivityRule.getActivity()).getDataset().length)));

        // click the add button
        onView(commandView).perform(scrollTo(),click());

        // check that the input view (ie the dialog) is displayed
        onView(inputView).check(matches(isDisplayed()));

        // input value
        onView(inputView).perform(action);

        // click the cancel button
        onView(withText(actionButtons[VALID])).perform(click());

        // list should have one element
        onView(listView).check(ViewAssertions.matches(withFixedListSize(((AbstractFixedListActivity)mActivityRule.getActivity()).getDataset().length+1)));

        // click validate button
        onView(withId(R.id.validateButton)).perform(ViewActions.actionWithAssertions(scrollTo()), click());

        // check there are no error
        onView(errorView).check(matches(withText(R.string.test_empty_string)));

        // delete the element
        onView(listView).perform(scrollTo(),RecyclerViewActions.actionOnItemAtPosition(0, clickChildViewWithId(actionButtons[DELETE])));

        // list should be empty
        onView(listView).check(ViewAssertions.matches(withFixedListSize(((AbstractFixedListActivity)mActivityRule.getActivity()).getDataset().length)));
    }
}

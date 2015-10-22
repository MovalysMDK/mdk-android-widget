package com.soprasteria.movalysmdk.espresso.matcher;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Espresso matchers for RecyclerViews.
 */
public class MdkRecyclerViewMatchers {

    /**
     * Constructor.
     */
    private MdkRecyclerViewMatchers() {
        // nothing to do
    }

    /**
     * Creates a matcher to check if a RecyclerView has a given number of elements.
     * @param size the expected number of elements of the RecyclerView
     * @return matcher
     */
    public static Matcher<View> withFixedListSize(final int size) {
        return new TypeSafeMatcher<View>() {
            @Override public boolean matchesSafely (final View view) {
                return ((RecyclerView) view).getChildCount () == size;
            }

            @Override public void describeTo (final Description description) {
                description.appendText ("ListView should have " + size + " items");
            }
        };
    }

}

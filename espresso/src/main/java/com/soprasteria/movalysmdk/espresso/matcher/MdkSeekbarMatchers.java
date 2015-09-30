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
package com.soprasteria.movalysmdk.espresso.matcher;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;

import com.soprasteria.movalysmdk.widget.basic.MDKRichSeekBar;
import com.soprasteria.movalysmdk.widget.basic.MDKSeekBar;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Espresso matchers for checking seekbar.
 */
public class MdkSeekbarMatchers {

    /**
     * Constructor.
     */
    private MdkSeekbarMatchers() {
        // private because helper class.
    }

    /**
     * Creates a matcher to check the value of a MDKSeekbar component.
     * @param expectedProgress the seekbar value that was expected.
     * @return matcher.
     */
    public static Matcher<View> mdkSeekbarWithProgress(final int expectedProgress) {
        return new BoundedMatcher<View, MDKSeekBar>(MDKSeekBar.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText(String.valueOf(expectedProgress));
            }

            @Override
            public boolean matchesSafely(MDKSeekBar seekBar) {
                return seekBar.getSeekBarValue() == expectedProgress;
            }
        };
    }

    /**
     * Creates a matcher to check the value of a MDKRichSeekbar component.
     * @param expectedProgress the seekbar value that was expected.
     * @return matcher.
     */
    public static Matcher<View> mdkRichSeekbarWithProgress(final int expectedProgress) {
        return new BoundedMatcher<View, MDKRichSeekBar>(MDKRichSeekBar.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText(String.valueOf(expectedProgress));
            }

            @Override
            public boolean matchesSafely(MDKRichSeekBar seekBar) {
                return seekBar.getSeekBarValue() == expectedProgress;
            }
        };
    }

    /**
     * Creates a matcher to check the displayed value in the edittext of a MDKRichSeekbar component.
     * @param expectedString the seekbar displayed value that was expected.
     * @return matcher.
     */
    public static Matcher<View> mdkRichSeekbarWithDisplayedValue(final String expectedString) {
        return new BoundedMatcher<View, MDKRichSeekBar>(MDKRichSeekBar.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText(expectedString);
            }

            @Override
            public boolean matchesSafely(MDKRichSeekBar seekBar) {
                return seekBar.getAttachedEditText().getText().toString().equals(expectedString);
            }
        };
    }
}

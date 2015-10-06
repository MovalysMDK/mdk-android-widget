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
package com.soprasteria.movalysmdk.widget.sample;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.soprasteria.movalysmdk.widget.sample.factor.AbstractCommandWidgetTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.soprasteria.movalysmdk.espresso.action.OrientationChangeAction.orientationPortrait;
import static com.soprasteria.movalysmdk.espresso.matcher.MdkViewMatchers.withConcatText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Non regression testing class for custom MDK Position widget.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class PositionTest extends AbstractCommandWidgetTest {

    /**
     *  Rule to initialize PositionActivity.
     */
    @Rule
    public ActivityTestRule<PositionActivity> mActivityRule = new ActivityTestRule<>(PositionActivity.class);


    @Override
    protected ActivityTestRule getActivity() {
        return mActivityRule;
    }

    /**
     * Check MDK position widget behaviour with valid position.
     */
    @Test
    public void testValidPosition() {

        testMultiTextEntryOutsideWidget(
                new String[] {
                        mActivityRule.getActivity().getString(R.string.test_position_valid_latitude),
                        mActivityRule.getActivity().getString(R.string.test_position_valid_longitude)
                },
                new int[]{R.string.test_empty_string},
                R.id.mdkPosition_withErrorAndCommandOutside,
                new int[] {
                        R.id.component_internal_latitude,
                        R.id.component_internal_longitude
                },
                R.id.buttonPosition,
                R.id.commons_error,
                true
        );
    }

    /**
     * Check MDK position widget behaviour with invalid (empty) position.
     */
    @Test
    public void testInvalidPosition() {

        testMultiTextEntryOutsideWidget(
                new String[] {
                        mActivityRule.getActivity().getString(R.string.test_empty_string),
                        mActivityRule.getActivity().getString(R.string.test_empty_string)
                },
                new int[]{R.string.test_fortyTwoTextFormater_prefix, R.string.test_mdkvalidator_position_error_validation_mandatory},
                R.id.mdkPosition_withErrorAndCommandOutside,
                new int[] {
                        R.id.component_internal_latitude,
                        R.id.component_internal_longitude
                },
                R.id.buttonPosition,
                R.id.position_errorText,
                true
        );
    }

    /**
     * Check MDK rich position widget behaviour with valid position.
     */
    @Test
    public void testRichPositionWithLabelValidEntry() {
        testMultiTextEntryRichWidget(
                new String[] {
                        mActivityRule.getActivity().getString(R.string.test_position_valid_latitude),
                        mActivityRule.getActivity().getString(R.string.test_position_valid_longitude)
                },
                new int[]{R.string.test_empty_string},
                R.id.mdkRichPosition_locationWithLabelAndError,
                new int[] {
                        R.id.component_internal_latitude,
                        R.id.component_internal_longitude
                },
                R.id.component_mapButton,
                true
        );
    }

    /**
     * Check MDK rich position widget behaviour with invalid (empty) position.
     */
    @Test
    public void testRichPositionWithLabelInvalidEntry() {
        testMultiTextEntryRichWidget(
                new String[] {
                        mActivityRule.getActivity().getString(R.string.test_empty_string),
                        mActivityRule.getActivity().getString(R.string.test_empty_string)
                },
                new int[]{R.string.test_fortyTwoTextFormater_prefix, R.string.test_mdkvalidator_position_error_validation_mandatory},
                R.id.mdkRichPosition_locationWithLabelAndError,
                new int[] {
                        R.id.component_internal_latitude,
                        R.id.component_internal_longitude
                },
                R.id.component_mapButton,
                false
        );
    }

    /**
     * Check MDK rich position with custom layout widget with valid position.
     */
    @Test
    public void testRichPositionWithCustomLayoutValidEntry() {
        testMultiTextEntryRichWidget(
                new String[] {
                        mActivityRule.getActivity().getString(R.string.test_position_valid_latitude),
                        mActivityRule.getActivity().getString(R.string.test_position_valid_longitude)
                },
                new int[]{R.string.test_empty_string},
                R.id.mdkRichPosition_withCustomLayout,
                new int[] {
                        R.id.component_internal_latitude,
                        R.id.component_internal_longitude
                },
                R.id.mapButton,
                true
        );
    }

    /**
     * Check MDK rich position with custom layout widget with invalid (empty) position.
     */
    @Test
    public void testRichPositionWithCustomLayoutInvalidEntry() {
        testMultiTextEntryRichWidget(
                new String[] {
                        mActivityRule.getActivity().getString(R.string.test_empty_string),
                        mActivityRule.getActivity().getString(R.string.test_empty_string)
                },
                new int[]{R.string.test_fortyTwoTextFormater_prefix, R.string.test_mdkvalidator_position_error_validation_mandatory},
                R.id.mdkRichPosition_withCustomLayout,
                new int[] {
                        R.id.component_internal_latitude,
                        R.id.component_internal_longitude
                },
                R.id.mapButton,
                false
        );
    }

    /**
     * Check MDK rich position widget disability behaviour toggle.
     */
    @Test
    public void testDisableRichWidget() {
        testDisableRichWidget(R.id.mdkRichPosition_locationWithLabelAndError);
    }

    /**
     * Check MDK position with outside error widget disability behaviour toggle.
     */
    @Test
    public void testDisableOutsideWidget() {
        testDisableOutsideWidget(R.id.mdkPosition_withErrorAndCommandOutside);
    }

}

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

/**
 * Tests for MdkRichDate et MdkRichDateTime widgets.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class DateTest extends AbstractCommandWidgetTest {

    /**
     *  Rule to initialize DateActivity.
     */
    @Rule
    public ActivityTestRule<DateActivity> mActivityRule = new ActivityTestRule<>(DateActivity.class);


    @Override
    protected ActivityTestRule getActivity() {
        return mActivityRule;
    }

    /**
     * Check MDK datetime widget behaviour with valid date.
     */
    @Test
    public void testValidDate() {

        testDateEntryOutsideWidget(
                2015, 2, 2, 10, 30,
                new int[]{R.string.empty_string},
                R.id.mdkDateTime_withSharedError,
                R.id.mdkdatetime_errorText,
                true
        );
    }

    /**
     * Check MDK datetime widget behaviour with invalid (empty) date.
     */
    @Test
    public void testInvalidDate() {

        testEmptyDateEntryOutsideWidget(
                new int[]{R.string.fortyTwoTextFormater_prefix, R.string.mdkvalidator_mandatory_error_invalid},
                R.id.mdkDateTime_withSharedError,
                R.id.mdkdatetime_errorText,
                false
        );
    }

    /**
     * Check MDK datetime widget behaviour with valid date and range.
     */
    @Test
    public void testValidRangedDate() {

        testDateEntryOutsideWidget(
                2015, 6, 20, 10, 30,
                new int[]{R.string.empty_string},
                R.id.mdkDateTime_withLabelAndMandatoryAndMinDate,
                R.id.errorText2,
                true
        );
    }

    /**
     * Check MDK datetime widget behaviour with invalid date and range.
     */
    @Test
    public void testInvalidRangedDate() {

        testDateEntryOutsideWidget(
                2015, 1, 1, 10, 30,
                new int[]{R.string.fortyTwoTextFormater_prefix, R.string.mdkvalidator_datetimerange_error_range, R.string.test_date_error_ranged},
                R.id.mdkDateTime_withLabelAndMandatoryAndMinDate,
                R.id.errorText2,
                false
        );
    }

    /**
     * Check MDK rich datetime widget behaviour with valid date.
     */
    @Test
    public void testRichDateTimeWithLabelValidEntry() {
        testDateEntryRichWidget(
                2015, 2, 2, 10, 30,
                new int[]{R.string.empty_string},
                R.id.mdkRichDateTime_withLabelAndMandatory,
                true
        );
    }

    /**
     * Check MDK rich datetime widget behaviour with invalid (empty) date.
     */
    @Test
    public void testRichDateTimeWithLabelInvalidEntry() {
        testEmptyDateEntryRichWidget(
                new int[]{R.string.fortyTwoTextFormater_prefix, R.string.mdkvalidator_mandatory_error_invalid},
                R.id.mdkRichDateTime_withLabelAndMandatory,
                false
        );
    }

    /**
     * Check MDK rich datetime with custom layout widget with valid date.
     */
    @Test
    public void testRichDateTimeWithCustomLayoutValidEntry() {
        testDateEntryRichWidget(
                2015, 2, 2, 0, 0,
                new int[]{R.string.empty_string},
                R.id.mdkRichDateTime_withCustomLayout,
                true
        );
    }

    /**
     * Check MDK rich datetime with custom layout widget with invalid (empty) date.
     */
    @Test
    public void testRichDateTimeWithCustomLayoutInvalidEntry() {
        testEmptyDateEntryRichWidget(
                new int[]{R.string.fortyTwoTextFormater_prefix, R.string.mdkvalidator_mandatory_error_invalid},
                R.id.mdkRichDateTime_withCustomLayout,
                false
        );
    }

    /**
     * Check MDK rich datetime widget disability behaviour toggle.
     */
    @Test
    public void testDisableRichWidget() {
        testDisableRichWidget(R.id.mdkRichDateTime_withLabelAndMandatory);
    }

    /**
     * Check MDK rich datetime with outside error widget disability behaviour toggle.
     */
    @Test
    public void testDisableOutsideWidget() {
        testDisableOutsideWidget(R.id.mdkDateTime_withSharedError);
    }

    /**
     * Check MDK rich datetime widget mandatory behaviour toggle.
     */
    @Test
    public void testMandatoryRichWidget() {
        testMandatoryRichWidget(R.id.mdkRichDateTime_withLabelAndMandatory, R.string.app_name);
    }

    /**
     * Check MDK rich datetime with outside error widget mandatory behaviour toggle.
     */
    @Test
    public void testMandatoryOutsideWidget() {
        testMandatoryOutsideWidget(R.id.mdkDateTime_withSharedError, R.string.default_date_hint_text);
    }

}

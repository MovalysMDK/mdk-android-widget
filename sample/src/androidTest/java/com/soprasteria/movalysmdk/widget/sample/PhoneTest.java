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

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.soprasteria.movalysmdk.widget.sample.factor.AbstractCommandWidgetTest;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Non regression testing class for custom MDK phone widget.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class PhoneTest extends AbstractCommandWidgetTest {

    /** Valid phone number. */
    private static final String PHONE_VALUE = "0123456789";

    /** Invalid phone number. */
    private static final String PHONE_INVALID_VALUE = "0+0+1+1+2";

    /**
     * Constructor.
     */
    public PhoneTest() {
        super(PhoneActivity.class);
    }

    /**
     * Check MDK phone widget behaviour with invalid phone format.
     */
    @Test
    public void testInvalidPhone() {

        this.getEntryScenario().testTextEntryOutsideWidget(
                PHONE_INVALID_VALUE,
                new int[]{R.string.test_fortyTwoTextFormater_prefix, R.string.test_mdkvalidator_phone_error_invalid},
                R.id.mdkPhone_withErrorAndCommandOutside,
                R.id.buttonCall,
                R.id.mdkphone_errorText,
                false
        );
    }

    /**
     * Check MDK phone widget behaviour with valid phone format.
     */
    @Test
    public void testValidPhone() {

        this.getEntryScenario().testTextEntryOutsideWidget(
                PHONE_VALUE,
                new int[]{R.string.test_empty_string},
                R.id.mdkPhone_withErrorAndCommandOutside,
                R.id.buttonCall,
                R.id.mdkphone_errorText,
                true
        );
    }

    /**
     * Check MDK rich phone widget behaviour with valid phone format.
     */
    @Test
    public void testRichPhoneWithLabelValidEntry() {
        this.getEntryScenario().testTextEntryRichWidget(
                PHONE_VALUE,
                new int[]{R.string.test_empty_string},
                R.id.mdkRichPhone_withLabelAndError,
                R.id.component_phoneButton,
                true
        );
    }

    /**
     * Check MDK rich phone widget behaviour with invalid phone format.
     */
    @Test
    public void testRichPhoneWithLabelInvalidEntry() {
        this.getEntryScenario().testTextEntryRichWidget(
                PHONE_INVALID_VALUE,
                new int[]{R.string.test_fortyTwoTextFormater_prefix, R.string.test_mdkvalidator_phone_error_invalid},
                R.id.mdkRichPhone_withLabelAndError,
                R.id.component_phoneButton,
                false
        );
    }

    /**
     * Check MDK rich phone with custom layout widget behaviour with valid phone format.
     */
    @Test
    public void testRichPhoneWithCustomLayoutValidEntry() {
        this.getEntryScenario().testTextEntryRichWidget(
                PHONE_VALUE,
                new int[]{R.string.test_empty_string},
                R.id.mdkRichPhone_withCustomLayout,
                0,
                true
        );
    }

    /**
     * Check MDK rich phone with custom layout widget behaviour with invalid phone format.
     */
    @Test
    public void testRichPhoneWithCustomLayoutInvalidEntry() {
        this.getEntryScenario().testTextEntryRichWidget(
                PHONE_INVALID_VALUE,
                new int[]{R.string.test_fortyTwoTextFormater_prefix, R.string.test_mdkvalidator_phone_error_invalid},
                R.id.mdkRichPhone_withCustomLayout,
                0,
                false
        );
    }

    /**
     * Check MDK rich phone with helper widget behaviour with valid phone format.
     */
    @Test
    public void testRichPhoneWithHelperValidEntry() {
        this.getEntryScenario().testTextEntryRichWidget(
                PHONE_VALUE,
                new int[]{R.string.test_phone_helper_text},
                R.id.mdkRichPhone_withHelper,
                R.id.component_phoneButton,
                true
        );
    }

    /**
     * Check MDK rich phone with helper widget behaviour with invalid phone format.
     */
    @Test
    public void testRichPhoneWithHelperInvalidEntry() {
        this.getEntryScenario().testTextEntryRichWidget(
                PHONE_INVALID_VALUE,
                new int[]{R.string.test_fortyTwoTextFormater_prefix, R.string.test_mdkvalidator_phone_error_invalid},
                R.id.mdkRichPhone_withHelper,
                R.id.component_phoneButton,
                false
        );
    }

    /**
     * Check MDK rich phone widget disability behaviour toggle.
     */
    @Test
    public void testDisableRichWidget() {
        this.getEnabledScenario().testDisableRichWidget(R.id.mdkRichPhone_withLabelAndError);
    }

    /**
     * Check MDK rich phone with outside error widget disability behaviour toggle.
     */
    @Test
    public void testDisableOutsideWidget() {
        this.getEnabledScenario().testDisableOutsideWidget(R.id.mdkPhone_withErrorAndCommandOutside);
    }

    /**
     * Check MDK rich phone widget mandatory behaviour toggle.
     */
    @Test
    public void testMandatoryRichWidget() {
        this.getMandatoryScenario().testMandatoryRichWidget(R.id.mdkRichPhone_withLabelAndError, R.string.test_app_name);
    }

    /**
     * Check MDK rich phone with outside error widget mandatory behaviour toggle.
     */
    @Test
    public void testMandatoryOutsideWidget() {
        this.getMandatoryScenario().testMandatoryOutsideWidget(R.id.mdkPhone_withErrorAndCommandOutside, R.string.test_testHintText);
    }
}

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
 * Non regression testing class for custom MDK email widget.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class EmailTest extends AbstractCommandWidgetTest {

    /**
     * Valid input.
     */
    private static final String EMAIL_VALUE = "myemail@soprasteria.com";

    /**
     * invalid input.
     */
    private static final String EMAIL_INVALID_VALUE = "wrong format";

    /**
     * Constructor.
     */
    public EmailTest() {
        super(EmailActivity.class);
    }

    /**
     * Check MDK email widget behaviour with invalid email format.
     */
    @Test
    public void testInvalidEmail() {

        this.getEntryScenario().testTextEntryOutsideWidget(
                EMAIL_INVALID_VALUE,
                new int[]{R.string.test_fortyTwoTextFormater_prefix, R.string.test_mdkvalidator_email_error_invalid},
                R.id.mdkEmail_withErrorAndCommandOutside,
                R.id.buttonSend,
                R.id.mdkemail_errorText,
                false
        );
    }

    /**
     * Check MDK email widget behaviour with valid email format.
     */
    @Test
    public void testValidEmail() {

        this.getEntryScenario().testTextEntryOutsideWidget(
                EMAIL_VALUE,
                new int[]{R.string.test_empty_string},
                R.id.mdkEmail_withErrorAndCommandOutside,
                R.id.buttonSend,
                R.id.mdkemail_errorText,
                true
        );
    }

    /**
     * Check MDK rich email widget behaviour with valid email format.
     */
    @Test
    public void testRichEmailWithLabelValidEntry() {
        this.getEntryScenario().testTextEntryRichWidget(
                EMAIL_VALUE,
                new int[]{R.string.test_empty_string},
                R.id.mdkRichEmail_withLabelAndError,
                R.id.component_emailButton,
                true
        );
    }

    /**
     * Check MDK rich email widget behaviour with invalid email format.
     */
    @Test
    public void testRichEmailWithLabelInvalidEntry() {
        this.getEntryScenario().testTextEntryRichWidget(
                EMAIL_INVALID_VALUE,
                new int[]{R.string.test_fortyTwoTextFormater_prefix, R.string.test_mdkvalidator_email_error_invalid},
                R.id.mdkRichEmail_withLabelAndError,
                R.id.component_emailButton,
                false
        );
    }

    /**
     * Check MDK rich email with custom layout widget behaviour with valid email format.
     */
    @Test
    public void testRichEmailWithCustomLayoutValidEntry() {
        this.getEntryScenario().testTextEntryRichWidget(
                EMAIL_VALUE,
                new int[]{R.string.test_empty_string},
                R.id.mdkRichEmail_withCustomLayout,
                0,
                true
        );
    }

    /**
     * Check MDK rich email with custom layout widget behaviour with invalid email format.
     */
    @Test
    public void testRichEmailWithCustomLayoutInvalidEntry() {
        this.getEntryScenario().testTextEntryRichWidget(
                EMAIL_INVALID_VALUE,
                new int[]{R.string.test_fortyTwoTextFormater_prefix, R.string.test_mdkvalidator_email_error_invalid},
                R.id.mdkRichEmail_withCustomLayout,
                0,
                false
        );
    }

    /**
     * Check MDK rich email with helper widget behaviour with valid email format.
     */
    @Test
    public void testRichEmailWithHelperValidEntry() {
        this.getEntryScenario().testTextEntryRichWidget(
                EMAIL_VALUE,
                new int[]{R.string.test_email_helper_text},
                R.id.mdkRichEmail_withHelper,
                R.id.component_emailButton,
                true
        );
    }

    /**
     * Check MDK rich email with helper widget behaviour with invalid email format.
     */
    @Test
    public void testRichEmailWithHelperInvalidEntry() {
        this.getEntryScenario().testTextEntryRichWidget(
                EMAIL_INVALID_VALUE,
                new int[]{R.string.test_fortyTwoTextFormater_prefix, R.string.test_mdkvalidator_email_error_invalid},
                R.id.mdkRichEmail_withHelper,
                R.id.component_emailButton,
                false
        );
    }

    /**
     * Check MDK rich email widget disability behaviour toggle.
     */
    @Test
    public void testDisableRichWidget() {
        this.getEnabledScenario().testDisableRichWidget(R.id.mdkRichEmail_withLabelAndError);
    }

    /**
     * Check MDK rich email with outside error widget disability behaviour toggle.
     */
    @Test
    public void testDisableOutsideWidget() {
        this.getEnabledScenario().testDisableOutsideWidget(R.id.mdkEmail_withErrorAndCommandOutside);
    }

    /**
     * Check MDK rich email widget mandatory behaviour toggle.
     */
    @Test
    public void testMandatoryRichWidget() {
        this.getMandatoryScenario().testMandatoryRichWidget(R.id.mdkRichEmail_withLabelAndError, R.string.test_app_name);
    }

    /**
     * Check MDK rich email with outside error widget mandatory behaviour toggle.
     */
    @Test
    public void testMandatoryOutsideWidget() {
        this.getMandatoryScenario().testMandatoryOutsideWidget(R.id.mdkEmail_withErrorAndCommandOutside, R.string.test_testHintText);
    }

}

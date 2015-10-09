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

import com.soprasteria.movalysmdk.widget.sample.factor.AbstractCommandWidgetTest;

import org.junit.Test;

/**
 * Tests for MdkRichUri widget.
 */
public class UriTest extends AbstractCommandWidgetTest {

    /** Valid input. */
    private static final String URI_VALUE = "www.google.com";

    /** Valid full input. */
    private static final String URI_VALUE_PREFIX = "http://";

    /** Invalid input. */
    private static final String URI_INVALID_VALUE = "invalid uri";

    /**
     * Constructor.
     */
    public UriTest() {
        super(UriActivity.class);
    }

    /**
     * Check MDK uri widget behaviour with invalid uri format.
     */
    @Test
    public void testInvalidUri() {

        this.getEntryScenario().testTextEntryOutsideWidget(
                URI_INVALID_VALUE,
                new int[]{R.string.test_fortyTwoTextFormater_prefix, R.string.test_mdkvalidator_uri_error_invalid},
                R.id.mdkUri_withErrorAndCommandOutside,
                R.id.buttonNavigate,
                R.id.mdkuri_errorText,
                false
        );
    }

    /**
     * Check MDK uri widget behaviour with valid uri format.
     */
    @Test
    public void testValidUri() {

        this.getEntryScenario().testTextEntryOutsideWidget(
                URI_VALUE_PREFIX + URI_VALUE,
                new int[]{R.string.test_empty_string},
                R.id.mdkUri_withErrorAndCommandOutside,
                R.id.buttonNavigate,
                R.id.mdkuri_errorText,
                true
        );
    }

    /**
     * Check MDK rich uri widget behaviour with valid uri format.
     */
    @Test
    public void testRichUriWithLabelValidEntry() {
        this.getEntryScenario().testTextEntryRichUriWidget(
                URI_VALUE,
                URI_VALUE_PREFIX + URI_VALUE,
                new int[]{R.string.test_empty_string},
                R.id.mdkRichUri_withLabelAndError,
                R.id.component_uriButton,
                true
        );
    }

    /**
     * Check MDK rich uri widget behaviour with invalid uri format.
     */
    @Test
    public void testRichUriWithLabelInvalidEntry() {
        this.getEntryScenario().testTextEntryRichUriWidget(
                URI_INVALID_VALUE,
                URI_VALUE_PREFIX + URI_INVALID_VALUE,
                new int[]{R.string.test_fortyTwoTextFormater_prefix, R.string.test_mdkvalidator_uri_error_invalid},
                R.id.mdkRichUri_withLabelAndError,
                R.id.component_uriButton,
                false
        );
    }

    /**
     * Check MDK rich uri with custom layout widget behaviour with valid uri format.
     */
    @Test
    public void testRichUriWithCustomLayoutValidEntry() {
        this.getEntryScenario().testTextEntryRichWidget(
                URI_VALUE_PREFIX + URI_VALUE,
                new int[]{R.string.test_empty_string},
                R.id.mdkRichUri_withCustomLayout,
                0,
                true
        );
    }

    /**
     * Check MDK rich uri with custom layout widget behaviour with invalid uri format.
     */
    @Test
    public void testRichUriWithCustomLayoutInvalidEntry() {
        this.getEntryScenario().testTextEntryRichWidget(
                URI_INVALID_VALUE,
                new int[]{R.string.test_fortyTwoTextFormater_prefix, R.string.test_mdkvalidator_uri_error_invalid},
                R.id.mdkRichUri_withCustomLayout,
                0,
                false
        );
    }

    /**
     * Check MDK rich uri with helper widget behaviour with valid uri format.
     */
    @Test
    public void testRichUriWithHelperValidEntry() {
        this.getEntryScenario().testTextEntryRichUriWidget(
                URI_VALUE,
                URI_VALUE_PREFIX + URI_VALUE,
                new int[]{R.string.test_uri_helper_text},
                R.id.mdkRichUri_withHelper,
                R.id.component_uriButton,
                true
        );
    }

    /**
     * Check MDK rich uri with helper widget behaviour with invalid uri format.
     */
    @Test
    public void testRichUriWithHelperInvalidEntry() {
        this.getEntryScenario().testTextEntryRichUriWidget(
                URI_INVALID_VALUE,
                URI_VALUE_PREFIX + URI_INVALID_VALUE,
                new int[]{R.string.test_fortyTwoTextFormater_prefix, R.string.test_mdkvalidator_uri_error_invalid},
                R.id.mdkRichUri_withHelper,
                R.id.component_uriButton,
                false
        );
    }

    /**
     * Check MDK rich uri widget disability behaviour toggle.
     */
    @Test
    public void testDisableRichWidget() {
        this.getEnabledScenario().testDisableRichWidget(R.id.mdkRichUri_withLabelAndError);
    }

    /**
     * Check MDK rich uri with outside error widget disability behaviour toggle.
     */
    @Test
    public void testDisableOutsideWidget() {
        this.getEnabledScenario().testDisableOutsideWidget(R.id.mdkUri_withErrorAndCommandOutside);
    }

    /**
     * Check MDK rich uri widget mandatory behaviour toggle.
     */
    @Test
    public void testMandatoryRichWidget() {
        this.getMandatoryScenario().testMandatoryRichWidget(R.id.mdkRichUri_withLabelAndError, R.string.test_app_name);
    }

    /**
     * Check MDK rich uri with outside error widget mandatory behaviour toggle.
     */
    @Test
    public void testMandatoryOutsideWidget() {
        this.getMandatoryScenario().testMandatoryOutsideWidget(R.id.mdkUri_withErrorAndCommandOutside, R.string.test_testHintText);
    }
}

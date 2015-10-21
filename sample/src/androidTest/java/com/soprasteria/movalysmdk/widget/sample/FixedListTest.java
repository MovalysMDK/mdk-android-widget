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
 * Non regression testing class for custom MDK FixedList widget.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class FixedListTest extends AbstractCommandWidgetTest {

    /**
     * Constructor.
     */
    public FixedListTest() {
        super(WrappedFixedListActivity.class);
    }

    /**
     * Check MDK FixedList widget behaviour.
     */
    @Test
    public void testEntry() {
        this.getListEntryScenario().testTextEntryOutsideWidget(
                getActivityRule().getActivity().getString(R.string.test_fixedlist_entry),
                new int[]{R.string.test_fortyTwoTextFormater_prefix, R.string.test_mdkvalidator_mandatory_error_invalid},
                R.id.mdkFixedList,
                R.id.item_value,
                R.id.addButton,
                R.id.fixedlist_error
        );
    }

    /**
     * Check MDK rich FixedList widget behaviour.
     */
    @Test
    public void testRichFixedListWithLabelEntry() {
        this.getListEntryScenario().testTextEntryRichWidget(
                getActivityRule().getActivity().getString(R.string.test_fixedlist_entry),
                new int[]{R.string.test_fortyTwoTextFormater_prefix, R.string.test_mdkvalidator_mandatory_error_invalid},
                R.id.mdkRichFixedList,
                R.id.item_value,
                R.id.component_addButton
        );
    }

    /**
     * Check MDK rich FixedList widget disability behaviour toggle.
     */
    @Test
    public void testDisableRichWidget() {
        this.getEnabledScenario().testDisableRichWidget(R.id.mdkRichFixedList);
    }

    /**
     * Check MDK FixedList with outside error widget disability behaviour toggle.
     */
    @Test
    public void testDisableOutsideWidget() {
        this.getEnabledScenario().testDisableOutsideWidget(R.id.mdkFixedList);
    }

}

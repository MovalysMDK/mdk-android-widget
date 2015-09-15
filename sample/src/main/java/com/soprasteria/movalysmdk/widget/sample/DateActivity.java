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

import android.os.Bundle;

/**
 * Test activity for the date widget.
 */
public class DateActivity extends AbstractWidgetTestableActivity {

    @Override
    protected int[] getWidgetIds() {
        return new int[] {
                R.id.mdkRichDateTime_withLabelAndMandatory,
                R.id.mdkRichDate_withLabelAndMandatory,
                R.id.mdkRichTime_withLabelAndMandatory,
                R.id.mdkDateTime_withSharedError,
                R.id.mdkRichDateTime_withCustomLayout,
                R.id.mdkDateTime_withLabelAndMandatoryAndMinDate,
                R.id.mdkRichDateTime_withLabelAndMandatoryAndHints,
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
    }

}

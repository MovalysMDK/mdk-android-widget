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
 * Test activity for the MDKRichUri widget.
 */
public class UriActivity extends AbstractWidgetTestableActivity {

    @Override
    protected int[] getWidgetIds() {
        return new int[] {
                R.id.mdkRichUri_withLabelAndError,
                R.id.mdkUri_withErrorAndCommandOutside,
                R.id.mdkRichUri_withCustomLayout,
                R.id.mdkRichUri1_withSharedError,
                R.id.mdkRichUri2_withSharedError,
                R.id.mdkRichUri_withHelper
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uri);
    }

}

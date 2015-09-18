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
 * Test activity for the MDKRichSeekBar widget.
 */
public class SeekBarActivity extends AbstractWidgetTestableActivity {

    @Override
    protected int[] getWidgetIds() {
        return new int[]{
                R.id.mdkRichSeekBar_withLabelAndError,
                R.id.mdkSeekBar_withErrorAndCommandOutside,
                R.id.mdkRichSeekBar_withCustomLayout,
                R.id.mdkRichSeekBar1_withSharedErrorAndInitValue,
                R.id.mdkRichSeekBar2_withSharedError,
                R.id.mdkRichSeekBar_helper_and_init_value,
                R.id.mdkRichSeekBar_max_10000
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seekbar);
    }

}

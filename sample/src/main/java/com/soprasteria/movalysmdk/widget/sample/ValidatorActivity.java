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
 * Test Activity for custom FormFieldValidator.
 */
public class ValidatorActivity extends AbstractWidgetTestableActivity {


    @Override
    protected int[] getWidgetIds() {
        return new int[] {
                R.id.mdkRichText_withCustomValidator,
                R.id.mdkRichText_nomandatory_withCustomValidator,
                R.id.mdkRichText_lenthvalidator_withCustomValidator,
                R.id.mdkRichCheckbox_trueValidation,
                R.id.mdkRichCheckbox_falseValidation,
                R.id.mdkRichSwitch_trueValidation,
                R.id.mdkRichSwitch_falseValidation
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validator);
    }
}

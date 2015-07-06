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
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.soprasteria.movalysmdk.widget.basic.MDKRichEditText;

/**
 * Test Activity for custom FormFieldValidator.
 */
public class ValidatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validator);
    }

    /**
     * Callback method for widget validation.
     * @param view the clicked view
     */
    public void validate(View view) {

        ((MDKRichEditText) findViewById(R.id.mdkRichText_withCustomValidator)).validate();
        ((MDKRichEditText) findViewById(R.id.mdkRichText_nomandatory_withCustomValidator)).validate();
        ((MDKRichEditText) findViewById(R.id.mdkRichText_lenthvalidator_withCustomValidator)).validate();

    }
}

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
import android.widget.ArrayAdapter;

import com.soprasteria.movalysmdk.widget.spinner.MDKRichSpinner;
import com.soprasteria.movalysmdk.widget.spinner.MDKSpinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Test activity for the MDKRichSpinner widget.
 */
public class SpinnerActivity extends AbstractWidgetTestableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        MDKRichSpinner spinner1 = (MDKRichSpinner) findViewById(R.id.MDKRichSpinner_1);
        MDKRichSpinner spinner2 = (MDKRichSpinner) findViewById(R.id.MDKRichSpinner_2);
        MDKRichSpinner spinner3 = (MDKRichSpinner) findViewById(R.id.MDKRichSpinner_3);
        MDKRichSpinner spinner4 = (MDKRichSpinner) findViewById(R.id.MDKRichSpinner_4);
        MDKRichSpinner spinner5 = (MDKRichSpinner) findViewById(R.id.MDKRichSpinner_5);
        MDKRichSpinner spinner6 = (MDKRichSpinner) findViewById(R.id.MDKRichSpinner_6);
        MDKRichSpinner spinner7 = (MDKRichSpinner) findViewById(R.id.MDKRichSpinner_7);
        MDKRichSpinner spinnerNotEditable = (MDKRichSpinner) findViewById(R.id.MDKRichSpinner_not_editable);
        MDKSpinner iSpinner = (MDKSpinner) findViewById(R.id.MDKSpinner);

        //Create adapter
        List<String> exemple = new ArrayList<>();
        exemple.add("Element 1");
        exemple.add("Element 2");
        exemple.add("Element 3");
        exemple.add("Element 4");
        exemple.add("Element 5");
        exemple.add("Element 6");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, exemple);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner3.setAdapter(adapter);
        spinner4.setAdapterWithCustomBlankLayout(adapter, R.layout.custom_spinner_layout);
        spinner5.setAdapterSpinnerDropDownBlankLayout(adapter, R.layout.custom_spinner_layout_1, R.layout.custom_spinner_layout_2);
        spinner6.setAdapterWithCustomBlankLayout(adapter, R.layout.custom_spinner_layout);
        spinner7.setAdapterSpinnerDropDownBlankLayout(adapter, R.layout.custom_spinner_layout_1, R.layout.custom_spinner_layout_2);
        spinnerNotEditable.setAdapter(adapter);
        iSpinner.setAdapter(adapter);

    }

    @Override
    protected int[] getWidgetIds() {
        return new int[]{
                R.id.MDKRichSpinner_1,
                R.id.MDKRichSpinner_2,
                R.id.MDKRichSpinner_3,
                R.id.MDKRichSpinner_4,
                R.id.MDKRichSpinner_5,
                R.id.MDKRichSpinner_6,
                R.id.MDKRichSpinner_7,
                R.id.MDKSpinner,
                R.id.MDKRichSpinner_not_editable
        };
    }
}

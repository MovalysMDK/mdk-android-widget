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

        MDKRichSpinner rSpinner = (MDKRichSpinner) findViewById(R.id.MDKRichSpinner_withLabelAndError);
        MDKSpinner iSpinner = (MDKSpinner) findViewById(R.id.MDKSpinner);

        //Creat adapter
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

        iSpinner.setAdapterWithCustomBlankLayout(adapter, R.layout.custom_spinner_hint);
        rSpinner.setAdapterSpinnerDropDownBlankLayout(adapter, R.layout.custom_spinner_hint, R.layout.custom_spinner_hint_2);
    }

    @Override
    protected int[] getWidgetIds() {
        return new int[]{
                R.id.MDKRichSpinner_withLabelAndError,
                R.id.MDKSpinner,
        };
    }
}

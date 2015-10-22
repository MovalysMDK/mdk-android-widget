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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.basic.MDKRichEditText;

/**
 * Detail activity for the MDKRichFixedList widget.
 */
public class FixedListDetailActivity extends Activity {

    MDKRichEditText edit;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_list_detail);

        position = getIntent().getIntExtra("pos", -1);
        String itemValue = getIntent().getStringExtra("value");

        ((TextView) findViewById(R.id.pos)).setText(String.valueOf(position));
        edit = (MDKRichEditText) findViewById(R.id.item_value);
        edit.setText(itemValue);
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("value", edit.getText().toString());
        returnIntent.putExtra("position", position);
        setResult(RESULT_OK, returnIntent);
        super.onBackPressed();
    }
}

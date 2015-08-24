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
import android.widget.Button;

import com.soprasteria.movalysmdk.widget.basic.MDKSeekBar;
import com.soprasteria.movalysmdk.widget.basic.MDKRichSeekBar;

/**
 * Test activity for the MDKRichSeekBar widget.
 */
public class SeekBarActivity extends AppCompatActivity {

    /**
     * MDKRichSeekBar with label and error.
     */
    private MDKRichSeekBar richSeekBarWithLabelAndError;

    /**
     * MDKSeekBar with error and command outside.
     */
    private MDKSeekBar seekbarWithErrorAndCommandOutside;

    /**
     * MDKRichSeekBar with custom layout.
     */
    private MDKRichSeekBar richSeekBarWithCustomLayout;

    /**
     * MDKRichSeekBar with shared error.
     */
    private MDKRichSeekBar richSeekBar1WithSharedError;

    /**
     * MDKRichSeekBar with shared error.
     */
    private MDKRichSeekBar richSeekBar2WithSharedError;

    /**
     * MDKRichSeekBar with external helper.
     */
    private MDKRichSeekBar mdkRichSeekBar_helper_and_init_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seekbar);

        this.richSeekBarWithLabelAndError = (MDKRichSeekBar) findViewById(R.id.mdkRichSeekBar_withLabelAndError);
        this.seekbarWithErrorAndCommandOutside = (MDKSeekBar) findViewById(R.id.mdkSeekBar_withErrorAndCommandOutside);
        this.richSeekBarWithCustomLayout = (MDKRichSeekBar) findViewById(R.id.mdkRichSeekBar_withCustomLayout);
        this.richSeekBar1WithSharedError = (MDKRichSeekBar) findViewById(R.id.mdkRichSeekBar1_withSharedErrorAndInitValue);
        this.richSeekBar2WithSharedError = (MDKRichSeekBar) findViewById(R.id.mdkRichSeekBar2_withSharedError);
        this.mdkRichSeekBar_helper_and_init_value = (MDKRichSeekBar) findViewById(R.id.mdkRichSeekBar_helper_and_init_value);
    }


    /**
     * Switch to enabled/disabled state of all mdk widgets.
     * @param view view
     */
    public void switchEnable(View view) {

        Button button = (Button) view;
        button.setText("Disable".equals(button.getText()) ? "Enable": "Disable");

        this.richSeekBarWithLabelAndError.setEnabled(!this.richSeekBarWithLabelAndError.isEnabled());
        this.seekbarWithErrorAndCommandOutside.setEnabled(!this.seekbarWithErrorAndCommandOutside.isEnabled());
        this.richSeekBarWithCustomLayout.setEnabled(!this.richSeekBarWithCustomLayout.isEnabled());
        this.richSeekBar1WithSharedError.setEnabled(!this.richSeekBar1WithSharedError.isEnabled());
        this.richSeekBar2WithSharedError.setEnabled(!this.richSeekBar2WithSharedError.isEnabled());
        this.mdkRichSeekBar_helper_and_init_value.setEnabled(!this.mdkRichSeekBar_helper_and_init_value.isEnabled());
    }
}

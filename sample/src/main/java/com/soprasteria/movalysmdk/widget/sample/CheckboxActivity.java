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

import com.soprasteria.movalysmdk.widget.basic.MDKCheckBox;
import com.soprasteria.movalysmdk.widget.basic.MDKRichCheckbox;
import com.soprasteria.movalysmdk.widget.basic.MDKRichSwitch;
import com.soprasteria.movalysmdk.widget.basic.MDKSwitch;

/**
 * Test activity for the MDKRichCheckable widget.
 */
public class CheckboxActivity extends AppCompatActivity {

    /**
     * MDKRichCheckable with label and error.
     */
    private MDKRichCheckbox richCheckBoxWithLabelAndError;

    /**
     * MDKCheckBox with error and command outside.
     */
    private MDKCheckBox checkboxWithErrorAndCommandOutside;

    /**
     * MDKRichCheckable with custom layout.
     */
    private MDKRichCheckbox richCheckBoxWithCustomLayout;

    /**
     * MDKRichCheckable with external helper.
     */
    private MDKRichCheckbox richCheckBoxWithExternalHelper;

    /**
     * Enable button
     */
    private Button enableButton;

    private boolean isEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkbox);

        this.richCheckBoxWithLabelAndError = (MDKRichCheckbox) findViewById(R.id.mdkRichCheckbox_withLabelAndError);
        this.checkboxWithErrorAndCommandOutside = (MDKCheckBox) findViewById(R.id.mdkCheckbox_withErrorAndCommandOutside);
        this.richCheckBoxWithCustomLayout = (MDKRichCheckbox) findViewById(R.id.mdkRichCheckbox_withCustomLayout);
        this.richCheckBoxWithExternalHelper = (MDKRichCheckbox) findViewById(R.id.checkbox_helper);

        this.enableButton = (Button) findViewById(R.id.enableButton);
    }

    /**
     * Validate all the mdk widgets.
     * @param view the view
     */
    public void validate(View view) {

        this.richCheckBoxWithLabelAndError.validate();
        this.checkboxWithErrorAndCommandOutside.validate();
        this.richCheckBoxWithCustomLayout.validate();
        this.richCheckBoxWithExternalHelper.validate();
    }

    /**
     * Switch on/off mandatory state of all mdk widgets.
     * @param view the view
     */
    public void mandatory(View view) {
        // nothing to do
    }

    /**
     * Switch to enabled/disabled state of all mdk widgets.
     * @param view view
     */
    public void switchEnable(View view) {

        Button button = (Button) view;
        button.setText(this.isEnabled ? "Enable" : "Disable");

        this.isEnabled = !isEnabled;

        this.richCheckBoxWithLabelAndError.setEnabled(this.isEnabled);
        this.checkboxWithErrorAndCommandOutside.setEnabled(this.isEnabled);
        this.richCheckBoxWithCustomLayout.setEnabled(this.isEnabled);
        this.richCheckBoxWithExternalHelper.setEnabled(this.isEnabled);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isEnabled", this.isEnabled);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.isEnabled = !savedInstanceState.getBoolean("isEnabled");

        this.switchEnable(this.enableButton);
    }
}

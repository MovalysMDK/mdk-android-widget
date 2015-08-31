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

import com.soprasteria.movalysmdk.widget.basic.MDKRichSwitch;
import com.soprasteria.movalysmdk.widget.basic.MDKSwitch;

/**
 * Test activity for the MDKRichCheckable widget.
 */
public class SwitchActivity extends AppCompatActivity {

    /**
     * MDKRichSwitch with label and error.
     */
    private MDKRichSwitch richSwitchWithLabelAndError;

    /**
     * MDKSwitch with error and command outside.
     */
    private MDKSwitch switchWithErrorAndCommandOutside;

    /**
     * MDKRichSwitch with custom layout.
     */
    private MDKRichSwitch richSwitchWithCustomLayout;

    /**
     * MDKRichSwitch with external helper.
     */
    private MDKRichSwitch richSwitchWithExternalHelper;

    /**
     * Enable button
     */
    private Button enableButton;

    private boolean isEnabled = true;

    /**
     * On activity created.
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);

        this.richSwitchWithLabelAndError = (MDKRichSwitch) findViewById(R.id.mdkRichSwitch_withLabelAndError);
        this.switchWithErrorAndCommandOutside = (MDKSwitch) findViewById(R.id.mdkSwitch_withErrorAndCommandOutside);
        this.richSwitchWithCustomLayout = (MDKRichSwitch) findViewById(R.id.mdkRichSwitch_withCustomLayout);
        this.richSwitchWithExternalHelper = (MDKRichSwitch) findViewById(R.id.switch_helper);

        this.enableButton = (Button) findViewById(R.id.enableButton);
    }

    /**
     * Validate all the mdk widgets.
     * @param view the view
     */
    public void validate(View view) {

        this.richSwitchWithLabelAndError.validate();
        this.switchWithErrorAndCommandOutside.validate();
        this.richSwitchWithCustomLayout.validate();
        this.richSwitchWithExternalHelper.validate();
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

        this.richSwitchWithLabelAndError.setEnabled(this.isEnabled);
        this.switchWithErrorAndCommandOutside.setEnabled(this.isEnabled);
        this.richSwitchWithCustomLayout.setEnabled(this.isEnabled);
        this.richSwitchWithExternalHelper.setEnabled(this.isEnabled);
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

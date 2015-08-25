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
public class CheckableActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkable);

        this.richCheckBoxWithLabelAndError = (MDKRichCheckbox) findViewById(R.id.mdkRichCheckbox_withLabelAndError);
        this.checkboxWithErrorAndCommandOutside = (MDKCheckBox) findViewById(R.id.mdkCheckbox_withErrorAndCommandOutside);
        this.richCheckBoxWithCustomLayout = (MDKRichCheckbox) findViewById(R.id.mdkRichCheckbox_withCustomLayout);
        this.richCheckBoxWithExternalHelper = (MDKRichCheckbox) findViewById(R.id.checkbox_helper);

        this.richSwitchWithLabelAndError = (MDKRichSwitch) findViewById(R.id.mdkRichSwitch_withLabelAndError);
        this.switchWithErrorAndCommandOutside = (MDKSwitch) findViewById(R.id.mdkSwitch_withErrorAndCommandOutside);
        this.richSwitchWithCustomLayout = (MDKRichSwitch) findViewById(R.id.mdkRichSwitch_withCustomLayout);
        this.richSwitchWithExternalHelper = (MDKRichSwitch) findViewById(R.id.switch_helper);
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
        // nohting to do
    }

    /**
     * Switch to enabled/disabled state of all mdk widgets.
     * @param view view
     */
    public void switchEnable(View view) {

        Button button = (Button) view;
        button.setText("Disable".equals(button.getText()) ? "Enable": "Disable");

        this.richCheckBoxWithLabelAndError.setEnabled(!this.richCheckBoxWithLabelAndError.isEnabled());
        this.checkboxWithErrorAndCommandOutside.setEnabled(!this.checkboxWithErrorAndCommandOutside.isEnabled());
        this.richCheckBoxWithCustomLayout.setEnabled(!this.richCheckBoxWithCustomLayout.isEnabled());
        this.richCheckBoxWithExternalHelper.setEnabled(!this.richCheckBoxWithExternalHelper.isEnabled());

        this.richSwitchWithLabelAndError.setEnabled(!this.richSwitchWithLabelAndError.isEnabled());
        this.switchWithErrorAndCommandOutside.setEnabled(!this.switchWithErrorAndCommandOutside.isEnabled());
        this.richSwitchWithCustomLayout.setEnabled(!this.richSwitchWithCustomLayout.isEnabled());
        this.richSwitchWithExternalHelper.setEnabled(!this.richSwitchWithExternalHelper.isEnabled());
    }
}

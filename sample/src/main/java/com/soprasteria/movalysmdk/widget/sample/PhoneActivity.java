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

import com.soprasteria.movalysmdk.widget.basic.MDKPhone;
import com.soprasteria.movalysmdk.widget.basic.MDKRichPhone;

/**
 * Test activity for the MDKRichPhone widget.
 */
public class PhoneActivity extends AppCompatActivity {

    /**
     * MDKRichPhone with label and error.
     */
    private MDKRichPhone richPhoneWithLabelAndError;

    /**
     * MDKPhone with error and command outside.
     */
    private MDKPhone phoneWithErrorAndCommandOutside;

    /**
     * MDKRichPhone with custom layout.
     */
    private MDKRichPhone richPhoneWithCustomLayout;

    /**
     * MDKRichPhone with shared error.
     */
    private MDKRichPhone richPhone1WithSharedError;

    /**
     * MDKRichPhone with shared error.
     */
    private MDKRichPhone richPhone2WithSharedError;

    /**
     * MDKRichPhone with external helper.
     */
    private MDKRichPhone richPhoneWithExternalHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        this.richPhoneWithLabelAndError = (MDKRichPhone) findViewById(R.id.mdkRichPhone_withLabelAndError);
        this.phoneWithErrorAndCommandOutside = (MDKPhone) findViewById(R.id.mdkPhone_withErrorAndCommandOutside);
        this.richPhoneWithCustomLayout = (MDKRichPhone) findViewById(R.id.mdkRichPhone_withCustomLayout);
        this.richPhone1WithSharedError = (MDKRichPhone) findViewById(R.id.mdkRichPhone1_withSharedError);
        this.richPhone2WithSharedError = (MDKRichPhone) findViewById(R.id.mdkRichPhone2_withSharedError);
        this.richPhoneWithExternalHelper = (MDKRichPhone) findViewById(R.id.mdkRichEmail_withHelper);
    }

    /**
     * Validate all the mdk widgets.
     * @param view the view
     */
    public void validate(View view) {

        this.richPhoneWithLabelAndError.validate();
        this.phoneWithErrorAndCommandOutside.validate();
        this.richPhoneWithCustomLayout.validate();
        this.richPhone1WithSharedError.validate();
        this.richPhone2WithSharedError.validate();
    }

    /**
     * Switch on/off mandatory state of all mdk widgets.
     * @param view the view
     */
    public void mandatory(View view) {

        this.richPhoneWithLabelAndError.setMandatory(!this.richPhoneWithLabelAndError.isMandatory());
        this.phoneWithErrorAndCommandOutside.setMandatory(!this.phoneWithErrorAndCommandOutside.isMandatory());
        this.richPhoneWithCustomLayout.setMandatory(!this.richPhoneWithCustomLayout.isMandatory());
        this.richPhone1WithSharedError.setMandatory(!this.richPhone1WithSharedError.isMandatory());
        this.richPhone2WithSharedError.setMandatory(!this.richPhone2WithSharedError.isMandatory());
        this.richPhoneWithExternalHelper.setMandatory(!this.richPhoneWithExternalHelper.isMandatory());
    }

    /**
     * Switch to enabled/disabled state of all mdk widgets.
     * @param view view
     */
    public void switchEnable(View view) {

        Button button = (Button) view;
        button.setText("Disable".equals(button.getText()) ? "Enable": "Disable");

        this.richPhoneWithLabelAndError.setEnabled(!this.richPhoneWithLabelAndError.isEnabled());
        this.phoneWithErrorAndCommandOutside.setEnabled(!this.phoneWithErrorAndCommandOutside.isEnabled());
        this.richPhoneWithCustomLayout.setEnabled(!this.richPhoneWithCustomLayout.isEnabled());
        this.richPhone1WithSharedError.setEnabled(!this.richPhone1WithSharedError.isEnabled());
        this.richPhone2WithSharedError.setEnabled(!this.richPhone2WithSharedError.isEnabled());
        this.richPhoneWithExternalHelper.setEnabled(!this.richPhoneWithExternalHelper.isEnabled());
    }
}

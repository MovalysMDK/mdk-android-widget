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

import com.soprasteria.movalysmdk.widget.position.MDKPosition;
import com.soprasteria.movalysmdk.widget.position.MDKRichPosition;

/**
 * Test activity for the MDKRichPosition widget.
 */
public class PositionActivity extends AppCompatActivity {

    /**
     * MDKRichPosition with label and error.
     */
    private MDKRichPosition richPositionWithLabelAndError;

    /**
     * MDKPosition with error and command outside.
     */
    private MDKPosition positionWithErrorAndCommandOutside;

    /**
     * MDKRichPosition with custom layout.
     */
    private MDKRichPosition richPositionWithCustomLayout;

    /**
     * MDKRichPosition with shared error.
     */
    private MDKRichPosition richPosition1WithSharedError;

    /**
     * MDKRichPosition with shared error.
     */
    private MDKRichPosition richPosition2WithSharedError;

    /**
     * MDKRichPosition with external helper.
     */
    private MDKRichPosition richPositionWithExternalHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);

        this.richPositionWithLabelAndError = (MDKRichPosition) findViewById(R.id.mdkRichPosition_withLabelAndError);
        this.positionWithErrorAndCommandOutside = (MDKPosition) findViewById(R.id.mdkPosition_withErrorAndCommandOutside);
        this.richPositionWithCustomLayout = (MDKRichPosition) findViewById(R.id.mdkRichPosition_withCustomLayout);
        this.richPosition1WithSharedError = (MDKRichPosition) findViewById(R.id.mdkRichPosition1_withSharedError);
        this.richPosition2WithSharedError = (MDKRichPosition) findViewById(R.id.mdkRichPosition2_withSharedError);
        this.richPositionWithExternalHelper = (MDKRichPosition) findViewById(R.id.position_helper);
    }

    /**
     * Validate all the mdk widgets.
     * @param view the view
     */
    public void validate(View view) {

        this.richPositionWithLabelAndError.validate();
        this.positionWithErrorAndCommandOutside.validate();
        this.richPositionWithCustomLayout.validate();
        this.richPosition1WithSharedError.validate();
        this.richPosition2WithSharedError.validate();
    }

    /**
     * Switch on/off mandatory state of all mdk widgets.
     * @param view the view
     */
    public void mandatory(View view) {

        this.richPositionWithLabelAndError.setMandatory(!this.richPositionWithLabelAndError.isMandatory());
        this.positionWithErrorAndCommandOutside.setMandatory(!this.positionWithErrorAndCommandOutside.isMandatory());
        this.richPositionWithCustomLayout.setMandatory(!this.richPositionWithCustomLayout.isMandatory());
        this.richPosition1WithSharedError.setMandatory(!this.richPosition1WithSharedError.isMandatory());
        this.richPosition2WithSharedError.setMandatory(!this.richPosition2WithSharedError.isMandatory());
        this.richPositionWithExternalHelper.setMandatory(!this.richPositionWithExternalHelper.isMandatory());
    }

    /**
     * Switch to enabled/disabled state of all mdk widgets.
     * @param view view
     */
    public void switchEnable(View view) {

        Button button = (Button) view;
        button.setText("Disable".equals(button.getText()) ? "Enable": "Disable");

        this.richPositionWithLabelAndError.setEnabled(!this.richPositionWithLabelAndError.isEnabled());
        this.positionWithErrorAndCommandOutside.setEnabled(!this.positionWithErrorAndCommandOutside.isEnabled());
        this.richPositionWithCustomLayout.setEnabled(!this.richPositionWithCustomLayout.isEnabled());
        this.richPosition1WithSharedError.setEnabled(!this.richPosition1WithSharedError.isEnabled());
        this.richPosition2WithSharedError.setEnabled(!this.richPosition2WithSharedError.isEnabled());
        this.richPositionWithExternalHelper.setEnabled(!this.richPositionWithExternalHelper.isEnabled());
    }
}
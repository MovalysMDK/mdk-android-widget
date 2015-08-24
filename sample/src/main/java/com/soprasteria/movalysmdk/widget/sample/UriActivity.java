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

import com.soprasteria.movalysmdk.widget.basic.MDKRichUri;
import com.soprasteria.movalysmdk.widget.basic.MDKUri;

/**
 * Test activity for the MDKRichUri widget.
 */
public class UriActivity extends AppCompatActivity {

    /**
     * MDKRichUri with label and error.
     */
    private MDKRichUri richUriWithLabelAndError;

    /**
     * MDKUri with error and command outside.
     */
    private MDKUri uriWithErrorAndCommandOutside;

    /**
     * MDKRichUri with custom layout.
     */
    private MDKRichUri richUriWithCustomLayout;

    /**
     * MDKRichUri with shared error.
     */
    private MDKRichUri richUri1WithSharedError;

    /**
     * MDKRichUri with shared error.
     */
    private MDKRichUri richUri2WithSharedError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uri);

        this.richUriWithLabelAndError = (MDKRichUri) findViewById(R.id.mdkRichUri_withLabelAndError);
        this.uriWithErrorAndCommandOutside = (MDKUri) findViewById(R.id.mdkUri_withErrorAndCommandOutside);
        this.richUriWithCustomLayout = (MDKRichUri) findViewById(R.id.mdkRichUri_withCustomLayout);
        this.richUri1WithSharedError = (MDKRichUri) findViewById(R.id.mdkRichUri1_withSharedError);
        this.richUri2WithSharedError = (MDKRichUri) findViewById(R.id.mdkRichUri2_withSharedError);
    }

    /**
     * Validate all the mdk widgets.
     * @param view the view
     */
    public void validate(View view) {

        this.richUriWithLabelAndError.validate();
        this.uriWithErrorAndCommandOutside.validate();
        this.richUriWithCustomLayout.validate();
        this.richUri1WithSharedError.validate();
        this.richUri2WithSharedError.validate();
    }

    /**
     * Switch on/off mandatory state of all mdk widgets.
     * @param view the view
     */
    public void mandatory(View view) {

        this.richUriWithLabelAndError.setMandatory(!this.richUriWithLabelAndError.isMandatory());
        this.uriWithErrorAndCommandOutside.setMandatory(!this.uriWithErrorAndCommandOutside.isMandatory());
        this.richUriWithCustomLayout.setMandatory(!this.richUriWithCustomLayout.isMandatory());
        this.richUri1WithSharedError.setMandatory(!this.richUri1WithSharedError.isMandatory());
        this.richUri2WithSharedError.setMandatory(!this.richUri2WithSharedError.isMandatory());
    }

    /**
     * Switch to enabled/disabled state of all mdk widgets.
     * @param view view
     */
    public void switchEnable(View view) {

        Button button = (Button) view;
        button.setText("Disable".equals(button.getText()) ? "Enable": "Disable");

        this.richUriWithLabelAndError.setEnabled(!this.richUriWithLabelAndError.isEnabled());
        this.uriWithErrorAndCommandOutside.setEnabled(!this.uriWithErrorAndCommandOutside.isEnabled());
        this.richUriWithCustomLayout.setEnabled(!this.richUriWithCustomLayout.isEnabled());
        this.richUri1WithSharedError.setEnabled(!this.richUri1WithSharedError.isEnabled());
        this.richUri2WithSharedError.setEnabled(!this.richUri2WithSharedError.isEnabled());
    }
}

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

import com.soprasteria.movalysmdk.widget.basic.MDKDateTime;
import com.soprasteria.movalysmdk.widget.basic.MDKRichDate;
import com.soprasteria.movalysmdk.widget.basic.MDKRichDateTime;
import com.soprasteria.movalysmdk.widget.basic.MDKRichTime;

/**
 * Test activity for the date widget.
 */
public class DateActivity extends AppCompatActivity {

    /**
     * MDKRichDateTime with a label and mandatory.
     */
    private MDKRichDateTime richDateTimeWithLabelAndMandatory;

    /**
     * MDKRichDateTime with a label and optional.
     */
    private MDKRichDateTime richDateTimeWithLabelAndNotMandatory;

    /**
     * MDKRichDate with a label and mandatory.
     */
    private MDKRichDate richDateWithLabelAndMandatory;

    /**
     * MDKRichTime with a label and mandatory.
     */
    private MDKRichTime richTimeWithLabelAndMandatory;

    /**
     * MDKDateTime with a label, mandatory and sharedError.
     */
    private MDKDateTime richDateTimeWithLabelAndMandatoryAndSharedError;

    /**
     * MDKDateTime  with a label, mandatory and a min date.
     */
    private MDKDateTime richDateTimeWithLabelAndMandatoryAndMinDate;

    /**
     * MDKRichDateTime with hints (for date and time).
     */
    private MDKRichDateTime richDateTimeWithLabelAndMandatoryAndHints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        /** Search for components into layout. */
        this.richDateTimeWithLabelAndMandatory = (MDKRichDateTime)findViewById(R.id.mdkRichDateTime_withLabelAndMandatory);
        this.richDateTimeWithLabelAndNotMandatory = (MDKRichDateTime)findViewById(R.id.mdkRichDateTime_withLabelAndNotMandatory);
        this.richDateWithLabelAndMandatory = (MDKRichDate)findViewById(R.id.mdkRichDate_withLabelAndMandatory);
        this.richTimeWithLabelAndMandatory = (MDKRichTime)findViewById(R.id.mdkRichTime_withLabelAndMandatory);
        this.richDateTimeWithLabelAndMandatoryAndSharedError = (MDKDateTime)findViewById(R.id.mdkDateTime_withLabelAndMandatoryAndSharedError);
        this.richDateTimeWithLabelAndMandatoryAndMinDate = (MDKDateTime)findViewById(R.id.mdkDateTime_withLabelAndMandatoryAndMinDate);
        this.richDateTimeWithLabelAndMandatoryAndHints = (MDKRichDateTime)findViewById(R.id.mdkRichDateTime_withLabelAndMandatoryAndHints);
    }

    /**
     * Start the validation for each widget.
     * @param view view
     */
    public void validate(View view) {
        this.richDateTimeWithLabelAndMandatory.validate();
        this.richDateTimeWithLabelAndNotMandatory.validate();
        this.richDateWithLabelAndMandatory.validate();
        this.richTimeWithLabelAndMandatory.validate();
        this.richDateTimeWithLabelAndMandatoryAndSharedError.validate();
        this.richDateTimeWithLabelAndMandatoryAndMinDate.validate();
        this.richDateTimeWithLabelAndMandatoryAndHints.validate();
    }

    /**
     * Switch enabled/disabled state on all mdk widgets.
     * @param view view
     */
    public void switchEnable(View view) {
        this.richDateTimeWithLabelAndMandatory.setEnabled(!this.richDateTimeWithLabelAndMandatory.isEnabled());
        this.richDateTimeWithLabelAndNotMandatory.setEnabled(!this.richDateTimeWithLabelAndNotMandatory.isEnabled());
        this.richDateWithLabelAndMandatory.setEnabled(!this.richDateWithLabelAndMandatory.isEnabled());
        this.richTimeWithLabelAndMandatory.setEnabled(!this.richTimeWithLabelAndMandatory.isEnabled());
        this.richDateTimeWithLabelAndMandatoryAndSharedError.setEnabled(!this.richDateTimeWithLabelAndMandatoryAndSharedError.isEnabled());
        this.richDateTimeWithLabelAndMandatoryAndMinDate.setEnabled(!this.richDateTimeWithLabelAndMandatoryAndMinDate.isEnabled());
        this.richDateTimeWithLabelAndMandatoryAndHints.setEnabled(!this.richDateTimeWithLabelAndMandatoryAndHints.isEnabled());
    }

    /**
     * Switch on/off mandatory state of all mdk widgets.
     * @param view the view
     */
    public void mandatory(View view) {
        this.richDateTimeWithLabelAndMandatory.setMandatory(!this.richDateTimeWithLabelAndMandatory.isMandatory());
        this.richDateTimeWithLabelAndNotMandatory.setMandatory(!this.richDateTimeWithLabelAndNotMandatory.isMandatory());
        this.richDateWithLabelAndMandatory.setMandatory(!this.richDateWithLabelAndMandatory.isMandatory());
        this.richTimeWithLabelAndMandatory.setMandatory(!this.richTimeWithLabelAndMandatory.isMandatory());
        this.richDateTimeWithLabelAndMandatoryAndSharedError.setMandatory(!this.richDateTimeWithLabelAndMandatoryAndSharedError.isMandatory());
        this.richDateTimeWithLabelAndMandatoryAndMinDate.setMandatory(!this.richDateTimeWithLabelAndMandatoryAndMinDate.isMandatory());
        this.richDateTimeWithLabelAndMandatoryAndHints.setMandatory(!this.richDateTimeWithLabelAndMandatoryAndHints.isMandatory());
    }
}

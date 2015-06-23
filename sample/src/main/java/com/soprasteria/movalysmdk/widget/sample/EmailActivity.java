package com.soprasteria.movalysmdk.widget.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.soprasteria.movalysmdk.widget.basic.MDKRichEmail;
import com.soprasteria.movalysmdk.widget.basic.MDKEmail;

/**
 * Test activity for the MDKRichEmail widget.
 */
public class EmailActivity extends AppCompatActivity {

    /**
     * MDKRichEmail with label and error
     */
    private MDKRichEmail richEmailWithLabelAndError;

    /**
     * MDKEmail with error and command outside
     */
    private MDKEmail emailWithErrorAndCommandOutside;

    /**
     * MDKRichEmail with custom layout
     */
    private MDKRichEmail richEmailWithCustomLayout;

    /**
     * MDKRichEmail with shared error
     */
    private MDKRichEmail richEmail1WithSharedError;

    /**
     * MDKRichEmail with shared error
     */
    private MDKRichEmail richEmail2WithSharedError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        this.richEmailWithLabelAndError = (MDKRichEmail) findViewById(R.id.mdkRichEmail_withLabelAndError);
        this.emailWithErrorAndCommandOutside = (MDKEmail) findViewById(R.id.mdkEmail_withErrorAndCommandOutside);
        this.richEmailWithCustomLayout = (MDKRichEmail) findViewById(R.id.mdkRichEmail_withCustomLayout);
        this.richEmail1WithSharedError = (MDKRichEmail) findViewById(R.id.mdkRichEmail1_withSharedError);
        this.richEmail2WithSharedError = (MDKRichEmail) findViewById(R.id.mdkRichEmail2_withSharedError);
    }

    /**
     * Validate all the mdk widgets.
     * @param view the view
     */
    public void validate(View view) {

        this.richEmailWithLabelAndError.validate();
        this.emailWithErrorAndCommandOutside.validate();
        this.richEmailWithCustomLayout.validate();
        this.richEmail1WithSharedError.validate();
        this.richEmail2WithSharedError.validate();
    }

    /**
     * Switch on/off mandatory state of all mdk widgets.
     * @param view the view
     */
    public void mandatory(View view) {

        this.richEmailWithLabelAndError.setMandatory(!this.richEmailWithLabelAndError.isMandatory());
        this.emailWithErrorAndCommandOutside.setMandatory(!this.emailWithErrorAndCommandOutside.isMandatory());
        this.richEmailWithCustomLayout.setMandatory(!this.richEmailWithCustomLayout.isMandatory());
        this.richEmail1WithSharedError.setMandatory(!this.richEmail1WithSharedError.isMandatory());
        this.richEmail2WithSharedError.setMandatory(!this.richEmail2WithSharedError.isMandatory());
    }

    /**
     * Switch to enabled/disabled state of all mdk widgets.
     * @param view view
     */
    public void switchEnable(View view) {

        Button button = (Button) view;
        button.setText( button.getText().equals("Disable")? "Enable": "Disable");

        this.richEmailWithLabelAndError.setEnabled(!this.richEmailWithLabelAndError.isEnabled());
        this.emailWithErrorAndCommandOutside.setEnabled(!this.emailWithErrorAndCommandOutside.isEnabled());
        this.richEmailWithCustomLayout.setEnabled(!this.richEmailWithCustomLayout.isEnabled());
        this.richEmail1WithSharedError.setEnabled(!this.richEmail1WithSharedError.isEnabled());
        this.richEmail2WithSharedError.setEnabled(!this.richEmail2WithSharedError.isEnabled());
    }
}

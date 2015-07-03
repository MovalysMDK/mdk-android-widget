package com.soprasteria.movalysmdk.widget.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.soprasteria.movalysmdk.widget.basic.MDKRichEditText;

/**
 * Test Activity for custom FormFieldValidator.
 */
public class ValidatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validator);
    }

    /**
     * Callback method for widget validation.
     * @param view the clicked view
     */
    public void validate(View view) {

        ((MDKRichEditText) findViewById(R.id.mdkRichText_withCustomValidator)).validate();
        ((MDKRichEditText) findViewById(R.id.mdkRichText_nomandatory_withCustomValidator)).validate();
        ((MDKRichEditText) findViewById(R.id.mdkRichText_lenthvalidator_withCustomValidator)).validate();

    }
}

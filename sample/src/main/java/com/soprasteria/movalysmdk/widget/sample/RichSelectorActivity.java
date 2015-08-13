package com.soprasteria.movalysmdk.widget.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.soprasteria.movalysmdk.widget.basic.MDKRichEditText;

/**
 * Activity to test the RichSelector functionality.
 */
public class RichSelectorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rich_selector);
    }


    /**
     * Validate all the mdk widgets.
     * @param view view
     */
    public void validate(View view) {

        ((MDKRichEditText) this.findViewById(R.id.mdkBasicSelector_richEditText)).validate();

        ((MDKRichEditText) this.findViewById(R.id.mdkMoreRichSelector_richEditText)).validate();

        ((MDKRichEditText) this.findViewById(R.id.mdkMoreRichSelector_richEditText2)).validate();
    }

    /**
     * Change the mandatory state of the chosen view's components.
     * @param view view
     */
    public void mandatory(View view) {

        // MDK EditText with label, no hint and mandatory
        ((MDKRichEditText) this.findViewById(R.id.mdkBasicSelector_richEditText)).setMandatory(
                !(((MDKRichEditText) this.findViewById(R.id.mdkBasicSelector_richEditText)).isMandatory()));

        // MDK EditText with label, no hint and mandatory
        ((MDKRichEditText) this.findViewById(R.id.mdkMoreRichSelector_richEditText)).setMandatory(
                !(((MDKRichEditText) this.findViewById(R.id.mdkMoreRichSelector_richEditText)).isMandatory()));

        // MDK EditText with label, no hint and mandatory
        ((MDKRichEditText) this.findViewById(R.id.mdkMoreRichSelector_richEditText2)).setMandatory(
                !(((MDKRichEditText) this.findViewById(R.id.mdkMoreRichSelector_richEditText2)).isMandatory()));
    }

}

package com.soprasteria.movalysmdk.widget.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.soprasteria.movalysmdk.widget.basic.MDKRichEditText;

/**
 * Test activity for the MDKRichEditText widget.
 */
public class EditTextActivity extends AppCompatActivity {

    /**
     * MDKRichEditText with custom layout.
     */
    private MDKRichEditText mdkRichEditText;

    /**
     * Button to clear/fill mdkRichEditText.
     */
    private Button fillEraseButton;

    /**
     * Initialisation of the activity.
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        this.mdkRichEditText = (MDKRichEditText) findViewById(R.id.mdkRichEditText_withCustomLayoutAndButton);
        this.fillEraseButton = (Button) findViewById(R.id.richedittext_button_remplir_effacer);

        fillEraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getString(R.string.action_remplir).equals(fillEraseButton.getText())) {
                    mdkRichEditText.setText(getString(R.string.hello_world));
                    fillEraseButton.setText(R.string.action_vider);
                } else {
                    mdkRichEditText.setText(getString(R.string.empty_string));
                    fillEraseButton.setText(R.string.action_remplir);
                }
            }
        });
    }

    /**
     * Validate all the mdk widgets.
     * @param view view
     */
    public void validate(View view) {
        ((MDKRichEditText) this.findViewById(R.id.mdkRichEditText_withLabelAndMandatory)).validate();
    }

    /**
     * Change the mandatory state of the chosen view's components
     * @param view view
     */
    public void mandatory(View view) {

        ((MDKRichEditText) this.findViewById(R.id.mdkRichEditText_withLabelAndMandatory)).setMandatory(
                !(((MDKRichEditText) this.findViewById(R.id.mdkRichEditText_withLabelAndMandatory)).isMandatory()));

    }
}

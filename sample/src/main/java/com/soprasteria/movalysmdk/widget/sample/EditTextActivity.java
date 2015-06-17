package com.soprasteria.movalysmdk.widget.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.soprasteria.movalysmdk.widget.standard.MDKRichEditText;

/**
 * EditTextActivity class definition.
 */
public class EditTextActivity extends AppCompatActivity {

    /** mdkRichEditText. */
    private MDKRichEditText mdkRichEditText;
    /** erase button. */
    private Button bFillErase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        this.mdkRichEditText = (MDKRichEditText) findViewById(R.id.test_edit_style_2);
        this.bFillErase = (Button) findViewById(R.id.bouton_remplir_effacer);

        bFillErase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ("REMPLIR".equals(bFillErase.getText())) {
                    mdkRichEditText.setText("Hello");
                    bFillErase.setText("VIDER");
                } else {
                    mdkRichEditText.setText("");
                    bFillErase.setText("REMPLIR");
                }
            }
        });
    }

    /**
     * Validate method.
     * @param view the view
     */
    public void validate(View view) {

        ( (MDKRichEditText) this.findViewById(R.id.test_case_1)).validate();

    }
}

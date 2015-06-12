package com.soprasteria.movalysmdk.widget.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.soprasteria.movalysmdk.widget.standard.MDKEditText;
import com.soprasteria.movalysmdk.widget.standard.MDKRichEditText;

public class EditTextActivity extends AppCompatActivity {

    private MDKRichEditText mdkRichEditText;
    private Button button_remplir_effacer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        this.mdkRichEditText = (MDKRichEditText) findViewById(R.id.test_edit_style_2);
        this.button_remplir_effacer = (Button) findViewById(R.id.bouton_remplir_effacer);

        button_remplir_effacer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(button_remplir_effacer.getText().equals("REMPLIR")) {
                    mdkRichEditText.setText("Hello");
                    button_remplir_effacer.setText("VIDER");
                } else {
                    mdkRichEditText.setText("");
                    button_remplir_effacer.setText("REMPLIR");
                }
            }
        });
    }

    public void validate(View view) {

        ( (MDKRichEditText) this.findViewById(R.id.test_case_1)).validate();

    }
}

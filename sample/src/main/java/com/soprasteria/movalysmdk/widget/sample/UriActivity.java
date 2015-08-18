package com.soprasteria.movalysmdk.widget.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.soprasteria.movalysmdk.widget.basic.MDKRichUri;
import com.soprasteria.movalysmdk.widget.basic.MDKUri;

/**
 * Test activity for the Uri widget.
 */
public class UriActivity extends AppCompatActivity {

    private MDKRichUri mdkRichUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uri);

        this.mdkRichUri = (MDKRichUri) findViewById(R.id.mdkRichUri_withLabelAndError);
    }

    /**
     * Validate all the mdk widgets.
     *
     * @param view the view
     */
    public void validate(View view) {
        this.mdkRichUri.validate();
    }

    /**
     * Switch on/off mandatory state of all mdk widgets.
     *
     * @param view the view
     */
    public void mandatory(View view) {
        this.mdkRichUri.setMandatory(!this.mdkRichUri.isMandatory());
    }

    /**
     * Switch to enabled/disabled state of all mdk widgets.
     *
     * @param view view
     */
    public void switchEnable(View view) {
        Button button = (Button) view;
        button.setText("Disable".equals(button.getText()) ? "Enable": "Disable");
        this.mdkRichUri.setEnabled(!this.mdkRichUri.isEnabled());
    }
}

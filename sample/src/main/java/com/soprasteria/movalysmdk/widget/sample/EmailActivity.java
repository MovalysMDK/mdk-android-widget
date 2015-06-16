package com.soprasteria.movalysmdk.widget.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.soprasteria.movalysmdk.widget.standard.MDKRichEmail;
import com.soprasteria.movalysmdk.widget.standard.MDKEmail;

/**
 * EmailActivity class definition
 */
public class EmailActivity extends AppCompatActivity {

    private MDKRichEmail email;
    private MDKEmail email2;
    private MDKRichEmail email3;
    private MDKRichEmail email4;
    private MDKRichEmail email5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        this.email = (MDKRichEmail) findViewById(R.id.view);
        this.email2 = (MDKEmail) findViewById(R.id.view2);
        this.email3 = (MDKRichEmail) findViewById(R.id.view3);
        this.email4 = (MDKRichEmail) findViewById(R.id.view4);
        this.email5 = (MDKRichEmail) findViewById(R.id.view5);

    }

    /**
     * Validate method
     * @param view the view
     */
    public void validate(View view) {

        this.email.validate();
        this.email2.validate();
        this.email3.validate();
        this.email4.validate();
        this.email5.validate();
    }

    /**
     * Mandatory method
     * @param view the view
     */
    public void mandatory(View view) {

        this.email.setMandatory(!this.email.isMandatory());
        this.email2.setMandatory(!this.email2.isMandatory());
        this.email3.setMandatory(!this.email3.isMandatory());
        this.email4.setMandatory(!this.email4.isMandatory());
        this.email5.setMandatory(!this.email5.isMandatory());

    }
}

package com.soprasteria.movalysmdk.widget.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.soprasteria.movalysmdk.widget.standard.MDKRichEmail;
import com.soprasteria.movalysmdk.widget.standard.MDKEmail;

public class EmailActivity extends AppCompatActivity {

    private MDKRichEmail email;
    private MDKEmail email2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        this.email = (MDKRichEmail) findViewById(R.id.view);
        this.email2 = (MDKEmail) findViewById(R.id.view2);


    }

    public void validate(View view) {

        boolean valid = this.email.validate();
        boolean valid2 = this.email2.validate();
    }

    public void mandatory(View view) {

        this.email.setMandatory(!this.email.isMandatory());

    }
}

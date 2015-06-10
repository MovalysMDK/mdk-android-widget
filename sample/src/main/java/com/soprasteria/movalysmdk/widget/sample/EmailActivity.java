package com.soprasteria.movalysmdk.widget.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.soprasteria.movalysmdk.widget.standard.MDKRichEmail;
import com.soprasteria.movalysmdk.widget.standard.MDKEmail;

public class EmailActivity extends AppCompatActivity {

    private MDKRichEmail email;
    private MDKEmail email2;
    private MDKRichEmail email3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        this.email = (MDKRichEmail) findViewById(R.id.view);
        this.email2 = (MDKEmail) findViewById(R.id.view2);
        this.email3 = (MDKRichEmail) findViewById(R.id.view3);


    }

    public void validate(View view) {

        this.email.validate();
        this.email2.validate();
        this.email3.validate();
    }

    public void mandatory(View view) {

        this.email.setMandatory(!this.email.isMandatory());
        this.email3.setMandatory(!this.email3.isMandatory());

    }
}

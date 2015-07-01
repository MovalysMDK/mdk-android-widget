package com.soprasteria.movalysmdk.widget.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.soprasteria.movalysmdk.widget.basic.MDKRichEditText;

public class ValidatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validator);
    }


    public void validate(View view) {

        ((MDKRichEditText) findViewById(R.id.mdkRichText_withCustomValidator)).validate();
        ((MDKRichEditText) findViewById(R.id.mdkRichText_nomandatory_withCustomValidator)).validate();

    }
}

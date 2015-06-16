package com.soprasteria.movalysmdk.widget.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.soprasteria.movalysmdk.widget.standard.MDKDateTime;
import com.soprasteria.movalysmdk.widget.standard.MDKRichDate;
import com.soprasteria.movalysmdk.widget.standard.MDKRichDateTime;
import com.soprasteria.movalysmdk.widget.standard.MDKRichTime;

public class DateActivity extends AppCompatActivity {

    private MDKRichDateTime datetime1;
    private MDKRichDateTime datetime2;
    private MDKRichDate date2;
    private MDKRichTime time3;
    private MDKDateTime date1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        this.datetime1 = (MDKRichDateTime)findViewById(R.id.rich_date_time_1);
        this.datetime2 = (MDKRichDateTime)findViewById(R.id.rich_date_time_2);
        this.date2 = (MDKRichDate)findViewById(R.id.rich_date_1);
        this.time3 = (MDKRichTime)findViewById(R.id.rich_time_1);
        this.date1 = (MDKDateTime)findViewById(R.id.date_1);
    }

    public void validate(View view) {

        this.datetime1.validate();
        this.datetime2.validate();
        this.date2.validate();
        this.time3.validate();
        this.date1.validate();
    }

    public void enable(View view) {
        this.datetime1.setEnabled(!this.datetime1.isEnabled());
        this.datetime2.setEnabled(!this.datetime2.isEnabled());
        this.date2.setEnabled(!this.date2.isEnabled());
        this.time3.setEnabled(!this.time3.isEnabled());
        this.date1.setEnabled(!this.date1.isEnabled());
    }
}

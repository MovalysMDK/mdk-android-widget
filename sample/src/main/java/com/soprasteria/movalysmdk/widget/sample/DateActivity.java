package com.soprasteria.movalysmdk.widget.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.soprasteria.movalysmdk.widget.standard.MDKDateTime;
import com.soprasteria.movalysmdk.widget.standard.MDKRichDate;
import com.soprasteria.movalysmdk.widget.standard.MDKRichDateTime;
import com.soprasteria.movalysmdk.widget.standard.MDKRichTime;

/**
 * DateActivity class definition.
 */
public class DateActivity extends AppCompatActivity {

    /**
     * Declaration for a rich DateTime component
     */
    private MDKRichDateTime datetime1;

    /**
     * Declaration for a rich DateTime component
     */
    private MDKRichDateTime datetime2;

    /**
     * Declaration for a rich Date component
     */
    private MDKRichDate date2;

    /**
     * Declaration for a rich Time component
     */
    private MDKRichTime time3;

    /**
     * Declaration for a DateTime component
     */
    private MDKDateTime date1;

    /**
     * onCreate method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        /** Search for components into layout */
        this.datetime1 = (MDKRichDateTime)findViewById(R.id.rich_date_time_1);
        this.datetime2 = (MDKRichDateTime)findViewById(R.id.rich_date_time_2);
        this.date2 = (MDKRichDate)findViewById(R.id.rich_date_1);
        this.time3 = (MDKRichTime)findViewById(R.id.rich_time_1);
        this.date1 = (MDKDateTime)findViewById(R.id.date_1);
    }

    /**
     * validate method.
     * @param view the view
     */
    public void validate(View view) {

        this.datetime1.validate();
        this.datetime2.validate();
        this.date2.validate();
        this.time3.validate();
        this.date1.validate();
    }

    /**
     * Enable method.
     * @param view the view
     */
    public void enable(View view) {
        this.datetime1.setEnabled(!this.datetime1.isEnabled());
        this.datetime2.setEnabled(!this.datetime2.isEnabled());
        this.date2.setEnabled(!this.date2.isEnabled());
        this.time3.setEnabled(!this.time3.isEnabled());
        this.date1.setEnabled(!this.date1.isEnabled());
    }
}

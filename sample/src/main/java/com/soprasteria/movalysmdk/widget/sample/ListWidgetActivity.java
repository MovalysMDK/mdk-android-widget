package com.soprasteria.movalysmdk.widget.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * ListWidgetActivity class definition.
 */
public class ListWidgetActivity extends AppCompatActivity implements WidgetFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_widget);
    }

    @Override
    public void onFragmentInteraction(Class<?> activityToLaunch) {
        this.startActivity(new Intent(this, activityToLaunch));
    }
}

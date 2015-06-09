package com.soprasteria.movalysmdk.widget.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ListWidgetActivity extends AppCompatActivity implements WidgetFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_widget);
    }

    @Override
    public void onFragmentInteraction(Class<? extends Activity> activityToLaunch) {
        this.startActivity(new Intent(this, activityToLaunch));
    }
}

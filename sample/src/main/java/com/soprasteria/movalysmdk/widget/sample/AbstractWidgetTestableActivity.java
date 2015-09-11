package com.soprasteria.movalysmdk.widget.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.soprasteria.movalysmdk.widget.core.MDKBaseWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract testable activity.
 */
public abstract class AbstractWidgetTestableActivity extends AppCompatActivity {

    /**
     * the enable button boolean.
     */
    protected boolean isEnabled = true;
    /**
     * Mandatory button boolean.
     */
    private boolean isMandatory = true;
    /**
     * The widget list.
     */
    private List<View> listWidget;

    /**
     * getter for the list of testable widgets.
     * @return a array of widget ids
     */
    protected abstract int[] getWidgetIds();

    /**
     * Enable button.
     */
    private Button enableButton;

    @Override
    public void onStart() {
        super.onStart();

        this.listWidget = new ArrayList<>();

        for (int id : this.getWidgetIds()) {
            if (findViewById(id) != null) {
                listWidget.add(findViewById(id));
            }
        }
        this.enableButton = (Button) findViewById(R.id.enableButton);
    }

    /**
     * Validate button callback.
     * @param view the validate button view
     */
    public void validate(View view) {

        for (View v : this.listWidget) {
            if (v instanceof HasValidator) {
                ((HasValidator) v).validate();
            }
        }

    }

    /**
     * Mandatory button callback.
     * @param view the mandatory button view
     */
    public void mandatory(View view) {
        this.isMandatory = !isMandatory;

        for (View v : this.listWidget) {
            if (v instanceof MDKBaseWidget) {
                ((MDKBaseWidget) v).setMandatory(isMandatory);
            }
        }

    }

    /**
     * Enable button callback.
     * @param view the enable/disable button view
     */
    public void switchEnable(View view) {
        Button button = (Button) view;
        button.setText(this.isEnabled ? "Enable" : "Disable");

        this.isEnabled = !isEnabled;

        for (View v : this.listWidget) {
            v.setEnabled(isEnabled);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isEnabled", this.isEnabled);
        outState.putBoolean("isMandatory", this.isMandatory);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.isEnabled = !savedInstanceState.getBoolean("isEnabled");
        this.isMandatory = savedInstanceState.getBoolean("isMandatory");

        this.switchEnable(this.enableButton);
    }
}

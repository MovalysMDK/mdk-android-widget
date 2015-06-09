package com.soprasteria.movalysmdk.widget.core.behavior;

import android.view.View;

/**
 * Interface to add button handling capacity to a widget
 */
public interface HasActions extends View.OnClickListener {


    /**
     * Register actons views on the components
     * (or its ActionDelegate)
     */
    void registerActionViews();
}

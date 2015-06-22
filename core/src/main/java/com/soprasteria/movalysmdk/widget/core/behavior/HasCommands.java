package com.soprasteria.movalysmdk.widget.core.behavior;

import android.view.View;

/**
 * Interface to add button handling capacity to a widget.
 */
public interface HasCommands extends View.OnClickListener {


    /**
     * Register commands views on the components
     * (or its ActionDelegate).
     */
    void registerWidgetCommands();
}

package com.soprasteria.movalysmdk.widget.core.behavior;

import android.view.View;

/**
 * Interface to add button handling capacity to a widget
 */
public interface HasButtons extends View.OnClickListener {

    /**
     * register primary action View
     */
    public void registerPrimaryActionView();

    /**
     * register secondary action View
     */
    public void registerSecondaryActionView();
}

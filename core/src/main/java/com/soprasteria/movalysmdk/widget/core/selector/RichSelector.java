package com.soprasteria.movalysmdk.widget.core.selector;

import android.view.View;

/**
 * Manage change of states for rich widget
 */
public interface RichSelector {
    /**
     * Handle behaviour when a change of state happens on rich widget
     * @param state the new state
     * @param v the view
     */
    void onStateChange(int[] state, View v);
}

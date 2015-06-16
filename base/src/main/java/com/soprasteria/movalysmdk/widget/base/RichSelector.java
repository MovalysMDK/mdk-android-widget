package com.soprasteria.movalysmdk.widget.base;

import android.view.View;

/**
 * Created by abelliard on 09/06/2015.
 */
public interface RichSelector {
    /**
     * onStateChange method.
     * @param state the new state
     * @param v the view
     */
    void onStateChange(int[] state, View v);
}

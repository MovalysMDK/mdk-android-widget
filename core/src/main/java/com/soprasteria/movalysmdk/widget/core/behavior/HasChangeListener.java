package com.soprasteria.movalysmdk.widget.core.behavior;

import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;

/**
 * Has change listener behavior.
 * <p>This interface is used to add change listener behavior</p>
 */
public interface HasChangeListener {

    /**
     * Register a ChangeListener.
     * @param listener the ChangeListener to register
     */
    void registerChangeListener(ChangeListener listener);

}

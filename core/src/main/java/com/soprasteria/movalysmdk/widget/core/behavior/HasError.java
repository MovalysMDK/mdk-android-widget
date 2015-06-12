package com.soprasteria.movalysmdk.widget.core.behavior;

import com.soprasteria.movalysmdk.widget.core.error.MDKError;

/**
 * Interface to add error behavior to a widget
 */
public interface HasError {

    void setError(CharSequence error);

    /**
     * Set the error on the widget
     */
    public void setMDKError(MDKError error);
}

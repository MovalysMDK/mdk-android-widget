package com.soprasteria.movalysmdk.widget.core.behavior;

import com.soprasteria.movalysmdk.widget.core.error.MDKError;

/**
 * Interface to add error behavior to a widget.
 */
public interface HasError {

    /**
     * Set the error message on the widget.
     * @param error the error message
     */
    void setError(CharSequence error);

    /**
     * Set the mdk error on the widget.
     * @param error the mdk error
     */
    void setError(MDKError error);

    /**
     * Remove error on the widget.
     */
    void clearError();
}

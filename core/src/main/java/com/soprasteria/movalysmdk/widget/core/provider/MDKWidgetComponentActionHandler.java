package com.soprasteria.movalysmdk.widget.core.provider;

import android.content.Intent;

/**
 * Interface for the widget components to handle an activity result.
 */
public interface MDKWidgetComponentActionHandler {

    /**
     * Handles the logic after an activity result.
     * @param resultCode the result code
     * @param data the result data
     */
    void handleActivityResult(int resultCode, Intent data);
}

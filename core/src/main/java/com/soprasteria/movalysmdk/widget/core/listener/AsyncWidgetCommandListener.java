package com.soprasteria.movalysmdk.widget.core.listener;

import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;

/**
 * Asynchronous Widget Command listener.
 * @param <O> the returned object type
 */
public interface AsyncWidgetCommandListener<O> extends HasDelegate {

    /**
     * Returns the time out to set on the asynchronous command.
     * @return the desired timeout
     */
    int getTimeOut();

    /**
     * Indicates that the asynchronous task has been launched.
     * @param newValue the initial value
     */
    void onStart(O newValue);

    /**
     * Called when the location has been recalculated.
     * @param newValue the updated value
     */
    void onUpdate(O newValue);

    /**
     * Called when the desired precision has been reached.
     * @param newValue the final value
     */
    void onFinish(O newValue);

    /**
     * Called when an error occurs while processing the command.
     * @param errorType the type of the error
     */
    void onError(int errorType);

}

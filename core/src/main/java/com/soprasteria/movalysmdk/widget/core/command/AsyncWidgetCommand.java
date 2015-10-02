package com.soprasteria.movalysmdk.widget.core.command;

import com.soprasteria.movalysmdk.widget.core.listener.AsyncWidgetCommandListener;

/**
 * Asynchronous WidgetCommand.
 * You may link a listener with the type {@link AsyncWidgetCommandListener}
 * @param <I> Input
 * @param <O> output
 */
public interface AsyncWidgetCommand<I, O> extends WidgetCommand<I, O> {

    /**
     * Returns the defined listener.
     * @return the listener on the command
     */
    AsyncWidgetCommandListener getListener();

    /**
     * Sets a listener on the command.
     * Please note that, should the listener be an Android view or layout object, it should be stored as a WeakReference.
     * @param listener the listener to set
     */
    void setListener(AsyncWidgetCommandListener listener);

}

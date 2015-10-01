package com.soprasteria.movalysmdk.widget.core.command;

import com.soprasteria.movalysmdk.widget.core.listener.AsyncWidgetCommandListener;

/**
 * Asynchronous WidgetCommand.
 * You may link a listener with the type {@link AsyncWidgetCommandListener}
 */
public interface AsyncWidgetCommand<I, O> extends WidgetCommand<I, O> {

    /**
     * Returns the defined listener.
     * @return the listener on the command
     */
    AsyncWidgetCommandListener getListener();

    /**
     * Sets a listener on the command.
     * @param listener the listener to set
     */
    void setListener(AsyncWidgetCommandListener listener);

}

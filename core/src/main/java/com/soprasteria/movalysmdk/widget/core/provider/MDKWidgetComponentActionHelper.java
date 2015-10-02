package com.soprasteria.movalysmdk.widget.core.provider;

import android.content.Context;

import com.soprasteria.movalysmdk.widget.core.command.AsyncWidgetCommand;
import com.soprasteria.movalysmdk.widget.core.listener.AsyncWidgetCommandListener;

/**
 * Helper for the asynchronous actions handled by the components.
 * Use this helper to:
 * <ul>
 *     <li>execute a startActivityForResult action</li>
 *     <li>launch an asynchronous command</li>
 * </ul>
 */
public interface MDKWidgetComponentActionHelper {

    /**
     * Starts an asynchronous command on a widget.
     * @param widget the widget
     * @param command the command to start
     * @return the error code of the command execution, or 0 if everything went fine
     */
    <I, O> O startAsyncCommandOnWidget(Context context, AsyncWidgetCommandListener widget, AsyncWidgetCommand<I, O> command, I commandParam);

    /**
     * Restores the asynchronous commands launched on a given widget.
     * @param widget the widget identifier
     */
    void restoreAsyncCommandsOnWidget(AsyncWidgetCommandListener widget);

    /**
     * Removes an asynchronous command on a given widget.
     * @param widget the widget
     * @param commandClass the class of the command to remove
     */
    void removeCommandOnWidget(AsyncWidgetCommandListener widget, Class<?> commandClass, boolean cancel);

    /**
     * Removes the listener on a given Command object.
     * @param widget the listener to remove
     * @param commandClass the command to clear
     */
    void removeCommandListenerOnWidget(AsyncWidgetCommandListener widget, Class<?> commandClass);

}

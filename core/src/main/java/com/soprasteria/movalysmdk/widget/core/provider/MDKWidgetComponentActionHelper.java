package com.soprasteria.movalysmdk.widget.core.provider;

import android.app.Activity;
import android.content.Intent;

import android.content.Context;

import com.soprasteria.movalysmdk.widget.core.command.AsyncWidgetCommand;
import com.soprasteria.movalysmdk.widget.core.listener.AsyncWidgetCommandListener;

/**
 * Helper for the asynchronous actions handled by the components.
 * Use this helper to:
 * <ul>
 *     <li>execute a startActivityForResult action and registering a handler</li>
 *     <li>pass the the result of this request to the right handler</li>
 *     <li>launch an asynchronous command</li>
 * </ul>
 */
public interface MDKWidgetComponentActionHelper {

    /**
     * Computes a unique identifier to be used on views.
     * @return a unique identifier
     */
    int getUniqueId();

    /**
     * Starts an asynchronous command on a widget.
     * @param context an android context
     * @param widget the widget
     * @param command the command to start
     * @param commandParam the parameters of the command
     * @param <I> the command input type
     * @param <O> the command output type
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
     * @param cancel true if the command should be canceled
     */
    void removeCommandOnWidget(AsyncWidgetCommandListener widget, Class<?> commandClass, boolean cancel);

    /**
     * Removes the listener on a given Command object.
     * @param widget the listener to remove
     * @param commandClass the command to clear
     */
    void removeCommandListenerOnWidget(AsyncWidgetCommandListener widget, Class<?> commandClass);

    /**
     * Exececutes a startActivityForResult method from a given activity, registering a handler for the result.
     * @param activity the activity to start the request from
     * @param intent the intent of the request
     * @param handler the result handler to register
     */
    void startActivityForResult(Activity activity, Intent intent, MDKWidgetComponentActionHandler handler);

    /**
     * Passes the result data to the right registered handler.
     * @param requestCode the request code of the result
     * @param resultCode the result code
     * @param data the result data
     */
    void handleActivityResult(int requestCode, int resultCode, Intent data);
}

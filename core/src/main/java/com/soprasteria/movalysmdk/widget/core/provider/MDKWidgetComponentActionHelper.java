/**
 * Copyright (C) 2010 Sopra Steria Group (movalys.support@soprasteria.com)
 *
 * This file is part of Movalys MDK.
 * Movalys MDK is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Movalys MDK is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with Movalys MDK. If not, see <http://www.gnu.org/licenses/>.
 */
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
     * Registers a given handler for ActivityResult at the handler key (also ActivityResult requestCode) in a map.
     * @param handlerKey the key to the handler (requestCode)
     * @param handler the handler to register
     */
    void registerActivityResultHandler(int handlerKey, MDKWidgetComponentActionHandler handler);

    /**
     * Unregisters the handler at the specified key.
     * @param handlerKey the key to the handler to unregister
     */
    void unregisterActivityResultHandler(int handlerKey);

    /**
     * Passes the result data to the right registered handler.
     * @param requestCode the request code of the result
     * @param resultCode the result code
     * @param data the result data
     */
    void handleActivityResult(int requestCode, int resultCode, Intent data);
}

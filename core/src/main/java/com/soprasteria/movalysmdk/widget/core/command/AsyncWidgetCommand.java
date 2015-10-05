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

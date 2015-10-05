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

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

import android.content.Context;

/**
 * WidgetCommand interface.
 * <p>Should be implemented by all commands in components.</p>
 * @param <I> Input
 * @param <O> output
 */
public interface WidgetCommand<I, O> {
    /**
     * execute the concrete command.
     * @param context the android context
     * @param param parameters for the command
     * @return return of the command
     */
    // params are not an array because java cannot cast from I[] to Whaterever[]
    O execute(Context context, I param);
}

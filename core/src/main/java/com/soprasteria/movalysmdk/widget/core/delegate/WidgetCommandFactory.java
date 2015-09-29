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
package com.soprasteria.movalysmdk.widget.core.delegate;

import android.support.annotation.Nullable;

import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.command.WidgetCommand;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetApplication;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetComponentProvider;

public class WidgetCommandFactory {

    /**
     * Return the base key name for the specified parameters.
     * @param widgetClassName the simple name class of the widget
     * @param command the id of the command view
     * @return the base key associated with the parameters
     */
    @Nullable
    public static String baseKey(String widgetClassName, String command) {
        StringBuilder baseKey = new StringBuilder("mdkwidget_");

        baseKey.append(widgetClassName.toLowerCase());
        baseKey.append("_");
        baseKey.append(command);
        baseKey.append("_command_class");

        return baseKey.toString();
    }

    /**
     * Get command.
     * @param command the string command to get
     * @param qualifier the qualifier of the widget
     * @param view the widget instantiating the command
     * @return the command
     */
    @Nullable
    public static WidgetCommand getWidgetCommand(String command, String qualifier, MDKWidget view) {
        WidgetCommand<?,?> widgetCommand = null;

        if (view != null
                && view.getContext().getApplicationContext() instanceof MDKWidgetApplication) {
            MDKWidgetComponentProvider widgetComponentProvider = ((MDKWidgetApplication) view.getContext().getApplicationContext()).getMDKWidgetComponentProvider();
            widgetCommand = widgetComponentProvider.getCommand(baseKey(view.getClass().getSimpleName(), command), qualifier, view.getContext());
        }

        return  widgetCommand;
    }

}

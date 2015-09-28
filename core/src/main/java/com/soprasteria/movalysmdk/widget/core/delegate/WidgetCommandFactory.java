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

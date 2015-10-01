package com.soprasteria.movalysmdk.widget.core.provider;

import com.soprasteria.movalysmdk.widget.core.command.AsyncWidgetCommand;

import java.util.List;

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
     * @param widgetId the widget identifier
     * @param command the command to start
     */
    void startAsyncCommand(int widgetId, AsyncWidgetCommand command);

    /**
     * Returns a list of the asynchronous commands launched on a given widget.
     * @param widgetId the widget identifier
     * @return a list of commands, or null if none exist
     */
    List<AsyncWidgetCommand> getCommandsForWidget(int widgetId);

}

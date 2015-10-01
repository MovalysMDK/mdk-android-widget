package com.soprasteria.movalysmdk.widget.core.provider;

import com.soprasteria.movalysmdk.widget.core.command.AsyncWidgetCommand;

import java.util.List;

/**
 * Simple implementation for the MDKWidgetComponentActionHelper interface.
 */
public class MDKWidgetSimpleComponentActionHelper implements MDKWidgetComponentActionHelper {

    @Override
    public void startAsyncCommand(int widgetId, AsyncWidgetCommand command) {

    }

    @Override
    public List<AsyncWidgetCommand> getCommandsForWidget(int widgetId) {
        return null;
    }
}

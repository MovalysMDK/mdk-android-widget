package com.soprasteria.movalysmdk.widget.core.provider;

import android.content.Context;

import android.app.Activity;
import android.content.Intent;

import com.soprasteria.movalysmdk.widget.core.command.AsyncWidgetCommand;
import com.soprasteria.movalysmdk.widget.core.listener.AsyncWidgetCommandListener;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Simple implementation for the MDKWidgetComponentActionHelper interface.
 * This class can manage only one instance of a given command class per component.
 * Regarding asynchronous commands, the implementation should be the following:
 * <ul>
 *     <li>call the <em>startAsyncCommandOnWidget</em> method to start the command</li>
 *     <li>call the <em>restoreAsyncCommandsOnWidget</em> in the restoration phase of the listener (in the onRestoreInstanceState for a view)</li>
 *     <li>call the <em>removeCommandListenerOnWidget</em> when the listener is destroyed</li>
 *     <li>call the <em>removeCommandOnWidget</em> when the command is done</li>
 * </ul>
 */
public class MDKWidgetSimpleComponentActionHelper implements MDKWidgetComponentActionHelper {

    /** A map that stores all the registered handlers. **/
    private Map<Integer, MDKWidgetComponentActionHandler> widgetHandlerForIntentMap;

    /** asynchronous commands list to widgets maps. */
    private Map<Integer, List<AsyncWidgetCommand>> asyncCommandsMap;

    /** The incrementing key for the handler map. **/
    Integer key;

    /**
     * Constructor.
     */
    public MDKWidgetSimpleComponentActionHelper() {
        asyncCommandsMap = new HashMap<>();
        widgetHandlerForIntentMap = new HashMap<>();
        key=0;
    }

    @Override
    public <I, O> O startAsyncCommandOnWidget(Context context, AsyncWidgetCommandListener widget, AsyncWidgetCommand<I, O> command, I commandParam) {
        removeCommandOnWidget(widget, command.getClass(), true);

        int widgetId = widget.getMDKWidgetDelegate().getUniqueId();//TODO pb le getUniqueId n'est pas unique dans le cas d'une liste

        if (!asyncCommandsMap.containsKey(widgetId)) {
            asyncCommandsMap.put(widgetId, new ArrayList<AsyncWidgetCommand>());
        }
        asyncCommandsMap.get(widgetId).add((AsyncWidgetCommand)command);

        command.setListener(widget);

        return command.execute(context, commandParam);
    }

    @Override
    public void restoreAsyncCommandsOnWidget(AsyncWidgetCommandListener widget) {
        int widgetId = widget.getMDKWidgetDelegate().getUniqueId();

        if (asyncCommandsMap.containsKey(widgetId)) {
            List<AsyncWidgetCommand> asyncCommands = asyncCommandsMap.get(widgetId);

            for (AsyncWidgetCommand command : asyncCommands) {
                if (command != null) {
                    command.setListener(widget);
                }
            }
        }
    }

    @Override
    public void removeCommandListenerOnWidget(AsyncWidgetCommandListener widget, Class<?> commandClass) {
        int widgetId = widget.getMDKWidgetDelegate().getUniqueId();

        if (asyncCommandsMap.containsKey(widgetId)) {
            List<AsyncWidgetCommand> asyncCommands = asyncCommandsMap.get(widgetId);

            for (AsyncWidgetCommand cmd : asyncCommands) {
                if (cmd != null && commandClass.equals(cmd.getClass())) {
                    cmd.setListener(null);
                }
            }
        }
    }

    @Override
    public void removeCommandOnWidget(AsyncWidgetCommandListener widget, Class<?> commandClass, boolean cancel) {
        int widgetId = widget.getMDKWidgetDelegate().getUniqueId();

        if (asyncCommandsMap.containsKey(widgetId)) {
            List<AsyncWidgetCommand> asyncCommands = asyncCommandsMap.get(widgetId);

            List<AsyncWidgetCommand> asyncCommandsToRemove = new ArrayList<>();

            for (AsyncWidgetCommand cmd : asyncCommands) {
                if (cmd != null) {
                    if (commandClass.equals(cmd.getClass())) {
                        if (cancel) {
                            cmd.cancel();
                        }
                        cmd.setListener(null);
                        asyncCommandsToRemove.add(cmd);
                    }
                }
            }

            asyncCommands.removeAll(asyncCommandsToRemove);

            if (asyncCommandsMap.get(widgetId).isEmpty()) {
                asyncCommandsMap.remove(widgetId);
            }
        }
    }

    @Override
    public void startActivityForResult(Activity activity, Intent intent, MDKWidgetComponentActionHandler handler) {

        // Generate a unique request code.
        Integer requestCode = key;
        key++;

        // Register the handler in the HashMap.
        widgetHandlerForIntentMap.put(requestCode, handler);
        // Start the intent.
        activity.startActivityForResult(intent, requestCode);
    }


    @Override
    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        // Retrieve the registered handler at the requestCode key, and give it the data.
        if (widgetHandlerForIntentMap.containsKey(requestCode)) {
            widgetHandlerForIntentMap.get(requestCode).handleActivityResult(resultCode, data);
            widgetHandlerForIntentMap.remove(requestCode);
        }
    }

}

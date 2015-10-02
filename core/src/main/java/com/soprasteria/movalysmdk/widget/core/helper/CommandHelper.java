package com.soprasteria.movalysmdk.widget.core.helper;

import android.content.Context;
import android.util.Log;

import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.command.AsyncWidgetCommand;
import com.soprasteria.movalysmdk.widget.core.command.WidgetCommand;
import com.soprasteria.movalysmdk.widget.core.delegate.WidgetCommandFactory;
import com.soprasteria.movalysmdk.widget.core.listener.AsyncWidgetCommandListener;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetApplication;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetComponentActionHelper;

/**
 * Asynchronous command execution helper.
 */
public class CommandHelper {

    /** TAG for logging. */
    private static final String TAG = CommandHelper.class.getSimpleName();

    /**
     * Will try to connect to the application and get the MDKWidgetComponentActionHelper object.
     * If it is found, will launch the command.
     * @param widget the widget attached to the command
     * @param command the command to launch
     * @param commandParam the parameters of the command
     * @param <I> the Input type of the command
     * @param <O> the Ouput type of the command
     * @return the output object of the command
     */
    public static <I, O> O startCommandOnWidget(MDKWidget widget, String command, I commandParam) {
        O res = null;
        WidgetCommand<I, O> commandToExecute = WidgetCommandFactory.getWidgetCommand(command, "", widget);

        if (commandToExecute != null) {
            commandToExecute.execute(widget.getContext(), commandParam);
        } else {
            Log.e(TAG, "could not find command " + command + " for class " + widget.getClass().getSimpleName());
        }

        return res;
    }

    /**
     * Will try to connect to the application and get the MDKWidgetComponentActionHelper object.
     * If it is found, will launch the async command.
     * @param context android context
     * @param widget the listener widget
     * @param command the command to execute
     * @param commandParam the parameters of the command
     * @param <I> the Input type of the command
     * @param <O> the Ouput type of the command
     * @return the output object of the command
     */
    public static <I, O> O startAsyncCommandOnWidget(Context context, MDKWidget widget, String command, I commandParam) {
        O result = null;

        if (!(context.getApplicationContext() instanceof MDKWidgetApplication)) {
            Log.e(TAG, "the application class should implement the MDKWidgetApplication interface");
            return result;
        }

        if (!(widget instanceof AsyncWidgetCommandListener)) {
            Log.e(TAG, "the widget should implement the AsyncWidgetCommandListener interface");
            return result;
        }

        if (context != null) {
            AsyncWidgetCommand<I, O> commandToExecute = (AsyncWidgetCommand) WidgetCommandFactory.getWidgetCommand(command, "", widget);

            MDKWidgetComponentActionHelper helper = ((MDKWidgetApplication) context.getApplicationContext()).getMDKWidgetComponentActionHelper();
            result = helper.startAsyncCommandOnWidget(context, (AsyncWidgetCommandListener)widget, commandToExecute, commandParam);
        }

        return result;
    }

    /**
     * Will try to connect to the application and get the MDKWidgetComponentActionHelper object.
     * If it is found, will restore the listener on the async command.
     * @param context android context
     * @param widget the listener widget
     */
    public static void restoreAsyncCommandsOnWidget(Context context, AsyncWidgetCommandListener widget) {
        if (context != null) {
            if (context.getApplicationContext() instanceof MDKWidgetApplication) {
                MDKWidgetComponentActionHelper helper = ((MDKWidgetApplication) context.getApplicationContext()).getMDKWidgetComponentActionHelper();
                helper.restoreAsyncCommandsOnWidget(widget);
            } else {
                Log.e(TAG, "the application class should implement the MDKWidgetApplication interface");
            }
        }
    }

    /**
     * Will try to connect to the application and get the MDKWidgetComponentActionHelper object.
     * If it is found, will remove the command from the helper.
     * @param context android context
     * @param widget the listener widget
     * @param commandClass the class of the command
     * @param cancel true if the command should also be canceled
     */
    public static void removeCommandOnWidget(Context context, AsyncWidgetCommandListener widget, Class<?> commandClass, boolean cancel) {
        if (context != null) {
            if (context.getApplicationContext() instanceof MDKWidgetApplication) {
                MDKWidgetComponentActionHelper helper = ((MDKWidgetApplication) context.getApplicationContext()).getMDKWidgetComponentActionHelper();
                helper.removeCommandOnWidget(widget, commandClass, cancel);
            } else {
                Log.e(TAG, "the application class should implement the MDKWidgetApplication interface");
            }
        }
    }

    /**
     * Will try to connect to the application and get the MDKWidgetComponentActionHelper object.
     * If it is found, will remove the listener on the command command.
     * @param context android context
     * @param widget the listener widget
     * @param commandClass the class of the command
     */
    public static void removeCommandListenerOnWidget(Context context, AsyncWidgetCommandListener widget, Class<?> commandClass) {
        if (context != null) {
            if (context.getApplicationContext() instanceof MDKWidgetApplication) {
                MDKWidgetComponentActionHelper helper = ((MDKWidgetApplication) context.getApplicationContext()).getMDKWidgetComponentActionHelper();
                helper.removeCommandListenerOnWidget(widget, commandClass);
            } else {
                Log.e(TAG, "the application class should implement the MDKWidgetApplication interface");
            }
        }
    }

}

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
     * sendEmail the concrete command.
     * @param context the android context
     * @param params parameters for the command
     * @return return of the command
     */
    O execute(Context context, I... params);
}

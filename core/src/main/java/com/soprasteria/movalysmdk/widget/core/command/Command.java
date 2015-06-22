package com.soprasteria.movalysmdk.widget.core.command;

import android.content.Context;

/**
 * Command interface.
 * Should be implemented by all command in components.
 * @param <I> Input
 * @param <O> output
 */
public interface Command<I, O> {
    /**
     * sendEmail the concrete command.
     * @param context the android context
     * @param params parameters for the command
     * @return return of the command
     */
    O execute(Context context, I... params);
}

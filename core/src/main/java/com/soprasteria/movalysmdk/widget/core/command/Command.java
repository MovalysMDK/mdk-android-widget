package com.soprasteria.movalysmdk.widget.core.command;

/**
 * Command interface.
 * Should be implemented by all command in components.
 * @param <I> Input
 * @param <O> output
 */
public interface Command<I, O> {
    /**
     * execute the concrete command.
     * @param params parameters for the command
     * @return return of the command
     */
    O execute(I... params);
}

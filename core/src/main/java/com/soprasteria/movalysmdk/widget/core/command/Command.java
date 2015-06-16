package com.soprasteria.movalysmdk.widget.core.command;

/**
 * Command interface.
 *
 * should be implemented by all command in components
 */
public interface Command<T, V> {
    /**
     * execute the concrete command.
     * @param params parameters for the command
     * @return return of the command
     */
    V execute(T... params);
}

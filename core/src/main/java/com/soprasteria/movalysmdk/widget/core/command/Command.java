package com.soprasteria.movalysmdk.widget.core.command;

/**
 * Command interface
 *
 * should be implemented by all command in components
 */
public interface Command {
    /**
     * execute the concrete command
     */
    void execute();
}

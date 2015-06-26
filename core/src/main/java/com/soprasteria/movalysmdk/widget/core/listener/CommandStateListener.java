package com.soprasteria.movalysmdk.widget.core.listener;

/**
 * Interface to enable or not the command.
 */
public interface CommandStateListener {

    /**
     * Enable or not the command.
     * @param enable enable or not the command
     */
    void notifyCommandStateChanged(boolean enable);
}

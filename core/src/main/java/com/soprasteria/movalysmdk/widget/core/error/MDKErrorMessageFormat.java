package com.soprasteria.movalysmdk.widget.core.error;

/**
 * Interface definition of the MDK error message formatter.
 * This interface can be implemented for custom error widget in order to personalize message.
 */
public interface MDKErrorMessageFormat {

    /**
     * Message formatter for custom error widget.
     * @param sharedErrorWidget true if this error is declared into a Rich component which shares error's messages.
     * @param error Custom MDKError object
     * @return CharSequence Formatted message
     */
    CharSequence formatText(MDKError error, boolean sharedErrorWidget);
}

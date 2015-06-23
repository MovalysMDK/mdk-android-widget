package com.soprasteria.movalysmdk.widget.core.error;

/**
 * Error Formatter for MDK Widgets.
 */
public interface MDKErrorMessageFormat {

    /**
     * Message formatter for custom error widget.
     * @param sharedErrorWidget true if the error view displays errors for more than one mdk widget.
     * @param error Custom MDKError object
     * @return CharSequence Formatted message
     */
    CharSequence formatText(MDKError error, boolean sharedErrorWidget);
}

package com.soprasteria.movalysmdk.widget.core.error;

/**
 * Class dedicated to methods on error messages formatting
 */
public class MDKSimpleErrorMessageFormat implements MDKErrorMessageFormat {

    /**
     * Constructor.
     * @param sharedErrorWidget defined if the error is defined inside a Rich component
     * @param error MDKError object containing
     * @return formattedMessage the formatted message
     */
    public CharSequence formatText(MDKError error, boolean sharedErrorWidget) {

        CharSequence formattedMessage = error.getErrorMessage();

        if (sharedErrorWidget) {
            return error.getComponentLabelName() + ": " + formattedMessage;
        }

        return formattedMessage;
    }

}




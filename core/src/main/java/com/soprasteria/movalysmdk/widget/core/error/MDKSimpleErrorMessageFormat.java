package com.soprasteria.movalysmdk.widget.core.error;

/**
 * Class dedicated to methods on error messages formatting.
 */
public class MDKSimpleErrorMessageFormat implements MDKErrorMessageFormat {

    /**
     * Constructor.
     * @param sharedErrorWidget defined if the error is defined inside a Rich component
     * @param error MDKError object containing
     * @return oFormattedMessage the formatted message
     */
    public CharSequence formatText(MDKError error, boolean sharedErrorWidget) {

        CharSequence oFormattedMessage = error.getErrorMessage();

        if (sharedErrorWidget) {
            StringBuilder formattedText = new StringBuilder(error.getComponentLabelName());
            formattedText.append(": ");
            formattedText.append(oFormattedMessage);
            oFormattedMessage = formattedText.toString();
        }
        return oFormattedMessage;
    }

}




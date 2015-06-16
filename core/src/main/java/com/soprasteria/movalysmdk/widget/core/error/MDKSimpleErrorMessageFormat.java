package com.soprasteria.movalysmdk.widget.core.error;

/**
 * Created by gestionnaire on 11/06/2015.
 */
public class MDKSimpleErrorMessageFormat implements MDKErrorMessageFormat {

    /**
     * Constructor.
     * @param centralizedError defined if the error is defined inside a Rich component
     * @param error MDKError object containing
     * @return formattedMessage the formatted message
     */
    public CharSequence textFormatter(boolean centralizedError, MDKError error) {

        CharSequence formattedMessage = error.getErrorMessage();

        if (centralizedError) {
            return error.getComponentLabelName() + ": " + formattedMessage;
        }

        return formattedMessage;
    }

}




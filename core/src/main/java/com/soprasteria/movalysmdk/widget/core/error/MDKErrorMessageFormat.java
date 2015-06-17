package com.soprasteria.movalysmdk.widget.core.error;

/**
 * MDKErrorMessageFormat interface definition.
 * Created by gestionnaire on 11/06/2015.
 */
public interface MDKErrorMessageFormat {

    /**
     * Format a message.
     * @param centralizedError defined if the error is defined inside a Rich component
     * @param error MDKError object containing
     * @return CharSequence the frmatted message
     */
    CharSequence textFormatter(boolean centralizedError, MDKError error);


}

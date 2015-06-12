package com.soprasteria.movalysmdk.widget.core.error;

/**
 * Created by gestionnaire on 11/06/2015.
 */
public interface MDKErrorMessageFormat {

    /**
     * @param centralizedError defined if the error is defined inside a Rich component
     * @param error MDKError object containing
     */
    public CharSequence textFormatter(boolean centralizedError, MDKError error);


}

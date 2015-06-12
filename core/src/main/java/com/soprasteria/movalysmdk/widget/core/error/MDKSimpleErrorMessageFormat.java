package com.soprasteria.movalysmdk.widget.core.error;

/**
 * Created by gestionnaire on 11/06/2015.
 */
public class MDKSimpleErrorMessageFormat implements MDKErrorMessageFormat {

    public CharSequence textFormatter(boolean centralizedError, MDKError error) {

       CharSequence formattedMessage = error.getErrorMessage();

       if (centralizedError){
           return error.getComponentLabelName()+": "+formattedMessage;
       }else
           return formattedMessage;
    }
}




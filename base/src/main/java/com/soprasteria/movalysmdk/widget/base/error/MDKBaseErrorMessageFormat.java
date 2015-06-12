package com.soprasteria.movalysmdk.widget.base.error;

import com.soprasteria.movalysmdk.widget.core.error.MDKError;
import com.soprasteria.movalysmdk.widget.core.error.MDKErrorMessageFormat;

/**
 * Created by gestionnaire on 11/06/2015.
 */
public class MDKBaseErrorMessageFormat implements MDKErrorMessageFormat {

    public CharSequence textFormatter(boolean centralizedError, MDKError error) {

       CharSequence formattedMessage = error.getErrorMessage();

       if (centralizedError){
           return error.getComponentLabelName()+": "+formattedMessage;
       }else
           return formattedMessage;
    }
}




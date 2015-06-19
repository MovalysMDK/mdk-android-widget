package com.soprasteria.movalysmdk.widget.sample.error;

import com.soprasteria.movalysmdk.widget.core.error.MDKError;
import com.soprasteria.movalysmdk.widget.core.error.MDKErrorMessageFormat;

/**
 * FortyTwoErrorMessageFormat class definition.
 */
public class FortyTwoErrorMessageFormat implements MDKErrorMessageFormat {

    public CharSequence formatText(MDKError error, boolean sharedErrorWidget) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("42 /!\\ ").append(error.getErrorMessage());
        return stringBuilder.toString();
    }
}

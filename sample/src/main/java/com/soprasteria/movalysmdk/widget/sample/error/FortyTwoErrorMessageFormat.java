package com.soprasteria.movalysmdk.widget.sample.error;

import com.soprasteria.movalysmdk.widget.core.error.MDKError;
import com.soprasteria.movalysmdk.widget.core.error.MDKErrorMessageFormat;

public class FortyTwoErrorMessageFormat implements MDKErrorMessageFormat {

    @Override
    public CharSequence textFormatter(boolean centralizedError, MDKError error) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("42 /!\\ ").append(error.getErrorMessage());
        return stringBuilder.toString();
    }
}

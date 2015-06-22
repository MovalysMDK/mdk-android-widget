package com.soprasteria.movalysmdk.widget.sample.error;

import com.soprasteria.movalysmdk.widget.core.error.MDKError;
import com.soprasteria.movalysmdk.widget.core.error.MDKErrorMessageFormat;

/**
 * Error Formatter that add the prefix "42 /!\ " to MDKError messages.
 */
public class FortyTwoErrorMessageFormat implements MDKErrorMessageFormat {

    @Override
    public CharSequence formatText(MDKError error, boolean sharedErrorWidget) {
        return "42 /!\\ " + error.getErrorMessage();
    }
}

package com.soprasteria.movalysmdk.widget.standard.validator;

import android.content.Context;

import com.soprasteria.movalysmdk.widget.core.error.MDKError;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;
import com.soprasteria.movalysmdk.widget.standard.R;

/**
 * This validator check the mandatory settings of a component.
 *
 * If the component is mandatory and the value is empty, return
 * an empty mandatory error.
 *
 * This validator return an error code ERROR_MANDATORY and associate the
 * message R.string.mdk_mandatory_error if the value is empty and the
 * widget is mandatory.
 */
public class MandatoryValidator implements FormFieldValidator<String> {

    /**
     * ERROR_MANDATORY.
     */
    public static final int ERROR_MANDATORY=0;


    @Override
    public MDKError validate(Context context, String objectToValidate, boolean mandatory) {
        MDKError mdkError = null;
        if (mandatory && objectToValidate.length() < 1) {
            mdkError = new MDKError();
            mdkError.setErrorCode(ERROR_MANDATORY);
            String error = context.getString(R.string.mdk_mandatory_error);
            mdkError.setErrorMessage(error);
        }
        return mdkError;
    }
}

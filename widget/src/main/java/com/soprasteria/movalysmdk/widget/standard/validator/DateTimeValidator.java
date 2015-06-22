package com.soprasteria.movalysmdk.widget.standard.validator;

import android.content.Context;

import com.soprasteria.movalysmdk.widget.core.error.MDKError;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;
import com.soprasteria.movalysmdk.widget.standard.R;

import java.util.Date;

/**
 * DateTimeValidator class definition.
 */
public class DateTimeValidator implements FormFieldValidator<Date> {

    /**
     * Constant.
     */
    public static final int ERROR_MANDATORY=0;

    /**
     * Validator.
     * @param objectToValidate object to validate
     * @param mandatory true if component is mandatory
     * @param context the android context
     * @return MDKError object or null if the value is valid
     */
    public MDKError validate(Date objectToValidate, boolean mandatory, Context context) {

        MDKError mdkError = null;
        String error = null;
        if (mandatory && objectToValidate == null) {
            mdkError = new MDKError();
            mdkError.setErrorCode(ERROR_MANDATORY);
            error = context.getString(R.string.mdk_mandatory_error);
            mdkError.setErrorMessage(error);
        }

        return mdkError;
    }
}

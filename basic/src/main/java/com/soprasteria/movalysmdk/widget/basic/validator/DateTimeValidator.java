package com.soprasteria.movalysmdk.widget.basic.validator;

import android.content.Context;

import com.soprasteria.movalysmdk.widget.core.error.MDKError;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;
import com.soprasteria.movalysmdk.widget.basic.R;

import java.util.Date;

/**
 * DateTimeValidator.
 */
public class DateTimeValidator implements FormFieldValidator<Date> {

    /**
     * Constant.
     */
    public static final int ERROR_MANDATORY = R.string.mdkwidget_mandatory_error;

    /**
     * Validator.
     * @param objectToValidate object to validate
     * @param mandatory true if component is mandatory
     * @param context the android context
     * @return MDKError object or null if the value is valid
     */
    public MDKError validate(Date objectToValidate, boolean mandatory, Context context) {

        MDKError mdkError = null;
        if (mandatory && objectToValidate == null) {
            mdkError = new MDKError();
            mdkError.setErrorCode(ERROR_MANDATORY);
            String error = context.getString(R.string.mdkwidget_mandatory_error);
            mdkError.setErrorMessage(error);
        }

        return mdkError;
    }
}

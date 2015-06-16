package com.soprasteria.movalysmdk.widget.standard.validator;

import android.content.Context;

import com.soprasteria.movalysmdk.widget.core.error.MDKError;
import com.soprasteria.movalysmdk.widget.core.validator.IFormFieldValidator;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class DateTimeValidator implements IFormFieldValidator<Date> {

    public static int ERROR_MANDATORY=0;

    protected final Context context;
    protected final int mandatoryErrorId;

    public DateTimeValidator(Context context) {

        this.context = context;
        this.mandatoryErrorId = context.getResources().getIdentifier("mdk_mandatory_error", "string", context.getPackageName());
    }

    @Override
    public MDKError validate(Date objectToValidate, boolean mandatory) {

        MDKError mdkError = null;
        String error = null;
        if (mandatory && objectToValidate == null) {
            mdkError = new MDKError();
            mdkError.setErrorCode(ERROR_MANDATORY);
            error = this.context.getString(this.mandatoryErrorId);
            mdkError.setErrorMessage(error);
        }

        return mdkError;
    }
}

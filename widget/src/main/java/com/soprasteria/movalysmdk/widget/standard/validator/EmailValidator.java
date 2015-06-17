package com.soprasteria.movalysmdk.widget.standard.validator;

import android.content.Context;

import com.soprasteria.movalysmdk.widget.core.error.MDKError;
import com.soprasteria.movalysmdk.widget.standard.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * //TODO must hinerited from MandatoryValidator
 * only one error is "right" the value cannot accumulate 2 errors.
 * its mandatory OR invalid (the empty string cannot be invalidate)
 */
public class EmailValidator extends MandatoryValidator {

    public static final int ERROR_INVALID_EMAIL = 1;
    private final Pattern pattern;
    private int errorId;

    /**
     * Constructor.
     * @param context the context
     */
    public EmailValidator(Context context) {
        super(context);

        int stringId = context.getResources().getIdentifier("mdk_email_regexp", "string", context.getPackageName());

        this.errorId = context.getResources().getIdentifier("mdk_email_error", "string", context.getPackageName());

        String regExp = getContext().getString(R.string.email_regex);

        if (stringId != 0) {
            regExp = context.getString(stringId);
        }

        this.pattern = Pattern.compile(regExp);
    }

    @Override
    public MDKError validate(String objectToValidate, boolean mandatory) {

        MDKError mdkError = null;
        String error;
        if (objectToValidate != null && objectToValidate.length() > 0) {
            Matcher matcher = this.pattern.matcher(objectToValidate);
            if (!matcher.find() && this.errorId != 0) {
                    mdkError = new MDKError();
                    mdkError.setErrorCode(ERROR_INVALID_EMAIL);
                    error = this.context.getString(this.errorId);
                    mdkError.setErrorMessage(error);
            }
        } else if (mandatory) {
            // TODO merge error with super call
            mdkError = new MDKError();
            mdkError.setErrorCode(getErrorMandatory());
            error = this.context.getString(this.mandatoryErrorId);
            mdkError.setErrorMessage(error);
        }

        return mdkError;
    }
}

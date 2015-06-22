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

    /** ERROR_INVALID_EMAIL. */
    public static final int ERROR_INVALID_EMAIL = 1;
    /** Attribute for regex pattern. */
    private final Pattern pattern;

    /**
     * Constructor.
     * @param context the context
     */
    public EmailValidator(Context context) {

        int stringId = context.getResources().getIdentifier("mdk_email_regexp", "string", context.getPackageName());

        String regExp = context.getString(R.string.email_regex);

        if (stringId != 0) {
            regExp = context.getString(stringId);
        }

        this.pattern = Pattern.compile(regExp);
    }

    /**
     * Validator.
     * @param context the android context
     * @param objectToValidate object to validate
     * @param mandatory true if component is mandatory
     * @return MDKError object or null if the value is valid
     */
    @Override
    public MDKError validate(Context context, String objectToValidate, boolean mandatory) {

        MDKError mdkError = null;
        String error;
        if (objectToValidate != null && objectToValidate.length() > 0) {
            Matcher matcher = this.pattern.matcher(objectToValidate);
            if (!matcher.find() && R.string.mdk_email_error != 0) {
                    mdkError = new MDKError();
                    mdkError.setErrorCode(ERROR_INVALID_EMAIL);
                    error = context.getString(R.string.mdk_email_error);
                    mdkError.setErrorMessage(error);
            }
        } else if (mandatory) {
            mdkError = super.validate(context, objectToValidate, mandatory);
        }

        return mdkError;
    }
}

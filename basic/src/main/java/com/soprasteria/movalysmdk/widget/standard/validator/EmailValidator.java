package com.soprasteria.movalysmdk.widget.standard.validator;

import android.content.Context;

import com.soprasteria.movalysmdk.widget.core.error.MDKError;
import com.soprasteria.movalysmdk.widget.standard.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validate Email format error with regExp
 * //FIXME: uses html tags
 * The validation regexp is : '^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$'
 *
 * This validator can be parametrized by string resources :
 * the regexp : R.string.email_regex
 * the error string : R.string.mdk_email_error
 *
 * only one error is "right" the value cannot accumulate 2 errors.
 * its mandatory OR invalid (the empty string cannot be invalidate)
 */
public class EmailValidator extends MandatoryValidator {

    /**
     * ERROR_INVALID_EMAIL.
     */
    public static final int ERROR_INVALID_EMAIL = R.string.mdk_email_error;

    /**
     * Attribute for regex pattern.
     */
    private Pattern pattern;

    /**
     * Validator.
     * @param objectToValidate object to validate
     * @param mandatory true if component is mandatory
     * @param context the android context
     * @return MDKError object or null if the value is valid
     */
    public MDKError validate(String objectToValidate, boolean mandatory, Context context) {

        MDKError mdkError = null;
        if (objectToValidate != null && objectToValidate.length() > 0) {
            if (this.pattern == null) {
                String regExp = context.getString(R.string.email_regex);
                this.pattern = Pattern.compile(regExp);
            }
            Matcher matcher = this.pattern.matcher(objectToValidate);
            if (!matcher.find() && R.string.mdk_email_error != 0) {
                    mdkError = new MDKError();
                    mdkError.setErrorCode(ERROR_INVALID_EMAIL);
                    String error = context.getString(R.string.mdk_email_error);
                    mdkError.setErrorMessage(error);
            }
        } else if (mandatory) {
            mdkError = super.validate(objectToValidate, mandatory, context);
        }

        return mdkError;
    }
}

package com.soprasteria.movalysmdk.widget.standard.validator;

import android.content.Context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * //TODO must hinerited from MandatoryValidator
 * only one error is "right" the value connot cummulate 2 errors
 * its mandtory OR invalide (the empty string cannot be invalidate)
 */
public class EmailValidator extends MandatoryValidator {

    public static final String EMAIL_REGEX = "[a-z0-9!#$%&\\'*+/=?^_\\`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@" +
            "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

    private final Pattern pattern;
    private int errorId;

    public EmailValidator(Context context) {
        super(context);

        int stringId = context.getResources().getIdentifier("mdk_email_regexp", "string", context.getPackageName());

        this.errorId = context.getResources().getIdentifier("mdk_email_error", "string", context.getPackageName());

        String regExp = EMAIL_REGEX;
        if (stringId != 0) {
            regExp = context.getString(stringId);
        }

        this.pattern = Pattern.compile(regExp);
    }

    @Override
    public String validate(String objectToValidate, boolean mandatory) {
        String error = super.validate(objectToValidate, mandatory);
        if (error == null) {
            if (objectToValidate != null && objectToValidate.length() > 0) {
                Matcher matcher = this.pattern.matcher(objectToValidate);
                if (!matcher.find()) {
                    if (this.errorId != 0) {
                        error = this.getContext().getString(this.errorId);
                    }
                }
            }
        }
        return error;
    }
}

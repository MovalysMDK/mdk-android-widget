package com.soprasteria.movalysmdk.widget.standard.validator;

import android.content.Context;

import com.soprasteria.movalysmdk.widget.core.validator.IFormFieldValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by abelliard on 03/06/2015.
 */
public class EmailValidator implements IFormFieldValidator<String> {

    public static final String EMAIL_REGEX = "[a-z0-9!#$%&\\'*+/=?^_\\`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@" +
            "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

    private final Pattern pattern;
    private int errorId;
    private Context context;
    private int mandatoryErrorId;

    public EmailValidator(Context context) {

        this.context = context;

        int stringId = context.getResources().getIdentifier("mdk_email_regexp", "string", context.getPackageName());

        this.errorId = context.getResources().getIdentifier("mdk_email_error", "string", context.getPackageName());

        this.mandatoryErrorId = context.getResources().getIdentifier("mdk_mandatory_error", "string", context.getPackageName());

        String regExp = EMAIL_REGEX;
        if (stringId != 0) {
            regExp = context.getString(stringId);
        }

        this.pattern = Pattern.compile(regExp);
    }

    @Override
    public String validate(String objectToValidate, boolean mandatory) {
        String error = null;
        if (objectToValidate != null && objectToValidate.length() > 0) {
            Matcher matcher = this.pattern.matcher(objectToValidate);
            if (!matcher.find()) {
                if (this.errorId != 0) {
                    error = this.context.getString(this.errorId);
                }
            }
        } else if (mandatory) {
            error = this.context.getString(this.mandatoryErrorId);
        }
        return error;
    }
}

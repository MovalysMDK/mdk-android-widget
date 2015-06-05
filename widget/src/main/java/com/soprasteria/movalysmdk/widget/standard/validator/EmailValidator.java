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

    public EmailValidator(Context context) {

        this.context = context;

        int stringId = context.getResources().getIdentifier("mdk_email_regexp", "string", context.getPackageName());

        this.errorId = context.getResources().getIdentifier("mdk_email_error", "string", context.getPackageName());

        String regExp = EMAIL_REGEX;
        if (stringId != 0) {
            regExp = context.getString(stringId);
        }

        this.pattern = Pattern.compile(regExp);
    }

    @Override
    public String validate(String objectToValidate) {
        String error = null;
        Matcher matcher = this.pattern.matcher(objectToValidate);
        if (!matcher.find()) {
            if (this.errorId != 0) {
                error = this.context.getString(this.errorId);
            }
        }
        return error;
    }
}

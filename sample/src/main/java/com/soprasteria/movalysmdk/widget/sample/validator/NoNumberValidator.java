package com.soprasteria.movalysmdk.widget.sample.validator;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.soprasteria.movalysmdk.widget.basic.MDKRichEditText;
import com.soprasteria.movalysmdk.widget.basic.MDKRichEmail;
import com.soprasteria.movalysmdk.widget.core.error.MDKError;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;
import com.soprasteria.movalysmdk.widget.sample.R;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * No number validator.
 * <p>
 *     Check if a number is in the text of a text view. This Validator is associated with the
 *     attribute : R.attr.no_number.
 * </p>
 */
public class NoNumberValidator implements FormFieldValidator<String> {

    /**
     * no number R.string.no_number_allowed error.
     */
    public static final Integer ERROR_NONUMBER = R.string.no_number_allowed;

    /**
     * one number regexp.
     */
    public static final String NO_NUMBER_REGEX = "[0-9]+";

    /**
     * Attribute for regex pattern.
     */
    private Pattern pattern;

    @Override
    public boolean accept(View view) {
        boolean accept = false;
        if (view instanceof EditText
                || view instanceof MDKRichEditText
                || view instanceof MDKRichEmail) {
            accept = true;
        }
        return accept;
    }

    @Override
    public int[] configuration() {
        return new int[] {R.attr.no_number};
    }

    @Override
    public MDKError validate(String objectToValidate, Map<Integer, Object> mdkParameter, Map<String, MDKError> resultPreviousValidator, Context context) {
        MDKError mdkError = null;
        if ( (Boolean) mdkParameter.get(R.attr.no_number)
                && objectToValidate.length() > 0
                && !resultPreviousValidator.containsKey(this.getClass().getName()) ) {

            if (this.pattern == null) {
                this.pattern = Pattern.compile(NO_NUMBER_REGEX);
            }
            Matcher matcher = this.pattern.matcher(objectToValidate);
            if (matcher.find()) {
                mdkError = new MDKError();
                mdkError.setErrorCode(ERROR_NONUMBER);
                String error = context.getString(R.string.no_number_allowed);
                mdkError.setErrorMessage(error);
            }

            resultPreviousValidator.put(this.getClass().getName(), mdkError);
        }
        return mdkError;
    }
}

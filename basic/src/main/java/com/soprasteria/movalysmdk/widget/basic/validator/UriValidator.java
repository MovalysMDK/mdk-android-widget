package com.soprasteria.movalysmdk.widget.basic.validator;

import android.content.Context;
import android.view.View;

import com.soprasteria.movalysmdk.widget.basic.R;
import com.soprasteria.movalysmdk.widget.core.behavior.HasText;
import com.soprasteria.movalysmdk.widget.core.error.MDKMessage;
import com.soprasteria.movalysmdk.widget.core.helper.MDKAttributeSet;
import com.soprasteria.movalysmdk.widget.core.helper.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validate Uri format error with regExp.
 * <p>The validation done using a regexp</p>
 *
 * <p>This validator can be parametrized by string resources :</p>
 * <ul>
 *     <li>the regexp : R.string.mdkwidget_user_regex</li>
 *     <li>the error string : R.string.mdkwidget_uri_error</li>
 * </ul>
 *
 * <p>Only one error is "right" the value cannot accumulate 2 errors.</p>
 * <p>Its mandatory OR invalid (the empty string cannot be invalidate).</p>
 */
public class UriValidator implements FormFieldValidator<String> {

    /**
     * ERROR_INVALID_URI.
     */
    public static final int ERROR_INVALID_URI= R.string.mdkwidget_uri_error;

    /**
     * Attribute for regex pattern.
     */
    private Pattern pattern;

    @Override
    public String getIdentifier(Context context) {
        return context.getResources().getResourceName(R.string.mdkwidget_mdkuri_validator_class);
    }

    @Override
    public int[] configuration() {
        return new int[0];
    }

    @Override
    public boolean accept(View view) {
        boolean accept = false;
        if (view instanceof HasText) {
            accept = true;
        }
        return accept;
    }

    @Override
    public MDKMessage validate(String objectToValidate,
                               MDKAttributeSet mdkParameter,
                               MDKMessages resultPreviousValidator,
                               @EnumFormFieldValidator.EnumValidationMode int validationMode,
                               Context context) {

        MDKMessage mdkMessage = null;

        if (objectToValidate != null
                && objectToValidate.length() > 0
                && !resultPreviousValidator.containsKey(this.getClass().getName())) {

            if (this.pattern == null) {
                String regExp = context.getString(R.string.mdkwidget_simple_uri_regex);
                this.pattern = Pattern.compile(regExp);
            }
            Matcher matcher = this.pattern.matcher(objectToValidate);
            if ((!matcher.find() && R.string.mdkwidget_uri_error != 0)|| objectToValidate.toString().contains(" ")){
                mdkMessage = new MDKMessage();
                mdkMessage.setErrorCode(ERROR_INVALID_URI);
                String error = context.getString(R.string.mdkwidget_uri_error);
                mdkMessage.setMessage(error);
            }
        }
        resultPreviousValidator.put(this.getClass().getName(), mdkMessage);
        return mdkMessage;
    }
}

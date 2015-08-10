/**
 * Copyright (C) 2010 Sopra Steria Group (movalys.support@soprasteria.com)
 *
 * This file is part of Movalys MDK.
 * Movalys MDK is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Movalys MDK is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with Movalys MDK. If not, see <http://www.gnu.org/licenses/>.
 */
package com.soprasteria.movalysmdk.widget.sample.validator;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.soprasteria.movalysmdk.widget.basic.MDKRichEditText;
import com.soprasteria.movalysmdk.widget.basic.MDKRichEmail;
import com.soprasteria.movalysmdk.widget.core.error.MDKMessage;
import com.soprasteria.movalysmdk.widget.core.helper.MDKAttributeSet;
import com.soprasteria.movalysmdk.widget.core.helper.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;
import com.soprasteria.movalysmdk.widget.sample.R;

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
    public String getIdentifier(Context context) {
        return context.getResources().getResourceName(R.string.no_number_validator);
    }

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
    public MDKMessage validate(String objectToValidate,
                               MDKAttributeSet mdkParameter,
                               MDKMessages resultPreviousValidator,
                               @EnumFormFieldValidator.EnumValidationMode int validationMode,
                               Context context) {
        MDKMessage mdkMessage = null;
        if ( mdkParameter.getBoolean(R.attr.no_number)
                && objectToValidate.length() > 0
                && !resultPreviousValidator.containsKey(this.getClass().getName()) ) {

            if (this.pattern == null) {
                this.pattern = Pattern.compile(NO_NUMBER_REGEX);
            }
            Matcher matcher = this.pattern.matcher(objectToValidate);
            if (matcher.find()) {
                mdkMessage = new MDKMessage();
                mdkMessage.setErrorCode(ERROR_NONUMBER);
                String error = context.getString(R.string.no_number_allowed);
                mdkMessage.setMessage(error);
            }

            resultPreviousValidator.put(this.getClass().getName(), mdkMessage);
        }
        return mdkMessage;
    }
}

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
package com.soprasteria.movalysmdk.widget.core.validator;

import android.content.Context;
import android.view.View;

import com.soprasteria.movalysmdk.widget.core.behavior.HasText;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessage;
import com.soprasteria.movalysmdk.widget.core.helper.MDKAttributeSet;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validate a regex format.
 *
 * <p>This validator needs a pattern and an error message</p>
 *
 * <p>Only one error is "right" the value cannot accumulate 2 errors.</p>
 * <p>Its mandatory OR invalid (the empty string cannot be invalidate).</p>
 */
public class BaseRegexValidator implements FormFieldValidator<String> {

    /**
     * Error message.
     */
    private int errorMessageId;

    /**
     * Attribute for regex pattern.
     */
    private int regexResourceId;

    /**
     * the validator identifier.
     */
    private int identifier;

    /**
     * Attribute for regex pattern.
     */
    private Pattern pattern;

    /**
     * Constructor.
     */
    public BaseRegexValidator() {
        // Nothing to do
    }

    /**
     * Constructor.
     * @param identifier the validator identifier resource
     * @param regex the regex pattern to check
     * @param errorMessageId the error message identifier to return
     */
    public BaseRegexValidator(int identifier, int regex, int errorMessageId) {
        this.identifier = identifier;
        this.regexResourceId = regex;
        this.errorMessageId = errorMessageId;
    }

    @Override
    public String getIdentifier(Context context) {
        return context.getString(this.identifier);
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
    public int[] configuration() {
        return new int[0];
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
                String regExp = context.getString(this.regexResourceId);
                this.pattern = Pattern.compile(regExp);
            }
            Matcher matcher = this.pattern.matcher(objectToValidate);
            if (!matcher.find() && errorMessageId != 0) {
                mdkMessage = new MDKMessage();
                mdkMessage.setMessageCode(errorMessageId);
                String error = context.getString(errorMessageId);
                mdkMessage.setMessage(error);
            }
        }
        resultPreviousValidator.put(this.getClass().getName(), mdkMessage);
        return mdkMessage;
    }
}

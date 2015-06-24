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
package com.soprasteria.movalysmdk.widget.basic.validator;

import android.content.Context;

import com.soprasteria.movalysmdk.widget.core.error.MDKError;
import com.soprasteria.movalysmdk.widget.basic.R;

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
    public static final int ERROR_INVALID_EMAIL = R.string.mdkwidget_email_error;

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
                String regExp = context.getString(R.string.mdkwidget_email_regex);
                this.pattern = Pattern.compile(regExp);
            }
            Matcher matcher = this.pattern.matcher(objectToValidate);
            if (!matcher.find() && R.string.mdkwidget_email_error != 0) {
                    mdkError = new MDKError();
                    mdkError.setErrorCode(ERROR_INVALID_EMAIL);
                    String error = context.getString(R.string.mdkwidget_email_error);
                    mdkError.setErrorMessage(error);
            }
        } else if (mandatory) {
            mdkError = super.validate(objectToValidate, mandatory, context);
        }

        return mdkError;
    }
}

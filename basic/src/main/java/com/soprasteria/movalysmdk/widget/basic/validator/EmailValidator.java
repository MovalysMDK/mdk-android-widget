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
import android.view.View;

import com.soprasteria.movalysmdk.widget.basic.MDKEmail;
import com.soprasteria.movalysmdk.widget.basic.MDKRichEmail;
import com.soprasteria.movalysmdk.widget.basic.R;
import com.soprasteria.movalysmdk.widget.core.error.MDKError;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validate Email format error with regExp.
 * <p>The validation regexp is : '^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$'.</p>
 *
 * <p>This validator can be parametrized by string resources :</p>
 * <ul>
 *     <li>the regexp : R.string.mdkwidget_email_regex</li>
 *     <li>the error string : R.string.mdkwidget_email_error</li>
 * </ul>
 *
 * <p>Only one error is "right" the value cannot accumulate 2 errors.</p>
 * <p>Its mandatory OR invalid (the empty string cannot be invalidate).</p>
 */
public class EmailValidator implements FormFieldValidator<String> {

    /**
     * ERROR_INVALID_EMAIL.
     */
    public static final int ERROR_INVALID_EMAIL = R.string.mdkwidget_email_error;

    /**
     * Attribute for regex pattern.
     */
    private Pattern pattern;

    @Override
    public boolean accept(View view) {
        boolean accept = false;
        if (view instanceof MDKEmail
                || view instanceof MDKRichEmail) {
            accept = true;
        }
        return accept;
    }

    @Override
    public int[] configuration() {
        return new int[0];
    }


    @Override
    public MDKError validate(String objectToValidate, Map<Integer, Object> mdkParameter, Map<String, MDKError> resultPreviousValidator, Context context) {
        MDKError mdkError = null;
        if (objectToValidate != null
                && objectToValidate.length() > 0
                && !resultPreviousValidator.containsKey(this.getClass().getName())) {
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
        }
        resultPreviousValidator.put(this.getClass().getName(), mdkError);
        return mdkError;
    }
}

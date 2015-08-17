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
 * Validate Phone format.
 * <p>The validation regexp is : ^\+?(\([0-9-\ ]+\))?\ ?[0-9-\ ]+$.</p>
 *
 * <p>This validator can be parametrized by string resources :</p>
 * <ul>
 *     <li>the regexp : R.string.mdkwidget_phone_regex</li>
 *     <li>the error string : R.string.mdkwidget_phone_error</li>
 * </ul>
 *
 * <p>Only one error is "right" the value cannot accumulate 2 errors.</p>
 * <p>Its mandatory OR invalid (the empty string cannot be invalidate).</p>
 */
public class PhoneValidator implements FormFieldValidator<String> {

    /**
     * ERROR_INVALID_PHONE.
     */
    public static final int ERROR_INVALID_PHONE = R.string.mdkwidget_phone_error;

    /**
     * Attribute for regex pattern.
     */
    private Pattern pattern;

    @Override
    public String getIdentifier(Context context) {
        return context.getResources().getResourceName(R.string.mdkwidget_mdkphone_validator_class);
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
                String regExp = context.getString(R.string.mdkwidget_phone_regex);
                this.pattern = Pattern.compile(regExp);
            }
            Matcher matcher = this.pattern.matcher(objectToValidate);
            if (!matcher.find() && R.string.mdkwidget_phone_error != 0) {
                mdkMessage = new MDKMessage();
                mdkMessage.setErrorCode(ERROR_INVALID_PHONE);
                String error = context.getString(R.string.mdkwidget_phone_error);
                mdkMessage.setMessage(error);
            }
        }
        resultPreviousValidator.put(this.getClass().getName(), mdkMessage);
        return mdkMessage;
    }
}

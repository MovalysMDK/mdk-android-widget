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
import android.widget.CheckBox;
import android.widget.Switch;

import com.soprasteria.movalysmdk.widget.basic.MDKRichCheckable;
import com.soprasteria.movalysmdk.widget.basic.R;
import com.soprasteria.movalysmdk.widget.core.error.MDKMessage;
import com.soprasteria.movalysmdk.widget.core.helper.MDKAttributeSet;
import com.soprasteria.movalysmdk.widget.core.helper.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;

/**
 * Checkable widget validator.
 * <p>
 *     Check if a checkable widget has the specified mandatory value. This Validator is associated with the
 *     attribute : R.attr.mandatory_value.
 * </p>
 */
public class CheckableValidator implements FormFieldValidator<String> {

    /**
     * Mandatory value R.string.checkable_value_error error.
     */
    public static final Integer ERROR_VALUE = R.string.mdkwidget_error_checkable_validator;

    @Override
    public String getIdentifier(Context context) {
        return context.getResources().getResourceName(R.string.mdkwidget_checkable_validator_class);
    }

    @Override
    public boolean accept(View view) {
        boolean accept = false;
        if (view instanceof CheckBox
                || view instanceof Switch
                || view instanceof MDKRichCheckable) {
            accept = true;
        }
        return accept;
    }

    @Override
    public int[] configuration() {
        return new int[] {R.attr.mandatory_value};
    }

    @Override
    public MDKMessage validate(String objectToValidate,
                               MDKAttributeSet mdkParameter,
                               MDKMessages resultPreviousValidator,
                               @EnumFormFieldValidator.EnumValidationMode int validationMode,
                               Context context) {
        MDKMessage mdkMessage = null;
        if ( mdkParameter.containsKey(R.attr.mandatory_value)
                && objectToValidate.length() > 0
                && !resultPreviousValidator.containsKey(this.getClass().getName())
                && !String.valueOf(mdkParameter.getBoolean(R.attr.mandatory_value)).equals(objectToValidate)) {
            mdkMessage = new MDKMessage();
            mdkMessage.setErrorCode(ERROR_VALUE);
            String error = context.getString(R.string.mdkwidget_error_checkable_validator) + " " + mdkParameter.getBoolean(R.attr.mandatory_value);
            mdkMessage.setMessage(error);

            resultPreviousValidator.put(this.getClass().getName(), mdkMessage);
        }
        return mdkMessage;
    }
}

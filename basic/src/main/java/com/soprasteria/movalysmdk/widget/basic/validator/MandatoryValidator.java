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

/**
 * This validator check the mandatory settings of a component.
 *
 * If the component is mandatory and the value is empty, return
 * an empty mandatory error.
 *
 * This validator return an error code ERROR_MANDATORY and associate the
 * message R.string.mdk_mandatory_error if the value is empty and the
 * widget is mandatory.
 */
public class MandatoryValidator implements FormFieldValidator<String> {

    /**
     * ERROR_MANDATORY.
     */
    public static final int ERROR_MANDATORY = R.string.mdkwidget_mandatory_error;


    @Override
    public String getIdentifier(Context context) {
        return context.getResources().getResourceName(R.string.mdkwidget_mdkedittext_validator_class);
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
        return new int[] {R.attr.mandatory};
    }

    @Override
    public MDKMessage validate(String objectToValidate,
                               MDKAttributeSet mdkParameter,
                               MDKMessages resultPreviousValidator,
                               @EnumFormFieldValidator.EnumValidationMode int validationMode,
                               Context context) {

        MDKMessage mdkMessage = null;

        boolean isMandatory = mdkParameter.containsKey(R.attr.mandatory) && mdkParameter.getBoolean(R.attr.mandatory);

        if (validationMode != EnumFormFieldValidator.VALIDATE
            && isMandatory
            && objectToValidate.length() < 1
            && !resultPreviousValidator.containsKey(this.getClass().getName()) ) {

            mdkMessage = new MDKMessage();
            mdkMessage.setErrorCode(ERROR_MANDATORY);
            String error = context.getString(R.string.mdkwidget_mandatory_error);
            mdkMessage.setMessage(error);
        }

        resultPreviousValidator.put(this.getClass().getName(), mdkMessage);
        return mdkMessage;
    }

}

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
import com.soprasteria.movalysmdk.widget.core.behavior.HasDate;
import com.soprasteria.movalysmdk.widget.core.error.MDKMessage;
import com.soprasteria.movalysmdk.widget.core.helper.MDKAttributeSet;
import com.soprasteria.movalysmdk.widget.core.helper.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;

import java.util.Date;

/**
 * DateTimeValidator.
 */
public class DateTimeValidator implements FormFieldValidator<Date> {

    /**
     * Constant.
     */
    public static final int ERROR_MANDATORY = R.string.mdkwidget_mandatory_error;

    @Override
    public boolean accept(View view) {
        boolean accept = false;
        if (view instanceof HasDate) {
            accept = true;
        }
        return accept;
    }

    @Override
    public int[] configuration() {
        return new int[] {R.attr.mandatory};
    }


    @Override
    public MDKMessage validate(Date objectToValidate,
                               MDKAttributeSet mdkParameter,
                               MDKMessages resultPreviousValidator,
                               @EnumFormFieldValidator.EnumValidationMode int validationMode,
                               Context context) {

        MDKMessage mdkMessage = null;

        if (validationMode != EnumFormFieldValidator.VALIDATE
                && mdkParameter.containsKey(R.attr.mandatory)
                && mdkParameter.getBoolean(R.attr.mandatory)
                && objectToValidate == null
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

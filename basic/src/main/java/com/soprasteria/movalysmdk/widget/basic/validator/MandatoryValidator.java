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
import android.widget.EditText;

import com.soprasteria.movalysmdk.widget.basic.MDKRichEditText;
import com.soprasteria.movalysmdk.widget.basic.MDKRichEmail;
import com.soprasteria.movalysmdk.widget.core.error.MDKError;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;
import com.soprasteria.movalysmdk.widget.basic.R;

import java.util.Map;

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
        return new int[] {R.attr.mandatory};
    }

    @Override
    public MDKError validate(String objectToValidate, Map<Integer, Object> mdkParameter, Map<String, MDKError> resultPreviousValidator, Context context) {
        MDKError mdkError = null;
        if ( mdkParameter.get(R.attr.mandatory) != null
                && (Boolean) mdkParameter.get(R.attr.mandatory)
                && objectToValidate.length() < 1
                && !resultPreviousValidator.containsKey(this.getClass().getName()) ) {
            mdkError = new MDKError();
            mdkError.setErrorCode(ERROR_MANDATORY);
            String error = context.getString(R.string.mdkwidget_mandatory_error);
            mdkError.setErrorMessage(error);
        }
        resultPreviousValidator.put(this.getClass().getName(), mdkError);
        return mdkError;
    }

}

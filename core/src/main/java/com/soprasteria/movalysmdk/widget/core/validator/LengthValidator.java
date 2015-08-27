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

import com.soprasteria.movalysmdk.widget.core.R;
import com.soprasteria.movalysmdk.widget.core.behavior.HasText;
import com.soprasteria.movalysmdk.widget.core.error.MDKMessage;
import com.soprasteria.movalysmdk.widget.core.helper.MDKAttributeSet;
import com.soprasteria.movalysmdk.widget.core.helper.MDKMessages;

/**
 * Length validator.
 */
public class LengthValidator implements FormFieldValidator<String> {

    /**
     * max length error code.
     */
    private static final int ERROR_MAX_LENGTH = R.string.mdkvalidator_length_error_max;
    /**
     * min length error code.
     */
    private static final int ERROR_MIN_LENGTH = R.string.mdkvalidator_length_error_min;

    @Override
    public String getIdentifier(Context context) {
        return context.getResources().getResourceName(R.string.mdkvalidator_length_class);
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
        return new int[] { R.attr.maxLength, R.attr.minLength};
    }

    @Override
    public MDKMessage validate(String objectToValidate,
                               MDKAttributeSet mdkParameter,
                               MDKMessages resultPreviousValidator,
                               @EnumFormFieldValidator.EnumValidationMode int validationMode,
                               Context context) {

        MDKMessage mdkMessage = null;
        if (objectToValidate != null
                && (mdkParameter.containsKey(R.attr.maxLength)
                || mdkParameter.containsKey(R.attr.minLength))) {
            if (mdkParameter.containsKey(R.attr.maxLength)) {
                if ( objectToValidate.length() > mdkParameter.getInteger(R.attr.maxLength) ) {
                    // error
                    mdkMessage = new MDKMessage();
                    mdkMessage.setErrorCode(ERROR_MAX_LENGTH);
                    mdkMessage.setMessageType(MDKMessage.ERROR_TYPE);
                    String error = objectToValidate.length() +"/"+ mdkParameter.getInteger(R.attr.maxLength) ;
                    mdkMessage.setMessage(error);
                } else {
                    // message ex:"2/6"
                    mdkMessage = new MDKMessage();
                    mdkMessage.setErrorCode(ERROR_MAX_LENGTH);
                    mdkMessage.setMessageType(MDKMessage.MESSAGE_TYPE);
                    String error = objectToValidate.length() +"/"+ mdkParameter.getInteger(R.attr.maxLength) ;
                    mdkMessage.setMessage(error);
                }
                resultPreviousValidator.put(this.getClass().getName(), mdkMessage);
            }
            if ((mdkMessage == null || mdkMessage.getMessageType() != MDKMessage.ERROR_TYPE)
                    && mdkParameter.containsKey(R.attr.minLength)) {
                if ( objectToValidate.length() < mdkParameter.getInteger(R.attr.minLength) ) {
                    // error
                    mdkMessage = new MDKMessage();
                    mdkMessage.setErrorCode(ERROR_MIN_LENGTH);
                    mdkMessage.setMessageType(MDKMessage.ERROR_TYPE);
                    String error = objectToValidate.length() +"/"+ mdkParameter.getInteger(R.attr.minLength)+"+" ;
                    mdkMessage.setMessage(error);
                } else {
                    // message ex:"6/2+"
                    mdkMessage = new MDKMessage();
                    mdkMessage.setErrorCode(ERROR_MIN_LENGTH);
                    mdkMessage.setMessageType(MDKMessage.MESSAGE_TYPE);
                    String error = objectToValidate.length() +"/"+ mdkParameter.getInteger(R.attr.minLength)+"+" ;
                    mdkMessage.setMessage(error);
                }
                resultPreviousValidator.put(this.getClass().getName(), mdkMessage);
            }
        }
        return mdkMessage;
    }
}

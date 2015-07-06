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
import com.soprasteria.movalysmdk.widget.basic.R;
import com.soprasteria.movalysmdk.widget.core.error.MDKMessage;
import com.soprasteria.movalysmdk.widget.core.helper.MDKAttributeSet;
import com.soprasteria.movalysmdk.widget.core.helper.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;

/**
 * Length validator.
 */
public class LengthValidator implements FormFieldValidator<String> {

    /**
     * max length error code.
     */
    //FIXME pas homogène avec les autres validateurs : int ?
    private static final Integer ERROR_MAX_LENGTH = R.string.mdkwidget_max_length_error;
    /**
     * min length error code.
     */
    //FIXME pas homogène avec les autres validateurs : int ?
    private static final Integer ERROR_MIN_LENGTH = R.string.mdkwidget_min_length_error;

    @Override
    public boolean accept(View view) {
        boolean accept = false;
        //FIXME utiliser hasText
        if (view instanceof EditText
                || view instanceof MDKRichEditText
                || view instanceof MDKRichEmail) {
            accept = true;
        }
        return accept;
    }

    @Override
    public int[] configuration() {
        return new int[] { R.attr.maxLength, R.attr.minLength};
    }

    @Override
    public MDKMessage validate(String objectToValidate, MDKAttributeSet mdkParameter, MDKMessages resultPreviousValidator, Context context) {
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

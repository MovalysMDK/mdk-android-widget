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
import com.soprasteria.movalysmdk.widget.core.behavior.types.HasSeekBar;
import com.soprasteria.movalysmdk.widget.core.helper.MDKAttributeSet;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessage;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;

/**
 * Validate SeekBar format error with regExp.
 * <p>The validation done using a regexp</p>
 *
 * <p>This validator can be parametrized by string resources :</p>
 * <ul>
 *     <li>the error string : R.string.mdkwidget_seekbar_error</li>
 * </ul>
 *
 * <p>Only one error is "right" the value cannot accumulate 2 errors.</p>
 * <p>Its mandatory OR invalid (the empty string cannot be invalidate).</p>
 */
public class SeekBarValidator implements FormFieldValidator<Integer> {

    /**
     * ERROR_INVALID_SB.
     */
    public static final int ERROR_INVALID_SB_VALUE = R.string.mdkvalidator_seekbar_error_invalid;

    @Override
    public String getIdentifier(Context context) {
        return context.getResources().getResourceName(R.string.mdkvalidator_seekbar_class);
    }

    @Override
    public int[] configuration() {
        return new int[0];
    }

    @Override
    public boolean accept(View view) {
        boolean accept = false;
        if (view instanceof HasSeekBar) {
            accept = true;
        }
        return accept;
    }

    @Override
    public MDKMessage validate(Integer objectToValidate,
                               MDKAttributeSet mdkAttributeSet,
                               MDKMessages resultPreviousValidator,
                               @EnumFormFieldValidator.EnumValidationMode int validationMode,
                               Context context) {

        MDKMessage mdkMessage = null;

        if (objectToValidate != null
                && !resultPreviousValidator.containsKey(this.getClass().getName())) {

            /** Check that the current value does not exceed the maximum one if the attribute
             * maxSeekBarValue is defined into the widget.*/

            int currentValueToValidate = objectToValidate;

            mdkMessage = executeValidation(currentValueToValidate, context, mdkAttributeSet);

        }

        resultPreviousValidator.put(this.getClass().getName(), mdkMessage);
        return mdkMessage;
    }

    /**
     * Determines what error message must be used if applicable.
     * @param valueToValidate the value of the seekbar.
     * @param context the context of the message.
     * @param mdkAttributeSet the attributes of the seekbar.
     * @return the error message or null if validation passed.
     */
    private MDKMessage executeValidation(int valueToValidate, Context context, MDKAttributeSet mdkAttributeSet){
        MDKMessage mdkMessage = null;

        if (mdkAttributeSet.containsKey(R.attr.maxAllowed)&&mdkAttributeSet.containsKey(R.attr.minAllowed)
                && (valueToValidate > mdkAttributeSet.getInteger(R.attr.maxAllowed) || valueToValidate < mdkAttributeSet.getInteger(R.attr.minAllowed))){
            mdkMessage = prepareErrorMessage(context.getString(R.string.mdkvalidator_seekbar_error_max_min, String.valueOf(mdkAttributeSet.getInteger(R.attr.minAllowed)), String.valueOf(mdkAttributeSet.getInteger(R.attr.maxAllowed))));
        }else if((mdkAttributeSet.containsKey(R.attr.maxAllowed))
                && (valueToValidate > mdkAttributeSet.getInteger(R.attr.maxAllowed))) {
            mdkMessage = prepareErrorMessage(context.getString(R.string.mdkvalidator_seekbar_error_max, String.valueOf(mdkAttributeSet.getInteger(R.attr.maxAllowed))));
        }else if ((mdkAttributeSet.containsKey(R.attr.minAllowed))
                && (valueToValidate < mdkAttributeSet.getInteger(R.attr.minAllowed))) {
            mdkMessage = prepareErrorMessage(context.getString(R.string.mdkvalidator_seekbar_error_min, String.valueOf(mdkAttributeSet.getInteger(R.attr.minAllowed))));
        }

        return mdkMessage;
    }

    /**
     * Prepares the error message to deliver.
     * @param errorStr the error string to put in the message.
     * @return the error message.
     */
    private MDKMessage prepareErrorMessage(String errorStr){
        MDKMessage mdkMessage = new MDKMessage();
        mdkMessage.setMessageCode(ERROR_INVALID_SB_VALUE);
        mdkMessage.setMessageType(MDKMessage.ERROR_TYPE);
        mdkMessage.setMessage(errorStr);
        return mdkMessage;
    }
}

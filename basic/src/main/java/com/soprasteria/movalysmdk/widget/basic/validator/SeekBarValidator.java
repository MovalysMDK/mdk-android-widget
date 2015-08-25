package com.soprasteria.movalysmdk.widget.basic.validator;

import android.content.Context;
import android.view.View;

import com.soprasteria.movalysmdk.widget.basic.R;
import com.soprasteria.movalysmdk.widget.core.behavior.HasSeekBar;
import com.soprasteria.movalysmdk.widget.core.error.MDKMessage;
import com.soprasteria.movalysmdk.widget.core.helper.MDKAttributeSet;
import com.soprasteria.movalysmdk.widget.core.helper.MDKMessages;
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
    public static final int ERROR_INVALID_SB_VALUE = R.string.mdkwidget_seekbar_error;

    @Override
    public String getIdentifier(Context context) {
        return context.getResources().getResourceName(R.string.mdkwidget_mdkseekbar_validator_class);
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

            if ((mdkAttributeSet.containsKey(R.attr.maxSeekBarValue))
                    &&(currentValueToValidate > mdkAttributeSet.getInteger(R.attr.maxSeekBarValue))){
                mdkMessage = new MDKMessage();
                mdkMessage.setErrorCode(ERROR_INVALID_SB_VALUE);
                mdkMessage.setMessageType(MDKMessage.MESSAGE_TYPE);
                String error = context.getString(R.string.mdkwidget_seekbar_max_value_error)+ " " + mdkAttributeSet.getInteger(R.attr.maxSeekBarValue) ;
                mdkMessage.setMessage(error);
            }
        }

        resultPreviousValidator.put(this.getClass().getName(), mdkMessage);
        return mdkMessage;
    }
}

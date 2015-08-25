package com.soprasteria.movalysmdk.widget.position.validator;

import android.content.Context;
import android.view.View;

import com.soprasteria.movalysmdk.widget.core.behavior.HasLocation;
import com.soprasteria.movalysmdk.widget.core.error.MDKMessage;
import com.soprasteria.movalysmdk.widget.core.helper.MDKAttributeSet;
import com.soprasteria.movalysmdk.widget.core.helper.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;
import com.soprasteria.movalysmdk.widget.position.R;

/**
 * This validator check the mandatory settings of a component.
 *
 * If the component is mandatory and the value is empty, return
 * an empty mandatory error.
 *
 * This validator return an error code ERROR_MANDATORY and associate the
 * message R.string.mdkposition_mandatory_error if the value is empty and the
 * widget is mandatory.
 */
public class MultiMandatoryValidator implements FormFieldValidator<String[]> {

    /**
     * ERROR_MANDATORY.
     */
    public static final int ERROR_MANDATORY = R.string.mdkposition_mandatory_error;


    @Override
    public String getIdentifier(Context context) {
        return context.getResources().getResourceName(R.string.mdkwidget_mdkposition_validator_class);
    }

    @Override
    public boolean accept(View view) {
        boolean accept = false;
        if (view instanceof HasLocation) {
            accept = true;
        }
        return accept;
    }

    @Override
    public int[] configuration() {
        return new int[] {R.attr.mandatory};
    }

    @Override
    public MDKMessage validate(String[] objectToValidate,
                               MDKAttributeSet mdkParameter,
                               MDKMessages resultPreviousValidator,
                               @EnumFormFieldValidator.EnumValidationMode int validationMode,
                               Context context) {

        MDKMessage mdkMessage = null;

        boolean isMandatory = mdkParameter.containsKey(R.attr.mandatory) && mdkParameter.getBoolean(R.attr.mandatory);

        if (isMandatory
                && !resultPreviousValidator.containsKey(this.getClass().getName()) ) {

            boolean isFilled = objectToValidate.length > 0;

            if (isFilled) {
                for (String object : objectToValidate) {
                    isFilled &= object != null && object.length() > 0;
                }
            }

            if (!isFilled) {
                mdkMessage = new MDKMessage();
                mdkMessage.setErrorCode(ERROR_MANDATORY);
                String error = context.getString(ERROR_MANDATORY);
                mdkMessage.setMessage(error);
            }
        }

        resultPreviousValidator.put(this.getClass().getName(), mdkMessage);
        return mdkMessage;
    }
}

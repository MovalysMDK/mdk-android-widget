package com.soprasteria.movalysmdk.widget.position.validator;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.soprasteria.movalysmdk.widget.core.behavior.types.HasPosition;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessage;
import com.soprasteria.movalysmdk.widget.core.helper.MDKAttributeSet;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;
import com.soprasteria.movalysmdk.widget.position.R;
import com.soprasteria.movalysmdk.widget.core.behavior.model.Position;

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
public class PositionValidator implements FormFieldValidator<Position> {

    /** ERROR_MANDATORY. */
    public static final int ERROR_MANDATORY = R.string.mdkvalidator_position_error_validation_mandatory;

    /** ERROR_INCORRECT. */
    public static final int ERROR_INCORRECT = R.string.mdkvalidator_position_error_validation_incorrect;

    @Override
    public String getIdentifier(Context context) {
        return context.getResources().getResourceName(R.string.mdkvalidator_position_class);
    }

    @Override
    public boolean accept(View view) {
        boolean accept = false;
        if (view instanceof HasPosition) {
            accept = true;
        }
        return accept;
    }

    @Override
    public int[] configuration() {
        return new int[] {R.attr.mandatory};
    }

    @Override
    public MDKMessage validate(Position objectToValidate,
                               MDKAttributeSet mdkParameter,
                               MDKMessages resultPreviousValidator,
                               @EnumFormFieldValidator.EnumValidationMode int validationMode,
                               Context context) {

        MDKMessage mdkMessage = null;

        boolean isMandatory = mdkParameter.containsKey(R.attr.mandatory) && mdkParameter.getBoolean(R.attr.mandatory);

        if (isMandatory && !resultPreviousValidator.containsKey(this.getClass().getName()) ) {
            boolean isFilled = objectToValidate.getLatitude() != null && objectToValidate.getLongitude() != null;

            if (!isFilled) {
                mdkMessage = new MDKMessage();
                mdkMessage.setMessageCode(ERROR_MANDATORY);
                String error = context.getString(ERROR_MANDATORY);
                mdkMessage.setMessage(error);
            } else {
                boolean isCorrect = true;

                try {
                    // we check that the input value are correct
                    isCorrect &= objectToValidate.getLatitude() >= -90 && objectToValidate.getLatitude() <= 90;
                    isCorrect &= objectToValidate.getLongitude() >= -180 && objectToValidate.getLongitude() <= 180;
                } catch (NumberFormatException e) {
                    Log.e(this.getClass().getSimpleName(), "NumberFormatException", e);
                    isCorrect = false;
                }

                if (!isCorrect) {
                    mdkMessage = new MDKMessage();
                    mdkMessage.setMessageCode(ERROR_INCORRECT);
                    String error = context.getString(ERROR_INCORRECT);
                    mdkMessage.setMessage(error);
                }
            }
        }

        resultPreviousValidator.put(this.getClass().getName(), mdkMessage);
        return mdkMessage;
    }
}

package com.soprasteria.movalysmdk.widget.standard.validator;

import android.content.Context;

import com.soprasteria.movalysmdk.widget.core.error.MDKError;
import com.soprasteria.movalysmdk.widget.core.validator.IFormFieldValidator;

/**
 * class MandatoryValidator
 * Created by abelliard on 11/06/2015.
 */
public class MandatoryValidator implements IFormFieldValidator<String> {

    private static final int ERROR_MANDATORY=0;

    protected final Context context;
    protected final int mandatoryErrorId;

    /**
     * Constructor.
     * @param context the context
     */
    public MandatoryValidator(Context context) {
        this.context = context;

        this.mandatoryErrorId = context.getResources().getIdentifier("mdk_mandatory_error", "string", context.getPackageName());
    }

    /**
     * Get the context
     * @return  MDKError mdkError
     */
    public Context getContext() {
        return this.context;
    }

    @Override
    public MDKError validate(String objectToValidate, boolean mandatory) {
        MDKError mdkError = null;
        String error = null;
        if (mandatory && objectToValidate.length() < 1) {
            mdkError = new MDKError();
            mdkError.setErrorCode(ERROR_MANDATORY);
            error = this.context.getString(this.mandatoryErrorId);
            mdkError.setErrorMessage(error);
        }
        return mdkError;
    }

    /**
     * Get error mandatory.
     * @return ERROR_MANDATORY ERROR_MANDATORY
     */
    public static int getErrorMandatory() {
        return ERROR_MANDATORY;
    }

    /**
     * Get mandatory error id.
     * @return mandatoryErrorId the mandatory error id
     */
    public int getMandatoryErrorId() {
        return mandatoryErrorId;
    }
}

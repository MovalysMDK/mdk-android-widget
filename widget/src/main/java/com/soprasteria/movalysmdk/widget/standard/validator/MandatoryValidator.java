package com.soprasteria.movalysmdk.widget.standard.validator;

import android.content.Context;

import com.soprasteria.movalysmdk.widget.core.error.MDKError;
import com.soprasteria.movalysmdk.widget.core.validator.IFormFieldValidator;

/**
 * Created by abelliard on 11/06/2015.
 */
public class MandatoryValidator implements IFormFieldValidator<String> {

    public static int ERROR_MANDATORY=0;

    protected final Context context;
    protected final int mandatoryErrorId;

    public MandatoryValidator(Context context) {
        this.context = context;

        this.mandatoryErrorId = context.getResources().getIdentifier("mdk_mandatory_error", "string", context.getPackageName());
    }

    /**
     * return the associated context
     * @return
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
}

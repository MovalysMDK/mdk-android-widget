package com.soprasteria.movalysmdk.widget.standard.validator;

import android.content.Context;

import com.soprasteria.movalysmdk.widget.core.validator.IFormFieldValidator;

/**
 * Created by abelliard on 11/06/2015.
 */
public class MandatoryValidator implements IFormFieldValidator<String> {

    private final Context context;
    private final int mandatoryErrorId;

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
    public String validate(String objectToValidate, boolean mandatory) {
        String error = null;
        if (mandatory && objectToValidate.length() < 1) {
            error = this.context.getString(this.mandatoryErrorId);
        }
        return error;
    }
}

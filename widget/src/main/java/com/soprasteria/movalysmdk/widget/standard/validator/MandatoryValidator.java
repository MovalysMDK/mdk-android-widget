package com.soprasteria.movalysmdk.widget.standard.validator;

import android.content.Context;

import com.soprasteria.movalysmdk.widget.core.error.MDKError;
import com.soprasteria.movalysmdk.widget.core.validator.IFormFieldValidator;

import java.util.regex.Matcher;

/**
 * Created by abelliard on 11/06/2015.
 */
public class MandatoryValidator implements IFormFieldValidator<String> {

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
        MDKError mdkError = new MDKError();
        String error = null;
        if (mandatory && objectToValidate.length() < 1) {
            error = this.context.getString(this.mandatoryErrorId);
        }
        mdkError.setErrorMessage(error);
        return mdkError;
    }
}

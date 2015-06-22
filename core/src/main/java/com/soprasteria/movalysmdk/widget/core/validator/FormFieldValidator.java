package com.soprasteria.movalysmdk.widget.core.validator;

import android.content.Context;

import com.soprasteria.movalysmdk.widget.core.error.MDKError;

/**
 * Interface for Validator definition.
 * @param <T> the type of data to validate.
 */
public interface FormFieldValidator<T> {

    /**
     * Validate the parameter objectToValidate.
     * @param context the android context
     * @param objectToValidate the value to validate
     * @param mandatory specify if mandatory
     * @return a MDKError containing the error or null
     */
    MDKError validate(Context context, T objectToValidate, boolean mandatory);
}

package com.soprasteria.movalysmdk.widget.core.validator;

import com.soprasteria.movalysmdk.widget.core.error.MDKError;

/**
 * Interface for Validator definition.
 * @param <T> the type of data to validate.
 */
public interface FormFieldValidator<T> {

    /**
     * Validate the parameter objectToValidate.
     * @param objectToValidate the value to validate
     * @param mandatory specify if mandatory
     * @return a MDKError containing the error or null
     */
    MDKError validate(T objectToValidate, boolean mandatory);
}

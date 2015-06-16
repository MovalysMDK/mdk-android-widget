package com.soprasteria.movalysmdk.widget.core.validator;

import com.soprasteria.movalysmdk.widget.core.error.MDKError;

/**
 * Interface for Validator implementation
 * @param <T> the type of data to validate.
 */
public interface IFormFieldValidator<T> {

    /**
     * validate the parameter value.
     * @param objectToValidate the value to validate
     * @param mandatory
     * @return a string of the error or null
     */
    public MDKError validate(T objectToValidate, boolean mandatory);
}

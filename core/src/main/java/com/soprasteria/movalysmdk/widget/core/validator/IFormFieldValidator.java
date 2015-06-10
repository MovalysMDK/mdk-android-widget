package com.soprasteria.movalysmdk.widget.core.validator;

/**
 * Interface for Validator implementation
 * @param <T> the type of data to validate
 */
public interface IFormFieldValidator<T> {

    /**
     * validate the parameter value
     * @param objectToValidate the value to validate
     * @param mandatory
     * @return a string of the error or null
     */
    public String validate(T objectToValidate, boolean mandatory);
}

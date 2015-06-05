package com.soprasteria.movalysmdk.widget.core.validator;

/**
 * TODO
 * Created by abelliard on 03/06/2015.
 */
public interface IFormFieldValidator<T> {

    /**
     * validate the parameter value
     * @param objectToValidate the value to validate
     * @return a string of the error or null
     */
    public String validate(T objectToValidate);
}

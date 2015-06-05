package com.soprasteria.movalysmdk.widget.core.behavior;

import com.soprasteria.movalysmdk.widget.core.validator.IFormFieldValidator;

/**
 * Interface to add validation capacity to a widget
 *
 *
 */
public interface HasValidator {

    /**
     * Get widget validator
     * @return the widget validator
     */
    IFormFieldValidator getValidator();

    /**
     * validate the widget
     * basically execute the validation on the validator
     * @return true if the wiget is valide, false otherwise
     */
    boolean validate();

}

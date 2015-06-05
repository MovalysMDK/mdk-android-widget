package com.soprasteria.movalysmdk.widget.core.behavior;

import com.soprasteria.movalysmdk.widget.core.validator.IFormFieldValidator;

/**
 * Use in component to retrieve it's validator.
 * <p>
 * Each component that contains a validator should implement this interface. The interface
 * method is call as a callback from the component delegate to get the form validator of a
 * component.
 * </p>
 * <p>This implementation has been choose to limit the methods count in android compiled
 * file</p>
 *
 */
public interface HasValidator {

    /**
     * Get Component validator
     * @return the widget validator
     */
    IFormFieldValidator getValidator();

    /**
     * validate the component
     * @return true if the component is valide, false otherwise
     */
    boolean validate();

}

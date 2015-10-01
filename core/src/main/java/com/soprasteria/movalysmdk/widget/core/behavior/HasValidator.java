/**
 * Copyright (C) 2010 Sopra Steria Group (movalys.support@soprasteria.com)
 *
 * This file is part of Movalys MDK.
 * Movalys MDK is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Movalys MDK is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with Movalys MDK. If not, see <http://www.gnu.org/licenses/>.
 */
package com.soprasteria.movalysmdk.widget.core.behavior;

import com.soprasteria.movalysmdk.widget.core.message.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;

/**
 * Interface to add validation capacity to a widget.
 * @param <T> the type of the object to be validate
 */
public interface HasValidator<T> {

    /**
     * Get widget validator keys.
     * @return an array of the widget validator keys
     */
    int[] getValidators();

    /**
     * Validate the widget.
     * <p>Basically execute all compatible validator for the widget</p>
     * @return true if the widget is valid, false otherwise
     */
    boolean validate();

    /**
     * Validate the widget.
     * <p>Basically execute all compatible validator for the widget</p>
     * @param validationMode Enumerate according validation mode: VALIDATE, ON_FOCUS, ON_USER
     * @return true if the widget is valid, false otherwise
     */
    boolean validate(@EnumFormFieldValidator.EnumValidationMode int validationMode);

    /**
     * Set the error message on the widget.
     * @param error the error message
     */
    void setError(CharSequence error);

    /**
     * Set the mdk error on the widget.
     * @param error the mdk error
     */
    void addError(MDKMessages error);

    /**
     * Remove error on the widget.
     */
    void clearError();

    /**
     * Returns the value to be validated on the component.
     * @return the object to be validated
     */
    T getValueToValidate();
}

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
package com.soprasteria.movalysmdk.widget.core.validator;

import android.content.Context;
import android.view.View;

import com.soprasteria.movalysmdk.widget.core.error.MDKMessage;
import com.soprasteria.movalysmdk.widget.core.helper.MDKAttributeSet;
import com.soprasteria.movalysmdk.widget.core.helper.MDKMessages;

/**
 * Validation of form field.
 * <p>
 *     This interface is used to create validator for widgets.<br/>
 *     Validators can have multiple attributes defined throw the <em>configuration</em> method, those
 *     attributes must be given from <em>R.attr.*</em> resources.<br/>
 *     A validator can <em>accept</em> only a subset of widgets this subset is defined in the accept
 *     method. This method return true only if the given <em>View</em> is validate by the Validator.
 * </p>
 * <p>
 *     When the <em>validate</em> method is called the <em>mdkParameter</em> contains all the attributes
 *     from the widget. All validators are called if the specified attributes in <em>configuration</em>
 *     method match a attribute in the widget. <em>validate</em> may be called multiple times in the same
 *     widget, <em>Validator</em> must check the <em>resultPreviousValidator</em> before validating the
 *     widget.
 * </p>
 * @param <T> the type of data to validate.
 */
public interface FormFieldValidator<T> {

    /**
     * Return the Validator configuration.
     * <p>The array is the attributes references handled by the validator</p>
     * <p>For exemple : {R.attr.mandatory} for the mandatory Validator</p>
     * @return an integer array representing the R.attr handled by this validator
     */
    int[] configuration();

    /**
     * Return if the validator handle the current widget.
     * @param view the widget that need validation
     * @return true if the Validator accept the widget, false otherwise
     */
    boolean accept(View view);

    /**
     * Validate the parameter objectToValidate.
     * @param objectToValidate the value to validate
     * @param mdkParameter map containing the parameter to handle in the validator (this map is modified in validator)
     * @param resultPreviousValidator map containing the result of the preceding validators, this method must filled this map
     * @param validationMode Enumerate according validation mode: VALIDATE, ON_FOCUS, ON_USER
     * @param context the android context
     * @return a MDKError containing the error or null
     */
    MDKMessage validate(T objectToValidate, MDKAttributeSet mdkParameter, MDKMessages resultPreviousValidator, @EnumFormFieldValidator.EnumValidationMode int validationMode, Context context);
}

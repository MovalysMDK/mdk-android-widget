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

import com.soprasteria.movalysmdk.widget.core.error.MDKError;

/**
 * Validation of form field.
 * //FIXME: add more documentation
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
    MDKError validate(T objectToValidate, boolean mandatory, Context context);
}

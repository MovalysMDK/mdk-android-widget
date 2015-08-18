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

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Abstract class on validation mode
 * <p>
 *     This abstract class is used to access to available validation modes.<br/>
 * </p>
 */
public abstract class EnumFormFieldValidator implements FormFieldValidator {

    /** EnumValidationMode validation mode enumeration. */
    @IntDef({VALIDATE, ON_FOCUS, ON_USER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface EnumValidationMode {
    }

    /** VALIDATE mode. */
    public static final int VALIDATE = 0;
    /** ON_FOCUS mode. */
    public static final int ON_FOCUS = 1;
    /** ON_USER mode. */
    public static final int ON_USER = 2;
}

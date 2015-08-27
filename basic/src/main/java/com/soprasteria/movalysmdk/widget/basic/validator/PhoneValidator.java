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
package com.soprasteria.movalysmdk.widget.basic.validator;

import com.soprasteria.movalysmdk.widget.basic.R;
import com.soprasteria.movalysmdk.widget.core.validator.BaseRegexValidator;

/**
 * Validate Phone format.
 * <p>The validation regexp is : ^\+?(\([0-9-\ ]+\))?\ ?[0-9-\ ]+$.</p>
 *
 * <p>This validator can be parametrized by string resources :</p>
 * <ul>
 *     <li>the regexp : R.string.mdkwidget_phone_regex</li>
 *     <li>the error string : R.string.mdkwidget_phone_error</li>
 * </ul>
 *
 * <p>Only one error is "right" the value cannot accumulate 2 errors.</p>
 * <p>Its mandatory OR invalid (the empty string cannot be invalidate).</p>
 */
public class PhoneValidator extends BaseRegexValidator {

    /**
     * Constructor.
     */
    public PhoneValidator() {
        super(R.string.mdkvalidator_phone_class, R.string.mdkvalidator_phone_regex, R.string.mdkvalidator_phone_error_invalid);
    }

}

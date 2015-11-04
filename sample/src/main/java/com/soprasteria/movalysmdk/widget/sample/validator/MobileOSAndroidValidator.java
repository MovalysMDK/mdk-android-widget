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
package com.soprasteria.movalysmdk.widget.sample.validator;

import android.content.Context;
import android.view.View;

import com.soprasteria.movalysmdk.widget.basic.MDKEnumView;
import com.soprasteria.movalysmdk.widget.core.helper.MDKAttributeSet;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessage;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;
import com.soprasteria.movalysmdk.widget.sample.R;
import com.soprasteria.movalysmdk.widget.sample.enums.MobileOS;


/**
 * EnumView OS validator.
 * <p>
 *     Checks the value of the enum associated to an enumview of MobileOS.
 *     Validates that it has the value Android.
 * </p>
 */
public class MobileOSAndroidValidator implements FormFieldValidator<MobileOS> {

    /**
     * Wrong OS error.
     */
    public static final Integer ERROR_WRONG_OS = R.string.wrong_os;



    @Override
    public String getIdentifier(Context context) {
        return context.getResources().getResourceName(R.string.mobileos_android_validator);
    }

    @Override
    public boolean accept(View view) {
        boolean accept = false;
        if (view instanceof MDKEnumView) {
            accept = true;
        }
        return accept;
    }

    @Override
    public int[] configuration() {
        return new int[] {R.attr.enum_mobileos_android_validator};
    }

    @Override
    public MDKMessage validate(MobileOS objectToValidate,
                               MDKAttributeSet mdkParameter,
                               MDKMessages resultPreviousValidator,
                               @EnumFormFieldValidator.EnumValidationMode int validationMode,
                               Context context) {
        MDKMessage mdkMessage = null;
        if ( mdkParameter.getBoolean(R.attr.enum_mobileos_android_validator)
                && !resultPreviousValidator.containsKey(this.getClass().getName()) ) {

            if (objectToValidate!=MobileOS.ANDROID) {
                mdkMessage = new MDKMessage();
                mdkMessage.setMessageCode(ERROR_WRONG_OS);
                String error = context.getString(R.string.wrong_os);
                mdkMessage.setMessage(error);
            }

            resultPreviousValidator.put(this.getClass().getName(), mdkMessage);
        }
        return mdkMessage;
    }
}

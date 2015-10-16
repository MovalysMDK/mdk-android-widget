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
import com.soprasteria.movalysmdk.widget.sample.enums.BabyAnimals;


/**
 * No number validator.
 * <p>
 *     Check if a number is in the text of a text view. This Validator is associated with the
 *     attribute : R.attr.no_number.
 * </p>
 */
public class BabyAnimalsPuppyValidator implements FormFieldValidator<BabyAnimals> {

    /**
     * Wrong animal error.
     */
    public static final Integer ERROR_WRONG_ANIMAL = R.string.wrong_animal;



    @Override
    public String getIdentifier(Context context) {
        return context.getResources().getResourceName(R.string.baby_animals_validator);
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
        return new int[] {R.attr.enum_animals_puppy_validator};
    }

    @Override
    public MDKMessage validate(BabyAnimals objectToValidate,
                               MDKAttributeSet mdkParameter,
                               MDKMessages resultPreviousValidator,
                               @EnumFormFieldValidator.EnumValidationMode int validationMode,
                               Context context) {
        MDKMessage mdkMessage = null;
        if ( mdkParameter.getBoolean(R.attr.enum_animals_puppy_validator)
                && !resultPreviousValidator.containsKey(this.getClass().getName()) ) {

            if (objectToValidate!=BabyAnimals.PUPPY) {
                mdkMessage = new MDKMessage();
                mdkMessage.setMessageCode(ERROR_WRONG_ANIMAL);
                String error = context.getString(R.string.wrong_animal);
                mdkMessage.setMessage(error);
            }

            resultPreviousValidator.put(this.getClass().getName(), mdkMessage);
        }
        return mdkMessage;
    }
}

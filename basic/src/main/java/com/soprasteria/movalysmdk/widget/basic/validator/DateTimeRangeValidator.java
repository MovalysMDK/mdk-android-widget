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

import android.content.Context;
import android.view.View;

import com.soprasteria.movalysmdk.widget.basic.R;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDate;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessage;
import com.soprasteria.movalysmdk.widget.core.helper.MDKAttributeSet;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DateTime range validator.
 * The validation will be ok if the date to be validated is in the range of minDate / maxDate.
 */
public class DateTimeRangeValidator implements FormFieldValidator<Date> {

    /** Error on the defined range. */
    public static final int ERROR_RANGE = R.string.mdkvalidator_datetimerange_error_range;

    /** Error when parsing dates to validate. */
    public static final int ERROR_DATE_VALIDATION = R.string.mdkvalidator_datetimerange_error_validation;

    @Override
    public String getIdentifier(Context context) {
        return context.getResources().getResourceName(R.string.mdkvalidator_datetimerange_class);
    }

    @Override
    public boolean accept(View view) {
        boolean accept = false;
        if (view instanceof HasDate) {
            accept = true;
        }
        return accept;
    }

    @Override
    public MDKMessage validate(Date objectToValidate,
                               MDKAttributeSet mdkParameter,
                               MDKMessages resultPreviousValidator,
                               @EnumFormFieldValidator.EnumValidationMode int validationMode,
                               Context context) {
        MDKMessage mdkMessage = null;
        Date minDate = null;
        Date maxDate = null;

        if (objectToValidate != null && mdkParameter.containsKey(R.attr.dateFormat)) {
            SimpleDateFormat formattedDatePattern = new SimpleDateFormat(mdkParameter.getValue(R.attr.dateFormat));

            try {
                if (mdkParameter.containsKey(R.attr.min) && mdkParameter.getValue(R.attr.min) != null) {
                    minDate = formattedDatePattern.parse(mdkParameter.getValue(R.attr.min));
                }
                if (mdkParameter.containsKey(R.attr.max) && mdkParameter.getValue(R.attr.max) != null) {
                    maxDate = formattedDatePattern.parse(mdkParameter.getValue(R.attr.max));
                }

                String error = "";

                if (minDate != null && objectToValidate.before(minDate)) {
                    error = context.getString(R.string.mdkvalidator_datetimerange_error_min, mdkParameter.getValue(R.attr.min),
                            formattedDatePattern.format(objectToValidate));
                    error += "\\n";
                }

                if (maxDate != null && objectToValidate.after(maxDate)) {
                    error += context.getString(R.string.mdkvalidator_datetimerange_error_max, mdkParameter.getValue(R.attr.max),
                            formattedDatePattern.format(objectToValidate));
                }

                if (error.length() > 0) {
                    mdkMessage = new MDKMessage();
                    mdkMessage.setMessageCode(ERROR_RANGE);
                    mdkMessage.setMessage(error);
                }
            } catch (ParseException e) {
                mdkMessage = new MDKMessage();
                mdkMessage.setMessageCode(ERROR_DATE_VALIDATION);
                mdkMessage.setMessage(context.getString(R.string.mdkvalidator_datetimerange_error_validation));
            }
        }

        resultPreviousValidator.put(this.getClass().getName(), mdkMessage);
        return mdkMessage;
    }

    @Override
    public int[] configuration() {
        return new int[]{R.attr.min, R.attr.max};
    }
}

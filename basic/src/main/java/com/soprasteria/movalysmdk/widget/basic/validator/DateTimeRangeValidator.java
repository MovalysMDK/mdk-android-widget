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

import com.soprasteria.movalysmdk.widget.basic.MDKDateTime;
import com.soprasteria.movalysmdk.widget.basic.R;
import com.soprasteria.movalysmdk.widget.core.MDKBaseRichDateWidget;
import com.soprasteria.movalysmdk.widget.core.error.MDKMessage;
import com.soprasteria.movalysmdk.widget.core.helper.MDKAttributeSet;
import com.soprasteria.movalysmdk.widget.core.helper.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DateTimeRangeValidator.
 */
public class DateTimeRangeValidator  implements FormFieldValidator<Date> {

    /**
     * Constant.
     */
    public static final int ERROR_MANDATORY = R.string.mdkwidget_mandatory_error;

    /**
     * Constant.
     */
    public static final int ERROR_DATE = R.string.mdkwidget_error_date_range_validator;

    /**
     * Constant.
     */
    public static final int ERROR_DATE_COMPARISON = R.string.mdkwidget_error_date_comparison;

    @Override
    public boolean accept(View view) {
        boolean accept = false;
        if (view instanceof MDKDateTime
                || view instanceof MDKBaseRichDateWidget) {
            accept = true;
        }
        return accept;
    }


    @Override
    public MDKMessage validate(Date objectToValidate, MDKAttributeSet mdkParameter, MDKMessages resultPreviousValidator, Context context) {
        MDKMessage mdkMessage = null;
        Date minDate = null;
        Date maxDate = null;
        boolean errorDate = false;
        boolean errorDateComparison = false;
        SimpleDateFormat formattedDatePattern = null;

        if (mdkParameter.containsKey(R.attr.dateFormat)) {
            formattedDatePattern = new SimpleDateFormat(mdkParameter.getValue(R.attr.dateFormat));
            if (mdkParameter.containsKey(R.attr.min) && mdkParameter.getValue(R.attr.min) != null) {

                try {
                    minDate = formattedDatePattern.parse(mdkParameter.getValue(R.attr.min));
                    errorDate = (minDate != null && objectToValidate != null && objectToValidate.before(minDate));
                } catch (ParseException e) {
                    errorDateComparison = true;
                }
            }

            if (mdkParameter.containsKey(R.attr.max) && mdkParameter.getValue(R.attr.max) != null) {
                try {
                    maxDate = formattedDatePattern.parse(mdkParameter.getValue(R.attr.max));
                    errorDate =  (maxDate != null && objectToValidate != null && objectToValidate.before(maxDate));
                } catch (ParseException e) {
                    errorDateComparison = true;
                }
            }
        }

        if (errorDateComparison) {
            mdkMessage = new MDKMessage();
            mdkMessage.setErrorCode(ERROR_DATE_COMPARISON);
            mdkMessage.setMessage(context.getString(R.string.mdkwidget_error_date_comparison));
        } else if (errorDate) {
            mdkMessage = new MDKMessage();
           mdkMessage.setErrorCode(ERROR_DATE);
           String error = context.getString(R.string.mdkwidget_error_date_range_validator);
           error += " (";
           if (minDate != null) {
               error += mdkParameter.getValue(R.attr.min) + " < ";
           }
           error += formattedDatePattern.format(objectToValidate);
           if (maxDate != null) {
               error += " < "+mdkParameter.getValue(R.attr.max);
           }
            error += ")";
            mdkMessage.setMessage(error);
        }

        resultPreviousValidator.put(this.getClass().getName(), mdkMessage);
        return mdkMessage;
    }

    @Override
    public int[] configuration() {
        return new int[] {R.attr.min,R.attr.max};
    }
}

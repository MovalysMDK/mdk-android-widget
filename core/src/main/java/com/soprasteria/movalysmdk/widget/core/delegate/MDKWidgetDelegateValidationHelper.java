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
package com.soprasteria.movalysmdk.widget.core.delegate;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.helper.MDKAttributeSet;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessage;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetApplication;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Helper for the validation of a component in the {@link MDKWidgetDelegate}.
 * This class is a static class.
 */
public class MDKWidgetDelegateValidationHelper {

    /**
     * private constructor.
     */
    private MDKWidgetDelegateValidationHelper() {
        // nothing to do
    }

    /**
     * Returns a List of FormFieldValidator to use based on the Set of attributes passed as
     * parameters.
     * (component, qualifier)
     * @param rootView the view to validate
     * @param widgetAttrs a Set of integer representing R.attr.* attributes to validate
     * @return a List of FormFieldValidator tha can validate the Set of parameters
     */
    public static List<FormFieldValidator> getValidators(View rootView, Set<Integer> widgetAttrs) {
        List<FormFieldValidator> rValidator = new ArrayList<>();

        if (rootView != null
                && rootView.getContext().getApplicationContext() instanceof MDKWidgetApplication) {
            rValidator = ((MDKWidgetApplication) rootView.getContext().getApplicationContext())
                    .getMDKWidgetComponentProvider().getValidators(widgetAttrs);
        }
        return rValidator;
    }

    /**
     * Return the FormFieldValidator for the String key passed as parameter.
     * @param rootView the view to validate
     * @param validatorKey the key of the validator
     * @return the FormFieldValidator associated to the parameter key
     */
    @Nullable
    private static FormFieldValidator getValidator(View rootView, String validatorKey) {
        FormFieldValidator rValidator = null;

        if (rootView != null
                && rootView.getContext().getApplicationContext() instanceof MDKWidgetApplication) {
            rValidator = ((MDKWidgetApplication) rootView.getContext().getApplicationContext())
                    .getMDKWidgetComponentProvider().getValidator(validatorKey);

        }
        return rValidator;
    }

    /**
     * Validate the linked widget with the mandatory FormFieldValidator and the optional
     * FormFieldValidator linked to the widget attributes.
     * @param delegate the delegate of the view being validated
     * @param setError true if the error must be set at validation, false otherwise
     * @param validationMode Enumerate according validation mode: VALIDATE, ON_FOCUS, ON_USER
     * @return true if all validators passed, false otherwise
     */
    public static boolean validate(MDKWidgetDelegate delegate, boolean setError, @EnumFormFieldValidator.EnumValidationMode int validationMode) {

        boolean bValid = true;

        List<FormFieldValidator> attributesValidators = delegate.getValidators(delegate.valueObject.getAttributesMap().keySet());

        View v = delegate.valueObject.getView();
        if (v != null) {

            MDKMessages returnMessages = new MDKMessages();

            // get the validation object
            Object objectToValidate = null;
            if (v instanceof HasValidator) {
                objectToValidate = ((HasValidator)v).getValueToValidate();
            }

            // we have to clear all errors before validation
            if (setError) {
                delegate.clearError();
            }

            // run "mandatory" validator defined by the widget
            if (v instanceof HasValidator) {
                int[] validatorsResKey = ((HasValidator) v).getValidators();
                if(validatorsResKey!=null){
                    for (int validatorRes : validatorsResKey) {
                        // this get the last part of the resource name
                        String validatorKey = v.getContext().getResources().getResourceName(validatorRes).split("/")[1];
                        FormFieldValidator mandatoryValidator = getValidator(v, validatorKey);
                        bValid = bValid & executeValidator(mandatoryValidator, objectToValidate, v, delegate.valueObject.getAttributesMap(), returnMessages, validationMode, delegate.getContext());
                    }
                }
            }

            // execute all others validators if no mandatory error
            if (bValid) {
                for (FormFieldValidator validator : attributesValidators) {
                    bValid = executeValidator(validator, objectToValidate, v, delegate.valueObject.getAttributesMap(), returnMessages, validationMode, delegate.getContext()) & bValid;
                }
            }

            // set Errors
            if (setError) {
                delegate.addError(returnMessages);
            }

        } else {
            //if the component doesn't have any validator, there is no error to show.
            delegate.clearError();
        }

        delegate.notifyValidationListeners(bValid);

        delegate.setValid(bValid);
        return bValid;
    }

    /**
     * Execute a FormFieldValidator.
     * @param validator the FormFieldValidator to execute
     * @param objectToValidate the object to validate
     * @param validatingView the view of the widget to accept on FormFieldValidator
     * @param attributesMap a Map containing widget attributes for validation
     * @param returnMap a Map containing previous validation errors
     * @param context the context to use
     * @param validationMode Enumerate according validation mode: VALIDATE, ON_FOCUS, ON_USER
     * @return true if the FormFieldValidator return no error, false otherwise
     */
    public static boolean executeValidator(FormFieldValidator validator, Object objectToValidate, View validatingView,
                                    MDKAttributeSet attributesMap, MDKMessages returnMap,
                                    @EnumFormFieldValidator.EnumValidationMode int validationMode, Context context) {
        boolean bValid = true;
        if (validator.accept(validatingView)) {
            MDKMessage mdkMessage = validator.validate(objectToValidate, attributesMap, returnMap, validationMode, context);

            if (mdkMessage != null && mdkMessage.getMessageType() == MDKMessage.ERROR_TYPE) {
                bValid = false;
            }
        }
        return bValid;
    }
}

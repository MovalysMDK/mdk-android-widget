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
package com.soprasteria.movalysmdk.widget.core.provider;

import android.content.Context;
import android.util.Log;

import com.soprasteria.movalysmdk.widget.core.command.WidgetCommand;
import com.soprasteria.movalysmdk.widget.core.error.MDKErrorMessageFormat;
import com.soprasteria.movalysmdk.widget.core.error.MDKSimpleErrorMessageFormat;
import com.soprasteria.movalysmdk.widget.core.exception.MDKWidgetException;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;

import static com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetApplication.LOG_TAG;

/**
 * Simple implementation of the MDKWidgetComponentProvider.
 * <p>Uses the class package and name to create a singleton of the WidgetCommand/Validator
 * to be returned for the widget.</p>
 */
public class MDKWidgetSimpleComponentProvider implements MDKWidgetComponentProvider {

    /**
     * MDK_ERROR_MESSAGE_FORMAT_KEY.
     */
    private static final String MDK_ERROR_MESSAGE_FORMAT_KEY = "mdk_error_message_format";

    /**
     * MDK_ERROR_MESSAGE_NOT_INSTANCE.
     */
    private static final String MDK_ERROR_MESSAGE_NOT_INSTANCE = "could not instanciate class : \"";

    /**
     * Create a WidgetCommand instance from the specified key and attribute.
     * <p>Search for the concatenation of baseKey and qualifier in resources
     * and if not exist juste for the baseKey and instantiate the Class specified
     * by this resource.</p>
     *
     * @param context the Android context
     * @param baseKey the base key to find
     * @param qualifier the qualifier to append
     * @return a WidgetCommand instance corresponding to the passed key
     */
    private WidgetCommand createCommandFromKey(Context context, String baseKey, String qualifier) {

        String classPath = findClassPathFromResource(context, baseKey, qualifier);

        WidgetCommand widgetCommand ;
        try {
            widgetCommand = (WidgetCommand) Class.forName(classPath).newInstance();
        } catch ( Exception e) {
            throw new MDKWidgetException(MDK_ERROR_MESSAGE_NOT_INSTANCE + classPath + "\"", e);
        }

        return widgetCommand;
    }



    /**
     * Return a classPath corresponding to the concatenation of base key and qualifier.
     * <p>search for the concatenation of basekey and qualifier (if the qualifier is not null).
     * If the concatenation is not found or if the qualifier is null search for the baseKey
     * in string resources.</p>
     * @param context the Android context
     * @param baseKey the base key
     * @param qualifier the qualifier
     * @return a String containing the ClassPath of the given resources key
     */
    private String findClassPathFromResource(Context context, String baseKey, String qualifier) {
        String classPath = null;
        // case with qualifier
        if (qualifier != null) {
            classPath = findStringFromResourceName(context, baseKey + "_" + qualifier);
            if (classPath == null) {
                Log.d(LOG_TAG, "no string resource define for :" + baseKey + "_" + qualifier + " but qualifier was defined");
            }
        }
        // case without qualifier
        if (classPath == null) {
            classPath = findStringFromResourceName(context, baseKey);
        }
        // create instance
        if (classPath == null) {
            throw new MDKWidgetException("no string resource define for :"+baseKey);
        }

        return classPath;
    }

    /**
     * Return a String for the resource name.
     * @param context the Android context
     * @param resourceStringName the string name
     * @return a string matching the name in the Android resources
     */
    private String findStringFromResourceName(Context context, String resourceStringName) {
        int resourceId = context.getResources().getIdentifier(resourceStringName, "string", context.getPackageName());
        if (resourceId != 0) {
            return context.getString(resourceId);
        }
        return null;
    }

    @Override
    public WidgetCommand getCommand(String baseKey, String qualifier, Context context) {
        return createCommandFromKey(context, baseKey, qualifier);
    }

    @Override
    public FormFieldValidator getValidator(String baseKey, String qualifier, Context context) {
        return createValidatorFromKey(context, baseKey, qualifier);
    }

    @Override
    public MDKErrorMessageFormat getErrorMessageFormat(Context context) {
        MDKErrorMessageFormat errorMessageFormat = null;

        // Check the existence of a custom error message formatter resource
        String classPath = findStringFromResourceName(context, MDK_ERROR_MESSAGE_FORMAT_KEY);

        if (classPath != null) {
            try {
                // Try to instantiate the class found in android resource
                errorMessageFormat = (MDKErrorMessageFormat) Class.forName(classPath).newInstance();
            } catch (Exception e) {
                throw new MDKWidgetException(MDK_ERROR_MESSAGE_NOT_INSTANCE + classPath + "\"", e);
            }
        } else {
            // Default error message formatter
            errorMessageFormat = new MDKSimpleErrorMessageFormat();
        }

        return errorMessageFormat;

    }

    /**
     * Create a validator from the key.
     * @param context the context
     * @param baseKey the base key
     * @param qualifier the qualifier
     * @return validator the validator
     */
    private FormFieldValidator createValidatorFromKey(Context context, String baseKey, String qualifier) {

        FormFieldValidator<?> validator;

        String classPath = findClassPathFromResource(context, baseKey, qualifier);

        try {
            validator = (FormFieldValidator<?>) Class.forName(classPath).newInstance();
        } catch (Exception e) {
            throw new MDKWidgetException(MDK_ERROR_MESSAGE_NOT_INSTANCE + classPath + "\"", e);
        }

        return validator;
    }
}

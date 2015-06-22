package com.soprasteria.movalysmdk.widget.core.provider;

import android.content.Context;
import android.util.Log;

import com.soprasteria.movalysmdk.widget.core.command.WidgetCommand;
import com.soprasteria.movalysmdk.widget.core.error.MDKErrorMessageFormat;
import com.soprasteria.movalysmdk.widget.core.error.MDKSimpleErrorMessageFormat;
import com.soprasteria.movalysmdk.widget.core.exception.MDKWidgetException;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;

import java.lang.reflect.Constructor;

/**
 * Simple implementation of the MDKWidgetComponentProvider.
 * uses the class package and name to create a singleton of the WidgetCommand/Validator
 * to be return for the widget
 */
public class MDKWidgetSimpleComponentProvider implements MDKWidgetComponentProvider {

    /** TAG. */
    private static final String TAG = "MDKProvider";
    /** MDK_ERROR_MESSAGE_FORMAT_KEY. */
    private static final String MDK_ERROR_MESSAGE_FORMAT_KEY = "mdk_error_message_format";
    /** MDK_ERROR_MESSAGE_NOT_INSTANCE. */
    private static final String MDK_ERROR_MESSAGE_NOT_INSTANCE = "could not instanciate class : \"";

    /**
     * Create a WidgetCommand instance from the specified key and attribute.
     * <p>Search for the contactenation of baseKey and qualifier in resources
     * and if not exist juste for the baseKey and instanciate the Class specified
     * by this resource</p>
     *
     * @param context the Android context
     * @param baseKey the base key to find
     * @param qualifier the qualifier to append
     * @return a WidgetCommand instance corresponding to the passed key
     */
    private WidgetCommand createCommandFromKey(Context context, String baseKey, String qualifier) {

        String classPath = findClassPathFromRessource(context, baseKey, qualifier);

        WidgetCommand widgetCommand = null;
        try {
            Class commandClass = Class.forName(classPath);
            Constructor constructor = commandClass.getConstructor();
            widgetCommand = (WidgetCommand) constructor.newInstance();
        } catch (Exception e) {
            Log.e(TAG, MDK_ERROR_MESSAGE_NOT_INSTANCE + classPath + "\"", e);
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
    private String findClassPathFromRessource(Context context, String baseKey, String qualifier) {
        String classPath = null;
        // case with qualifier
        if (qualifier != null) {
            classPath = findStringFromRessourceName(context, baseKey + "_" + qualifier);
            if (classPath == null) {
                Log.d("ActionDelegate", "no string resource define for :" + baseKey + "_" + qualifier + " but qualifier was defined");
            }
        }
        // case without qualifier
        if (classPath == null) {
            classPath = findStringFromRessourceName(context, baseKey);
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
    private String findStringFromRessourceName(Context context, String resourceStringName) {
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

        // Check the existence of a custom error message formatter ressource
        String classPath = findStringFromRessourceName(context, MDK_ERROR_MESSAGE_FORMAT_KEY);

        if (classPath != null) {
            try {
                // Try to instanciate the class found in android ressource
                Class validatorClass = Class.forName(classPath);
                Constructor constructor = validatorClass.getConstructor();
                errorMessageFormat = (MDKErrorMessageFormat) constructor.newInstance();
            } catch (Exception e) {

                // Log the error
                Log.e(TAG, MDK_ERROR_MESSAGE_NOT_INSTANCE + classPath + "\"", e);

                // In case of a wrong classpath or non existent class, fallback in default case
                errorMessageFormat = new MDKSimpleErrorMessageFormat();
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

        FormFieldValidator<?> validator = null;


        String classPath = findClassPathFromRessource(context, baseKey, qualifier);

        try {
            Class validatorClass = Class.forName(classPath);
            Constructor constructor = validatorClass.getConstructor();
            validator = (FormFieldValidator) constructor.newInstance();
        } catch (Exception e) {
            Log.e(TAG, MDK_ERROR_MESSAGE_NOT_INSTANCE + classPath + "\"", e);
        }

        return validator;
    }
}

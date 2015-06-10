package com.soprasteria.movalysmdk.widget.core.provider;

import android.content.Context;
import android.util.Log;

import com.soprasteria.movalysmdk.widget.core.command.Command;
import com.soprasteria.movalysmdk.widget.core.validator.IFormFieldValidator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

;
;

/**
 * Simple implementation of the MDKWidgetComponentProvider
 * uses the class package and name to create a singleton of the Command/Validator
 * to be return for the widget
 */
public class MDKWidgetSimpleComponentProvider implements MDKWidgetComponentProvider {



    /**
     * Create a Command instance from the specified key and attribute
     * <p>Search for the contactenation of baseKey and qualifier in resources
     * and if not exist juste for the baseKey and instanciate the Class specified
     * by this resource</p>
     *
     * @param context the Android context
     * @param baseKey the base key to find
     * @param qualifier the qualifier to append
     * @return a Command instance corresponding to the passed key
     */
    private Command createCommandFromKey(Context context, String baseKey, String qualifier) {

        String classPath = findClassPathFromRessource(context, baseKey, qualifier);

        Command command = null;
        try {
            Class commandClass = Class.forName(classPath);
            Constructor constructor = commandClass.getConstructor(Context.class);
            command = (Command) constructor.newInstance(context);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        return command;
    }



    /**
     * Return a classPath corresponding to the concatenation of base key and qualifier
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
            throw new RuntimeException("no string resource define for :"+baseKey);
        }

        return classPath;
    }

    /**
     * Return a String for the resource name
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
    public Command getCommand(Context context, String baseKey, String qualifier) {
        return createCommandFromKey(context, baseKey, qualifier);
    }

    @Override
    public IFormFieldValidator getValidator(Context context, String baseKey, String qualifier) {
        return createValidatorFromKey(context, baseKey, qualifier);
    }

    private IFormFieldValidator createValidatorFromKey(Context context, String baseKey, String qualifier) {

        IFormFieldValidator<?> validator = null;


        String classPath = findClassPathFromRessource(context, baseKey, qualifier);

        try {
            Class validatorClass = Class.forName(classPath);
            Constructor constructor = validatorClass.getConstructor(Context.class);
            validator = (IFormFieldValidator) constructor.newInstance(context);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return validator;
    }
}

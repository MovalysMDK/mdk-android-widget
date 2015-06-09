package com.soprasteria.movalysmdk.widget.core.provider;

import android.content.Context;
import android.util.Log;

import com.soprasteria.movalysmdk.widget.core.command.Command;
import com.soprasteria.movalysmdk.widget.core.validator.IFormFieldValidator;
import com.sun.istack.internal.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Simple implementation of the MDKWidgetComponentProvider
 * uses the class package and name to create a singleton of the Command/Validator
 * to be return for the widget
 */
public class MDKWidgetSimpleComponentProvider implements MDKWidgetComponentProvider {



    /**
     *
     * @param context
     * @param baseKey
     * @return
     */
    @Nullable
    private Command findCommandFromKey(Context context, String baseKey, String qualifier) {

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
     * @param context
     * @param resourceStringName
     * @return
     */
    @Nullable private String findStringFromRessourceName(Context context, String resourceStringName) {
        int resourceId = context.getResources().getIdentifier(resourceStringName, "string", context.getPackageName());
        if (resourceId != 0) {
            return context.getString(resourceId);
        }
        return null;
    }

    @Override
    public Command getCommand(Context context, String baseKey, String qualifier) {
        return findCommandFromKey(context, baseKey, qualifier);
    }

    @Override
    public IFormFieldValidator getValidator(Context context, String baseKey, String qualifier) {
        return null;
    }
}

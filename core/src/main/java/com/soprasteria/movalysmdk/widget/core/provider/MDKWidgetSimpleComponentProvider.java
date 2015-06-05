package com.soprasteria.movalysmdk.widget.core.provider;

import android.content.Context;

import com.soprasteria.movalysmdk.widget.core.command.Command;
import com.soprasteria.movalysmdk.widget.core.validator.IFormFieldValidator;

/**
 * Simple implementation of the MDKWidgetComponentProvider
 * uses the class package and name to create a singleton of the Command/Validator
 * to be return for the widget
 */
public class MDKWidgetSimpleComponentProvider implements MDKWidgetComponentProvider {

    @Override
    public Command getCommand(Class widgetClass, Context context) {
        return null;
    }

    @Override
    public IFormFieldValidator getValidator(Class widgetClass, Context context) {
        return null;
    }
}

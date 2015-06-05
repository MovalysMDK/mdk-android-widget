package com.soprasteria.movalysmdk.widget.core.provider;

import android.content.Context;

import com.soprasteria.movalysmdk.widget.core.command.Command;
import com.soprasteria.movalysmdk.widget.core.validator.IFormFieldValidator;

/**
 * Interface to describe the MDKWidgetComponentProvider
 * create commands and Validator for MDK commponents
 */
public interface MDKWidgetComponentProvider {

    /**
     * Return the singleton Command for the specified Class
     * @param widgetClass the widget class from who to return Command
     * @param context the Android context
     * @return a singleton of the Command to use in the widget
     */
    public Command getCommand(Class widgetClass, Context context);

    /**
     * Return the singleton Command for the specified Class
     * @param widgetClass the widget class from who to return Command
     * @param context the Android context
     * @return a singleton of the IFormFieldValidator to use in the widget
     */
    public IFormFieldValidator getValidator(Class widgetClass, Context context);

}

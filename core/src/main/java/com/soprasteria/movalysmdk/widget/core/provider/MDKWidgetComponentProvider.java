package com.soprasteria.movalysmdk.widget.core.provider;

import android.content.Context;

import com.soprasteria.movalysmdk.widget.core.command.Command;
import com.soprasteria.movalysmdk.widget.core.error.MDKErrorMessageFormat;
import com.soprasteria.movalysmdk.widget.core.validator.IFormFieldValidator;

/**
 * Interface to describe the MDKWidgetComponentProvider
 * create commands and Validator for MDK commponents.
 */
public interface MDKWidgetComponentProvider {

    /**
     * Return the singleton Command for the specified Class.
     * @param context the Android context
     * @param baseKey the base key for the widget
     * @param qualifier the qualifier of the widget
     * @return a singleton of the Command to use in the widget
     */
    public Command getCommand(Context context, String baseKey, String qualifier);

    /**
     * Return the singleton Command for the specified Class.
     * @param context the Android context
     * @param baseKey the base key for the widget
     * @param qualifier the qualifier of the widget
     * @return a singleton of the IFormFieldValidator to use in the widget
     */
    public IFormFieldValidator getValidator(Context context, String baseKey, String qualifier);

    /**
     * Returns the error message formatter.
      * @param context the Android context
     * @return MDKErrorMessageFormat MDKErrorMessageFormat
     */
    public MDKErrorMessageFormat getErrorMessageFormat(Context context);

}

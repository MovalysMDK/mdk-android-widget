package com.soprasteria.movalysmdk.widget.core.provider;

import android.content.Context;

import com.soprasteria.movalysmdk.widget.core.command.Command;
import com.soprasteria.movalysmdk.widget.core.error.MDKErrorMessageFormat;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;

/**
 *  Bean provider for MDK Widget. <p>Bean types are : </p> <ul> <li>Command</li> <li>Validator</li> <li></li> </ul>.
 */
public interface MDKWidgetComponentProvider {

    /**
     * Return the singleton Command for the specified Class.
     * @param baseKey the base key for the widget
     * @param qualifier the qualifier of the widget
     * @param context the Android context
     * @return a singleton of the Command to use in the widget
     */
    Command getCommand(String baseKey, String qualifier, Context context);

    /**
     * Return the singleton Command for the specified Class.
     * @param baseKey the base key for the widget
     * @param qualifier the qualifier of the widget
     * @param context the Android context
     * @return a singleton of the FormFieldValidator to use in the widget
     */
    FormFieldValidator getValidator(String baseKey, String qualifier, Context context);

    /**
     * Returns the error message formatter.
      * @param context the Android context
     * @return MDKErrorMessageFormat MDKErrorMessageFormat
     */
    MDKErrorMessageFormat getErrorMessageFormat(Context context);

}

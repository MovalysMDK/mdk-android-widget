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

import com.soprasteria.movalysmdk.widget.core.command.WidgetCommand;
import com.soprasteria.movalysmdk.widget.core.error.MDKErrorMessageFormat;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;

import java.util.List;
import java.util.Set;

/**
 *  Bean provider for MDK Widget.
 *  <p>Bean types are : </p>
 *  <ul>
 *      <li>WidgetCommand</li>
 *      <li>Validator</li>
 *  </ul>.
 */
public interface MDKWidgetComponentProvider {

    /**
     * Return the singleton WidgetCommand for the specified Class.
     * @param baseKey the base key for the widget
     * @param qualifier the qualifier of the widget
     * @param context the Android context
     * @return a singleton of the WidgetCommand to use in the widget
     */
    WidgetCommand getCommand(String baseKey, String qualifier, Context context);

    /**
     * Return the singleton WidgetCommand for the specified Class.
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


    /**
     * Return the list of all registered Validators for the given attributes.
     * @see FormFieldValidator#configuration()
     * @param attributes a Set of int representing the attributes to use in FormFieldValidator
     * @return a List of all registered Validator for attributes in parameter
     */
    List<FormFieldValidator> getValidators(Set<Integer> attributes);

    /**
     * Return a FormFieldValidator.
     * @param key a String representing the key of the FormFieldValidator
     * @return the FormFieldValidator associated to the key
     */
    FormFieldValidator getValidator(String key);

}

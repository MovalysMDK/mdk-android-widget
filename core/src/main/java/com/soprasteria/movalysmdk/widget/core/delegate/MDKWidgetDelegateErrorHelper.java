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
import android.view.View;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessageWidget;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessage;
import com.soprasteria.movalysmdk.widget.core.helper.MDKMessages;

/**
 * Error processing helper for the class {@link MDKWidgetDelegate}.
 * This class is a singleton.
 */
// FIXME ? NO SINGLETON!!!
public class MDKWidgetDelegateErrorHelper {

    /**
     * instance of the singleton.
     */
    private static MDKWidgetDelegateErrorHelper instance;

    /**
     * private constructor.
     */
    private MDKWidgetDelegateErrorHelper() {
        // nothing to do
    }

    /**
     * Returns the instance of the singleton.
     * @return the instance of the singleton
     */
    public static MDKWidgetDelegateErrorHelper getInstance() {
        if (instance == null) {
            instance = new MDKWidgetDelegateErrorHelper();
        }

        return instance;
    }

    /**
     * Set error.
     * @param errorView the error component
     * @param valueObject the delegate value object
     * @param label the label to set
     * @param error the new error
     * @param context the context to use
     */
    public void setError(View errorView, MDKWidgetDelegateValueObject valueObject, CharSequence label, CharSequence error, Context context) {
        // empty error and add the CharSequence as only error
        clearMessages(errorView, valueObject, label, context);
        MDKMessage mdkMessage = new MDKMessage(label, error, MDKMessage.NO_MESSAGE_CODE);
        MDKMessages messages = new MDKMessages();
        messages.put(MDKWidgetDelegateValueObject.USER_ERROR, mdkMessage);
        displayMessages(errorView, valueObject, label, messages, context);
    }

    /**
     * Set mdk errors widget.
     * @param mdkErrorWidget the error widget
     * @param messages the errors
     * @param valueObject the delegate value object
     * @param label the label to set
     * @param context the context to use
     */
    private void setMdkErrorWidget(MDKMessageWidget mdkErrorWidget, MDKMessages messages, MDKWidgetDelegateValueObject valueObject, CharSequence label, Context context) {
        View v = valueObject.getView();
        if (v instanceof MDKWidget) {
            if (messages == null) {
                (mdkErrorWidget).clear(context, ((MDKWidget) v).getTechnicalInnerWidgetDelegate().getUniqueId());
            } else {
                messages.setComponentId(((MDKWidget) v).getTechnicalInnerWidgetDelegate().getUniqueId());
                messages.setComponentLabelName(label);
                (mdkErrorWidget).addMessages(context, ((MDKWidget) v).getTechnicalInnerWidgetDelegate().getUniqueId(), messages);
            }
        }
    }

    /**
     * Set errors.
     * @param errorView the error component
     * @param valueObject the delegate value object
     * @param label the label to set
     * @param messages the messages to set
     * @param context the context to use
     */
    public void displayMessages(View errorView, MDKWidgetDelegateValueObject valueObject, CharSequence label, MDKMessages messages, Context context) {
        if (errorView instanceof MDKMessageWidget){
            setMdkErrorWidget((MDKMessageWidget) errorView, messages, valueObject, label, context);
        } else if (errorView instanceof TextView) {
            ((TextView)errorView).setText(messages != null ? messages.getErrorMessage() : null);
        }
        valueObject.setError(messages != null);
        View v = valueObject.getView();
        if (v != null) {
            v.refreshDrawableState();
        }
    }

    /**
     * Remove messages.
     * @param errorView the error component
     * @param valueObject the delegate value object
     * @param label the label to set
     * @param context the context to use
     */
    public void clearMessages(View errorView, MDKWidgetDelegateValueObject valueObject, CharSequence label, Context context) {
        displayMessages(errorView, valueObject, label, null, context);
    }

}

package com.soprasteria.movalysmdk.widget.core.delegate;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.error.MDKErrorWidget;
import com.soprasteria.movalysmdk.widget.core.error.MDKMessage;
import com.soprasteria.movalysmdk.widget.core.helper.MDKMessages;

/**
 * Error processing helper for the class {@link MDKWidgetDelegate}.
 * This class is a singleton.
 */
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
     * @param rootView the view hosting the error component
     * @param valueObject the delegate value object
     * @param label the label to set
     * @param error the new error
     * @param context the context to use
     */
    public void setError(View rootView, MDKWidgetDelegateValueObject valueObject, CharSequence label, CharSequence error, Context context) {
        // empty error and add the CharSequence as only error
        clearError(rootView, valueObject, label, context);
        MDKMessage mdkMessage = new MDKMessage(label, error, MDKMessage.NO_ERROR_CODE);
        MDKMessages messages = new MDKMessages();
        messages.put(MDKWidgetDelegateValueObject.USER_ERROR, mdkMessage);
        addError(rootView, valueObject, label, messages, context);
    }

    /**
     * Set mdk error widget.
     * @param mdkErrorWidget the error widget
     * @param error the error
     * @param valueObject the delegate value object
     * @param label the label to set
     * @param context the context to use
     */
    private void setMdkErrorWidget(MDKErrorWidget mdkErrorWidget, MDKMessages error, MDKWidgetDelegateValueObject valueObject, CharSequence label, Context context) {
        View v = valueObject.getView();
        if (v instanceof MDKWidget) {
            if (error == null) {
                (mdkErrorWidget).clear(context, ((MDKWidget) v).getUniqueId());
            } else {
                error.setComponentId(((MDKWidget) v).getUniqueId());
                error.setComponentLabelName(label);
                (mdkErrorWidget).addError(context,((MDKWidget) v).getUniqueId(), error);
            }
        }
    }

    /**
     * Set error.
     * @param rootView the view hosting the error component
     * @param valueObject the delegate value object
     * @param label the label to set
     * @param messages the error to set
     * @param context the context to use
     */
    public void addError(View rootView, MDKWidgetDelegateValueObject valueObject, CharSequence label, MDKMessages messages, Context context) {
        if (rootView != null) {
            TextView errorView = (TextView) rootView.findViewById(valueObject.getErrorViewId());
            if (errorView instanceof MDKErrorWidget){
                setMdkErrorWidget((MDKErrorWidget) errorView, messages, valueObject, label, context);
            } else if (errorView != null){
                errorView.setText(messages.getErrorMessage());
            }
        }
        valueObject.setError(messages != null);
        View v = valueObject.getView();
        if (v != null) {
            v.refreshDrawableState();
        }
    }

    /**
     * Remove error.
     * @param rootView the view hosting the error component
     * @param valueObject the delegate value object
     * @param label the label to set
     * @param context the context to use
     */
    public void clearError(View rootView, MDKWidgetDelegateValueObject valueObject, CharSequence label, Context context) {
        if (rootView != null) {
            TextView errorView = (TextView) rootView.findViewById(valueObject.getErrorViewId());
            if (errorView instanceof MDKErrorWidget){
                setMdkErrorWidget((MDKErrorWidget) errorView, null, valueObject, label, context);
            } else {
                errorView.setText("");
            }
        }
        valueObject.setError(false);
        View v = valueObject.getView();
        if (v != null) {
            v.refreshDrawableState();
        }
    }

}

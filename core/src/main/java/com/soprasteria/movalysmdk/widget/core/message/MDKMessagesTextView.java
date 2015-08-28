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
package com.soprasteria.movalysmdk.widget.core.message;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.core.R;
import com.soprasteria.movalysmdk.widget.core.helper.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * The MDKMessagesTextView display messages of a widget.
 * <p>This is the simplest representation of an error. This widget
 * display the error message in a TextView</p>
 */
public class MDKMessagesTextView extends TextView implements MDKMessageWidget {

    /** Keyword for instanceState in save instance state. */
    private static final String SAVE_BUNDLE_INSTANCE_STATE = "instanceState";
    /** Keyword for shared error widget in save instance state. */
    private static final String SAVE_BUNDLE_SHARED_ERROR_WIDGET = "sharedErrorWidget";
    /** Keyword for helper text in save instance state. */
    private static final String SAVE_BUNDLE_HELPER_TEXT = "helperText";
    /** Keyword for error sparse array in save instance state. */
    private static final String SAVE_BUNDLE_ERROR_SPARSE_ARRAY = "errorSparseArray";
    /** Keyword for display error order array list in save instance state. */
    private static final String SAVE_BUNDLE_ERROR_ORDER_ARRAY = "displayErrorOrderArrayList";

    /** Data structure to store component id and its associated error messages. */
    private SparseArray<MDKMessages> errorSparseArray = new SparseArray<>();

    /** Array list of error Ids to display messages from first to last index. */
    List<Integer> displayErrorOrderArrayList;
    /**
     * HelperText.
     */
    private CharSequence helperText;

    /**
     * True if the error component is into a MDK rich one.
     * */
    private boolean sharedErrorWidget = false;

    
    /**
     * MDKErrorWidge builder.
     * @param context Application context
     * @param attrs Collection of attributes
     */
    public MDKMessagesTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * MDKErrorWidge builder with style attribute definition.
     * @param context Context
     * @param attrs Collection of attributes
     * @param defStyleAttr Attribute in the current theme referencing a style resource
     */
    public MDKMessagesTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Initialisation method.
     * @param context the context
     * @param attrs attributes
     */
    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MDKCommons);

        int helperResId = typedArray.getResourceId(R.styleable.MDKCommons_helper, 0);

        typedArray.recycle();

        typedArray = context.obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKErrorComponent);
        // parse error order attribute
        int resErrorOrderId = typedArray.getResourceId(R.styleable.MDKCommons_MDKErrorComponent_errorsDisplayOrder, 0);
        this.sharedErrorWidget = typedArray.getBoolean(R.styleable.MDKCommons_MDKErrorComponent_errorCentralized, false);

        if (helperResId != 0) {
            this.setHelper(context, getResources().getString(helperResId));
        }

        if (resErrorOrderId != 0) {
            int[] displayErrorOrderArray = getResources().getIntArray(resErrorOrderId);
            this.setComponentDisplayOrder(displayErrorOrderArray);
        }
        typedArray.recycle();
    }

    /**
     * Setter.
     * @param context application context to access resource
     * @param helper the new helper
     */
    public void setHelper(Context context,CharSequence helper) {
        this.helperText = helper;
        updateErrorMessage(context);
    }

    /**
     * Add and the component and its associated error message to the current list of errors.
     * @param context the android context
     * @param componentId the component id
     * @param messages MDKError object to add
     */
    @Override
    public void addMessages(Context context, int componentId, MDKMessages messages) {
        //TODO serialize MDKMessages
        this.errorSparseArray.put(componentId, messages);
        this.updateErrorMessage(context);
    }

    /**
     * Remove component from the error list.
     * @param context the Android context
     * @param innerComponentId Resource Id of the component
     * */
    @Override
    public void clear(Context context, int innerComponentId) {
        this.errorSparseArray.remove(innerComponentId);
        this.updateErrorMessage(context);
    }

    /**
     * Remove all components from the error list.
     */
    @Override
    public void clear(Context context) {
        this.errorSparseArray.clear();
        this.updateErrorMessage(context);
    }

    /**
     * setComponentDisplayOrder.
     * @param displayOrder Array of error Ids
     */
    @Override
    public void setComponentDisplayOrder(int[] displayOrder) {
        this.displayErrorOrderArrayList = new ArrayList<>();
        for (int current: displayOrder) {
            this.displayErrorOrderArrayList.add(current);
        }
    }

    /**
     * Update the component in order to display messages.
     * @param context application context to access resource
     */
    private void updateErrorMessage(Context context) {

        // Concatenation of all error messages to be displayed
        SpannableStringBuilder sbErrorMessage = new SpannableStringBuilder();

        if (this.displayErrorOrderArrayList == null) {
            for(int currentComponentId = 0; currentComponentId < errorSparseArray.size(); currentComponentId++) {
                MDKMessages currentMDKError = this.errorSparseArray.valueAt(currentComponentId);
                sbErrorMessage = generateCurrentMessage(context,
                                                        sbErrorMessage,
                                                        currentMDKError);
            }

        } else {
            for(Integer currentComponentId : this.displayErrorOrderArrayList) {
                if (this.errorSparseArray.get(currentComponentId) != null){
                    MDKMessages currentMDKError = this.errorSparseArray.valueAt(currentComponentId);
                    sbErrorMessage = generateCurrentMessage(context,
                                                            sbErrorMessage,
                                                            currentMDKError);
                }
            }
        }

        if (helperText != null && sbErrorMessage.length() < 1) {
            this.setText(helperText);
        } else {
            // Find the error component to update
            this.setText(sbErrorMessage);
        }

    }

    /**
     * Returns the error message formatter.
     * @return mdkErrorMessageFormat the mdk error message
     */
    private MDKMessageFormat getMDKErrorMessageFormat() {
        MDKMessageFormat mdkErrorMessageFormat = null;

        if (this.getContext().getApplicationContext() instanceof MDKWidgetApplication) {
            mdkErrorMessageFormat = ((MDKWidgetApplication) this.getContext().getApplicationContext()).getMDKWidgetComponentProvider()
                    .getErrorMessageFormat(this.getContext());
        }

        return mdkErrorMessageFormat;
    }

    /**
     * Return a SpannableStringBuilder object in order to build messages to display.
     * @param context application context to access resource
     * @param outputStringBuild the output string
     * @param messages the mdk error
     * @return outputStringBuild the output string
     */
    private SpannableStringBuilder generateCurrentMessage(Context context, SpannableStringBuilder outputStringBuild, MDKMessages messages){

        MDKMessageFormat interfaceFormat = getMDKErrorMessageFormat();

        if (messages.getErrorMessage() != null && messages.getErrorMessage().length() > 0) {
            CharSequence message = interfaceFormat.formatText(context, messages, isSharedErrorWidget());

            if (outputStringBuild.length() > 0) {
                outputStringBuild.append("\n");
            }
            outputStringBuild.append(message);
        }

        return outputStringBuild;

    }

    /**
     * Return true if the component is included into a MDK rich one.
     * @return sharedErrorWidget the centralized error
     */
    public boolean isSharedErrorWidget() {
        return sharedErrorWidget;
    }

    /**
     * Set true if Defined if the error component is inside a MDK rich one.
     * @param sharedErrorWidget sharedErrorWidget
     */
    public void setSharedErrorWidget(boolean sharedErrorWidget) {
        this.sharedErrorWidget = sharedErrorWidget;
    }
    
    @Override
    public Parcelable onSaveInstanceState() {

        Bundle bundle = new Bundle();
        // Save the android state
        bundle.putParcelable(SAVE_BUNDLE_INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putBoolean(SAVE_BUNDLE_SHARED_ERROR_WIDGET, this.sharedErrorWidget);
        bundle.putCharSequence(SAVE_BUNDLE_HELPER_TEXT,this.helperText);
        bundle.putSparseParcelableArray(SAVE_BUNDLE_ERROR_SPARSE_ARRAY, this.errorSparseArray);
        if (displayErrorOrderArrayList != null) {
            bundle.putIntegerArrayList(SAVE_BUNDLE_ERROR_ORDER_ARRAY, new ArrayList<Integer>(displayErrorOrderArrayList));
        } else {
            bundle.putIntegerArrayList(SAVE_BUNDLE_ERROR_ORDER_ARRAY, null);
        }
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {

        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            this.sharedErrorWidget = bundle.getBoolean(SAVE_BUNDLE_SHARED_ERROR_WIDGET);
            this.helperText = bundle.getCharSequence(SAVE_BUNDLE_HELPER_TEXT);
            this.errorSparseArray = bundle.getSparseParcelableArray(SAVE_BUNDLE_ERROR_SPARSE_ARRAY);
            this.displayErrorOrderArrayList = bundle.getIntegerArrayList(SAVE_BUNDLE_ERROR_ORDER_ARRAY);
            super.onRestoreInstanceState(bundle.getParcelable(SAVE_BUNDLE_INSTANCE_STATE));
            updateErrorMessage(getContext());
            return;
        }
        super.onRestoreInstanceState(state);
    }

}

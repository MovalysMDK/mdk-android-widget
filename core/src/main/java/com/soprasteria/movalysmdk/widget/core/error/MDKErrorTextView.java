package com.soprasteria.movalysmdk.widget.core.error;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.core.R;
import com.soprasteria.movalysmdk.widget.core.error.MDKError;
import com.soprasteria.movalysmdk.widget.core.error.MDKErrorMessageFormat;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetApplication;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetComponentProvider;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetSimpleComponentProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDK team on 09/06/2015.
 */
public class MDKErrorTextView extends TextView implements MDKErrorWidget {

    /** Data structure to store component id and its associated error messages. */
    private SparseArray<MDKError> errorSparseArray = new SparseArray<>();

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
    public MDKErrorTextView(Context context, AttributeSet attrs) {
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
    public MDKErrorTextView(Context context, AttributeSet attrs, int defStyleAttr) {
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
            this.setHelper(getResources().getString(helperResId));
        }

        if (resErrorOrderId != 0) {
            int[] displayErrorOrderArray = getResources().getIntArray(resErrorOrderId);
            this.setDisplayErrorOrder(displayErrorOrderArray);
        }
        typedArray.recycle();

    }

    /**
     * Setter.
     * @param helper the new helper
     */
    public void setHelper(CharSequence helper) {
        this.helperText = helper;
        updateErrorMessage();
    }

    /**
     * Add and the component and its associated error message to the current list of errors.
     * @param componentId the component id
     * @param error MDKError object to add
     */
    public void addError(int componentId, MDKError error) {
        this.errorSparseArray.put(componentId, error);
        this.updateErrorMessage();
    }

    /**
     * Remove component from the error list.
     * @param innerComponentId Resource Id of the component
     * */
    @Override
    public void clear(int innerComponentId) {
        this.errorSparseArray.remove(innerComponentId);
        this.updateErrorMessage();
    }

    /**
     * Remove all components from the error list.
     */
    @Override
    public void clear() {
        this.errorSparseArray.clear();
        this.updateErrorMessage();
    }

    /**
     * setDisplayErrorOrder.
     * @param displayErrorOrder Array of error Ids
     */
    @Override
    public void setDisplayErrorOrder(int[] displayErrorOrder) {
        this.displayErrorOrderArrayList = new ArrayList<>();
        for (int current: displayErrorOrder) {
            this.displayErrorOrderArrayList.add(current);
        }
    }

    /**
     * Update the component in order to display messages.
     */
    private void updateErrorMessage() {

        // Concatenation of all error messages to be displayed
        SpannableStringBuilder sbErrorMessage = new SpannableStringBuilder();

        if (this.displayErrorOrderArrayList == null) {
            for(int currentComponentId = 0; currentComponentId < errorSparseArray.size(); currentComponentId++) {
                MDKError currentMDKError = this.errorSparseArray.valueAt(currentComponentId);
                sbErrorMessage = generateCurrentMessage(sbErrorMessage,
                                                        currentMDKError);
            }

        } else {
            for(Integer currentComponentId : this.displayErrorOrderArrayList) {
                if (this.errorSparseArray.get(currentComponentId) != null){
                    MDKError currentMDKError = this.errorSparseArray.valueAt(currentComponentId);
                    sbErrorMessage = generateCurrentMessage(sbErrorMessage,
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
    private MDKErrorMessageFormat getMDKErrorMessageFormat() {
        MDKErrorMessageFormat mdkErrorMessageFormat = null;

        if (this.getContext().getApplicationContext() instanceof MDKWidgetApplication) {
            mdkErrorMessageFormat = ((MDKWidgetApplication) this.getContext().getApplicationContext()).getMDKWidgetComponentProvider()
                    .getErrorMessageFormat(this.getContext());
        } else {
            MDKWidgetComponentProvider widgetComponentProvider = new MDKWidgetSimpleComponentProvider();
            mdkErrorMessageFormat = widgetComponentProvider.getErrorMessageFormat(this.getContext());
        }

        return mdkErrorMessageFormat;
    }

    /**
     * Return a SpannableStringBuilder object in order to build messages to display.
     * @param outputStringBuild the output string
     * @param mdkError the mdk error
     * @return outputStringBuild the output string
     */
    private SpannableStringBuilder generateCurrentMessage(SpannableStringBuilder outputStringBuild, MDKError mdkError){

        MDKErrorMessageFormat interfaceFormat = getMDKErrorMessageFormat();

        CharSequence message = mdkError.getErrorMessage();
        message = interfaceFormat.formatText(mdkError, isSharedErrorWidget());

        if (outputStringBuild.length() > 0) {
            outputStringBuild.append("\n");
        }
        outputStringBuild.append(message);

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

}

package com.soprasteria.movalysmdk.widget.base.error;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.base.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDK team on 09/06/2015.
 */
public class MDKErrorTextView extends TextView implements MDKErrorWidget {

    /** Data structure to store component id and its associated error messages */
    private SparseArray<CharSequence> errorSparseArray = new SparseArray<CharSequence>();

    /** Array list of error Ids to display messages from first to last index */
    List<Integer> displayErrorOrderArrayList;
    private CharSequence helperText;

    /**
     * MDKErrorWidge builder
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
     * MDKErrorWidge builder with style attribute definition
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
     * Initialisation method
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MDKCommons);

        int helperResId = typedArray.getResourceId(R.styleable.MDKCommons_helper, 0);

        typedArray.recycle();

        typedArray = context.obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKErrorComponent);
        // parse error order attribute
        int resErrorOrderId = typedArray.getResourceId(R.styleable.MDKCommons_MDKErrorComponent_errorsDisplayOrder, 0);

        if (helperResId != 0) {
            this.setHelper(getResources().getString(helperResId));
        }

        if (resErrorOrderId != 0) {
            int[] displayErrorOrderArray = getResources().getIntArray(resErrorOrderId);
            this.setDisplayErrorOrder(displayErrorOrderArray);
        }
        typedArray.recycle();

    }

    public void setHelper(CharSequence helper) {
        this.helperText = helper;
        updateErrorMessage();
    }

    /**
     * Add and the component and its associated error message to the current list of errors
     * @param innerComponentId Resource Id of the component
     * @param errorMessage Error message to display
     */
    @Override
    public void addError(int innerComponentId, CharSequence errorMessage) {
        this.errorSparseArray.put(innerComponentId, errorMessage);
        this.updateErrorMessage();
    }

    /**
     * Remove component from the error list
     * @param innerComponentId Resource Id of the component
     * */
    @Override
    public void clear(int innerComponentId) {
        this.errorSparseArray.remove(innerComponentId);
        this.updateErrorMessage();
    }

    /**
     * Remove all components from the error list
     */
    @Override
    public void clear() {
        this.errorSparseArray.clear();
        this.updateErrorMessage();
    }

    /**
     *
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
     * Update the component in order to display messages
     */
    private void updateErrorMessage() {

        // Concatenation of all error messages to be displayed
        SpannableStringBuilder sbErrorMessage = new SpannableStringBuilder();

        if (this.displayErrorOrderArrayList == null) {
            for(int i = 0; i < errorSparseArray.size(); i++) {
                int key = errorSparseArray.keyAt(i);
                // get the object by the key.
                CharSequence message = this.errorSparseArray.get(key);
                if (sbErrorMessage.length() > 0) {
                    sbErrorMessage.append("\n");
                }
                sbErrorMessage.append(message);
            }

        } else {
            for(Integer currentId : this.displayErrorOrderArrayList) {
                if (this.errorSparseArray.get(currentId) != null){
                    CharSequence message = this.errorSparseArray.valueAt(currentId);
                    if (sbErrorMessage.length() > 0) {
                        sbErrorMessage.append("\n");
                    }
                    sbErrorMessage.append(message);
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


}

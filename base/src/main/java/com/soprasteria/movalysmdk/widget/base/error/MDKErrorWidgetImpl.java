package com.soprasteria.movalysmdk.widget.base.error;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;
import android.util.SparseArray;

import com.soprasteria.movalysmdk.widget.base.R;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by MDK team on 09/06/2015.
 */
public class MDKErrorWidgetImpl extends TextView implements MDKErrorWidget {

    /** Data structure to store component id and its associated error messages */
    private SparseArray<CharSequence> errorSparseArray = new SparseArray<CharSequence>();

    /** Array list of error Ids to display messages from first to last index */
    List<Integer> displayErrorOrderArrayList;

    /** retrieve XML attributes with style and theme information applied */
    TypedArray typedArray = null;

    /**
     * MDKErrorWidge builder
     * @param context Application context
     * @param attrs Collection of attributes
     */
    public MDKErrorWidgetImpl(Context context, AttributeSet attrs) {
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
    public MDKErrorWidgetImpl(Context context, AttributeSet attrs, int defStyleAttr) {
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

        this.typedArray = context.obtainStyledAttributes(attrs, R.styleable.MDKCommons);
        // parse error order attribute
        int resErrorOrderId = this.typedArray.getResourceId(R.styleable.MDKCommons_MDKErrorComponent_errorsDisplayOrder, 0);

        if (resErrorOrderId != 0) {
            int[] displayErrorOrderArray = getResources().getIntArray(resErrorOrderId);
            this.setDisplayErrorOrder(displayErrorOrderArray);
        }
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
        this.displayErrorOrderArrayList = new ArrayList<>(displayErrorOrder.length);
    }

    /**
     * Update the component in order to display messages
     */
    private void updateErrorMessage() {

        // Concatenation of all error messages to be displayed
        StringBuilder sbErrorMessage = new StringBuilder();

        // Browse list defining the component order to check if all Ids are still present to be displayed
        List<Integer> tmpListIdForUpdate = new ArrayList<Integer>();

        for(Integer currentId : this.displayErrorOrderArrayList) {
            if (this.errorSparseArray.get(currentId) != null){
                tmpListIdForUpdate.add(currentId);
                CharSequence message = this.errorSparseArray.valueAt(currentId);
                sbErrorMessage.append(message);
            }
        }

        // Update ordered list to be displayed according FIFO
        this.displayErrorOrderArrayList = tmpListIdForUpdate;

        // Find the error component to update
        this.setText(sbErrorMessage);

    }
}

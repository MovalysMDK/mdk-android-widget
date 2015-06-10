package com.soprasteria.movalysmdk.widget.base.error;

import android.content.Context;

/**
 * Created by MDK Team on 09/06/2015.
 */
public interface MDKErrorWidget {

    /**
     * Add and the component and its associated error message to the current list of errors
     * @param innerComponentId Resource Id of the component to display error message
     * @param errorMessage Error message to display
     */
    public void addError(int innerComponentId, CharSequence errorMessage);

    /**
     * Remove component from the error list
     * @param innerComponentId Resource Id of the component
     */
    public void clear(int innerComponentId);

    /**
     * Remove all components from the error list
     */
    public void clear();

    /**
     * Set priority to of the component to be displayed (0 higher)
     * @param displayErrorOrder
     */
    public void setDisplayErrorOrder(int[] displayErrorOrder);
}
package com.soprasteria.movalysmdk.widget.base.error;

import com.soprasteria.movalysmdk.widget.core.error.MDKError;

/**
 * Created by MDK Team on 09/06/2015.
 */
public interface MDKErrorWidget {

    /**
     * Add and the component and its associated error message to the current list of errors.
     * @param error MDKError object to add
     */
    public void addError(int componentId, MDKError error);

    /**
     * Remove component from the error list.
     * @param innerComponentId Resource Id of the component
     */
    public void clear(int innerComponentId);

    /**
     * Remove all components from the error list.
     */
    public void clear();

    /**
     * Set priority to of the component to be displayed (0 higher).
     * @param displayErrorOrder
     */
    public void setDisplayErrorOrder(int[] displayErrorOrder);
}
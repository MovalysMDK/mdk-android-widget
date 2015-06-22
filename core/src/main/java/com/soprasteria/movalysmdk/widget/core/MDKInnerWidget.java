package com.soprasteria.movalysmdk.widget.core;

import android.content.Context;

/**
 * Widget that can be included in a RichWidget.
 */
public interface MDKInnerWidget extends MDKWidget {

    /**
     * Set unique id of the widget.
     * @param parentId the parent id
     */
    void setUniqueId(int parentId);

    /**
     * Get uniqueId of the widget.
     * @return uniqueid the id
     */
    int getUniqueId();

    /**
     * Return android context.
     * @see android.view.View
     * @return android context
     */
    Context getContext();

    /**
     * superOnCreateDrawableState method.
     * @param extraSpace the extra space
     * @return int[] ..
     */
    int[] superOnCreateDrawableState(int extraSpace);

    /**
     * callMergeDrawableStates method.
     * @param baseState the base state
     * @param additionalState the additional state
     */
    void callMergeDrawableStates(int[] baseState, int[] additionalState);
}

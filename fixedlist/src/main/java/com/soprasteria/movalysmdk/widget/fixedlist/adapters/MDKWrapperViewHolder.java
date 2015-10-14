package com.soprasteria.movalysmdk.widget.fixedlist.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Interface used on wrapper view holders for fixed lists.
 */
public interface MDKWrapperViewHolder {

    /**
     * Returns the delete button set on the wrapper.
     * @return the delete button
     */
    View getDeleteButton();

    /**
     * Returns the view holder of the wrapped view.
     * @return the view holder
     */
    RecyclerView.ViewHolder getViewHolder();

}

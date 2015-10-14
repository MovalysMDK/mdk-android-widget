package com.soprasteria.movalysmdk.widget.fixedlist.adapters;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * View holder for the wrapping views of the items of the fixed list.
 */
public class WrapperViewHolder extends RecyclerView.ViewHolder implements MDKWrapperViewHolder {

    /** the delete button set on the wrapper. */
    private View deleteButton;

    /** the wrapped view holder. */
    private RecyclerView.ViewHolder viewHolder;

    /**
     * Constructor.
     * @param v the view being wrapped
     * @param vh the view holder of the wrapped view
     * @param deleteId the identifier of the delete button
     */
    public WrapperViewHolder(View v, RecyclerView.ViewHolder vh, @IdRes int deleteId) {
        super(v);
        deleteButton = v.findViewById(deleteId);
        this.viewHolder = vh;
    }

    @Override
    public View getDeleteButton() {
        return deleteButton;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder() {
        return viewHolder;
    }
}

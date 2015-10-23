package com.soprasteria.movalysmdk.widget.core.behavior.types;

import android.widget.BaseAdapter;

/**
 * Interface to add set adapter capacity to a widget.
 */
public interface HasAdapter {
    /**
     * Set the adapter into widget.
     *
     * @param adapter the adapter created by the user
     */
    void setAdapter(BaseAdapter adapter);

    /**
     * Returns the adapter currently associated with this widget.
     *
     * @return The adapter used to provide this view's content.
     */
    BaseAdapter getAdapter();

}

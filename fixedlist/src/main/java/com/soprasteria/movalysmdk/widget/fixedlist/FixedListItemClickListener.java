package com.soprasteria.movalysmdk.widget.fixedlist;

/**
 * Interface defining a listener to call when an item is clicked in a MDKFixedList.
 */
public interface FixedListItemClickListener {

    /**
     * Called when an item is clicked in the MDKFixedList.
     * @param position the position of the clicked item in the fixed list
     */
    void onItemClick(int position);
}

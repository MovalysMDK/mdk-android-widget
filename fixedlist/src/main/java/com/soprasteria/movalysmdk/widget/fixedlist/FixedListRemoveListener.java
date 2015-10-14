package com.soprasteria.movalysmdk.widget.fixedlist;

/**
 * Interface defining a listener to call when an item is removed from a MDKFixedList.
 */
public interface FixedListRemoveListener {

    /**
     * Called when an item is removed from the fixed list.
     * @param position the position of the removed item
     */
    void onRemoveItemClick(int position);
}

package com.soprasteria.movalysmdk.widget.core.behavior.types;

/**
 * Add MDKPresenter behavior on a widget.
 */
public interface HasPresenter {
    /**
     * Sets the email object.
     * <ul>
     * <li>title the title of the textView</li>
     * <li>uri the Uri to load</li>
     * </ul>
     *
     * @param presenter an object array with the following content
     */
    void setPresenter(Object[] presenter);

    /**
     * Returns the presenter object as an array of object.
     * The rows are the following:
     * <ul>
     * <li>title the title of the textView</li>
     * <li>uri the Uri to load</li>
     * </ul>
     *
     * @return the presenter object
     */
    Object[] getPresenter();
}

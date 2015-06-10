package com.soprasteria.movalysmdk.widget.core.behavior;

/**
 * Interface to add label behavior on widget
 */
public interface HasLabel {

    /**
     * Get the CharSequence representing the label
     * @return the label
     */
    CharSequence getLabel();

    /**
     * Set the label on the widget
     * @param label the label to set
     */
    void setLabel(CharSequence label);
}

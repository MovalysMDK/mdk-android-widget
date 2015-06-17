package com.soprasteria.movalysmdk.widget.core.behavior;

/**
 * Interface to add hint handling capacity to a widget.
 */
public interface HasHint {

    /**
     * Get the hint value.
     * @return the hint value
     */
    CharSequence getHint();

    /**
     * Set the hint value.
     * @param hint the hint value
     */
    void setHint(CharSequence hint);
}

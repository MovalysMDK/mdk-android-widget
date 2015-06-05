package com.soprasteria.movalysmdk.widget.core.behavior;

/**
 * Interface to add hint handling capacity to a widget
 */
public interface HasHint {

    /**
     * Get the hint value
     * @return the hint value
     */
    public CharSequence getHint();

    /**
     * Set the hint value
     * @param hint the hint value
     */
    public void setHint(CharSequence hint);
}

package com.soprasteria.movalysmdk.widget.core.behavior;

/**
 * Interface to add multiple hints as array handling capacity to a widget.
 */
public interface HasHints {

    /**
     * Get the hints array value.
     * @return the hints array value
     */
    CharSequence[] getHints();

    /**
     * Set the hints array value.
     * @param hints the hints array value
     */
    void setHints(CharSequence[] hints);

}

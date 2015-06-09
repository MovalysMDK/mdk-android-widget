package com.soprasteria.movalysmdk.widget.core.behavior;

/**
 * Add Text behavior on a widget
 */
public interface HasText {

    /**
     * Get the text from the component
     * @return the text of the component
     */
    public CharSequence getText();

    /**
     * Set the text on the component
     * @param text the text to set
     */
    public void setText(CharSequence text);

}

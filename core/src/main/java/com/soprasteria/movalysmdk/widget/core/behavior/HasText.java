package com.soprasteria.movalysmdk.widget.core.behavior;

/**
 * Add Text behavior on a widget.
 */
public interface HasText {

    /**
     * Get the text from the component.
     *
     * @see android.text
     *
     * @return the text of the component
     */
    CharSequence getText();

    /**
     * Set the text on the component.
     *
     * @see android.text
     * @param text the text to set
     */
    void setText(CharSequence text);

    /**
     * @see android.text.InputType
     * @param type the type
     */
    void setInputType(int type);

}

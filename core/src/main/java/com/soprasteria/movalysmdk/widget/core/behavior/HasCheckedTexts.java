package com.soprasteria.movalysmdk.widget.core.behavior;

/**
 * Created by maxime on 24/09/2015.
 */
public interface HasCheckedTexts {

    /**
     * Get the fixed text from the component.
     *
     * @return the text of the component
     */
    String getFixedText();

    /**
     * Set the fixed text on the component.
     *
     * @param text the text to set
     */
    void setFixedText(String text);

    /**
     * Get the text in checked state from the component.
     *
     * @return the text of the component
     */
    String getCheckedText();

    /**
     * Set the text in checked state on the component.
     *
     * @param text the text to set
     */
    void setCheckedText(String text);

    /**
     * Get the text in unchecked state from the component.
     *
     * @return the text of the component
     */
    String getUncheckedText();

    /**
     * Set the text in unchecked state on the component.
     *
     * @param text the text to set
     */
    void setUncheckedText(String text);

}

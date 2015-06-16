package com.soprasteria.movalysmdk.widget.core.behavior;

import android.text.method.KeyListener;
import android.view.inputmethod.EditorInfo;

/**
 * Add Text behavior on a widget.
 */
public interface HasText {

    /**
     * Get the text from the component.
     * @return the text of the component
     */
    public CharSequence getText();

    /**
     * Set the text on the component.
     * @param text the text to set
     */
    public void setText(CharSequence text);

    /**
     * Set the type of the content with a constant as defined for {EditorInfo#inputType}. This
     * will take care of changing the key listener, by calling {setKeyListener(KeyListener)},
     * to match the given content type.  If the given content type is {EditorInfo#TYPE_NULL}
     * then a soft keyboard will not be displayed for this text view.
     *
     * Note that the maximum number of displayed lines (see setMaxLines(int)}) will be
     * modified if you change the {EditorInfo#TYPE_TEXT_FLAG_MULTI_LINE} flag of the input
     * type.
     *
     * see getInputType()
     * see setRawInputType(int)
     * @see android.text.InputType
     * @attr ref android.R.styleable#TextView_inputType
     */


    /**
     * Set the type of the content with a constant as defined for inputType.
     * This will take care of changing the key listener, by calling setKeyListener(KeyListener),
     * to match the given content type. If the given content type is TYPE_NULL then a soft keyboard
     * will not be displayed for this text view.
     *
     * Note that the maximum number of displayed lines (see setMaxLines(int))
     * will be modified if you change the TYPE_TEXT_FLAG_MULTI_LINE flag of the input type.
     *
     * @param type
     */
    public void setInputType(int type);

}

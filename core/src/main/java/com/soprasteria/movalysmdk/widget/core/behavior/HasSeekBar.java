package com.soprasteria.movalysmdk.widget.core.behavior;

/**
 * Add control behavior on values of the SeekBar widget.
 */
public interface HasSeekBar {

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
     * @see android.widget.TextView#setText(CharSequence)
     * @param text the text to set
     */
    void setText(CharSequence text);

    /**
     * Get the current SeekBar's value.
     * @return the value of the component
     */
    int getSeekBarValue();

    /**
     * Set the SeekBar's value.
     * @param seekBarValue value to set
     */
    void setSeekBarValue(int seekBarValue);

    /**
     * Get the maximum SeekBar's value.
     * @return the maximum value of the component
     */
    int getSeekBarMaxValue();

    /**
     * Set the maximum SeekBar's value.
     * @param seekBarMaxValue value to set
     */
    void setSeekBarMaxValue(int seekBarMaxValue);

    /**
     * Set the progress of the seek bar
     * @param value value to set
     */
    void setSeekProgress(int value);

}

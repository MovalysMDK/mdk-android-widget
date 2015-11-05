/**
 * Copyright (C) 2010 Sopra Steria Group (movalys.support@soprasteria.com)
 *
 * This file is part of Movalys MDK.
 * Movalys MDK is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Movalys MDK is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with Movalys MDK. If not, see <http://www.gnu.org/licenses/>.
 */
package com.soprasteria.movalysmdk.widget.core.behavior.types;

import android.widget.EditText;

/**
 * Add control behavior on values of the SeekBar widget.
 */
public interface HasSeekBar {

    /**
     * Get the current SeekBar's value.
     * @return the value of the component
     */
    int getSeekBarValue();

    /**
     * Get the minimum allowed SeekBar's value.
     * @return the maximum value of the component
     */
    int getSeekBarMinAllowed();

    /**
     * Set the minimum allowed SeekBar's value.
     * @param seekBarMinValue value to set
     */
    void setSeekBarMinAllowed(int seekBarMinValue);

    /**
     * Get the maximum allowed SeekBar's value.
     * @return the maximum value of the component
     */
    int getSeekBarMaxAllowed();

    /**
     * Set the maximum allowed SeekBar's value.
     * @param seekBarMaxValue value to set
     */
    void setSeekBarMaxAllowed(int seekBarMaxValue);

    /**
     * Get the max attr of SeekBar.
     * @return the maximum value of the component
     */
    int getMax();

    /**
     * Set the max attr SeekBar.
     * @param max value to set
     */
    void setMax(int max);

    /**
     * Get the max attr of SeekBar.
     * @return the maximum value of the component
     */
    int getMin();

    /**
     * Set the min attr SeekBar.
     * @param min value to set
     */
    void setMin(int min);

    /**
     * Set the progress of the seek bar.
     * @param value value to set
     */
    void setSeekProgress(int value);


    /**
     * Get the attached edittext of the SeekBar.
     * @return the edittext component
     */
    EditText getAttachedEditText();

    /**
     * Get the attached edittext of the SeekBar.
     * @param attachedEditText EditText to set
     */
    void setAttachedEditText(EditText attachedEditText);

    /**
     * Get the readonly attribute of the seekbar's attached edittext.
     * @return true if the edittext is readonly
     */
    boolean isReadonlyEditText();


    /**
     * Set the readonly attribute of the seekbar's attached edittext.
     * @param readonly parameter to set
     */
    void setReadonlyEditText(boolean readonly);

}

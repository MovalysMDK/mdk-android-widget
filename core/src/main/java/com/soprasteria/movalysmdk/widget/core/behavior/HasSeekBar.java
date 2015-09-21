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
package com.soprasteria.movalysmdk.widget.core.behavior;

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

}

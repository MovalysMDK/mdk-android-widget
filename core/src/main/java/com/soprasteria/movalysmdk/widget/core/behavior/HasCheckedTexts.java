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

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

import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

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
     * @see android.widget.TextView#setText(CharSequence)
     * @param text the text to set
     */
    void setText(CharSequence text);

    /**
     * Set the type of the content.
     *
     * @see android.widget.TextView#setInputType(int)
     * @param type content type
     */
    void setInputType(int type);

    /**
     * Set the {@link EditorInfo} on the widget.
     * @param outAttrs the {@link EditorInfo} to set
     * @return the created {@link InputConnection}
     */
    InputConnection onCreateInputConnection(EditorInfo outAttrs);

}

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

import android.view.View;

import com.soprasteria.movalysmdk.widget.core.listener.CommandStateListener;

/**
 * Add date behavior on a widget.
 */
public interface HasEmail extends View.OnClickListener {

    /**
     * Sets the email object.
     * @param email: a string array with the following content
     * <ul>
     * <li>to the recipient of the email</li>
     * <li>cc the carbon copy of the email</li>
     * <li>bcc the blind carbon copy of the email</li>
     * <li>object the object of the email</li>
     * <li>body the body of the email</li>
     * </ul>
     */
    void setEmail(String[] email);

    /**
     * Returns the email object as an array of strings.
     * The rows are the following:
     * <ul>
     * <li>to the recipient of the email</li>
     * <li>cc the carbon copy of the email</li>
     * <li>bcc the blind carbon copy of the email</li>
     * <li>object the object of the email</li>
     * <li>body the body of the email</li>
     * </ul>
     */
    String[] getEmail();

}

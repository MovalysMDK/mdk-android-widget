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

/**
 * Add MDKPresenter behavior on a widget.
 */
public interface HasPresenter {
    /**
     * Sets the email object.
     * <ul>
     * <li>title the title of the textView</li>
     * <li>uri the Uri to load</li>
     * </ul>
     *
     * @param presenter an object array with the following content
     */
    void setPresenter(Object[] presenter);

    /**
     * Returns the presenter object as an array of object.
     * The rows are the following:
     * <ul>
     * <li>title the title of the textView</li>
     * <li>uri the Uri to load</li>
     * </ul>
     *
     * @return the presenter object
     */
    Object[] getPresenter();
}

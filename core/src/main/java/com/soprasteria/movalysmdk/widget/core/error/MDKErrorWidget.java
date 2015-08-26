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
package com.soprasteria.movalysmdk.widget.core.error;

import android.content.Context;

import com.soprasteria.movalysmdk.widget.core.helper.MDKMessages;

/**
 * MDKErrorWidget interface definition.
 * Created by MDK Team on 09/06/2015.
 */
public interface MDKErrorWidget {

    /**
     * Add and the component and its associated error message to the current list of errors.
     * @param context application context to access resource
     * @param componentId the component id
     * @param error MDKError object to add
     */
    void addError(Context context, int componentId, MDKMessages error);

    /**
     * Remove component from the error list.
     * @param context application context to access resource
     * @param innerComponentId Resource Id of the component
     */
    void clear(Context context, int innerComponentId);

    /**
     * Remove all components from the error list.
     * @param context application context to access resource
     */
    void clear(Context context);

    /**
     * Set priority to of the component to be displayed (0 higher).
     * @param displayErrorOrder displayErrorOrder tab
     */
    void setDisplayErrorOrder(int[] displayErrorOrder);

    /**
     * Set widget visibility.
     * @see android.view.View#setVisibility(int)
     * @param visible visibility of the widget
     */
    void setVisibility(int visible);
}
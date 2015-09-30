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
package com.soprasteria.movalysmdk.widget.core.message;

import android.content.Context;

/**
 * MDKMessageWidget interface definition.
 */
public interface MDKMessageWidget {

    /**
     * Add the component and its associated messages to the current list of messages.
     * @param context application context to access resource
     * @param componentId the component id
     * @param messages MDKMessages object to add
     */
    void addMessages(Context context, int componentId, MDKMessages messages);

    /**
     * Remove component from the messages list.
     * @param context application context to access resource
     * @param innerComponentId Resource Id of the component
     */
    void clear(Context context, int innerComponentId);

    /**
     * Remove all components from the messages list.
     * @param context application context to access resource
     */
    void clear(Context context);

    /**
     * Set priority to of the component to be displayed (0 higher).
     * @param displayOrder displayOrder tab
     */
    void setComponentDisplayOrder(int[] displayOrder);

    /**
     * Set widget visibility.
     * @see android.view.View#setVisibility(int)
     * @param visible visibility of the widget
     */
    void setVisibility(int visible);
}
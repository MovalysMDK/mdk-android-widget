/**
 * Copyright (C) 2010 Sopra Steria Group (movalys.support@soprasteria.com)
 * <p/>
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

import android.widget.BaseAdapter;

/**
 * Interface to add set adapter capacity to a widget.
 */
public interface HasAdapter {
    /**
     * Set the adapter into widget.
     *
     * @param adapter the adapter created by the user
     */
    void setAdapter(BaseAdapter adapter);

    /**
     * Returns the adapter currently associated with this widget.
     *
     * @return The adapter used to provide this view's content.
     */
    BaseAdapter getAdapter();

    /**
     * To get selected item.
     *
     * @return item selected by the user
     */
    Object getSelectedItem();

    /**
     * To set the selection a the specific position.
     *
     * @param position the position to set the selection
     */
    void setSelection(int position);

    /**
     * To get selected item position.
     *
     * @return the position of the item selected by the user
     */
    int getSelectedItemPosition();
}

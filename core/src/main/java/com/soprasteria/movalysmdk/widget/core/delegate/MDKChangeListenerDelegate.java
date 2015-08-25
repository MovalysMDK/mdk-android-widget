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
package com.soprasteria.movalysmdk.widget.core.delegate;

import com.soprasteria.movalysmdk.widget.core.behavior.HasChangeListener;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Delegate class for the {@link ChangeListener} objects.
 */
public class MDKChangeListenerDelegate implements HasChangeListener {

    /** {@link ChangeListener} array. */
    private List<ChangeListener> listeners;

    /**
     * Constructor.
     */
    public MDKChangeListenerDelegate() {
        listeners = new ArrayList<>();
    }

    @Override
    public void registerChangeListener(ChangeListener listener) {
        listeners.add(listener);
    }

    @Override
    public void unregisterChangeListener(ChangeListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notify the registered {@link ChangeListener}.
     */
    public void notifyListeners() {
        for (ChangeListener listener : listeners) {
            listener.onChanged();
        }
    }
}

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

import com.soprasteria.movalysmdk.widget.core.delegate.WidgetCommandDelegate;

/**
 * Interface to add button handling capacity to a widget.
 */
public interface HasCommands extends View.OnClickListener {

    /**
     * Register commands views on the components
     * (or its WidgetCommandDelegate).
     */
//    void registerWidgetCommands();

    /**
     * Returns the {@link WidgetCommandDelegate} of the widget
     * @return the {@link WidgetCommandDelegate}
     */
    WidgetCommandDelegate getWidgetCommandDelegate();
}

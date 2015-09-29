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

import com.soprasteria.movalysmdk.widget.core.formatter.MDKBaseFormatter;

public interface HasFormatter<T1, T2> {

    /**
     * Get the formatter used to display the value.
     * @return true if the edittext is editable
     */
    MDKBaseFormatter<T1, T2> getFormatter();


    /**
     * Set the formatter used to display the value.
     * @param newFormatter formatter to set
     */
    void setFormatter(MDKBaseFormatter<T1, T2> newFormatter);
}

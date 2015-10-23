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
package com.soprasteria.movalysmdk.widget.core;

/**
 * Interface for MDK widget component.
 */
public interface MDKBaseWidget {

    /**
     * Returns a {@link MDKTechnicalWidgetDelegate} object.
     * @return a {@link MDKTechnicalWidgetDelegate} object
     */
    MDKTechnicalWidgetDelegate getTechnicalWidgetDelegate();

    /**
     * Set mandatory properties on widget.
     * @param mandatory true if mandatory, false otherwise
     */
    void setMandatory(boolean mandatory);

    /**
     * Return true if the widget is mandatory.
     * @return true if the widget is mandatory, false otherwise
     */
    boolean isMandatory();


    /**
     * Set editable properties on widget.
     * @param editable true if editable, false otherwise
     */
    void setEditable(boolean editable);

    /**
     * Return true if the widget is editable.
     * @return true if the widget is editable, false otherwise
     */
    boolean isEditable();
}
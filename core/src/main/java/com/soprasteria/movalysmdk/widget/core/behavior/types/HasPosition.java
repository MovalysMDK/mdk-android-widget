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

import com.soprasteria.movalysmdk.widget.core.behavior.model.Position;

/**
 * Add location behaviour to a widget.
 */
public interface HasPosition {

    /**
     * Returns the location of the component.
     * @return the {@link Position} of component
     */
    Position getPosition();

    /**
     * Sets the location of the component.
     * @param position the {@link Position} to set on the component.
     */
    void setPosition(Position position);

    /**
     * Sets the latitude hint.
     * @param latHint the new latitude hint
     */
    void setLatitudeHint(String latHint);

    /**
     * Sets the longitude hint.
     * @param lngHint the new longitude hint
     */
    void setLongitudeHint(String lngHint);

}

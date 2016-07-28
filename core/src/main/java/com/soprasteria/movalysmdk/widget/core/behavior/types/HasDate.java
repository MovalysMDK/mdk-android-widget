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

import java.util.Date;

/**
 * Add date behavior on a widget.
 */
public interface HasDate {

    /**
     * Getter.
     * @return date the date
     */
    Date getDate();

    /**
     * Setter.
     * @param date the new date
     */
    void setDate(Date date);

    /**
     * Setter date hint.
     * @param dateHint the new date hint
     */
    void setDateHint(String dateHint);

    /**
     * Setter time hint.
     * @param timeHint the new time hint
     */
    void setTimeHint(String timeHint);

    /**
     * Setter date format.
     * @param dateFormat the new date format
     */
    void setDateFormat(String dateFormat);

    /**
     * Setter time format.
     * @param timeFormat the new time format
     */
    void setTimeFormat(String timeFormat);
}

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
package com.soprasteria.movalysmdk.widget.sample.formatter;

import com.soprasteria.movalysmdk.widget.core.formatter.MDKBaseFormatter;


/**
 * Formatter that transforms seekbar value into weekdays strings.
 */
public class WeekDaysFormatter implements MDKBaseFormatter<Integer, String> {

    /** String array of weekdays for formatting. **/
    private String[] weekdays = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    @Override
    public String format(Integer valueToFormat) {
        if (valueToFormat<7) {
            return weekdays[valueToFormat];
        }else{
            return "###";
        }
    }

    @Override
    public Integer unformat(String valueToUnformat) {
        for(int i=0;i<weekdays.length;i++){
            if(weekdays[i].equals(valueToUnformat)){
                return i;
            }
        }
        return 0;
    }
}

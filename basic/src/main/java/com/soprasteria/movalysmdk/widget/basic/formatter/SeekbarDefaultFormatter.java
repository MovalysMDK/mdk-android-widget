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
package com.soprasteria.movalysmdk.widget.basic.formatter;

import com.soprasteria.movalysmdk.widget.core.formatter.MDKBaseFormatter;

import java.text.NumberFormat;
import java.text.ParseException;

public class SeekbarDefaultFormatter implements MDKBaseFormatter<Integer, String> {

    private NumberFormat nf;

    public SeekbarDefaultFormatter(){
        nf = NumberFormat.getIntegerInstance();
        nf.setGroupingUsed(false);
    }

    @Override
    public String format(Integer valueToFormat) {
        return nf.format(valueToFormat);
    }

    @Override
    public Integer unformat(String valueToUnformat) {
        try {
            return nf.parse(valueToUnformat).intValue();
        } catch (ParseException e) {
            return 0;
        }
    }
}

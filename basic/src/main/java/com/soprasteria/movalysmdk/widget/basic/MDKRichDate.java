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
package com.soprasteria.movalysmdk.widget.basic;

import android.content.Context;
import android.util.AttributeSet;

import com.soprasteria.movalysmdk.widget.core.MDKBaseRichDateWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;

/**
 * Rich widget representing a date picker, conforming to the Material Design guidelines,
 * and including by default the floating label and the error component.
 */
public class MDKRichDate extends MDKBaseRichDateWidget<MDKDateTime> implements HasValidator {

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     */
    public MDKRichDate(Context context, AttributeSet attrs) {
        super(R.layout.mdkwidget_date_edit_label, R.layout.mdkwidget_date_edit, context, attrs);
    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     * @param defStyleAttr defStyleAttr
     */
    public MDKRichDate(Context context, AttributeSet attrs, int defStyleAttr) {
        super(R.layout.mdkwidget_date_edit_label, R.layout.mdkwidget_date_edit, context, attrs, defStyleAttr);
    }
}

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

import com.soprasteria.movalysmdk.widget.core.MDKBaseRichCheckableWidget;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasChangeListener;
import com.soprasteria.movalysmdk.widget.core.behavior.HasChecked;
import com.soprasteria.movalysmdk.widget.core.behavior.HasCheckedTexts;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;

/**
 * Rich widget representing a checkable component (CheckBox or Switch), conforming to the Material Design guidelines,
 * and including by default the error component.
 * @param <T> The class of the widget to encapsulate
 */
public class MDKRichCheckbox<T extends MDKWidget & HasValidator & HasDelegate & HasChecked & HasCheckedTexts & HasChangeListener> extends MDKBaseRichCheckableWidget<T> {

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     */
    public MDKRichCheckbox(Context context, AttributeSet attrs) {
        super(R.layout.mdkwidget_checkbox_edit_label, R.layout.mdkwidget_checkbox_edit, context, attrs);
    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     * @param defStyleAttr defStyleAttr
     */
    public MDKRichCheckbox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(R.layout.mdkwidget_checkbox_edit_label, R.layout.mdkwidget_checkbox_edit, context, attrs, defStyleAttr);
    }
}

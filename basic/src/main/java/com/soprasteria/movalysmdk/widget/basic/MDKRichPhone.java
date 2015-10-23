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

import com.soprasteria.movalysmdk.widget.core.MDKBaseRichEditWidget;
import com.soprasteria.movalysmdk.widget.core.MDKBaseWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;

/**
 * MDKRichPhone class definition.
 */
public class MDKRichPhone extends MDKBaseRichEditWidget<MDKPhone> implements MDKBaseWidget, HasValidator {

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     */
    public MDKRichPhone(Context context, AttributeSet attrs) {
        super(R.layout.mdkwidget_phone_edit_label, R.layout.mdkwidget_phone_edit, context, attrs);
    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     * @param defStyleAttr the style
     */
    public MDKRichPhone(Context context, AttributeSet attrs, int defStyleAttr) {
        super(R.layout.mdkwidget_phone_edit_label, R.layout.mdkwidget_phone_edit, context, attrs, defStyleAttr);
    }

}

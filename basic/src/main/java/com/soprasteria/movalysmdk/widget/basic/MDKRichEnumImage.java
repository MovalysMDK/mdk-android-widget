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
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.soprasteria.movalysmdk.widget.core.MDKBaseRichWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.behavior.HasEnum;

/**
 * Rich widget representing an image from enumerated resources,
 * and including by default the label and the error component.
 */
public class MDKRichEnumImage extends MDKBaseRichWidget<MDKEnumImage> implements HasValidator, HasEnum {

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     */
    public MDKRichEnumImage(Context context, AttributeSet attrs) {
        super(R.layout.mdkwidget_enumimage_edit_label, R.layout.mdkwidget_enumimage_edit, context, attrs);

        initDedicatedAttributes(attrs);
    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     * @param defStyleAttr defStyleAttr
     */
    public MDKRichEnumImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(R.layout.mdkwidget_enumimage_edit_label, R.layout.mdkwidget_enumimage_edit, context, attrs, defStyleAttr);

        initDedicatedAttributes(attrs);
    }

    /**
     * Initialize MDK widget class variables with layout attributes of the Rich component.
     * @param attrs Array of attributes of the MDK widget
     */
    private void initDedicatedAttributes(AttributeSet attrs) {
        // Parse the enum_prefix attribute
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKEnumImage);
        String resEnumPrefix = typedArray.getString(R.styleable.MDKCommons_MDKEnumImage_enum_prefix);
        if(resEnumPrefix != null) {
            setEnumPrefix(resEnumPrefix);
        }
        typedArray.recycle();
    }

    @Override
    public Enum getEnumValue() {
        return getInnerWidget().getEnumValue();
    }

    @Override
    public void setEnumValue(Enum value) {
        getInnerWidget().setEnumValue(value);
    }

    @Override
    public String getEnumPrefix() {
        return getInnerWidget().getEnumPrefix();
    }

    @Override
    public void setEnumPrefix(String prefix) {
        getInnerWidget().setEnumPrefix(prefix);
    }
}

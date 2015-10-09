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
import android.view.View;

import com.soprasteria.movalysmdk.widget.core.MDKBaseRichWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.behavior.types.HasEnum;

/**
 * Rich widget representing an image from enumerated resources,
 * and including by default the label and the error component.
 */
public class MDKRichEnumView extends MDKBaseRichWidget<MDKEnumView> implements HasValidator, HasEnum {

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     */
    public MDKRichEnumView(Context context, AttributeSet attrs) {
        super(R.layout.mdkwidget_enumimage_edit_label, R.layout.mdkwidget_enumimage_edit, context, attrs);

        initDedicatedAttributes(attrs);
    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     * @param defStyleAttr defStyleAttr
     */
    public MDKRichEnumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(R.layout.mdkwidget_enumimage_edit_label, R.layout.mdkwidget_enumimage_edit, context, attrs, defStyleAttr);

        initDedicatedAttributes(attrs);
    }

    /**
     * Initialize MDK widget class variables with layout attributes of the Rich component.
     * @param attrs Array of attributes of the MDK widget
     */
    private void initDedicatedAttributes(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKEnumImage);

        // Parse the enum_prefix attribute
        String resEnumPrefix = typedArray.getString(R.styleable.MDKCommons_MDKEnumImage_enum_prefix);
        if(resEnumPrefix != null) {
            setResourceNamePrefix(resEnumPrefix);
        }

        // Parse the mode of EnumView
        if (typedArray.getInt(R.styleable.MDKCommons_MDKEnumImage_enum_mode, MDKEnumView.MODE_IMAGE) == MDKEnumView.MODE_TEXT) {
            setMode(MDKEnumView.MODE_TEXT);
        } else {
            setMode(MDKEnumView.MODE_IMAGE);
        }

        typedArray.recycle();
    }

    @Override
    public Enum getValueAsEnumValue() {
        return getInnerWidget().getValueAsEnumValue();
    }

    @Override
    public void setValueFromEnum(Enum value) {
        getInnerWidget().setValueFromEnum(value);
    }

    @Override
    public String getValueAsString() {
        return getInnerWidget().getValueAsString();
    }

    @Override
    public void setValueFromString(String name) {
        getInnerWidget().setValueFromString(name);
    }

    @Override
    public int getValueAsId() {
        return getInnerWidget().getValueAsId();
    }

    @Override
    public void setValueFromId(int id) {
        getInnerWidget().setValueFromId(id);
    }

    @Override
    public String getResourceNamePrefix() {
        return getInnerWidget().getResourceNamePrefix();
    }

    @Override
    public void setResourceNamePrefix(String prefix) {
        getInnerWidget().setResourceNamePrefix(prefix);
    }

    /**
     * Gets the view managed by this widget. Type depends on the mode:
     * - ImageView for image mode.
     * - TextView for text mode.
     * @return the inner view
     */
    public View getModeView(){
        return getInnerWidget().getModeView();
    }

    /**
     * Gets the widget's mode.
     * @return the resource name prefix
     */
    public int getMode() {
        return getInnerWidget().getMode();
    }

    /**
     * Sets the widget's mode.
     * @param mode the mode from the list of possible modes
     */
    public void setMode(@MDKEnumView.EnumMode int mode) {
        getInnerWidget().setMode(mode);
    }
}

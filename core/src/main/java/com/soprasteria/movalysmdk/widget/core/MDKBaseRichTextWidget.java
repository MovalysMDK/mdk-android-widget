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

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;

import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasText;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;

/**
 * Base implementation of the rich mdk widget for widget that uses text.
 * @param <T> the inner widget type
 */
public class MDKBaseRichTextWidget<T extends MDKWidget & MDKRestorableWidget & HasValidator & HasText & HasDelegate> extends MDKBaseRichWidget<T> implements HasText {

    /**
     * Constructor.
     * @param layoutWithLabelId the layoutWithLabelId
     * @param layoutWithoutLabelId the layoutWithoutLabelId
     * @param context the context
     * @param attrs attributes
     */
    public MDKBaseRichTextWidget(@LayoutRes int layoutWithLabelId, @LayoutRes int layoutWithoutLabelId, Context context, AttributeSet attrs) {
        super(layoutWithLabelId, layoutWithoutLabelId, context, attrs);
    }

    /**
     * Constructor.
     * @param layoutWithLabelId the layoutWithLabelId
     * @param layoutWithoutLabelId the layoutWithoutLabelId
     * @param context the context
     * @param attrs attributes
     * @param defStyleAttr defStyleAttr
     */
    public MDKBaseRichTextWidget(@LayoutRes int layoutWithLabelId, @LayoutRes int layoutWithoutLabelId, Context context, AttributeSet attrs, @StyleableRes int defStyleAttr) {
        super(layoutWithLabelId, layoutWithoutLabelId, context, attrs, defStyleAttr);
    }

    @Override
    public CharSequence getText() {
        return this.getInnerWidget().getText();
    }

    @Override
    public void setText(CharSequence text) {
        this.getInnerWidget().setText(text);
    }

    @Override
    public void setInputType(int type) {
        this.getInnerWidget().setInputType(type);
    }
}

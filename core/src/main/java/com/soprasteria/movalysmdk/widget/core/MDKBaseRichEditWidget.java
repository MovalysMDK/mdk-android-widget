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
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasText;
import com.soprasteria.movalysmdk.widget.core.behavior.HasTextWatcher;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;

/**
 * Base implementation of the rich mdk widget for widget that uses text and are editable.
 * @param <T> the inner widget type
 */
public class MDKBaseRichEditWidget<T extends MDKWidget & MDKRestorableWidget & HasText & HasTextWatcher & HasValidator & HasDelegate> extends MDKBaseRichTextWidget<T> implements HasTextWatcher {

    /**
     * Constructor.
     * @param withLabelLayout the layoutWithLabelId
     * @param noLabelLayout the layoutWithoutLabelId
     * @param context the context
     * @param attrs attributes
     */
    public MDKBaseRichEditWidget(@LayoutRes int withLabelLayout,@LayoutRes int noLabelLayout, Context context, AttributeSet attrs) {
        super(withLabelLayout, noLabelLayout, context, attrs);
    }

    /**
     * Constructor.
     * @param layoutWithLabelId the layoutWithLabelId
     * @param layoutWithoutLabelId the layoutWithoutLabelId
     * @param context the android context
     * @param attrs collection of attributes
     * @param defStyleAttr attribute in the current theme referencing a style resource
     */
    public MDKBaseRichEditWidget(@LayoutRes int layoutWithLabelId, @LayoutRes int layoutWithoutLabelId, Context context, AttributeSet attrs, @StyleableRes int defStyleAttr) {
        super(layoutWithLabelId, layoutWithoutLabelId, context, attrs, defStyleAttr);
    }

    /**
     * Add a text change listener on the inner widget.
     * @param textWatcher the text watcher to register
     */
    @Override
    public void addTextChangedListener(TextWatcher textWatcher) {
        this.getInnerWidget().addTextChangedListener(textWatcher);
    }

    /**
     * Remove a text change listener from the inner widget.
     * @param textWatcher the text watcher to remove
     */
    @Override
    public void removeTextChangedListener(TextWatcher textWatcher) {
        this.getInnerWidget().removeTextChangedListener(textWatcher);
    }
}

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
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;

import com.soprasteria.movalysmdk.widget.core.behavior.HasChangeListener;
import com.soprasteria.movalysmdk.widget.core.behavior.HasChecked;
import com.soprasteria.movalysmdk.widget.core.behavior.HasCheckedTexts;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKChangeListenerDelegate;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;

import java.lang.ref.WeakReference;

/**
 * MDK Rich Checkable Widget.
 * <p>A rich widget adds the following features on an base widget :</p>
 * <ul>
 *     <li>label</li>
 *     <li>error/helper</li>
 * </ul>
 * <p>The layout can be customized with the attribute mdk:layout</p>
 * @param <T> the type of inner widget for the rich widget
 */
public class MDKBaseRichCheckableWidget<T extends MDKWidget & HasValidator & HasDelegate & HasChecked & HasCheckedTexts & HasChangeListener> extends MDKBaseRichWidget implements HasChangeListener, HasChecked {

    /**
     * Constructor.
     * @param layoutWithLabelId layoutWithLabelId
     * @param layoutWithoutLabelId layoutWithoutLabelId
     * @param context the context
     * @param attrs attributes
     */
    public MDKBaseRichCheckableWidget(@LayoutRes int layoutWithLabelId, @LayoutRes int layoutWithoutLabelId, Context context, AttributeSet attrs) {
        super(layoutWithLabelId, layoutWithoutLabelId, context, attrs);
        init(context, attrs);
    }

    /**
     * Constructor.
     * @param layoutWithLabelId layoutWithLabelId
     * @param layoutWithoutLabelId layoutWithoutLabelId
     * @param context the context
     * @param attrs attributes
     * @param defStyleAttr the style
     */
    public MDKBaseRichCheckableWidget(@LayoutRes int layoutWithLabelId, @LayoutRes int layoutWithoutLabelId, Context context, AttributeSet attrs, @StyleableRes int defStyleAttr) {
        super(layoutWithLabelId, layoutWithoutLabelId, context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * Initialise the object.
     * @param ctx the related context
     * @param attrs the view attributes
     */
    private void init(Context ctx, AttributeSet attrs) {

        TypedArray typedArray = ctx.obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKCheckableComponent);


        String textFixedStr = typedArray.getString(R.styleable.MDKCommons_MDKCheckableComponent_text_fixed);
        if (textFixedStr != null) {
            getInnerWidget().setFixedText(textFixedStr);
        }else {
            String textCheckedStr = typedArray.getString(R.styleable.MDKCommons_MDKCheckableComponent_text_checked);
            if (textCheckedStr != null) {
                getInnerWidget().setCheckedText(textCheckedStr);
            }

            String textUncheckedStr = typedArray.getString(R.styleable.MDKCommons_MDKCheckableComponent_text_unchecked);
            if (textUncheckedStr != null) {
                getInnerWidget().setUncheckedText(textUncheckedStr);
            }
        }

        typedArray.recycle();

    }

    /**
     * Getter for the inner widget of the rich widget.
     * @return the inner widget
     */
    @Override
    public T getInnerWidget()   {
        return (T) super.getInnerWidget();
    }

    @Override
    public void registerChangeListener(ChangeListener listener) {
        this.getInnerWidget().registerChangeListener(listener);
    }

    @Override
    public void unregisterChangeListener(ChangeListener listener) {
        this.getInnerWidget().unregisterChangeListener(listener);
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
    public boolean isChecked() {
        return this.getInnerWidget().isChecked();
    }

    @Override
    public void setChecked(boolean isChecked) {
        this.getInnerWidget().setChecked(isChecked);
    }
}

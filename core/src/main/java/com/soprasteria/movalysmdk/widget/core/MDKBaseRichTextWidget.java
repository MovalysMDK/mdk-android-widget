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
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.behavior.types.HasText;

/**
 * Base implementation of the rich mdk widget for widget that uses text.
 * @param <T> the inner widget type
 */
public class MDKBaseRichTextWidget<T extends MDKWidget & HasValidator & HasText & HasDelegate> extends MDKBaseRichWidget<T> implements HasText {

    /**
     * index of SANS typeface.
     */
    private static final int SANS = 1;
    /**
     * index of SERIF typeface.
     */
    private static final int SERIF = 2;
    /**
     * index of MONOSPACE typeface.
     */
    private static final int MONOSPACE = 3;

    /**
     * Constructor.
     * @param layoutWithLabelId the layoutWithLabelId
     * @param layoutWithoutLabelId the layoutWithoutLabelId
     * @param context the context
     * @param attrs attributes
     */
    public MDKBaseRichTextWidget(@LayoutRes int layoutWithLabelId, @LayoutRes int layoutWithoutLabelId, Context context, AttributeSet attrs) {
        super(layoutWithLabelId, layoutWithoutLabelId, context, attrs);
        init(this.getContext(), attrs);
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
        init(this.getContext(), attrs);
    }

    /**
     * Initialise method.
     * <p>
     *     forward the text color parameter,
     *     the text size parameter,
     *     the ems parameter,
     *     the type face parameter,
     *     the font family parameter,
     *     the text style parameter and
     *     the text paramter.
     * </p>
     * @param context the android context
     * @param attrs the widget attributes
     */
    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RichTextView);

        int textSize = 15;
        int typefaceIndex = -1;
        int ems = -1;
        ColorStateList textColor = null;
        String fontFamily = null;
        int styleIndex = -1;
        CharSequence text = null;

        if (!this.isInEditMode()) {

            int n = typedArray.getIndexCount();
            for (int i = 0; i < n; i++) {
                int attr = typedArray.getIndex(i);

                if (this.getInnerWidget() instanceof TextView) {
                    if (attr == R.styleable.RichTextView_android_textColor) {
                        textColor = typedArray.getColorStateList(attr);
                        ((TextView) this.getInnerWidget()).setTextColor(textColor);
                    } else if (attr == R.styleable.RichTextView_android_textSize) {
                        textSize = typedArray.getDimensionPixelSize(attr, -1);
                        if (textSize != -1) {
                            ((TextView) this.getInnerWidget()).setTextSize(textSize);
                        }
                    } else if (attr == R.styleable.RichTextView_android_ems) {
                        ems = typedArray.getInt(attr, -1);
                        ((TextView) this.getInnerWidget()).setEms(ems);
                    } else if (attr == R.styleable.RichTextView_android_typeface) {
                        typefaceIndex = typedArray.getInt(attr, -1);
                    } else if (attr == R.styleable.RichTextView_android_fontFamily) {
                        fontFamily = typedArray.getString(attr);
                    } else if (attr == R.styleable.RichTextView_android_textStyle) {
                        styleIndex = typedArray.getInt(attr, -1);
                    } else if (attr == R.styleable.RichTextView_android_text) {
                        text = typedArray.getText(attr);
                        this.getInnerWidget().setText(text);
                    }
                    this.setTypefaceFromAttrs(fontFamily, typefaceIndex, styleIndex);
                }
            }
        }

        typedArray.recycle();

    }

    /**
     * Set the typeface on the inner component from attributes.
     * @param familyName the typeface family as String
     * @param typefaceIndex the typeface index
     * @param styleIndex the style index
     */
    private void setTypefaceFromAttrs(String familyName, int typefaceIndex, int styleIndex) {
        Typeface tf = null;
        if (familyName != null) {
            tf = Typeface.create(familyName, styleIndex);
            if (tf != null) {
                ((TextView) this.getInnerWidget()).setTypeface(tf);
                return;
            }
        }
        switch (typefaceIndex) {
            case SANS:
                tf = Typeface.SANS_SERIF;
                break;

            case SERIF:
                tf = Typeface.SERIF;
                break;

            case MONOSPACE:
                tf = Typeface.MONOSPACE;
                break;
        }

        ((TextView) this.getInnerWidget()).setTypeface(tf, styleIndex);
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

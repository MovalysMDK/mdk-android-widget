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
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import com.soprasteria.movalysmdk.widget.core.MDKBaseRichEditWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasHint;
import com.soprasteria.movalysmdk.widget.core.behavior.HasText;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;

/**
 * Represents an Edit Text conforming with the Material Design guidelines, and including by default the floating
 * label and the error component.
 */
public class MDKRichEditText extends MDKBaseRichEditWidget<MDKEditText> implements HasHint, HasValidator, HasText {

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     */
    public MDKRichEditText(Context context, AttributeSet attrs) {
        super(R.layout.mdkwidget_edittext_edit_label, R.layout.mdkwidget_edittext_edit, context, attrs);

        if (!isInEditMode()) {
            init();
        }
    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     * @param defStyleAttr defStyleAttr
     */
    public MDKRichEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(R.layout.mdkwidget_edittext_edit_label, R.layout.mdkwidget_edittext_edit, context, attrs, defStyleAttr);

        if (!isInEditMode()) {
            init();
        }
    }

    /**
     * Called by the constructor.
     */
    private void init() {

        // If there is no hint, use the label value as hint
        if (this.getResHintId() != 0) {
            this.getInnerWidget().setHint(this.getResHintId());
        }
    }

    @Override
    public CharSequence getHint() {
        return this.getInnerWidget().getHint();
    }

    @Override
    public void setHint(CharSequence hint) {
        this.getInnerWidget().setHint(hint);
    }

    @Override
    public FormFieldValidator getValidator() {
        return this.getInnerWidget().getValidator();
    }

    /**
     * Sets the text color of the inner EditText.
     * @param colors the new color
     */
    public void setTextColor(ColorStateList colors) {
        this.getInnerWidget().setTextColor(colors);
    }

    /**
     * Sets the hint color of the inner EditText.
     * @param colors the new color
     */
    public void setHintTextColor(ColorStateList colors) {
        this.getInnerWidget().setHintTextColor(colors);
    }

    /**
     * Sets the hint color of the inner EditText.
     * @param color the new color
     */
    public void setHintTextColor(int color) {
        this.getInnerWidget().setHintTextColor(color);
    }

    /**
     * Sets the inner EditText enabled state.
     * @param enabled enabled
     */
    @Override
    public void setEnabled(boolean enabled) {
        this.getInnerWidget().setEnabled(enabled);
    }

    /**
     * Limit the EditText to be single line.
     */
    public void setSingleLine() {
        this.getInnerWidget().setSingleLine();
    }

    /**
     * Sets if the EditText is single or multi line.
     * @param singleLine the single line
     */
    public void setSingleLine(boolean singleLine) {
        this.getInnerWidget().setSingleLine(singleLine);
    }

    /**
     * Sets the EditText text size.
     * @param size the new size
     */
    public void setTextSize(float size) {
        this.getInnerWidget().setTextSize(size);
    }

    /**
     * Sets the EditText text size.
     * @param unit unit
     * @param size size
     */
    public void setTextSize(int unit, float size) {
        this.getInnerWidget().setTextSize(unit, size);
    }

    /**
     * onCreateInputConnection method.
     * @param outAttrs outAttrs
     * @return x
     */
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return getInnerWidget().onCreateInputConnection(outAttrs);
    }

    @Override
    public void setInputType(int type) {
        this.getInnerWidget().setInputType(type);
    }

}
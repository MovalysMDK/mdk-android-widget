package com.soprasteria.movalysmdk.widget.standard;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;

import com.soprasteria.movalysmdk.widget.base.MDKBaseRichEditWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasHint;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.validator.IFormFieldValidator;

/**
 * Represents an Edit Text conforming with the Material Design guidelines, and including by default the floating
 * label and the error component.
 *
 * Created by belamrani on 09/06/2015.
 */
public class MDKRichEditText extends MDKBaseRichEditWidget<MDKEditText> implements HasHint, HasValidator {

    /**
     * Constructor
     * @param context
     * @param attrs
     */
    public MDKRichEditText(Context context, AttributeSet attrs) {
        super(R.layout.fwk_component_edittext_edit_label, R.layout.fwk_component_edittext_edit, context, attrs);

        init();
    }

    /**
     * Constructor
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public MDKRichEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(R.layout.fwk_component_edittext_edit_label, R.layout.fwk_component_edittext_edit, context, attrs, defStyleAttr);

        init();
    }

    /**
     * Called by the constructor
     */
    private void init() {

        // If there is no hint, use the label value as hint
        CharSequence label = this.getInnerWidget().getLabel();
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
    public IFormFieldValidator getValidator() {
        return this.getInnerWidget().getValidator();
    }

    /**
     * Sets the text color of the inner EditText
     * @param colors
     */
    public void setTextColor(ColorStateList colors) {
        this.getInnerWidget().setTextColor(colors);
    }

    /**
     * Sets the hint color of the inner EditText
     * @param colors
     */
    public void setHintTextColor(ColorStateList colors) {
        this.getInnerWidget().setHintTextColor(colors);
    }

    /**
     * Sets the hint color of the inner EditText
     * @param color
     */
    public void setHintTextColor(int color) {
        this.getInnerWidget().setHintTextColor(color);
    }

    /**
     * Sets the inner EditText enabled state
     * @param enabled
     */
    public void setEnabled(boolean enabled) {
        this.getInnerWidget().setEnabled(enabled);
    }

    /**
     * Limit the EditText to be single line
     */
    public void setSingleLine() {
        this.getInnerWidget().setSingleLine();
    }

    /**
     * Sets if the EditText is single or multi line
     * @param singleLine
     */
    public void setSingleLine(boolean singleLine) {
        this.getInnerWidget().setSingleLine(singleLine);
    }

    /**
     * Sets the EditText text size
     * @param size
     */
    public void setTextSize(float size) {
        this.getInnerWidget().setTextSize(size);
    }

    /**
     * Sets the EditText text size
     * @param unit
     * @param size
     */
    public void setTextSize(int unit, float size) {
        this.getInnerWidget().setTextSize(unit, size);
    }
}
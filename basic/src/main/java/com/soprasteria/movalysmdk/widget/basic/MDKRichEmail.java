package com.soprasteria.movalysmdk.widget.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import com.soprasteria.movalysmdk.widget.core.MDKBaseRichEditWidget;
import com.soprasteria.movalysmdk.widget.core.MDKBaseWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasHint;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;

/**
 * MDKRichEmail class definition.
 */
public class MDKRichEmail extends MDKBaseRichEditWidget<MDKEmail> implements MDKBaseWidget, HasValidator, HasHint {

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     */
    public MDKRichEmail(Context context, AttributeSet attrs) {
        super(R.layout.mdkwidget_email_edit_label, R.layout.mdkwidget_email_edit, context, attrs);
    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     * @param defStyleAttr the style
     */
    public MDKRichEmail(Context context, AttributeSet attrs, int defStyleAttr) {
        super(R.layout.mdkwidget_email_edit_label, R.layout.mdkwidget_email_edit, context, attrs, defStyleAttr);
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
    public void setInputType(int type) {
        this.getInnerWidget().setInputType(type);
    }

    @Override
    public FormFieldValidator getValidator() {
        return this.getInnerWidget().getValidator();
    }

    /**
     * onCreateInputConnection method.
     * @param outAttrs attributes
     * @return InputConnection the input connection
     */
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return getInnerWidget().onCreateInputConnection(outAttrs);
    }
}

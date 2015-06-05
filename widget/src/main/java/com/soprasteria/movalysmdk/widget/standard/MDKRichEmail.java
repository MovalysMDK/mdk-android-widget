package com.soprasteria.movalysmdk.widget.standard;

import android.content.Context;
import android.util.AttributeSet;

import com.soprasteria.movalysmdk.widget.base.MDKBaseRichEditWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasHint;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.validator.IFormFieldValidator;

public class MDKRichEmail extends MDKBaseRichEditWidget<MDKEmail> implements HasValidator, HasHint {

    private boolean errorAlwaysVisible = false;

    public MDKRichEmail(Context context, AttributeSet attrs) {
        // TODO layout without label
        super(R.layout.fwk_component_email_edit_label, R.layout.fwk_component_email_edit_label, context, attrs);
    }

    public MDKRichEmail(Context context, AttributeSet attrs, int defStyleAttr) {
        // TODO layout without label
        super(R.layout.fwk_component_email_edit_label, R.layout.fwk_component_email_edit_label, context, attrs, defStyleAttr);
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
}

package com.soprasteria.movalysmdk.widget.standard;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.soprasteria.movalysmdk.widget.base.MDKBaseRichEditWidget;
import com.soprasteria.movalysmdk.widget.base.delegate.HasMdkDelegate;
import com.soprasteria.movalysmdk.widget.base.delegate.MdkWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasHint;
import com.soprasteria.movalysmdk.widget.core.behavior.HasLabel;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.validator.IFormFieldValidator;

/**
 * Represents an Edit Text conforming with the Material Design guidelines, and including by default the floating
 * label and the error component.
 *
 * Created by belamrani on 09/06/2015.
 */
public class MDKRichEditText extends MDKBaseRichEditWidget<MDKEditText> implements HasHint, HasValidator {


    public MDKRichEditText(Context context, AttributeSet attrs) {
        super(R.layout.fwk_component_edittext_edit_label, R.layout.fwk_component_edittext_edit, context, attrs);

        init();
    }

    public MDKRichEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(R.layout.fwk_component_edittext_edit_label, R.layout.fwk_component_edittext_edit, context, attrs, defStyleAttr);

        init();
    }

    private void init() {

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
}
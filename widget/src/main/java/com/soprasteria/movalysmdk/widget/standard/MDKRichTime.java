package com.soprasteria.movalysmdk.widget.standard;

import android.content.Context;
import android.util.AttributeSet;

import com.soprasteria.movalysmdk.widget.base.MDKBaseRichWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasError;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;

/**
 * Rich widget representing a time picker, conforming to the Material Design guidelines,
 * and including by default the floating label and the error component.
 */
public class MDKRichTime extends MDKBaseRichWidget<MDKDateTime> implements HasValidator, HasError {
    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     */
    public MDKRichTime(Context context, AttributeSet attrs) {
        super(R.layout.fwk_component_time_edit_label, R.layout.fwk_component_time_edit, context, attrs);
    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     * @param defStyleAttr defStyleAttr
     */
    public MDKRichTime(Context context, AttributeSet attrs, int defStyleAttr) {
        super(R.layout.fwk_component_time_edit_label, R.layout.fwk_component_time_edit, context, attrs, defStyleAttr);
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public FormFieldValidator getValidator() {
        return this.getInnerWidget().getValidator();
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public boolean validate() {
        return this.getInnerWidget().validate();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.getInnerWidget().setEnabled(enabled);
    }
}

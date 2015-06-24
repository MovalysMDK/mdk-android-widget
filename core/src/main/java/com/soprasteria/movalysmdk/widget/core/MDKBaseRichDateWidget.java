package com.soprasteria.movalysmdk.widget.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;

/**
 * MDK Base Rich Date Widget.
 * <p>Override the base class to refactor code for methods :</p>
 * <ul>
 *     <li>getValidator</li>
 *     <li>validate</li>
 *     <li>setEnable</li>
 * </ul>
 * <p>Add a validator behavior on the base rich widget</p>
 * @param <T> the inner widget type
 */
public class MDKBaseRichDateWidget<T extends MDKWidget & MDKRestorableWidget & HasValidator> extends MDKBaseRichWidget<T> implements HasValidator {

    /**
     * Constructor.
     * @param layoutWithLabelId Id of layoutWithLabel
     * @param layoutWithoutLabelId Id of layoutWithoutLabel
     * @param context Context
     * @param attrs Collection of attributes
     */
    public MDKBaseRichDateWidget(int layoutWithLabelId, int layoutWithoutLabelId, Context context, AttributeSet attrs) {
        super(layoutWithLabelId, layoutWithoutLabelId, context, attrs);
    }

    /**
     * Constructor.
     * @param layoutWithLabelId Id of layoutWithLabel
     * @param layoutWithoutLabelId Id of layoutWithoutLabel
     * @param context Context
     * @param attrs Collection of attributes
     * @param defStyleAttr Attribute in the current theme referencing a style resource
     */
    public MDKBaseRichDateWidget(int layoutWithLabelId, int layoutWithoutLabelId, Context context, AttributeSet attrs, int defStyleAttr) {
        super(layoutWithLabelId, layoutWithoutLabelId, context, attrs, defStyleAttr);
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

    /**
     * Override the default android setEnable on view and
     * call the inner component setEnable.
     * @param enabled Enable or not the view
     */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        ((View) this.getInnerWidget()).setEnabled(enabled);
    }
}

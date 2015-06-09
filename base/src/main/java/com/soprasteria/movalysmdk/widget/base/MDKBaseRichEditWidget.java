package com.soprasteria.movalysmdk.widget.base;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasText;
import com.soprasteria.movalysmdk.widget.core.behavior.HasTextWatcher;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;

/**
 * Base implementation of the rich mdk widget for widget that uses text and are editable
 * @param <T> the inner widget type
 */
public class MDKBaseRichEditWidget<T extends MDKWidget & HasText & HasTextWatcher & HasValidator> extends MDKBaseRichTextWidget<T> implements HasTextWatcher {

    public MDKBaseRichEditWidget(int layoutWithLabelId, int layoutWithoutLabelId, Context context, AttributeSet attrs) {
        super(layoutWithLabelId, layoutWithoutLabelId, context, attrs);
    }

    public MDKBaseRichEditWidget(int layoutWithLabelId, int layoutWithoutLabelId, Context context, AttributeSet attrs, int defStyleAttr) {
        super(layoutWithLabelId, layoutWithoutLabelId, context, attrs, defStyleAttr);
    }

    @Override
    public void addTextChangedListener(TextWatcher textWatcher) {
        this.getInnerWidget().addTextChangedListener(textWatcher);
    }

    @Override
    public void removeTextChangedListener(TextWatcher textWatcher) {
        this.getInnerWidget().removeTextChangedListener(textWatcher);
    }

    /**
     * Validate the component
     * <p>Use the validation of the inner widget</p>
     * @return true if the widget is valide, false otherwise
     */
    public boolean validate() {
        boolean bValid = true;
        bValid = this.getInnerWidget().validate();
        return bValid;
    }
}

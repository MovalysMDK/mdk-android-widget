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
    public void addTextWatcher(TextWatcher textWatcher) {
        this.getInnerWidget().addTextWatcher(textWatcher);
    }

    @Override
    public void removeTextWatcher(TextWatcher textWatcher) {
        this.getInnerWidget().removeTextWatcher(textWatcher);
    }

    public boolean validate() {
        boolean bValid = true;
        bValid = this.getInnerWidget().validate();
        return bValid;
    }
}

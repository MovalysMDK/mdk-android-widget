package com.soprasteria.movalysmdk.widget.base;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasText;
import com.soprasteria.movalysmdk.widget.core.behavior.HasTextWatcher;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;

/**
 * Created by abelliard on 04/06/2015.
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
        this.getInternalWidget().addTextWatcher(textWatcher);
    }

    @Override
    public void removeTextWatcher(TextWatcher textWatcher) {
        this.getInternalWidget().removeTextWatcher(textWatcher);
    }

    public boolean validate() {
        boolean bValid = true;
        bValid = this.getInternalWidget().validate();
        // TODO Change return type of validate to error
        return bValid;
    }
}

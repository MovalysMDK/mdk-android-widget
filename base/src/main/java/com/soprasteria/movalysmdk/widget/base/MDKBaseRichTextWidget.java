package com.soprasteria.movalysmdk.widget.base;

import android.content.Context;
import android.util.AttributeSet;

import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasText;

/**
 * Created by abelliard on 04/06/2015.
 */
public class MDKBaseRichTextWidget<T extends MDKWidget & HasText> extends MDKBaseRichWidget<T> implements HasText {

    public MDKBaseRichTextWidget(int layoutWithLabelId, int layoutWithoutLabelId, Context context, AttributeSet attrs) {
        super(layoutWithLabelId, layoutWithoutLabelId, context, attrs);
    }

    public MDKBaseRichTextWidget(int layoutWithLabelId, int layoutWithoutLabelId, Context context, AttributeSet attrs, int defStyleAttr) {
        super(layoutWithLabelId, layoutWithoutLabelId, context, attrs, defStyleAttr);
    }

    @Override
    public CharSequence getText() {
        return this.getInternalWidget().getText();
    }

    @Override
    public void setText(CharSequence text) {
        this.getInternalWidget().setText(text);
    }
}

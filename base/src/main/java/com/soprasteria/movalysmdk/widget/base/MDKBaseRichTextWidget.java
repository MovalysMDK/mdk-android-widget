package com.soprasteria.movalysmdk.widget.base;

import android.content.Context;
import android.util.AttributeSet;

import com.soprasteria.movalysmdk.widget.core.MDKRestoreWidget;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasText;

/**
 * Base implementation of the rich mdk widget for widget that uses text
 * @param <T> the inner widget type
 */
public class MDKBaseRichTextWidget<T extends MDKWidget & MDKRestoreWidget & HasText> extends MDKBaseRichWidget<T> implements HasText {

    public MDKBaseRichTextWidget(int layoutWithLabelId, int layoutWithoutLabelId, Context context, AttributeSet attrs) {
        super(layoutWithLabelId, layoutWithoutLabelId, context, attrs);
    }

    public MDKBaseRichTextWidget(int layoutWithLabelId, int layoutWithoutLabelId, Context context, AttributeSet attrs, int defStyleAttr) {
        super(layoutWithLabelId, layoutWithoutLabelId, context, attrs, defStyleAttr);
    }

    @Override
    public CharSequence getText() {
        return this.getInnerWidget().getText();
    }

    @Override
    public void setText(CharSequence text) {
        this.getInnerWidget().setText(text);
    }

    @Override
    public void setInputType(int type) { this.getInnerWidget().setInputType(type);
    }
}

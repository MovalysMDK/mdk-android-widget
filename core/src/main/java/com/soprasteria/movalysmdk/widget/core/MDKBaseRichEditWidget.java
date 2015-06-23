package com.soprasteria.movalysmdk.widget.core;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.soprasteria.movalysmdk.widget.core.behavior.HasText;
import com.soprasteria.movalysmdk.widget.core.behavior.HasTextWatcher;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;

/**
 * Base implementation of the rich mdk widget for widget that uses text and are editable.
 * @param <T> the inner widget type
 */
public class MDKBaseRichEditWidget<T extends MDKWidget & MDKRestorableWidget & HasText & HasTextWatcher & HasValidator> extends MDKBaseRichTextWidget<T> implements HasTextWatcher {

    /**
     * Constructor.
     * @param withLabelLayout the layoutWithLabelId
     * @param noLabelLayout the layoutWithoutLabelId
     * @param context the context
     * @param attrs attributes
     */
    public MDKBaseRichEditWidget(@LayoutRes int withLabelLayout,@LayoutRes int noLabelLayout, Context context, AttributeSet attrs) {
        super(withLabelLayout, noLabelLayout, context, attrs);
    }

    /**
     * Constructor.
     * @param layoutWithLabelId the layoutWithLabelId
     * @param layoutWithoutLabelId the layoutWithoutLabelId
     * @param context the android context
     * @param attrs collection of attributes
     * @param defStyleAttr attribute in the current theme referencing a style resource
     */
    public MDKBaseRichEditWidget(int layoutWithLabelId, int layoutWithoutLabelId, Context context, AttributeSet attrs, int defStyleAttr) {
        super(layoutWithLabelId, layoutWithoutLabelId, context, attrs, defStyleAttr);
    }

    /**
     * Add a text change listener on the inner widget.
     * @param textWatcher the text watcher to register
     */
    @Override
    public void addTextChangedListener(TextWatcher textWatcher) {
        this.getInnerWidget().addTextChangedListener(textWatcher);
    }

    /**
     * Remove a text change listener from the inner widget.
     * @param textWatcher the text watcher to remove
     */
    @Override
    public void removeTextChangedListener(TextWatcher textWatcher) {
        this.getInnerWidget().removeTextChangedListener(textWatcher);
    }

    /**
     * Set the type of the content.
     * @param type type of content
     */
    @Override
    public void setInputType(int type) {
        this.getInnerWidget().setInputType(type);
    }

    /**
     * Validate the component.
     * <p>Use the validation of the inner widget</p>
     * @return true if the widget is valid, false otherwise
     */
    public boolean validate() {
        return this.getInnerWidget().validate();
    }
}

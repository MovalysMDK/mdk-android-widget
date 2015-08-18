package com.soprasteria.movalysmdk.widget.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import com.soprasteria.movalysmdk.widget.core.MDKBaseRichUriWidget;
import com.soprasteria.movalysmdk.widget.core.MDKBaseWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;

/**
 * MDKRichUri class definition.
 */
public class MDKRichUri extends MDKBaseRichUriWidget<MDKUri> implements MDKBaseWidget, HasValidator {

    /**
    * Constructor.
    * @param context the context
    * @param attrs attributes
    */
    public MDKRichUri(Context context, AttributeSet attrs) {
        super(R.layout.mdkwidget_uri_edit_label, R.layout.mdkwidget_uri_edit, context, attrs);
    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     * @param defStyleAttr the style
     */
    public MDKRichUri(Context context, AttributeSet attrs, int defStyleAttr) {
       super(R.layout.mdkwidget_uri_edit_label, R.layout.mdkwidget_uri_edit, context, attrs, defStyleAttr);
    }

    @Override
    public void setInputType(int type) {
        this.getInnerWidget().setInputType(type);
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.getInnerWidget().setEnabled(enabled);
    }

    /**
     * onCreateInputConnection method.
     * @param outAttrs attributes
     * @return InputConnection the input connection
     */
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return getInnerWidget().onCreateInputConnection(outAttrs);
    }

}

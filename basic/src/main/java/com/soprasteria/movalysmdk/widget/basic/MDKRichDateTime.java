package com.soprasteria.movalysmdk.widget.basic;

import android.content.Context;
import android.util.AttributeSet;

import com.soprasteria.movalysmdk.widget.core.MDKBaseRichDateWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasError;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;

/**
 * Rich widget representing a date and time picker, conforming to the Material Design guidelines,
 * and including by default the floating label and the error component.
 */
public class MDKRichDateTime extends MDKBaseRichDateWidget<MDKDateTime> implements HasValidator, HasError {

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     */
    public MDKRichDateTime(Context context, AttributeSet attrs) {
        super(R.layout.mdkwidget_date_time_edit_label, R.layout.mdkwidget_date_time_edit, context, attrs);
    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     * @param defStyleAttr defStyleAttr
     */
    public MDKRichDateTime(Context context, AttributeSet attrs, int defStyleAttr) {
        super(R.layout.mdkwidget_date_time_edit_label, R.layout.mdkwidget_date_time_edit, context, attrs, defStyleAttr);
    }
}

package com.soprasteria.movalysmdk.widget.basic;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.soprasteria.movalysmdk.widget.core.MDKBaseRichSeekBarWidget;
import com.soprasteria.movalysmdk.widget.core.MDKRestorableWidget;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasChangeListener;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasSeekBar;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;

/**
 * Rich widget representing a seek bar component, conforming to the Material Design guidelines,
 * and including by default the error component.
 * @param <T> The class of the widget to encapsulate*
 */
public class MDKRichSeekBar <T extends MDKWidget & MDKRestorableWidget & HasValidator & HasDelegate & HasSeekBar & HasChangeListener> extends MDKBaseRichSeekBarWidget<T> {

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     */
    public MDKRichSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     * @param defStyleAttr defStyleAttr
     */
    public MDKRichSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Called by the constructor.
     * @param context the context of the view
     * @param attrs xml attributes
     */
    protected void init(Context context, AttributeSet attrs) {
        int layoutWithLabelId, layoutWithoutLabelId;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKSeekBarComponent);
        // parse widget attribute to get values

        layoutWithLabelId = R.layout.mdkwidget_seekbar_edit_label;
        layoutWithoutLabelId = R.layout.mdkwidget_seekbar_edit;

        typedArray.recycle();

        super.init(context, attrs, layoutWithLabelId, layoutWithoutLabelId);
    }
}

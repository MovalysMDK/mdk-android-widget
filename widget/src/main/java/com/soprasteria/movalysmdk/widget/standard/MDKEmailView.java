package com.soprasteria.movalysmdk.widget.standard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Email widget to show email value and add
 * a send button to call email Intent.ACTION_SEND intent.
 *
 * this widget has no editable properties.
 *
 * @see com.soprasteria.movalysmdk.widget.standard.MDKEmail
 */
public class MDKEmailView extends TextView {

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     */
    public MDKEmailView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     * @param defStyleAttr defStyleAttr
     */
    public MDKEmailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}

package com.soprasteria.movalysmdk.widget.core;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.ImageButton;

/**
 * ImageButton with a checkable status.
 * Used for Position widget.
 */
public class MDKCheckableImageButton extends ImageButton implements Checkable {

    /** Is the widget checked? */
    private boolean mChecked;

    /** true if the checked status is being processed */
    private boolean mBroadcasting;

    /** change listener. */
    private OnCheckedChangeListener mOnCheckedChangeListener;

    private static final int[] CHECKED_STATE_SET = {
            android.R.attr.state_checked
    };

    /**
     * Interface definition for a callback to be invoked when the checked state changed.
     */
    public interface OnCheckedChangeListener {
        /**
         * Called when the checked state has changed.
         *
         * @param button The button view whose state has changed.
         * @param isChecked  The new checked state of button.
         */
        void onCheckedChanged(MDKCheckableImageButton button, boolean isChecked);
    }

    /**
     * Constructor.
     * @param context an Android context
     */
    public MDKCheckableImageButton(Context context) {
        super(context);
    }

    /**
     * Constructor.
     * @param context an Android Context
     * @param attrs the XML attributes to be set on view
     */
    public MDKCheckableImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Constructor.
     * @param context an Android context
     * @param attrs the XML attributes to be set on view
     * @param defStyleAttr the style to apply
     */
    public MDKCheckableImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Register a callback to be invoked when the checked state of this button changes.
     * @param listener the callback to call on checked state change
     */
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }

    /**
     * Changes the checked state of this button.
     * @param checked true to check the button, false to uncheck it
     */
    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            refreshDrawableState();

            // Avoid infinite recursions if setChecked() is called from a listener
            if (mBroadcasting) {
                return;
            }

            mBroadcasting = true;
            if (mOnCheckedChangeListener != null) {
                mOnCheckedChangeListener.onCheckedChanged(this, mChecked);
            }

            mBroadcasting = false;
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }
}

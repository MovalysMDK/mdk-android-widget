package com.soprasteria.movalysmdk.widget.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import com.soprasteria.movalysmdk.widget.basic.delegate.MDKCommandDelegate;

/**
 * ImageButton to execute a widget broadcast action.
 * <p>
 *     3 arguments must be filled to work :
 *     <ul>
 *         <li>referenceWidget : @ResId,  who indicate witch widget action will be triggered</li>
 *         <li>action : @ResString or String, who indicate the executed action (Broadcasted intent)</li>
 *         <li>command : String who indicate the action to execute on widget (primary or secondary or other string)</li>
 *     </ul>
 * </p>
 */
public class MDKCommandImageButton extends ImageButton implements View.OnClickListener {

    /** widget delegate */
    private MDKCommandDelegate delegate;

    /**
     * Default constructor.
     * @param context Android context
     */
    public MDKCommandImageButton(Context context) {
        this(context, null);
    }

    /**
     * Defaut Constructor
     * @param context Android context
     * @param attrs android xml attributes
     */
    public MDKCommandImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            delegate = new MDKCommandDelegate(this, attrs);
        }
    }

    /**
     * Default Constructor
     * @param context Android context
     * @param attrs android xml attributes
     * @param defStyleAttr default style attribute
     */
    public MDKCommandImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            delegate = new MDKCommandDelegate(this, attrs);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.delegate.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        this.delegate.onDetachedFromWindow();
        super.onDetachedFromWindow();
    }

    @Override
    public void onClick(View view) {
        this.delegate.onClick();
    }
}

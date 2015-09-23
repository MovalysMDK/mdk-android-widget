package com.soprasteria.movalysmdk.widget.basic;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;

import com.soprasteria.movalysmdk.widget.basic.delegate.MDKCommandDelegate;

/**
 * Button to execute a widget broadcast action.
 * <p>
 *     3 arguments must be filled to work :
 *     <ul>
 *         <li>referenceWidget : @ResId,  who indicate witch widget action will be triggered</li>
 *         <li>action : @ResString or String, who indicate the executed action (Broadcasted intent)</li>
 *         <li>command : String who indicate the action to execute on widget (primary or secondary or other string)</li>
 *     </ul>
 * </p>
 */
public class MDKCommandButton extends AppCompatButton implements View.OnClickListener {

    /** widget delegate */
    private MDKCommandDelegate delegate;

    /**
     * Default constructor.
     * @param context Android context
     */
    public MDKCommandButton(Context context) {
        this(context, null);
    }

    /**
     * Defaut Constructor.
     * @param context Android context
     * @param attrs android xml attributes
     */
    public MDKCommandButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            delegate = new MDKCommandDelegate(this, attrs);
        }
    }

    /**
     * Defaut Constructor.
     * @param context Android context
     * @param attrs android xml attributes
     * @param defStyleAttr default style attribut
     */
    public MDKCommandButton(Context context, AttributeSet attrs, int defStyleAttr) {
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
    public void onClick(View v) {
        this.delegate.onClick();
    }
}

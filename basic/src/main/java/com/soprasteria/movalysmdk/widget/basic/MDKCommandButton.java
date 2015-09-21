package com.soprasteria.movalysmdk.widget.basic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;

import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;

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

    /** Reference widget id tag in Broadcast. */
    public static final String REFERENCE_WIDGET = "referenceWidget";
    /** Command widget string tag in Broadcast. */
    public static final String COMMAND_WIDGET = "commmand";

    /** reg widget id. */
    private int refWidgetId;
    /** action name (broadcast to execute). */
    private String actionName;
    /** command name to execute. */
    private String command;
    /** broadcast enable reciver. */
    private BroadcastReceiver enableReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getExtras() != null && intent.getIntExtra(MDKWidgetDelegate.EXTRA_WIDGET_ID, 0) == refWidgetId) {
                setEnabled(intent.getBooleanExtra(MDKWidgetDelegate.EXTRA_VALID, false));
            }
        }
    };

    /**
     * Default constructor.
     * @param context Android context
     */
    public MDKCommandButton(Context context) {
        this(context, null);
    }

    /**
     * Defaut Constructor
     * @param context Android context
     * @param attrs android xml attributes
     */
    public MDKCommandButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(attrs);
        }
    }

    /**
     * Defaut Constructor
     * @param context Android context
     * @param attrs android xml attributes
     * @param defStyleAttr default style attribut
     */
    public MDKCommandButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            init(attrs);
        }
    }

    /**
     * Initialisation method
     * @param attrs android xml attributes
     */
    private void init(AttributeSet attrs) {

        TypedArray typedArray = this.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommandButton);
        this.refWidgetId = typedArray.getResourceId(R.styleable.MDKCommandButton_referenceWidget, 0);
        int actionNameId = typedArray.getResourceId(R.styleable.MDKCommandButton_action, 0);
        if (actionNameId == 0) {
            this.actionName = typedArray.getString(R.styleable.MDKCommandButton_action);
        } else {
            this.actionName = getResources().getString(actionNameId);
        }
        this.command = typedArray.getString(R.styleable.MDKCommandButton_command);
        typedArray.recycle();

        this.setOnClickListener(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        LocalBroadcastManager.getInstance(this.getContext()).registerReceiver(enableReceiver, new IntentFilter(getContext().getString(R.string.mdkwidget_enableboadcast)));
    }

    @Override
    protected void onDetachedFromWindow() {
        LocalBroadcastManager.getInstance(this.getContext()).unregisterReceiver(enableReceiver);
        super.onDetachedFromWindow();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this.actionName);
        intent.putExtra(REFERENCE_WIDGET, this.refWidgetId);
        intent.putExtra(COMMAND_WIDGET, this.command);
        LocalBroadcastManager.getInstance(this.getContext()).sendBroadcast(
                intent
        );
    }
}

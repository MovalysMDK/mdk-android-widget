/**
 * Copyright (C) 2010 Sopra Steria Group (movalys.support@soprasteria.com)
 *
 * This file is part of Movalys MDK.
 * Movalys MDK is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Movalys MDK is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with Movalys MDK. If not, see <http://www.gnu.org/licenses/>.
 */
package com.soprasteria.movalysmdk.widget.basic.delegate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.view.View;

import com.soprasteria.movalysmdk.widget.basic.MDKCommandsEditText;
import com.soprasteria.movalysmdk.widget.basic.R;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;

import java.lang.ref.WeakReference;

/**
 * Delegate class for the MDKCommandButton and MDKCommandImageButton widgets.
 * Hosts the BroadcastReceiver and manages the launch of intents.
 */
public class MDKCommandDelegate {

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

    /** reference to the associated view */
    private WeakReference<View> widget;

    /** broadcast enable receiver. */
    private BroadcastReceiver enableReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getExtras() != null && intent.getIntExtra(MDKWidgetDelegate.EXTRA_WIDGET_ID, 0) == refWidgetId) {
                getWidget().setEnabled(intent.getBooleanExtra(MDKWidgetDelegate.EXTRA_VALID, false));
            }
        }
    };

    /**
     * Constructor.
     * @param view the associated view
     */
    public MDKCommandDelegate(View view, AttributeSet attrs) {

        this.widget = new WeakReference<>(view);

        TypedArray typedArray = view.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommandButton);
        this.refWidgetId = typedArray.getResourceId(R.styleable.MDKCommandButton_referenceWidget, 0);
        int actionNameId = typedArray.getResourceId(R.styleable.MDKCommandButton_action, 0);
        if (actionNameId == 0) {
            this.actionName = typedArray.getString(R.styleable.MDKCommandButton_action);
        } else {
            this.actionName = view.getContext().getResources().getString(actionNameId);
        }
        this.command = typedArray.getString(R.styleable.MDKCommandButton_command);
        typedArray.recycle();

        view.setOnClickListener((View.OnClickListener) view);
    }

    /**
     * Returns the associated widget view.
     * @return the associated view
     */
    private View getWidget() {
        return this.widget.get();
    }

    /**
     * Called when the associated view is attached to the current window.
     */
    public void onAttachedToWindow() {
        LocalBroadcastManager.getInstance(this.getWidget().getContext()).registerReceiver(enableReceiver, new IntentFilter(this.getWidget().getContext().getString(R.string.mdkwidget_enableboadcast)));
    }

    /**
     * Called when the associated view is detached from the current window.
     */
    public void onDetachedFromWindow() {
        LocalBroadcastManager.getInstance(this.getWidget().getContext()).unregisterReceiver(enableReceiver);
    }

    /**
     * Called when the associated view receives a click event.
     */
    public void onClick() {
        Intent intent = new Intent(this.actionName);
        intent.putExtra(REFERENCE_WIDGET, this.refWidgetId);
        intent.putExtra(COMMAND_WIDGET, this.command);
        LocalBroadcastManager.getInstance(this.getWidget().getContext()).sendBroadcast(intent);
    }
}

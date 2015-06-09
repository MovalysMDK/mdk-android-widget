package com.soprasteria.movalysmdk.widget.base.delegate;

import android.content.res.TypedArray;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.soprasteria.movalysmdk.widget.base.R;
import com.soprasteria.movalysmdk.widget.core.command.Command;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetApplication;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetComponentProvider;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetSimpleComponentProvider;

import java.lang.ref.WeakReference;

/**
 * Action Delegate for component
 * This class handle action for a widget
 * it register listener and launch command
 */
public class ActionDelegate {

    private final Class<? extends Command> primaryActionCommandClass;
    private final Class<? extends Command> secondaryActionCommandClass;
    private final WeakReference<View> weakView;
    private final int primaryActionViewId;
    private final int secondaryActionViewId;
    private final String qualifier;

    public ActionDelegate(View view, AttributeSet attrs) {

        this(view, attrs, null);

    }

    public ActionDelegate(View view, AttributeSet attrs, Class<? extends Command> primaryCommandClass) {

        this(view, attrs, primaryCommandClass, null);

    }

    public ActionDelegate(View view, AttributeSet attrs, Class<? extends Command> primaryCommandClass, Class<? extends Command> secondaryCommandClass) {

        this.weakView = new WeakReference<View>(view);
        this.primaryActionCommandClass = primaryCommandClass;
        this.secondaryActionCommandClass = secondaryCommandClass;

        TypedArray typedArray = view.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKButtonComponent);

        this.primaryActionViewId = typedArray.getResourceId(R.styleable.MDKCommons_MDKButtonComponent_primaryActionId, 0);
        this.secondaryActionViewId = typedArray.getResourceId(R.styleable.MDKCommons_MDKButtonComponent_secondaryActionId, 0);

        typedArray.recycle();

        typedArray = view.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons);

        this.qualifier = typedArray.getString(R.styleable.MDKCommons_qualifier);

        typedArray.recycle();

    }

    /**
     * Register existing actions on a click listener
     * @param listener the click listener to register action view
     */
    public void registerActions(View.OnClickListener listener)  {
        if (this.primaryActionViewId != 0) {
            View actionView = findActionView(this.primaryActionViewId);
            if (actionView != null) {
                actionView.setOnClickListener(listener);
            }
        }
        if (this.secondaryActionViewId != 0) {
            View actionView = findActionView(this.secondaryActionViewId);
            if (actionView != null) {
                actionView.setOnClickListener(listener);
            }
        }

    }

    /**
     * Find action view for the specified id
     * @param actionViewId the action view id
     * @return the view if exists
     */
    private View findActionView(int actionViewId) {
        View actionView = null;
        View v = this.weakView.get();
        if (v != null && v instanceof HasMdkDelegate) {
            View rootView = ((HasMdkDelegate) v).getMdkWidgetDelegate().findRootView(false);
            if (rootView != null) {
                actionView = rootView.findViewById(actionViewId);

            }
        }
        return actionView;
    }


    /**
     * Return the base key name for the specified parameters
     * @param widgetClassName the simple name class of the widget
     * @param actionViewId the id of the action view
     * @return the base key associated with the parameters
     */
    @Nullable private String baseKey(String widgetClassName, int actionViewId) {
        StringBuffer baseKey = new StringBuffer();

        baseKey.append(widgetClassName.toLowerCase());

        if (actionViewId == primaryActionViewId) {
            baseKey.append("_primary");
        } else {
            baseKey.append("_secondary");
        }

        baseKey.append("_command_class");

        return baseKey.toString();
    }

    /**
     *
     * @param id
     * @return
     */
    @Nullable public Command<?,?> getAction(@IdRes int id) {

        Command<?,?> command = null;
        View v = this.weakView.get();
        if (v != null) {

            if (v.getContext().getApplicationContext() instanceof MDKWidgetApplication) {
                MDKWidgetComponentProvider widgetComponentProvider = ((MDKWidgetApplication) v.getContext().getApplicationContext()).getMDKWidgetComponentProvider();
                widgetComponentProvider.getCommand(v.getContext(), baseKey(v.getClass().getSimpleName(), id), this.qualifier);
            } else if (v instanceof HasMdkDelegate) {
                MDKWidgetComponentProvider widgetComponentProvider = new MDKWidgetSimpleComponentProvider();
                command = widgetComponentProvider.getCommand(
                        v.getContext(),
                        baseKey(v.getClass().getSimpleName(), id),
                        this.qualifier);
//                command = this.findCommandFrom(v.getContext(), baseKey(v.getClass().getSimpleName(), id));
            }
        }

        return command;
    }
}

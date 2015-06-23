package com.soprasteria.movalysmdk.widget.core.delegate;

import android.content.res.TypedArray;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.soprasteria.movalysmdk.widget.core.R;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.command.WidgetCommand;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetApplication;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetComponentProvider;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetSimpleComponentProvider;

import java.lang.ref.WeakReference;

/**
 * Command handler on MDKButtonComponent for MDKWidgets.
 * <p>This class manages primary and secondary commands on the MDK button component.</p>
 * <p>It registers listeners and launches commands.</p>
 */
public class WidgetCommandDelegate {

    /** primary command class. */
    private final Class<? extends WidgetCommand> primaryCommandClass;

    /** secondary command class. */
    private final Class<? extends WidgetCommand> secondaryCommandClass;

    /** Weak reference on view. */
    private final WeakReference<MDKWidget> weakView;

    /** Id of the primary command view. */
    private final int primaryCommandViewId;

    /** Id of the secondary command view. */
    private final int secondaryCommandViewId;

    /** Attribute "Qualifier" of the component. */
    private final String qualifier;

    /**
     * Constructor.
     * @param mdkWidget view
     * @param attrs attributes
     */
    public WidgetCommandDelegate(MDKWidget mdkWidget, AttributeSet attrs) {

        this(mdkWidget, attrs, null);

    }

    /**
     * Constructor.
     * @param mdkWidget view
     * @param attrs attributes
     * @param primaryCommandClass command class
     */
    public WidgetCommandDelegate(MDKWidget mdkWidget, AttributeSet attrs, Class<? extends WidgetCommand> primaryCommandClass) {

        this(mdkWidget, attrs, primaryCommandClass, null);

    }

    /**
     * Constructor.
     * @param mdkWidget view
     * @param attrs attributes
     * @param primaryCommandClass primary command class
     * @param secondaryCommandClass secondary command class
     */
    public WidgetCommandDelegate(MDKWidget mdkWidget, AttributeSet attrs, Class<? extends WidgetCommand> primaryCommandClass, Class<? extends WidgetCommand> secondaryCommandClass) {

        this.weakView = new WeakReference<MDKWidget>(mdkWidget);
        this.primaryCommandClass = primaryCommandClass;
        this.secondaryCommandClass = secondaryCommandClass;

        TypedArray typedArray = mdkWidget.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKButtonComponent);

        this.primaryCommandViewId = typedArray.getResourceId(R.styleable.MDKCommons_MDKButtonComponent_primaryCommandId, 0);
        this.secondaryCommandViewId = typedArray.getResourceId(R.styleable.MDKCommons_MDKButtonComponent_secondaryCommandId, 0);

        typedArray.recycle();

        typedArray = mdkWidget.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons);

        this.qualifier = typedArray.getString(R.styleable.MDKCommons_qualifier);

        typedArray.recycle();

    }

    /**
     * Register existing commands on a click listener.
     * @param listener the click listener to register command view
     */
    public void registerCommands(View.OnClickListener listener)  {
        if (this.primaryCommandViewId != 0) {
            View commandView = findCommandView(this.primaryCommandViewId);
            if (commandView != null) {
                commandView.setOnClickListener(listener);
            }
        }
        if (this.secondaryCommandViewId != 0) {
            View commandView = findCommandView(this.secondaryCommandViewId);
            if (commandView != null) {
                commandView.setOnClickListener(listener);
            }
        }

    }

    /**
     * Find command view for the specified id.
     * @param commandViewId the command view id
     * @return the view if exists
     */
    private View findCommandView(@IdRes int commandViewId) {
        View commandView = null;
        MDKWidget v = this.weakView.get();
        if (v != null && v instanceof MDKWidget) {
            View rootView = ((MDKWidget) v).getMDKWidgetDelegate().findRootView(false);
            if (rootView != null) {
                commandView = rootView.findViewById(commandViewId);

            }
        }
        return commandView;
    }


    /**
     * Return the base key name for the specified parameters.
     * @param widgetClassName the simple name class of the widget
     * @param commandViewId the id of the command view
     * @return the base key associated with the parameters
     */
    @Nullable private String baseKey(String widgetClassName, @IdRes int commandViewId) {
        StringBuilder baseKey = new StringBuilder(widgetClassName.toLowerCase());

        if (commandViewId == primaryCommandViewId) {
            baseKey.append("_primary");
        } else {
            baseKey.append("_secondary");
        }

        baseKey.append("_command_class");

        return baseKey.toString();
    }

    /**
     * Get command.
     * @param id the id
     * @return command the command
     */
    @Nullable public WidgetCommand getWidgetCommand(@IdRes int id) {

        WidgetCommand<?,?> widgetCommand = null;
        MDKWidget v = this.weakView.get();
        if (v != null) {

            if (v.getContext().getApplicationContext() instanceof MDKWidgetApplication) {
                MDKWidgetComponentProvider widgetComponentProvider = ((MDKWidgetApplication) v.getContext().getApplicationContext()).getMDKWidgetComponentProvider();
                widgetComponentProvider.getCommand(baseKey(v.getClass().getSimpleName(), id), this.qualifier, v.getContext());
            } else {
                MDKWidgetComponentProvider widgetComponentProvider = new MDKWidgetSimpleComponentProvider();
                widgetCommand = widgetComponentProvider.getCommand(
                        baseKey(v.getClass().getSimpleName(), id), this.qualifier, v.getContext()
                );
            }
        }

        return widgetCommand;
    }
}

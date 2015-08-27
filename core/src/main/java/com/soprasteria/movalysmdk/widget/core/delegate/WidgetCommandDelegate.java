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
package com.soprasteria.movalysmdk.widget.core.delegate;

import android.content.res.TypedArray;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.R;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.command.WidgetCommand;
import com.soprasteria.movalysmdk.widget.core.listener.ValidationListener;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetApplication;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetComponentProvider;

import java.lang.ref.WeakReference;

/**
 * Command handler on MDKButtonComponent for MDKWidgets.
 * <p>This class manages primary and secondary commands on the MDK button component.</p>
 * <p>It registers listeners and launches commands.</p>
 * <p>Both commands may not be disabled on validation should the setup be properly done.</p>
 */
public class WidgetCommandDelegate implements ValidationListener {

    /** Weak reference on view. */
    private final WeakReference<MDKWidget> weakView;

    /** Id of the primary command view. */
    protected final int primaryCommandViewId;

    /** Id of the secondary command view. */
    protected final int secondaryCommandViewId;

    /** Attribute "Qualifier" of the component. */
    private final String qualifier;

    /** should we deactivate the primary command when the valid status of the widget is false. */
    private boolean deactivatePrimaryOnValidation = true;

    /** should we deactivate the secondary command when the valid status of the widget is false. */
    private boolean deactivateSecondaryOnValidation = true;

    /**
     * Constructor.
     * @param mdkWidget view the widget view
     * @param attrs attributes the widget xml attributes
     */
    public WidgetCommandDelegate(MDKWidget mdkWidget, AttributeSet attrs) {

        this(mdkWidget, attrs, true, true);
    }

    /**
     * Constructor.
     * @param mdkWidget view the widget view
     * @param attrs attributes the widget xml attributes
     * @param deactivatePrimaryOnValidation true to deactivate the primary command on validation
     * @param deactivateSecondaryOnValidation true to deactivate the secondary command on validation
     */
    public WidgetCommandDelegate(MDKWidget mdkWidget, AttributeSet attrs, boolean deactivatePrimaryOnValidation, boolean deactivateSecondaryOnValidation) {

        // the command delegate registers itself as a validation listener
        if (mdkWidget instanceof HasDelegate) {
            ((HasDelegate) mdkWidget).getMDKWidgetDelegate().addValidationListener(this);
        }

        this.weakView = new WeakReference<>(mdkWidget);
        this.deactivatePrimaryOnValidation = deactivatePrimaryOnValidation;
        this.deactivateSecondaryOnValidation = deactivateSecondaryOnValidation;

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
        if (v instanceof HasDelegate && commandViewId != 0) {
            commandView = ((HasDelegate)v).getMDKWidgetDelegate().reverseFindViewById(commandViewId);
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
        StringBuilder baseKey = new StringBuilder("mdkwidget_");
        baseKey.append(widgetClassName.toLowerCase());
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
        if (v != null
                && v.getContext().getApplicationContext() instanceof MDKWidgetApplication) {
            MDKWidgetComponentProvider widgetComponentProvider = ((MDKWidgetApplication) v.getContext().getApplicationContext()).getMDKWidgetComponentProvider();
            widgetCommand = widgetComponentProvider.getCommand(baseKey(v.getClass().getSimpleName(), id), this.qualifier, v.getContext());

        }

        return widgetCommand;
    }

    /**
     * Activate or not command on a specific view id.
     * @param enable Activation toggle
     * @param viewId the view id
     */
    protected void enableCommandOnView(boolean enable, int viewId) {
        View commandView = findCommandView(viewId);
        if (commandView != null) {
            commandView.setEnabled(enable);
            commandView.setFocusable(enable);
        }
    }


    @Override
    public void notifyCommandStateChanged(boolean valid) {
        if (valid) {
            enableCommandOnView(true, this.primaryCommandViewId);
            enableCommandOnView(true, this.secondaryCommandViewId);
        } else {
            enableCommandOnView(!deactivatePrimaryOnValidation, this.primaryCommandViewId);
            enableCommandOnView(!deactivateSecondaryOnValidation, this.secondaryCommandViewId);
        }

    }
}

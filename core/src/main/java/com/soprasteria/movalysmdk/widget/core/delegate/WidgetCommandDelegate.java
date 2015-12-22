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

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.TypedArray;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.R;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.types.HasText;
import com.soprasteria.movalysmdk.widget.core.command.WidgetCommand;
import com.soprasteria.movalysmdk.widget.core.listener.ValidationListener;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetApplication;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Command handler on MDKButtonComponent for MDKWidgets.
 * <p>This class manages primary and secondary commands on the MDK button component.</p>
 * <p>It registers listeners and launches commands.</p>
 * <p>Both commands may not be disabled on validation should the setup be properly done.</p>
 */
public class WidgetCommandDelegate implements ValidationListener {

    /**
     * Enum for commands (first or second).
     */
    @IntDef({FIRST_COMMAND, SECOND_COMMAND})
    @interface EnumKindOfCommand {
    }

    /** first command. */
    public static final int FIRST_COMMAND = 1;

    /** second command. */
    public static final int SECOND_COMMAND = 2;

    /** Weak reference on view. */
    private final WeakReference<MDKWidget> weakView;

    /** Id of the primary command view. */
    protected int primaryCommandViewId;

    /** Id of the secondary command view. */
    protected int secondaryCommandViewId;

    /** Attribute "Qualifier" of the component. */
    private final String qualifier;

    /** should we deactivate the primary command when the valid status of the widget is false. */
    private boolean deactivatePrimaryOnValidation = true;

    /** should we deactivate the secondary command when the valid status of the widget is false. */
    private boolean deactivateSecondaryOnValidation = true;

    /** true if the primary command view is out of the linked view. */
    private boolean outerPrimaryCommandView = true;

    /** true if the secondary command view is out of the linked view. */
    private boolean outerSecondaryCommandView = true;

    /** true if the intent linked to the action on the primary command is not handled by the device. */
    private boolean noIntentOnPrimary = false;

    /** true if the intent linked to the action on the secondary command is not handled by the device. */
    private boolean noIntentOnSecondary = false;

    /**
     * Constructor.
     * @param mdkWidget view the widget view
     * @param attrs attributes the widget xml attributes
     */
    public WidgetCommandDelegate(MDKWidget mdkWidget, AttributeSet attrs) {

        this(mdkWidget, attrs, true, true);
    }

    /**
     * Checks if the intents linked to the actions launched by a command are handled by the device.
     * If they are not, the commands will be disabled.
     * @param context an android context
     * @param commandsActionIntents the intents launched by the actions linked to the commands
     */
    public void setCommandsActivationFromIntents(Context context, Intent[] commandsActionIntents) {
        if (commandsActionIntents != null) {
            PackageManager manager = context.getPackageManager();
            List<ResolveInfo> info;

            if (commandsActionIntents.length > 1 && commandsActionIntents[1] != null) {
                info = manager.queryIntentActivities(commandsActionIntents[1], 0);
                noIntentOnSecondary = info.isEmpty();
            }

            if (commandsActionIntents[0] != null) {
                info = manager.queryIntentActivities(commandsActionIntents[0], 0);
                noIntentOnPrimary = info.isEmpty();
            }
        }
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
     * Manually adds a command to the delegate.
     * @param commandType the type of the command
     * @param commandId the identifier of the view for the command
     * @param deactivateOnValidation true to deactivate the command on validation
     * @param out true if the given command is out of the widget, otherwise the command should be looked for inside of it
     */
    public void manuallyAddCommand(@EnumKindOfCommand int commandType, @IdRes int commandId, boolean deactivateOnValidation, boolean out) {
        if (commandType == FIRST_COMMAND) {
            this.primaryCommandViewId = commandId;
            this.deactivatePrimaryOnValidation = deactivateOnValidation;
            this.outerPrimaryCommandView = out;
        } else if (commandType == SECOND_COMMAND) {
            this.secondaryCommandViewId = commandId;
            this.deactivateSecondaryOnValidation = deactivateOnValidation;
            this.outerSecondaryCommandView = out;
        }
    }

    /**
     * Register existing commands on a click listener.
     * @param listener the click listener to register command view
     */
    public void registerCommands(View.OnClickListener listener)  {
        if (this.primaryCommandViewId != 0) {
            View commandView = findCommandView(this.primaryCommandViewId, this.outerPrimaryCommandView);
            if (commandView != null) {
                commandView.setOnClickListener(listener);
            }
        }
        if (this.secondaryCommandViewId != 0) {
            View commandView = findCommandView(this.secondaryCommandViewId, this.outerSecondaryCommandView);
            if (commandView != null) {
                commandView.setOnClickListener(listener);
            }
        }

    }

    /**
     * Find command view for the specified id.
     * @param commandViewId the command view id
     * @param out true if the given command is out of the widget, otherwise the command should be looked for inside of it
     * @return the view if exists
     */
    private View findCommandView(@IdRes int commandViewId, boolean out) {
        View commandView = null;
        MDKWidget v = this.weakView.get();
        if (v instanceof HasDelegate && commandViewId != 0) {
            if (out) {
                commandView = ((HasDelegate) v).getMDKWidgetDelegate().reverseFindViewById(commandViewId);
            } else {
                commandView = ((ViewGroup)v).findViewById(commandViewId);
            }
        }
        return commandView;
    }

    /**
     * Get command.
     * @param id the id
     * @return command the command
     */
    @Nullable public WidgetCommand getWidgetCommandById(@IdRes int id) {

        WidgetCommand<?,?> widgetCommand = null;
        MDKWidget v = this.weakView.get();
        if (v != null
                && v.getContext().getApplicationContext() instanceof MDKWidgetApplication) {
            String command = getStringCommandById(id);
            widgetCommand = WidgetCommandFactory.getWidgetCommand(command, this.qualifier, v);
        }

        return widgetCommand;
    }

    /**
     * Get command.
     * @param command the string command to get
     * @return the command
     */
    @Nullable public WidgetCommand getWidgetCommand(String command) {
        WidgetCommand<?,?> widgetCommand = null;
        MDKWidget v = this.weakView.get();
        if (v != null
                && v.getContext().getApplicationContext() instanceof MDKWidgetApplication) {
            widgetCommand = WidgetCommandFactory.getWidgetCommand(command, this.qualifier, v);
        }

        return widgetCommand;
    }

    /**
     * Gets the command index as a string given its view id.
     * @param id the id of view of the command
     * @return a string representing the index of the command
     */
    @NonNull
    private String getStringCommandById(@IdRes int id) {
        String command;
        if (id == primaryCommandViewId) {
            command = "primary";
        } else {
            command = "secondary";
        }
        return command;
    }

    /**
     * Activate or not command on a specific view id.
     * @param enable Activation toggle
     * @param viewId the view id
     * @param out true if the given command is out of the widget, otherwise the command should be looked for inside of it
     */
    protected void enableCommandOnView(boolean enable, int viewId, boolean out) {
        View commandView = findCommandView(viewId, out);
        View view = (View) this.weakView.get();
        boolean commandEnable = enable;
        if (commandView != null) {
            if (view != null && view instanceof HasText && (((HasText) view).getText() == null || ((HasText) view).getText().length() == 0)) {
                // no text input, the command should be deactivated
                commandEnable = false;
            }
            commandView.setEnabled(commandEnable);
            commandView.setFocusable(commandEnable);
        }
    }

    @Override
    public void notifyCommandStateChanged(boolean valid) {
        if (valid) {
            enableCommandOnView(!noIntentOnPrimary, this.primaryCommandViewId, this.outerPrimaryCommandView);
            enableCommandOnView(!noIntentOnSecondary, this.secondaryCommandViewId, this.outerSecondaryCommandView);
        } else {
            enableCommandOnView(!deactivatePrimaryOnValidation && !noIntentOnPrimary, this.primaryCommandViewId, this.outerPrimaryCommandView);
            enableCommandOnView(!deactivateSecondaryOnValidation && !noIntentOnSecondary, this.secondaryCommandViewId, this.outerSecondaryCommandView);
        }

    }
}

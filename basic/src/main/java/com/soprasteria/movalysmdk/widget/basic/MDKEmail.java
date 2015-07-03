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
package com.soprasteria.movalysmdk.widget.basic;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;

import com.soprasteria.movalysmdk.widget.basic.command.EmailWidgetCommand;
import com.soprasteria.movalysmdk.widget.basic.model.Email;
import com.soprasteria.movalysmdk.widget.core.MDKRestorableWidget;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasCommands;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasHint;
import com.soprasteria.movalysmdk.widget.core.behavior.HasLabel;
import com.soprasteria.movalysmdk.widget.core.behavior.HasText;
import com.soprasteria.movalysmdk.widget.core.behavior.HasTextWatcher;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.delegate.WidgetCommandDelegate;
import com.soprasteria.movalysmdk.widget.core.error.MDKMessage;
import com.soprasteria.movalysmdk.widget.core.helper.MDKAttributeSet;
import com.soprasteria.movalysmdk.widget.core.listener.CommandStateListener;

/**
 * MDK Email
 * <p>Representing an editable text validate with an email regexp</p>
 * <p>
 *     This widget present a command who send the intent Intent.ACTION_SEND to the
 *     Android system with the component text by default.
 * </p>
 * <p>The validation regexp is stored in R.string.email_regexp</p>
 */
public class MDKEmail extends AppCompatEditText implements MDKWidget, MDKRestorableWidget, HasText, HasTextWatcher, HasHint, HasValidator, HasCommands, HasLabel, HasDelegate {

    /** CommandDelegate attribute. */
    protected WidgetCommandDelegate commandDelegate;
    /** MDK Widget implementation. */
    protected MDKWidgetDelegate mdkWidgetDelegate;
    /** Keyword instanceState. */
    private static final String INSTANCE_STATE = "instanceState";
    /** Keyword innerState. */
    private static final String INNER_STATE = "innerState";

    /**
     * Constructor.
     * @param context the android context
     * @param attrs the layout attributes
     */
    public MDKEmail(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Constructor.
     * @param context the android context
     * @param attrs the layout attributes
     * @param style the layout defined style
     */
    public MDKEmail(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Initialization.
     * @param context the android context
     * @param attrs the layout attributes
     */
    private final void init(Context context, AttributeSet attrs) {
        this.mdkWidgetDelegate = new MDKWidgetDelegate(this, attrs);

        this.commandDelegate = new WidgetCommandDelegate(this, attrs);
        this.addCommandStateListener(this.commandDelegate);

    }


    /**
     * Set an unique id for inner widget with the parent one as parameter.
     * @param parentId id of the parent
     */
    @Override
    public void setUniqueId(int parentId) {
        this.mdkWidgetDelegate.setUniqueId(parentId);
    }

    /**
     * Return the unique id of the inner widget.
     * @return int id
     */
    @Override
    public int getUniqueId() {
        return this.mdkWidgetDelegate.getUniqueId();
    }

    /**
     * Set the root id.
     * @param rootId the id of a view
     */
    @Override
    public void setRootViewId(@IdRes int rootId) {
        this.mdkWidgetDelegate.setRootViewId(rootId);
    }

    @Override
    public void setError(CharSequence error) {
        this.mdkWidgetDelegate.setError(error);
    }

    @Override
    public void addError(MDKMessage error) {
        this.mdkWidgetDelegate.addError(error);
    }

    @Override
    public void clearError() {
        this.mdkWidgetDelegate.clearError();
    }

    /**
     * Set if field is mandatory.
     * @param mandatory true if mandatory, false otherwise
     */
    @Override
    public void setMandatory(boolean mandatory) {
        this.mdkWidgetDelegate.setMandatory(mandatory);
    }

    /**
     * Return true if field is mandatory.
     * @return true or false
     */
    @Override
    public boolean isMandatory() {
        return this.mdkWidgetDelegate.isMandatory();
    }

    /**
     * onClick method.
     * @param v the view
     */
    @Override
    public void onClick(View v) {
        String sEmailAddress = this.getText().toString();
        if (sEmailAddress != null && sEmailAddress.length() > 0) {
            // invoke command
            Email email = new Email(sEmailAddress);
            ((EmailWidgetCommand)this.commandDelegate.getWidgetCommand(v.getId())).execute(this.getContext(), email);
        }
    }



    @Override
    public MDKAttributeSet getAttributeMap() {
        return this.getMDKWidgetDelegate().getAttributeMap();
    }

    @Override
    public void setAttributeMap(MDKAttributeSet attributeMap) {
        this.getMDKWidgetDelegate().setAttributeMap(attributeMap);
    }

    @Override
    public int[] getValidators() {
        return new int[] {R.string.mdkwidget_mdkedittext_validator_class, R.string.mdkwidget_mdkemail_validator_class};
    }

    /**
     * Widget validation method, if error found return true.
     * @return True if no error
     */
    @Override
    public boolean validate() {
        return this.getMDKWidgetDelegate().validate(true);
    }

    /**
     * Register commands on the view.
     */
    @Override
    public void registerWidgetCommands() {
        this.commandDelegate.registerCommands(this);
    }

    @Override
    public void addCommandStateListener(CommandStateListener commandListener) {
        this.getMDKWidgetDelegate().addCommandStateListener(commandListener);
    }

    @Override
   public void onTextChanged(CharSequence s, int start, int before, int count) {
       super.onTextChanged(s, start, before, count);
       if (this.getMDKWidgetDelegate() != null && this.commandDelegate != null && !isInEditMode() ) {
           this.getMDKWidgetDelegate().validate(false);
       }
   }

    /**
     * Return the MDKWidgetDelegate object.
     * @return MDKWidgetDelegate object
     */
    @Override
    public MDKWidgetDelegate getMDKWidgetDelegate() {
        return this.mdkWidgetDelegate;
    }

    /**
     *  Called when the view is attached to a window.
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            this.registerWidgetCommands();
            // Call validate to enable or not send button
            this.getMDKWidgetDelegate().validate(false);
        }
    }

    /**
     * Set component label's id.
     * @param labelId the id of a view
     */
    @Override
    public void setLabelViewId(@IdRes int labelId) {
        this.mdkWidgetDelegate.setLabelViewId(labelId);
    }

    /**
     * Set component helper's id.
     * @param helperId the id of a view
     */
    @Override
    public void setHelperViewId(@IdRes int helperId) {
        this.mdkWidgetDelegate.setHelperViewId(helperId);
    }

    /**
     * Set component error's id.
     * @param errorId the id of a view
     */
    @Override
    public void setErrorViewId(@IdRes int errorId) {
        this.mdkWidgetDelegate.setErrorViewId(errorId);
    }

    /**
     * Set component root's id if errors are shared.
     * @param useRootIdOnlyForError true if the error is not in the same layout
     */
    @Override
    public void setUseRootIdOnlyForError(boolean useRootIdOnlyForError) {
        this.mdkWidgetDelegate.setUseRootIdOnlyForError(useRootIdOnlyForError);
    }

    /**
     * To call when the focus state of a view has changed.
     * @param focused is component focused
     * @param direction component direction
     * @param previouslyFocusedRect component previous focus state
     */
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (!focused) {
            validate();
        }
    }

    /**
     *  super on onCreateDrawableState (TextView).
     * @param extraSpace extra space
     * @return the extra spaces
     */
    @Override
    public int[] superOnCreateDrawableState(int extraSpace) {
        return super.onCreateDrawableState(extraSpace);
    }

    /**
     * Depending on the state, merge different drawables for a view.
     * @param baseState the base state
     * @param additionalState the additional state
     */
    @Override
    public void callMergeDrawableStates(int[] baseState, int[] additionalState) {
        mergeDrawableStates(baseState, additionalState);
    }

    /**
     * Depending on the state, resource for displaying different drawables for a view.
     * @param extraSpace extra space
     * @return the extra spaces
     */
    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        if (this.getMDKWidgetDelegate() != null) {
            return this.getMDKWidgetDelegate().superOnCreateDrawableState(extraSpace);
        } else {
            // first called in the super constructor
            return super.onCreateDrawableState(extraSpace);
        }
    }

    /**
     * Get component label's name.
     * @return label's name of CharSequence
     */
    @Override
    public CharSequence getLabel() {
        return this.mdkWidgetDelegate.getLabel();
    }

    /**
     * Set component label's name.
     * @param label the label to set
     */
    @Override
    public void setLabel(CharSequence label) {
        this.mdkWidgetDelegate.setLabel(label);
    }

    /**
     * Method called to store data before pausing the activity.
     * @return activity's state
     */
    @Override
    public Parcelable onSaveInstanceState() {

        Bundle bundle = new Bundle();
        // Save the android view instance state
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        // Save the MDKWidgetDelegate instance state
        bundle.putParcelable(INNER_STATE,this.mdkWidgetDelegate.onSaveInstanceState(bundle.getParcelable(INSTANCE_STATE)));
        return bundle;
    }

    /**
     * Called when the activity is being re-initialized from a previously saved state, given here in onSavedInstanceState.
     * @param state of the activity
     */
    @Override
    public void onRestoreInstanceState(Parcelable state) {

        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            this.mdkWidgetDelegate.onRestoreInstanceState(this, bundle.getParcelable(INNER_STATE));
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(state);

    }

    /**
     * super on OnSaveInstanceState.
     * @return onSaveInstanceState
     */
    @Override
    public Parcelable superOnSaveInstanceState() {
        return onSaveInstanceState();
    }

    /**
     * super on OnRestoreInstanceState.
     * @param state the state
     */
    @Override
    public void superOnRestoreInstanceState(Parcelable state) {
        onRestoreInstanceState(state);
    }
}

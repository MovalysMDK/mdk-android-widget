package com.soprasteria.movalysmdk.widget.standard;

import android.content.Context;
import android.graphics.Rect;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;

import com.soprasteria.movalysmdk.widget.base.delegate.ActionDelegate;
import com.soprasteria.movalysmdk.widget.base.delegate.HasMdkDelegate;
import com.soprasteria.movalysmdk.widget.base.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKInnerWidget;
import com.soprasteria.movalysmdk.widget.core.MDKRestorableWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasCommands;
import com.soprasteria.movalysmdk.widget.core.behavior.HasHint;
import com.soprasteria.movalysmdk.widget.core.behavior.HasLabel;
import com.soprasteria.movalysmdk.widget.core.behavior.HasText;
import com.soprasteria.movalysmdk.widget.core.behavior.HasTextWatcher;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.error.MDKError;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;
import com.soprasteria.movalysmdk.widget.standard.command.EmailWidgetCommand;
import com.soprasteria.movalysmdk.widget.standard.model.Email;

/**
 * MDKEmail class definition.
 */
public class MDKEmail extends AppCompatEditText implements MDKInnerWidget, MDKRestorableWidget, HasText, HasTextWatcher, HasHint, HasValidator, HasCommands, HasMdkDelegate, HasLabel {

    /** ActionDelegate attribute. */
    protected ActionDelegate commandDelegate;
    /** MDK Widget implementation. */
    protected MDKWidgetDelegate mdkWidgetDelegate;

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     */
    public MDKEmail(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     * @param style the style
     */
    public MDKEmail(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Initialization.
     * @param context the context
     * @param attrs attributes
     */
    private final void init(Context context, AttributeSet attrs) {

        this.mdkWidgetDelegate = new MDKWidgetDelegate(this, attrs);

        this.commandDelegate = new ActionDelegate(this, attrs, EmailWidgetCommand.class);

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
    public void setRootViewId(@IdRes int rootId) {
        this.mdkWidgetDelegate.setRootViewId(rootId);
    }

    /**
     * Set the error message.
     * @param error the error to set
     */
    @Override
    public void setError(CharSequence error) {
        this.mdkWidgetDelegate.setError(error);
    }

    /**
     * Set the component error.
     * @param error the error to set
     */
    @Override
    public void setMDKError(MDKError error) {
        this.mdkWidgetDelegate.setMDKError(error);
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
    public void onClick(View v) {
        String sEmailAddress = this.getText().toString();
        if (sEmailAddress != null && sEmailAddress.length() > 0) {
            // invoke command
            Email email = new Email(sEmailAddress);
            ((EmailWidgetCommand)this.commandDelegate.getWidgetCommand(v.getId())).execute(this.getContext(), email);
        }
    }

    /**
     * Widget validation method, if error found return true.
     * @return True if no error
     */
    @Override
    public boolean validate() {
        boolean valid = true;
        MDKError error = this.mdkWidgetDelegate.getValidator().validate(this.getContext(), this.getText().toString(), this.getMDKWidgetDelegate().isMandatory());
        if (error == null) {
            this.setMDKError(null);
            valid = true;
        } else {
            this.setMDKError(error);
            valid = false;
        }
        this.getMDKWidgetDelegate().setValid(valid);
        return valid;
    }

    /**
     * Register commands on the view.
     */
    @Override
    public void registerWidgetCommands() {
        this.commandDelegate.registerActions(this);
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
        }
    }

    /**
     * Set component label's id.
     * @param labelId the id of a view
     */
    @Override
    public void setLabelId(@IdRes int labelId) {
        this.mdkWidgetDelegate.setLabelId(labelId);
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
     * Flexible forms validation.
     * @return FormFieldValidator object
     */
    @Override
    public FormFieldValidator getValidator() {
        return this.getMDKWidgetDelegate().getValidator();
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

        // Save the android view instance state
        Parcelable state = super.onSaveInstanceState();
        // Save the MDKWidgetDelegate instance state
        state = this.mdkWidgetDelegate.onSaveInstanceState(state);

        return state;
    }

    /**
     * Called when the activity is being re-initialized from a previously saved state, given here in onSavedInstanceState.
     * @param state of the activity
     */
    @Override
    public void onRestoreInstanceState(Parcelable state) {

        // Restore the MDKWidgetDelegate instance state
        Parcelable innerState = this.mdkWidgetDelegate.onRestoreInstanceState(this, state);
        // Restore the android view instance state
        super.onRestoreInstanceState(innerState);
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

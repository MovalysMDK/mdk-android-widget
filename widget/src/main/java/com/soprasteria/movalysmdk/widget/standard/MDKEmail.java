package com.soprasteria.movalysmdk.widget.standard;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;

import com.soprasteria.movalysmdk.widget.base.delegate.ActionDelegate;
import com.soprasteria.movalysmdk.widget.base.delegate.HasMdkDelegate;
import com.soprasteria.movalysmdk.widget.base.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasActions;
import com.soprasteria.movalysmdk.widget.core.behavior.HasHint;
import com.soprasteria.movalysmdk.widget.core.behavior.HasLabel;
import com.soprasteria.movalysmdk.widget.core.behavior.HasText;
import com.soprasteria.movalysmdk.widget.core.behavior.HasTextWatcher;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.validator.IFormFieldValidator;
import com.soprasteria.movalysmdk.widget.standard.command.EmailCommand;
import com.soprasteria.movalysmdk.widget.standard.model.Email;

public class MDKEmail extends AppCompatEditText implements MDKWidget, HasText, HasTextWatcher, HasHint, HasValidator, HasActions, HasMdkDelegate, HasLabel {

    protected ActionDelegate actionDelegate;
    protected MDKWidgetDelegate MDKWidgetDelegate;
    private int parentId = -1;

    public MDKEmail(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    public MDKEmail(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    private final void init(Context context, AttributeSet attrs) {

        this.MDKWidgetDelegate = new MDKWidgetDelegate(this, attrs);

        this.actionDelegate = new ActionDelegate(this, attrs, EmailCommand.class);

    }

    @Override
    public void setUniqueId(int parentId) {
        this.parentId = parentId;
    }

    @Override
    public int getUniqueId() {
        if (this.parentId == -1) {
            return this.getId();
        } else {
            return this.parentId;
        }
    }

    public void setRootId(int rootId) {
        this.MDKWidgetDelegate.setRootId(rootId);
    }


    @Override
    public void setError(CharSequence error) {
        this.MDKWidgetDelegate.setError(error);
    }

    @Override
    public void setMandatory(boolean mandatory) {
        this.MDKWidgetDelegate.setMandatory(mandatory);
    }

    @Override
    public boolean isMandatory() {
        return this.MDKWidgetDelegate.isMandatory();
    }

    public void onClick(View v) {
        String sEmailAddress = this.getText().toString();
        if (sEmailAddress != null && sEmailAddress.length() > 0) {
            // invoke command
            Email email = new Email(sEmailAddress);
            ((EmailCommand)this.actionDelegate.getAction(v.getId())).execute(email);
        }
    }

    @Override
    public boolean validate() {
        boolean bValid = true;
        String error = this.MDKWidgetDelegate.getValidator().validate(this.getText().toString(), this.getMDKWidgetDelegate().isMandatory());
        if (error == null) {
            this.setError("");
            bValid = true;
        } else {
            this.setError(error);
            bValid = false;
        }
        this.getMDKWidgetDelegate().setValid(bValid);
        return bValid;
    }

    @Override
    public void registerActionViews() {
        this.actionDelegate.registerActions(this);
    }

    @Override
    public MDKWidgetDelegate getMDKWidgetDelegate() {
        return this.MDKWidgetDelegate;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            this.registerActionViews();
        }
    }

    @Override
    public void setLabelId(int labelId) {
        this.MDKWidgetDelegate.setLabelId(labelId);
    }

    @Override
    public void setHelperId(int helperId) {
        this.MDKWidgetDelegate.setHelperId(helperId);
    }

    @Override
    public void setErrorId(int errorId) {
        this.MDKWidgetDelegate.setErrorId(errorId);
    }

    @Override
    public void setUseRootIdOnlyForError(boolean useRootIdOnlyForError) {
        this.MDKWidgetDelegate.setUseRootIdOnlyForError(useRootIdOnlyForError);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (!focused) {
            validate();
        }
    }


    @Override
    public int[] superOnCreateDrawableState(int extraSpace) {
        return super.onCreateDrawableState(extraSpace);
    }

    @Override
    public void callMergeDrawableStates(int[] baseState, int[] additionalState) {
        mergeDrawableStates(baseState, additionalState);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        if (this.getMDKWidgetDelegate() != null) {
            return this.getMDKWidgetDelegate().superOnCreateDrawableState(extraSpace);
        } else {
            // first called in the super constructor
            return super.onCreateDrawableState(extraSpace);
        }
    }

    @Override
    public IFormFieldValidator getValidator() {
        return this.getMDKWidgetDelegate().getValidator();
    }

    @Override
    public CharSequence getLabel() {
        return this.MDKWidgetDelegate.getLabel();
    }

    @Override
    public void setLabel(CharSequence label) {
        this.MDKWidgetDelegate.setLabel(label);
    }
}

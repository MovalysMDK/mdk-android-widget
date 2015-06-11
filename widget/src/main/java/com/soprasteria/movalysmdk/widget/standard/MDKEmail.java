package com.soprasteria.movalysmdk.widget.standard;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;

import com.soprasteria.movalysmdk.widget.base.delegate.ActionDelegate;
import com.soprasteria.movalysmdk.widget.base.delegate.HasMdkDelegate;
import com.soprasteria.movalysmdk.widget.base.delegate.MdkWidgetDelegate;
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
    protected MdkWidgetDelegate mdkWidgetDelegate;
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

        this.mdkWidgetDelegate = new MdkWidgetDelegate(this, attrs);

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
        this.mdkWidgetDelegate.setRootId(rootId);
    }


    @Override
    public void setError(CharSequence error) {
        this.mdkWidgetDelegate.setError(error);
    }

    @Override
    public void setMandatory(boolean mandatory) {
        this.mdkWidgetDelegate.setMandatory(mandatory);
    }

    @Override
    public boolean isMandatory() {
        return this.mdkWidgetDelegate.isMandatory();
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
        String error = this.mdkWidgetDelegate.getValidator().validate(this.getText().toString(), this.getMdkWidgetDelegate().isMandatory());
        if (error == null) {
            this.setError("");
            bValid = true;
        } else {
            this.setError(error);
            bValid = false;
        }
        this.getMdkWidgetDelegate().setValid(bValid);
        return bValid;
    }

    @Override
    public void registerActionViews() {
        this.actionDelegate.registerActions(this);
    }

    @Override
    public MdkWidgetDelegate getMdkWidgetDelegate() {
        return this.mdkWidgetDelegate;
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
        this.mdkWidgetDelegate.setLabelId(labelId);
    }

    @Override
    public void setHelperId(int helperId) {
        this.mdkWidgetDelegate.setHelperId(helperId);
    }

    @Override
    public void setErrorId(int errorId) {
        this.mdkWidgetDelegate.setErrorId(errorId);
    }

    @Override
    public void setUseRootIdOnlyForError(boolean useRootIdOnlyForError) {
        this.mdkWidgetDelegate.setUseRootIdOnlyForError(useRootIdOnlyForError);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (!focused) {
            validate();
        }
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        int[] state = null;
        // first called in the super constructor
        if (this.getMdkWidgetDelegate() == null) {
            state = super.onCreateDrawableState(extraSpace);
        } else {
            int stateSpace = this.getMdkWidgetDelegate().getStateLength(extraSpace);
            state = super.onCreateDrawableState(stateSpace);
            int[] mdkState = this.getMdkWidgetDelegate().getWidgetState();

            mergeDrawableStates(state, mdkState);

            this.getMdkWidgetDelegate().callRichSelector(state);
        }
        return state;
    }

    @Override
    public IFormFieldValidator getValidator() {
        return this.getMdkWidgetDelegate().getValidator();
    }

    @Override
    public CharSequence getLabel() {
        return this.mdkWidgetDelegate.getLabel();
    }

    @Override
    public void setLabel(CharSequence label) {
        this.mdkWidgetDelegate.setLabel(label);
    }
}

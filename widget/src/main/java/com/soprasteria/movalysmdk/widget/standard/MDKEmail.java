package com.soprasteria.movalysmdk.widget.standard;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;

import com.soprasteria.movalysmdk.widget.base.delegate.ActionDelegate;
import com.soprasteria.movalysmdk.widget.base.delegate.HasMdkDelegate;
import com.soprasteria.movalysmdk.widget.base.delegate.MdkWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasActions;
import com.soprasteria.movalysmdk.widget.core.behavior.HasHint;
import com.soprasteria.movalysmdk.widget.core.behavior.HasText;
import com.soprasteria.movalysmdk.widget.core.behavior.HasTextWatcher;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetApplication;
import com.soprasteria.movalysmdk.widget.core.validator.IFormFieldValidator;
import com.soprasteria.movalysmdk.widget.standard.command.EmailCommand;
import com.soprasteria.movalysmdk.widget.standard.model.Email;
import com.soprasteria.movalysmdk.widget.standard.validator.EmailValidator;

public class MDKEmail extends AppCompatEditText implements MDKWidget, HasText, HasTextWatcher, HasHint, HasValidator, HasActions, HasMdkDelegate {

    protected ActionDelegate actionDelegate;
    protected MdkWidgetDelegate mdkWidgetDelegate;

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

    public void setRootId(int rootId) {
        this.mdkWidgetDelegate.setRootId(rootId);
    }



    @Override
    public void setError(CharSequence error) {
        this.mdkWidgetDelegate.setError(error);
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
        String error = this.getValidator().validate(this.getText().toString());
        if (error == null) {
            this.setError("");
        } else {
            this.setError(error);
        }
        return bValid;
    }

    public IFormFieldValidator getValidator() {
        IFormFieldValidator rValidator = null;
        if (this.getContext().getApplicationContext() instanceof MDKWidgetApplication) {
            rValidator = ((MDKWidgetApplication) this.getContext().getApplicationContext()).getMDKWidgetComponentProvider().getValidator(this.getContext(), "", "");
        } else {
            rValidator = new EmailValidator(this.getContext());
        }
        return rValidator;
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
        this.registerActionViews();
    }

    @Override
    public void addTextWatcher(TextWatcher textWatcher) {
        this.addTextChangedListener(textWatcher);
    }

    @Override
    public void removeTextWatcher(TextWatcher textWatcher) {
        this.removeTextChangedListener(textWatcher);
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
}

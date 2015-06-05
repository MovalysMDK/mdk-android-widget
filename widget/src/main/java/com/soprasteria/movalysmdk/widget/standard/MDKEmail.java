package com.soprasteria.movalysmdk.widget.standard;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasButtons;
import com.soprasteria.movalysmdk.widget.core.behavior.HasHint;
import com.soprasteria.movalysmdk.widget.core.behavior.HasText;
import com.soprasteria.movalysmdk.widget.core.behavior.HasTextWatcher;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetApplication;
import com.soprasteria.movalysmdk.widget.core.validator.IFormFieldValidator;
import com.soprasteria.movalysmdk.widget.standard.command.EmailCommand;
import com.soprasteria.movalysmdk.widget.standard.validator.EmailValidator;

public class MDKEmail extends AppCompatEditText implements MDKWidget, HasText, HasTextWatcher, HasHint, HasValidator, HasButtons {

    private int rootId;
    private int labelId;
    private int helperId;
    private int errorId;
    private int primaryButtonId;
    private int secondaryButtonId;
    private boolean useRootIdOnlyForError = false;

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

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MDKCommons);

        this.rootId = typedArray.getResourceId(R.styleable.MDKCommons_rootId, 0);
        this.errorId = typedArray.getResourceId(R.styleable.MDKCommons_errorId, 0);

        typedArray.recycle();

        typedArray = context.obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKButtonComponent);

        this.primaryButtonId = typedArray.getResourceId(R.styleable.MDKCommons_MDKButtonComponent_primaryButtonId, 0);
        this.secondaryButtonId = typedArray.getResourceId(R.styleable.MDKCommons_MDKButtonComponent_secondaryButtonId, 0);

        typedArray.recycle();
    }

    public void setRootId(int rootId) {
        this.rootId = rootId;
    }

    public View findRootView(boolean useRootIdForError) {
        if (!useRootIdForError) {
            if (this.rootId == 0) {
                return (View) this.getParent();
            } else {
                return getMatchRootParent((View) this.getParent());
            }
        } else {
            if (this.useRootIdOnlyForError || this.rootId != 0) {
                return getMatchRootParent((View) this.getParent());
            } else {
                return (View) this.getParent();
            }
        }
    }

    private View getMatchRootParent(View parent) {
        if (parent == null) {
            return null;
        }
        if (parent.getId() == this.rootId) {
            return parent;
        } else {
            return getMatchRootParent((View) parent.getParent());
        }
    }

    @Override
    public void setError(CharSequence error) {
        View rootView = findRootView(true);
        if (rootView != null) {
            TextView errorView = (TextView) rootView.findViewById(this.errorId);
            if (errorView != null) {
                errorView.setText(error);
            }
        }
        refreshDrawableState();
    }

    public void seErrorId(int errorId) {
        this.errorId = errorId;
    }

    public void onClick(View v) {
        String sEmailAddress = this.getText().toString();
        if (sEmailAddress != null && sEmailAddress.length() > 0) {
            // invoke command
            com.soprasteria.movalysmdk.widget.core.command.Command oCommand = null;
            if (this.getContext().getApplicationContext() instanceof MDKWidgetApplication) {
                oCommand = ((MDKWidgetApplication) this.getContext().getApplicationContext()).getMDKWidgetComponentProvider().getCommand(this.getClass(), this.getContext() /*, sEmailAddress*/);
            } else {
                oCommand = new EmailCommand(this.getContext(), sEmailAddress);
            }
            oCommand.execute();
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
            rValidator = ((MDKWidgetApplication) this.getContext().getApplicationContext()).getMDKWidgetComponentProvider().getValidator(this.getClass(), this.getContext());
        } else {
            rValidator = new EmailValidator(this.getContext());
        }
        return rValidator;
    }

    public void registerPrimaryButton() {
        this.registerButton(this.primaryButtonId);
    }

    public void registerSecondaryButton() {
        this.registerButton(this.secondaryButtonId);
    }

    private void registerButton(int buttonId) {
        View rootView = this.findRootView(false);
        if (rootView != null) {
            View button = rootView.findViewById(buttonId);
            if (button != null) {
                button.setOnClickListener(this);
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.registerPrimaryButton();
        this.registerSecondaryButton();
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
        this.labelId = labelId;
    }

    @Override
    public void setHelperId(int helperId) {
        this.helperId = helperId;
    }

    @Override
    public void setErrorId(int errorId) {
        this.errorId = errorId;
    }

    @Override
    public void setUseRootIdOnlyForError(boolean useRootIdOnlyForError) {
        this.useRootIdOnlyForError = useRootIdOnlyForError;
    }
}

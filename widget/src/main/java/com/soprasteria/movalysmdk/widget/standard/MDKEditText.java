package com.soprasteria.movalysmdk.widget.standard;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;

import com.soprasteria.movalysmdk.widget.base.delegate.HasMdkDelegate;
import com.soprasteria.movalysmdk.widget.base.delegate.MdkWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasHint;
import com.soprasteria.movalysmdk.widget.core.behavior.HasLabel;
import com.soprasteria.movalysmdk.widget.core.behavior.HasText;
import com.soprasteria.movalysmdk.widget.core.behavior.HasTextWatcher;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetApplication;
import com.soprasteria.movalysmdk.widget.core.validator.IFormFieldValidator;

/**
 * Represents an Edit Text conforming to the Material Design guidelines.
 *
 * The following behaviors are implemented:
 * - if there is a label and a hint in the xml layout, set the label value as label, and the hint value as hint
 * - if there is a label and no hint in the xml layout, set the label value as label and as hint
 * - if there is no label and a hint in the xml layout, there will be no label, and the hint value as hint
 * - if there is no label and no hint in the xml layout, there will be no label and no hint
 *
 * Created by belamrani on 09/06/2015.
 */
public class MDKEditText extends AppCompatEditText implements MDKWidget, HasText, HasTextWatcher, HasHint, HasMdkDelegate, HasValidator, HasLabel {

    /** The MdkWidgetDelegate handling the component logic */
    protected MdkWidgetDelegate mdkWidgetDelegate;

    /**
     * Constructor
     * @param context
     * @param attrs
     */
    public MDKEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Constructor
     * @param context
     * @param attrs
     * @param style
     */
    public MDKEditText(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Instantiate the MdkWidgetDelegate
     * @param context
     * @param attrs
     */
    private final void init(Context context, AttributeSet attrs) {

        // Parse the MDKCommons:hint attribute
        // so that both android:hint and MDKCommons:hint can be used
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MDKCommons);
        int resHintId = typedArray.getResourceId(R.styleable.MDKCommons_hint, 0);
        if (resHintId != 0) {
            this.setHint(resHintId);
        }
        typedArray.recycle();

        this.mdkWidgetDelegate = new MdkWidgetDelegate(this, attrs);
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

    @Override
    public MdkWidgetDelegate getMdkWidgetDelegate() {
        return this.mdkWidgetDelegate;
    }

    /**
     * Handle the hint value and hide the floating label
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        // If the hint is empty, set it to the label
        CharSequence label = this.getLabel();
        if (this.getHint() == null || this.getHint().length() == 0) {
            this.setHint(label);
        }

        // By default, hide the floating label
        this.mdkWidgetDelegate.setLabelVisibility(View.INVISIBLE);
    }

    /**
     * {@inheritDoc}
     * Show or hide the label according to the new text content
     *
     * @param text
     * @param start
     * @param lengthBefore
     * @param lengthAfter
     */
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);

        if (lengthBefore == 0 && lengthAfter > 0) {
            this.mdkWidgetDelegate.setLabelVisibility(View.VISIBLE);
        } else if (lengthBefore > 0 && lengthAfter == 0) {
            this.mdkWidgetDelegate.setLabelVisibility(View.INVISIBLE);
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
    public IFormFieldValidator getValidator() {

        return this.mdkWidgetDelegate.getValidator();
    }

    @Override
    public boolean validate() {
        boolean bValid = true;

        IFormFieldValidator rValidator = this.getValidator();

        if (rValidator != null) {

            String error = this.getValidator().validate(this.getText().toString(), this.mdkWidgetDelegate.isMandatory());
            if (error == null) {
                this.setError("");
            } else {
                this.setError(error);
            }
        } else {
            //if the component doesn't have any validator, there is no error to show.
            this.setError("");
        }

        return bValid;
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
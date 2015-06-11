package com.soprasteria.movalysmdk.widget.standard;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;

import com.soprasteria.movalysmdk.widget.base.delegate.HasMdkDelegate;
import com.soprasteria.movalysmdk.widget.base.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasHint;
import com.soprasteria.movalysmdk.widget.core.behavior.HasLabel;
import com.soprasteria.movalysmdk.widget.core.behavior.HasText;
import com.soprasteria.movalysmdk.widget.core.behavior.HasTextWatcher;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
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

    /** The MDKWidgetDelegate handling the component logic */
    protected MDKWidgetDelegate MDKWidgetDelegate;

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
     * Instantiate the MDKWidgetDelegate
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

        this.MDKWidgetDelegate = new MDKWidgetDelegate(this, attrs);
    }

    @Override
    public int getUniqueId() {
        return this.MDKWidgetDelegate.getUniqueId();
    }

    @Override
    public void setUniqueId(int parentId) {
        this.MDKWidgetDelegate.setUniqueId(parentId);
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

    @Override
    public MDKWidgetDelegate getMDKWidgetDelegate() {
        return this.MDKWidgetDelegate;
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
        this.MDKWidgetDelegate.setLabelVisibility(View.INVISIBLE, false);
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
            this.MDKWidgetDelegate.setLabelVisibility(View.VISIBLE, true);
        } else if (lengthBefore > 0 && lengthAfter == 0) {
            this.MDKWidgetDelegate.setLabelVisibility(View.INVISIBLE, true);
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
    public IFormFieldValidator getValidator() {

        return this.MDKWidgetDelegate.getValidator();
    }

    @Override
    public boolean validate() {
        boolean bValid = true;

        IFormFieldValidator rValidator = this.getValidator();

        if (rValidator != null) {

            String error = this.getValidator().validate(this.getText().toString(), this.MDKWidgetDelegate.isMandatory());
            if (error == null) {
                this.setError("");
                bValid = true;
            } else {
                this.setError(error);
                bValid = false;
            }
        } else {
            //if the component doesn't have any validator, there is no error to show.
            this.setError("");
            bValid = true;
        }

        this.getMDKWidgetDelegate().setValid(bValid);
        return bValid;
    }

    @Override
    public CharSequence getLabel() {
        return this.MDKWidgetDelegate.getLabel();
    }

    @Override
    public void setLabel(CharSequence label) {
        this.MDKWidgetDelegate.setLabel(label);
    }

    @Override
    public void callMergeDrawableStates(int[] baseState, int[] additionalState) {
        mergeDrawableStates(baseState, additionalState);
    }

    @Override
    public int[] superOnCreateDrawableState(int extraSpace) {
        return super.onCreateDrawableState(extraSpace);
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
}
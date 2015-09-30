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
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Parcelable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;

import com.soprasteria.movalysmdk.widget.core.MDKTechnicalInnerWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKTechnicalWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasHint;
import com.soprasteria.movalysmdk.widget.core.behavior.HasLabel;
import com.soprasteria.movalysmdk.widget.core.behavior.HasText;
import com.soprasteria.movalysmdk.widget.core.behavior.HasTextWatcher;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;

import java.util.Arrays;

/**
 * <p>Represents an Edit Text conforming to the Material Design guidelines.</p>
 * <p>The following behaviors are implemented:</p>
 * <ul>
 *     <li>if there is a label and a hint in the xml layout, set the label value as label, and the hint value as hint</li>
 *     <li>if there is a label and no hint in the xml layout, set the label value as label and as hint</li>
 *     <li>if there is no label and a hint in the xml layout, there will be no label, and the hint value as hint</li>
 *     <li>if there is no label and no hint in the xml layout, there will be no label and no hint</li>
 * </ul>
 */
public class MDKEditText extends AppCompatEditText implements MDKWidget, HasText, HasTextWatcher, HasHint, HasValidator, HasLabel, HasDelegate {

    /** The MDKWidgetDelegate handling the component logic. */
    protected MDKWidgetDelegate mdkWidgetDelegate;

    /** oldTextLength. */
    private int oldTextLength;

    /** specific InputType for the inherited widgets. */
    private int specificInputType = -1;

    /** widget specific validators. */
    private int[] specificValidators;

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes set
     */
    public MDKEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes set
     * @param style the style
     */
    public MDKEditText(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Sets the specific attributes on the widget.
     * @param specificInputType the specific InputType
     * @param specificValidators the specific validators
     */
    protected final void setSpecificAttributes(int specificInputType, int[] specificValidators) {
        if (!isInEditMode()) {
            // we have only one method for both attributes to limit the number of methods
            // no getter for the attributes for the same reason
            this.specificInputType = specificInputType;
            this.specificValidators = specificValidators;
        }
    }

    /**
     * Instantiate the MDKWidgetDelegate.
     * @param context the context
     * @param attrs attributes set
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

        this.mdkWidgetDelegate = new MDKWidgetDelegate(this, attrs);
    }

    /**
     * Handle the hint value and hide the floating label.
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (!isInEditMode()) {
            // If the hint is empty, set it to the label
            CharSequence label = this.getLabel();
            if (this.getHint() == null || this.getHint().length() == 0) {
                this.setHint(label);
            }

            // Hide the label
            if (this.getText().length() == 0) {
                this.mdkWidgetDelegate.setLabelVisibility(View.INVISIBLE, false);
            }

            if (this.specificInputType != -1) {
                // TODO : fuites mémoire ?
                this.setInputType(this.specificInputType);
            }
        }
    }

    /**
     * {@inheritDoc}.
     *
     * Show or hide the label according to the new text content.
     *
     * @param text The text the TextView is displaying
     * @param start The offset of the start of the range of the text that was modified
     * @param lengthBefore The length of the former text that has been replaced
     * @param lengthAfter The length of the replacement modified text
     */
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);

        int textLength = text.length();

        // Prevent early calls
        if (this.mdkWidgetDelegate != null) {
            if (textLength > 0 && oldTextLength == 0) {
                this.mdkWidgetDelegate.setLabelVisibility(View.VISIBLE, true);
            } else if (textLength == 0 && oldTextLength > 0) {
                this.mdkWidgetDelegate.setLabelVisibility(View.INVISIBLE, true);
            }
        }
        oldTextLength = textLength;
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
            validate(EnumFormFieldValidator.ON_FOCUS);
        }
    }

    @Override
    public int[] getValidators() {
        int[] basicValidators = {R.string.mdkvalidator_mandatory_class};
        int[] validators;

        if (specificValidators!= null && specificValidators.length > 0) {
            validators = Arrays.copyOf(basicValidators, basicValidators.length + specificValidators.length);

            System.arraycopy(specificValidators, 0, validators, basicValidators.length, specificValidators.length);
        } else {
            validators = basicValidators;
        }

        return validators;
    }

    /* technical delegate methods */

    @Override
    public MDKTechnicalWidgetDelegate getTechnicalWidgetDelegate() {
        return this.mdkWidgetDelegate;
    }

    @Override
    public MDKTechnicalInnerWidgetDelegate getTechnicalInnerWidgetDelegate() {
        return this.mdkWidgetDelegate;
    }

    @Override
    public MDKWidgetDelegate getMDKWidgetDelegate() {
        return this.mdkWidgetDelegate;
    }

    /* rich selector methods */

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

    /* delegate accelerator methods */

    @Override
    public void setMandatory(boolean mandatory) {
        this.mdkWidgetDelegate.setMandatory(mandatory);
    }

    @Override
    public boolean isMandatory() {
        return this.mdkWidgetDelegate.isMandatory();
    }

    @Override
    public void setError(CharSequence error) {
        this.mdkWidgetDelegate.setError(error);
    }

    @Override
    public void addError(MDKMessages error) {
        this.mdkWidgetDelegate.addError(error);
    }

    @Override
    public void clearError() {
        this.mdkWidgetDelegate.clearError();
    }

    @Override
    public CharSequence getLabel() {
        return this.mdkWidgetDelegate.getLabel();
    }

    @Override
    public void setLabel(CharSequence label) {
        this.mdkWidgetDelegate.setLabel(label);
    }

    @Override
    public boolean validate(@EnumFormFieldValidator.EnumValidationMode int validationMode) {
        return this.getMDKWidgetDelegate().validate(true, validationMode);
    }

    @Override
    public boolean validate() {
        return this.getMDKWidgetDelegate().validate(true, EnumFormFieldValidator.VALIDATE);
    }

    /* save / restore */

    @Override
    public Parcelable onSaveInstanceState() {

        // Save the android view instance state
        Parcelable state = super.onSaveInstanceState();
        // Save the MDKWidgetDelegate instance state
        state = this.mdkWidgetDelegate.onSaveInstanceState(state);

        return state;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {

        // Restore the MDKWidgetDelegate instance state
        Parcelable innerState = this.mdkWidgetDelegate.onRestoreInstanceState(this, state);
        // Restore the android view instance state
        super.onRestoreInstanceState(innerState);
    }
}
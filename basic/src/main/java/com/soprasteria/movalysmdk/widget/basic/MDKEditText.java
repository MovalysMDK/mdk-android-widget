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
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;

import com.soprasteria.movalysmdk.widget.core.MDKRestorableWidget;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasHint;
import com.soprasteria.movalysmdk.widget.core.behavior.HasLabel;
import com.soprasteria.movalysmdk.widget.core.behavior.HasText;
import com.soprasteria.movalysmdk.widget.core.behavior.HasTextWatcher;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.error.MDKMessage;
import com.soprasteria.movalysmdk.widget.core.helper.MDKAttributeSet;

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
public class MDKEditText extends AppCompatEditText implements MDKWidget, MDKRestorableWidget, HasText, HasTextWatcher, HasHint, HasValidator, HasLabel, HasDelegate {

    /** The MDKWidgetDelegate handling the component logic. */
    protected MDKWidgetDelegate mdkWidgetDelegate;

    /** oldTextLength. */
    private int oldTextLength;

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

    @Override
    public int getUniqueId() {
        return this.mdkWidgetDelegate.getUniqueId();
    }

    @Override
    public void setUniqueId(int parentId) {
        this.mdkWidgetDelegate.setUniqueId(parentId);
    }

    @Override
    public MDKAttributeSet getAttributeMap() {
        return this.getMDKWidgetDelegate().getAttributeMap();
    }

    @Override
    public void setAttributeMap(MDKAttributeSet attributeMap) {
        this.getMDKWidgetDelegate().setAttributeMap(attributeMap);
    }

    /**
     * Setter.
     * @param rootId the id of a view
     */
    @Override
    public void setRootViewId( @IdRes int rootId) {
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

    @Override
    public void setMandatory(boolean mandatory) {
        this.mdkWidgetDelegate.setMandatory(mandatory);
    }

    @Override
    public boolean isMandatory() {
        return this.mdkWidgetDelegate.isMandatory();
    }

    @Override
    public MDKWidgetDelegate getMDKWidgetDelegate() {
        return this.mdkWidgetDelegate;
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

    @Override
    public void setLabelViewId( @IdRes int labelId) {
        this.mdkWidgetDelegate.setLabelViewId(labelId);
    }

    @Override
    public void setHelperViewId( @IdRes int helperId) {
        this.mdkWidgetDelegate.setHelperViewId(helperId);
    }

    @Override
    public void setErrorViewId( @IdRes int errorId) {
        this.mdkWidgetDelegate.setErrorViewId(errorId);
    }


    @Override
    public void setUseRootIdOnlyForError(boolean useRootIdOnlyForError) {
        this.mdkWidgetDelegate.setUseRootIdOnlyForError(useRootIdOnlyForError);
    }

    @Override
    public int[] getValidators() {
        return new int[] {R.string.mdkwidget_mdkedittext_validator_class};
    }

    @Override
    public boolean validate() {
        return this.getMDKWidgetDelegate().validate(true);
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

    @Override
    public Parcelable superOnSaveInstanceState() {
        return onSaveInstanceState();
    }

    @Override
    public void superOnRestoreInstanceState(Parcelable state) {
        onRestoreInstanceState(state);
    }
}
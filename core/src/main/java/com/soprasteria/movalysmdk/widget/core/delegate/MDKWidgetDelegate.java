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
package com.soprasteria.movalysmdk.widget.core.delegate;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.core.MDKTechnicalInnerWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKTechnicalWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.R;
import com.soprasteria.movalysmdk.widget.core.helper.MDKAttributeSet;
import com.soprasteria.movalysmdk.widget.core.helper.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.listener.ValidationListener;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetApplication;
import com.soprasteria.movalysmdk.widget.core.selector.RichSelector;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Set;

/**
 * The MDKWidgetDelegate handles the MDK logic for rich widgets.
 */
public class MDKWidgetDelegate implements MDKWidget, MDKTechnicalWidgetDelegate, MDKTechnicalInnerWidgetDelegate {

    /**
     * delegate value object.
     */
    protected MDKWidgetDelegateValueObject valueObject = new MDKWidgetDelegateValueObject();

    /**
     * Constructor.
     * @param view the view
     * @param attrs the parameters set
     */
    public MDKWidgetDelegate(View view, AttributeSet attrs) {
        this.valueObject.initialize(view, attrs);
    }

    /**
     * Returns the {@link MDKAttributeSet} set on the delegate.
     * @return the {@link MDKAttributeSet} of the delegate
     */
    public MDKAttributeSet getAttributeMap() {
        return this.valueObject.getAttributesMap();
    }

    /**
     * Sets the {@link MDKAttributeSet} of the delegate.
     * @param attributeMap the {@link MDKAttributeSet} to set on the delegate
     */
    public void setAttributeMap(MDKAttributeSet attributeMap) {
        this.valueObject.setAttributesMap(attributeMap);
    }

    /**
     * Set a unique id to the widget from a view.
     * @param parentId the parent id
     */
    @Override
    public void setUniqueId(int parentId) {
        this.valueObject.setUniqueId(parentId);
    }

    /**
     * Return the unique id of the widget.
     * @return the unique id of the widget
     */
    @Override
    public int getUniqueId() {
        if (this.valueObject.getUniqueId() == 0) {
            View view = this.valueObject.getView();
            if (view != null) {
                return view.getId();
            }
        }
        return this.valueObject.getUniqueId();
    }

    /**
     * Provide the context of the widget.
     * @return the widget context
     */
    @Override
    public Context getContext() {
        View view = this.valueObject.getView();
        if (view != null) {
            return view.getContext();
        }
        return null;
    }

    /**
     * This function finds the view with the given identifier in the parents of the inner widget.
     * @param id the identifier to look for
     * @return oView the root view
     */
    public View reverseFindViewById(@IdRes int id) {
        View result = null;

        if (this.valueObject.getReverseFoundViews().containsKey(id)) {
            result = this.valueObject.getReverseFoundViews().get(id).get();
        } else {

            View v = this.valueObject.getView();

            if (v != null) {
                View parent = (View) v.getParent();
                while (result == null && parent != null) {
                    result = parent.findViewById(id);
                    if (result == null) {
                        parent = (View) parent.getParent();
                    }
                }
                if (parent != null) {
                    this.valueObject.getReverseFoundViews().put(id, new WeakReference<View>(result));
                }
            }
        }

        return result;
    }

    /**
     * Set error.
     * @param error the new error
     */
    public void setError(CharSequence error) {
        if (this.valueObject.getErrorViewId() != 0) {
            MDKWidgetDelegateErrorHelper.setError(this.reverseFindViewById(this.valueObject.getErrorViewId()), this.valueObject, this.getLabel(), error, this.getContext());
        }
    }

    /**
     * Set error.
     * @param messages the messages to set
     */
    public void addError(MDKMessages messages) {
        if (this.valueObject.getErrorViewId() != 0) {
            MDKWidgetDelegateErrorHelper.displayMessages(this.reverseFindViewById(this.valueObject.getErrorViewId()), this.valueObject, this.getLabel(), messages, this.getContext());
        }
    }

    /**
     * Remove error.
     */
    public void clearError() {
        if (this.valueObject.getErrorViewId() != 0) {
            MDKWidgetDelegateErrorHelper.clearMessages(this.reverseFindViewById(this.valueObject.getErrorViewId()), this.valueObject, this.getLabel(), this.getContext());
        }
    }

    @Override
    public MDKTechnicalWidgetDelegate getTechnicalWidgetDelegate() {
        return this;
    }

    @Override
    public MDKTechnicalInnerWidgetDelegate getTechnicalInnerWidgetDelegate() {
        return this;
    }

    @Override
    public void setMandatory(boolean mandatory) {
        this.valueObject.setMandatory(mandatory);
        this.valueObject.getAttributesMap().setBoolean(R.attr.mandatory, mandatory);
        View v = this.valueObject.getView();
        if (v != null) {
            v.refreshDrawableState();
        }
    }

    /**
     * Set the label's identifier of the MDK delegate widget to which it is attached to.
     * @param labelId the label's id of a view
     */
    @Override
    public void setLabelViewId(@IdRes int labelId) {
        this.valueObject.setLabelViewId(labelId);
    }

    /**
     * Set the helper's view identifier of the MDK delegate widget to which it is attached to.
     * @param helperId the helper's id of a view
     */
    @Override
    public void setHelperViewId(@IdRes int helperId) {
        this.valueObject.setHelperViewId(helperId);
    }

    /**
     * Set the error's identifier of the MDK delegate widget to which it is attached to.
     * @param errorId the error's id of a view
     */
    @Override
    public void setErrorViewId(@IdRes int errorId) {
        this.valueObject.setErrorViewId(errorId);
    }

    /**
     * Handles the creation of a drawable state event.
     * Add additional states as needed.
     * @param extraSpace new state to add to MDK widget
     * @return new drawable state
     */
    @Override
    public int[] superOnCreateDrawableState(int extraSpace) {
        int[] state = null;
        View v = this.valueObject.getView();
        if(v != null && v instanceof MDKWidget) {
            int stateSpace = this.getStateLength(extraSpace);
            state = ((MDKWidget) v).superOnCreateDrawableState(stateSpace);
            int[] mdkState = this.getWidgetState();

            ((MDKWidget) v).callMergeDrawableStates(state, mdkState);

            this.callRichSelector(state);
        }
        return state;
    }

    /**
     * Merge state values with additionalState into the base state values.
     * @param baseState initial drawable state
     * @param additionalState additional drawable state to merge with
     */
    @Override
    public void callMergeDrawableStates(int[] baseState, int[] additionalState) {
        // nothing here
    }

    /**
     * Get state length.
     * @param extraSpace extra space
     * @return length the state length
     */
    public int getStateLength(int extraSpace) {
        return extraSpace + MDKWidgetDelegateValueObject.ADDED_MDK_STATE;
    }

    /**
     * Get widget state.
     * @return the widget state
     */
    public int[] getWidgetState() {
        int[] state;

        if (this.valueObject.isValid() && this.valueObject.isMandatory()) {
            state = MDKWidgetDelegateValueObject.MANDATORY_VALID_STATE;
        } else if (this.valueObject.isError() && this.valueObject.isMandatory()) {
            state = MDKWidgetDelegateValueObject.MANDATORY_ERROR_STATE;
        } else if (this.valueObject.isMandatory()) {
            state = MDKWidgetDelegateValueObject.MANDATORY_STATE;
        } else if (this.valueObject.isValid()) {
            state = MDKWidgetDelegateValueObject.VALID_STATE;
        } else if (this.valueObject.isError()) {
            state = MDKWidgetDelegateValueObject.ERROR_STATE;
        } else {
            state = new int[] {};
        }

        return state;
    }

    /**
     * Call rich selector.
     * @param state the state
     */
    public void callRichSelector(int[] state) {
        for (String selectorKey: this.valueObject.getRichSelectors()) {
            if (this.getContext().getApplicationContext() instanceof MDKWidgetApplication) {
                RichSelector selector = ((MDKWidgetApplication) this.getContext().getApplicationContext())
                        .getMDKWidgetComponentProvider().getRichValidator(this.getContext(), selectorKey);
                selector.onStateChange(state, this.valueObject.getView());
            }
        }
    }

    /**
     * Set the valid parameter.
     * @param valid valid
     */
    public void setValid(boolean valid) {
        this.valueObject.setValid(valid);
        View v = this.valueObject.getView();
        if (v != null) {
            v.refreshDrawableState();
        }
    }

    /**
     * Get the label.
     * @return CharSequence the label
     */
    public CharSequence getLabel() {
        if (this.valueObject.getLabelViewId() != 0) {
            TextView labelView = (TextView) this.reverseFindViewById(this.valueObject.getLabelViewId());
            if (labelView != null) {
                return labelView.getText();
            }
        }
        return "";
    }

    /**
     * Set the label.
     * @param label the new label
     */
    public void setLabel(CharSequence label) {
        if (this.valueObject.getLabelViewId() != 0) {
            TextView labelView = (TextView) this.reverseFindViewById(this.valueObject.getLabelViewId());
            if (labelView != null) {
                labelView.setText(label);
            }
        }
    }

    /**
     * Return true if the MDK widget is mandatory.
     * @return boolean depending on mandatory state
     */
    @Override
    public boolean isMandatory() {
        return this.valueObject.isMandatory();
    }

    /**
     * Returns a List of FormFieldValidator to use based on the Set of attributes passed as
     * parameters.
     * (component, qualifier)
     * @param widgetAttrs a Set of integer representing R.attr.* attributes to validate
     * @return a List of FormFieldValidator tha can validate the Set of parameters
     */
    protected List<FormFieldValidator> getValidators(Set<Integer> widgetAttrs) {
        return MDKWidgetDelegateValidationHelper.getValidators(this.valueObject.getView(), widgetAttrs);
    }

    /**
     * Validate the linked widget with the mandatory FormFieldValidator and the optional
     * FormFieldValidator linked to the widget attributes.
     * @param setError true if the error must be set at validation, false otherwise
     * @param validationMode Enumerate according validation mode: VALIDATE, ON_FOCUS, ON_USER
     * @return true if all validators passed, false otherwise
     */
    public boolean validate(boolean setError, @EnumFormFieldValidator.EnumValidationMode int validationMode) {
        return MDKWidgetDelegateValidationHelper.validate(this, setError, validationMode);
    }

    /**
     *
     * Notify the command listeners registered.
     * @param bValid true if the validation is ok, false otherwise
     */
    protected void notifyValidationListeners(boolean bValid) {
        for (ValidationListener listener : this.valueObject.getValidationListeners()) {
            listener.notifyCommandStateChanged(bValid);
        }
    }

    /**
     * Play the animation if it is visible.
     * @param labelTextView the label testView
     * @param visibility the visibility
     */
    private void playAnimIfVisible(TextView labelTextView, int visibility) {
        Animation anim = null;
        if (visibility == View.VISIBLE) {
            if (this.valueObject.getShowFloatingLabelAnimId() != 0) {
                anim = AnimationUtils.loadAnimation(labelTextView.getContext(), this.valueObject.getShowFloatingLabelAnimId());
            }
        } else {
            if (this.valueObject.getHideFloatingLabelAnimId() != 0) {
                anim = AnimationUtils.loadAnimation(labelTextView.getContext(), this.valueObject.getHideFloatingLabelAnimId());
            }
        }
        if (anim != null) {
            labelTextView.startAnimation(anim);
        }
    }

    /**
     * Play the animation if necessary.
     * @param labelTextView the label textview
     * @param visibility the visibility
     * @param playAnim the play anim toggle
     */
    private void playAnimIfNecessary(TextView labelTextView, int visibility, boolean playAnim) {
        // labelTextView should never be null
        // Set visibility
        labelTextView.setVisibility(visibility);
        // Play animation
        if (playAnim) {
            playAnimIfVisible(labelTextView, visibility);
        }
    }

    /**
     * Sets the floating label visibility, and play the showFloatingLabelAnim.
     * or hideFloatingLabelAnim if asked
     * @param visibility the visibility
     * @param playAnim the play anim toggle
     */
    public void setLabelVisibility(int visibility, boolean playAnim){
        if(this.valueObject.getLabelViewId() != 0) {
            TextView labelView = (TextView) this.reverseFindViewById(this.valueObject.getLabelViewId());
            playAnimIfNecessary(labelView, visibility, playAnim);
        }
    }

    /**
     * onSaveInstanceState method.
     * @param superState the super state.
     * @return mdkWidgetDelegateSavedState mdkWidgetDelegateSavedState
     */
    public Parcelable onSaveInstanceState(Parcelable superState) {
        MDKWidgetDelegateSavedState mdkWidgetDelegateSavedState = new MDKWidgetDelegateSavedState(superState);
        mdkWidgetDelegateSavedState.initializeFromValueObject(this.valueObject);
        return mdkWidgetDelegateSavedState;
    }

    /**
     * onRestoreInstanceState method.
     * @param view the view
     * @param state the state
     * @return Parcelable the state
     */
    public Parcelable onRestoreInstanceState(View view, Parcelable state) {
        if(!(state instanceof MDKWidgetDelegateSavedState)) {
            return state;
        }
        MDKWidgetDelegateSavedState mdkWidgetDelegateSavedState = (MDKWidgetDelegateSavedState)state;
        mdkWidgetDelegateSavedState.restoreValueObject(this.valueObject);
        return mdkWidgetDelegateSavedState.getSuperState();
    }

    /**
     * Add a ValidationListener.
     * This listener will be called on each call of the MDKWidgetDelegate#validate.
     * @param validationListener the ValidationListener to add
     */
    public void addValidationListener(ValidationListener validationListener) {
        this.valueObject.getValidationListeners().add(validationListener);
    }

    @Override
    public void setRichSelectors(List<String> richSelectors) {
        this.valueObject.setRichSelectors(richSelectors);
    }
}
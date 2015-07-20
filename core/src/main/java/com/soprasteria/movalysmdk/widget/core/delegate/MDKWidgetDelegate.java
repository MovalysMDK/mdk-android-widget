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
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.R;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasText;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.error.MDKErrorWidget;
import com.soprasteria.movalysmdk.widget.core.error.MDKMessage;
import com.soprasteria.movalysmdk.widget.core.helper.MDKAttributeSet;
import com.soprasteria.movalysmdk.widget.core.helper.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.listener.CommandStateListener;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetApplication;
import com.soprasteria.movalysmdk.widget.core.selector.RichSelector;
import com.soprasteria.movalysmdk.widget.core.selector.SimpleMandatoryRichSelector;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The MDKWidgetDelegate handles the MDK logic for rich widgets.
 */
// la classe fait 1000 lignes à découper ...
// peut etre faire des delegates sous forme de singleton avec un value object pour les attributs
public class MDKWidgetDelegate implements MDKWidget {

    /**
     * ADDED_MDK_STATE.
     * <p>
     *     Number of state handle by the mdk :
     *     <ul>
     *         <li>R.attr.state_mandatory</li>
     *         <li>R.attr.state_valid</li>
     *         <li>R.attr.state_error</li>
     *     </ul>
     * </p>
     */
    private static final int ADDED_MDK_STATE = 3;
    /**
     * MANDATORY_VALID_STATE.
     */
    private static final int[] MANDATORY_VALID_STATE = {R.attr.state_valid, R.attr.state_mandatory};
    /**
     * MANDATORY_ERROR_STATE.
     */
    private static final int[] MANDATORY_ERROR_STATE = {R.attr.state_error, R.attr.state_mandatory};
    /**
     * MANDATORY_STATE.
     */
    private static final int[] MANDATORY_STATE = {R.attr.state_mandatory};
    /**
     * VALID_STATE.
     */
    private static final int[] VALID_STATE = {R.attr.state_valid};
    /**
     * ERROR_STATE.
     */
    private static final int[] ERROR_STATE = {R.attr.state_error};
    /**
     * user error key.
     */
    private static final String USER_ERROR = "user_error";
    /**
     * Component qualifier.
     */
    private String qualifier;
    /**
     * Resource id of the helper.
     */
    private int resHelperId;
    /**
     * richSelectors.
     */
    private List<RichSelector> richSelectors;
    /**
     * weakView.
     */
    protected WeakReference<View> weakView;
    /**
     * Widget root id.
     */
    protected int rootViewId;
    /**
     * Widget label id.
     */
    protected int labelViewId;
    /**
     * showFloatingLabelAnimId.
     */
    protected int showFloatingLabelAnimId;
    /**
     * hideFloatingLabelAnimId.
     */
    protected int hideFloatingLabelAnimId;
    /**
     * helperViewId.
     */
    protected int helperViewId;
    /**
     * errorViewId.
     */
    protected int errorViewId;
    /**
     * uniqueId.
     */
    protected int uniqueId;
    /**
     * useRootIdOnlyForError.
     */
    private boolean useRootIdOnlyForError = false;
    /**
     * valid.
     */
    private boolean valid = false;
    /**
     * mandatory.
     */
    private boolean mandatory = false;
    /**
     * error.
     */
    private boolean error = false;
    /**
     * Command state change listener, triggered when widget is validate.
     */
    private List<CommandStateListener> commandStateListeners;
    /**
     * attribute map for validator.
     */
    private MDKAttributeSet attributesMap;

    /**
     * Constructor.
     * @param view the view
     * @param attrs the parameters set
     */
    public MDKWidgetDelegate(View view, AttributeSet attrs) {

        this.weakView = new WeakReference<View>(view);

        this.richSelectors = new ArrayList<>();
        //TODO MDK-477
        this.richSelectors.add(new SimpleMandatoryRichSelector());

        this.commandStateListeners = new ArrayList<>();

        TypedArray typedArray = view.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons);

        this.rootViewId = typedArray.getResourceId(R.styleable.MDKCommons_rootId, 0);
        this.labelViewId = typedArray.getResourceId(R.styleable.MDKCommons_labelId, 0);
        this.showFloatingLabelAnimId = typedArray.getResourceId(R.styleable.MDKCommons_showFloatingLabelAnim, 0);
        this.hideFloatingLabelAnimId = typedArray.getResourceId(R.styleable.MDKCommons_hideFloatingLabelAnim, 0);
        this.helperViewId = typedArray.getResourceId(R.styleable.MDKCommons_helperId, 0);
        this.errorViewId = typedArray.getResourceId(R.styleable.MDKCommons_errorId, 0);

        this.resHelperId = typedArray.getResourceId(R.styleable.MDKCommons_helper, 0);

        this.mandatory = typedArray.getBoolean(R.styleable.MDKCommons_mandatory, false);

        this.qualifier = typedArray.getString(R.styleable.MDKCommons_qualifier);

        typedArray.recycle();

        this.attributesMap = new MDKAttributeSet(attrs);
        this.attributesMap.setBoolean(R.attr.mandatory, this.mandatory);

    }

    /**
     * Returns the {@link MDKAttributeSet} set on the delegate.
     * @return the {@link MDKAttributeSet} of the delegate
     */
    public MDKAttributeSet getAttributeMap() {
        return this.attributesMap;
    }

    /**
     * Sets the {@link MDKAttributeSet} of the delegate.
     * @param attributeMap the {@link MDKAttributeSet} to set on the delegate
     */
    public void setAttributeMap(MDKAttributeSet attributeMap) {
        this.attributesMap = attributeMap;
    }

    /**
     * Set a unique id to the widget from a view.
     * @param parentId the parent id
     */
    @Override
    public void setUniqueId(int parentId) {
        this.uniqueId = parentId;
    }

    // Return the unique id of the widget.
    @Override
    public int getUniqueId() {
        if (uniqueId == 0) {
            View view = this.weakView.get();
            if (view != null) {
                return view.getId();
            }
        }

        return uniqueId;
    }

    /**
     * Provide the context of the widget.
     * @return the widget context
     */
    @Override
    public Context getContext() {
        View view = this.weakView.get();
        if (view != null) {
            return view.getContext();
        }
        return null;
    }

    /**
     * <p>This function finds the root view of the error when </p>this last is shared within components.
     * @param useRootIdForError use id for error
     * @return oView the root view
     */
    public View findRootView(boolean useRootIdForError) {
        View oView = null;
        View v = this.weakView.get();
        if (v != null) {
            if (!useRootIdForError) {
                if (this.rootViewId == 0 || this.useRootIdOnlyForError ) {
                    oView = (View) v.getParent();
                } else {
                    oView = getMatchRootParent((View) v.getParent());
                }
            } else {
                if (this.useRootIdOnlyForError || this.rootViewId != 0) {
                    oView = getMatchRootParent((View) v.getParent());
                } else {
                    oView = (View) v.getParent();
                }
            }
        }
        return oView;
    }

    /**
     * <p>In the view hierarchy, recursively search for the parent's view </p>according the widget root's id.
     * @param parent the parent
     * @return View the matched parent
     */
    private View getMatchRootParent(View parent) {
        View viewToReturn ;

        // Check if the current parent's id matches the widget root's id
        if (parent.getId() == this.rootViewId) {
            viewToReturn = parent;

        } else {
            // Search recursively with the parent's view
            viewToReturn = getMatchRootParent((View) parent.getParent());
        }

        // No parent found in the view hierarchy matching the widget root's id
        return viewToReturn;
    }

    /**
     * Set error.
     * @param error the new error
     */
    public void setError(CharSequence error) {
        // empty error and add the CharSequence as only error
        this.clearError();
        MDKMessage mdkMessage = new MDKMessage(this.getLabel(), error, MDKMessage.NO_ERROR_CODE);
        MDKMessages messages = new MDKMessages();
        messages.put(USER_ERROR, mdkMessage);
        this.addError(messages);
    }

    /**
     * Set mdk error widget.
     * @param mdkErrorWidget the error widget
     * @param error the error
     */
    private void setMdkErrorWidget(MDKErrorWidget mdkErrorWidget, MDKMessages error) {
        View v = this.weakView.get();
        if (v instanceof MDKWidget) {
            if (error == null) {
                (mdkErrorWidget).clear(getContext(), ((MDKWidget) v).getUniqueId());
            } else {
                error.setComponentId(((MDKWidget) v).getUniqueId());
                error.setComponentLabelName(this.getLabel());
                (mdkErrorWidget).addError(getContext(),((MDKWidget) v).getUniqueId(), error);
            }
        }
    }

    /**
     * Set error.
     * @param messages the error to set
     */
    public void addError(MDKMessages messages) {
        View rootView = this.findRootView(true);
        if (rootView != null) {
            TextView errorView = (TextView) rootView.findViewById(this.errorViewId);
            if (errorView instanceof MDKErrorWidget){
                setMdkErrorWidget((MDKErrorWidget) errorView, messages);
            } else if (errorView != null){
                errorView.setText(messages.getFirstErrorMessage());
            }
        }
        this.error = (messages != null);
        View v = this.weakView.get();
        if (v != null) {
            v.refreshDrawableState();
        }
    }

    /**
     * Remove error.
     */
    public void clearError() {
        View rootView = this.findRootView(true);
        if (rootView != null) {
            TextView errorView = (TextView) rootView.findViewById(this.errorViewId);
            if (errorView instanceof MDKErrorWidget){
                setMdkErrorWidget((MDKErrorWidget) errorView, null);
            } else {
                errorView.setText("");
            }
        }
        this.error = false;
        View v = this.weakView.get();
        if (v != null) {
            v.refreshDrawableState();
        }
    }

    @Override
    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
        this.attributesMap.setBoolean(R.attr.mandatory, mandatory);
        View v = this.weakView.get();
        if (v != null) {
            v.refreshDrawableState();
        }
    }

    /**
     * Set the root's identifier of the MDK delegate widget to which it is attached to.
     * @param rootId the root's id of a view
     */
    @Override
    public void setRootViewId(@IdRes int rootId) {
        this.rootViewId = rootId;
    }

    /**
     * Set the label's identifier of the MDK delegate widget to which it is attached to.
     * @param labelId the label's id of a view
     */
    @Override
    public void setLabelViewId(@IdRes int labelId) {
        this.labelViewId = labelId;
    }

    /**
     * Set the helper's view identifier of the MDK delegate widget to which it is attached to.
     * @param helperId the helper's id of a view
     */
    @Override
    public void setHelperViewId(@IdRes int helperId) {
        this.helperViewId = helperId;
    }

    /**
     * Set the error's identifier of the MDK delegate widget to which it is attached to.
     * @param errorId the error's id of a view
     */
    @Override
    public void setErrorViewId(@IdRes int errorId) {
        this.errorViewId = errorId;
    }

    /**
     * Set true if the root id must only be used for the MDK delegate widget's error.
     * Only when this last is not in the same layout as the widget.
     * @param useRootIdOnlyForError true if the error is not in the same layout as the component
     */
    @Override
    public void setUseRootIdOnlyForError(boolean useRootIdOnlyForError) {
        this.useRootIdOnlyForError = useRootIdOnlyForError;
    }

    /**
     * <p>Handles the creation of a drawable state event.<p/>
     * Add additional states as needed.
     * @param extraSpace new state to add to MDK widget
     * @return new drawable state
     */
    @Override
    public int[] superOnCreateDrawableState(int extraSpace) {
        int[] state = null;

        View v = this.weakView.get();
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
        return extraSpace + ADDED_MDK_STATE;
    }

    /**
     * Get widget state.
     * @return the widget state
     */
    public int[] getWidgetState() {
        int[] state;

        if (this.valid && this.mandatory) {
            state = MANDATORY_VALID_STATE;
        } else if (this.error && this.mandatory) {
            state = MANDATORY_ERROR_STATE;
        } else if (this.mandatory) {
            state = MANDATORY_STATE;
        } else if (this.valid) {
            state = VALID_STATE;
        } else if (this.error) {
            state = ERROR_STATE;
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
        for (RichSelector selector: this.richSelectors) {
            selector.onStateChange(state, this.weakView.get());
        }
    }

    /**
     * Set the valid parameter.
     * @param valid valid
     */
    public void setValid(boolean valid) {
        this.valid = valid;
        View v = this.weakView.get();
        if (v != null) {
            v.refreshDrawableState();
        }
    }

    /**
     * Get the label.
     * @return CharSequence the label
     */
    public CharSequence getLabel() {
        View rootView = this.findRootView(false);

        if (rootView != null) {
            TextView labelView = (TextView) rootView.findViewById(this.labelViewId);
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
        View rootView = this.findRootView(false);
        if (rootView != null) {
            TextView labelView = (TextView) rootView.findViewById(this.labelViewId);
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
        return this.mandatory;
    }

    /**
     * Returns a List of FormFieldValidator to use based on the Set of attributes passed as
     * parameters.
     * (component, qualifier)
     * @param widgetAttrs a Set of interger representing R.attr.* attributes to validate
     * @return a List of FormFieldValidator tha can validate the Set of parameters
     */
    protected List<FormFieldValidator> getValidators(Set<Integer> widgetAttrs) {
        List<FormFieldValidator> rValidator = new ArrayList<>();

        View v = this.weakView.get();
        if (v != null
                && v.getContext().getApplicationContext() instanceof MDKWidgetApplication) {
            rValidator = ((MDKWidgetApplication) v.getContext().getApplicationContext())
                    .getMDKWidgetComponentProvider().getValidators(widgetAttrs);
        }
        return rValidator;
    }

    /**
     * Return the FormFieldValidator for the String key passed as parameter.
     * @param validatorKey the key of the validator
     * @return the FormFieldValidator associated to the parameter key
     */
    @Nullable
    private FormFieldValidator getValidator(String validatorKey) {
        FormFieldValidator rValidator = null;

        View v = this.weakView.get();
        if (v != null
                && v.getContext().getApplicationContext() instanceof MDKWidgetApplication) {
            rValidator = ((MDKWidgetApplication) v.getContext().getApplicationContext())
                    .getMDKWidgetComponentProvider().getValidator(validatorKey);

        }
        return rValidator;
    }

    /**
     * Validate the linked widget with the mandatory FormFieldValidator and the optional
     * FormFieldValidator linked to the widget attributes.
     * @param setError true if the error must be set at validation, false otherwise
     * @return true if all validators passed, false otherwise
     */
    public boolean validate(boolean setError) {

        boolean bValid = true;

        List<FormFieldValidator> attributesValidators = this.getValidators(this.attributesMap.keySet());

        View v = this.weakView.get();
        if (v != null) {

            MDKMessages returnMessages = new MDKMessages();

            // get the validation object
            Object objectToValidate = null;
            if (v instanceof HasText) {
                objectToValidate = ((HasText) v).getText().toString();
            } else if (v instanceof HasDate) {
                objectToValidate = ((HasDate) v).getDate();
            }

            // we have to clear all errors before validation
            if (setError) {
                this.clearError();
            }

            // run "mandatory" validator defined by the widget
            if (v instanceof HasValidator) {
                int[] validatorsResKey = ((HasValidator) v).getValidators();
                for (int validatorRes : validatorsResKey) {
                    // this get the last part of the resource name
                    String validatorKey = v.getContext().getResources().getResourceName(validatorRes).split("/")[1];
                    FormFieldValidator mandatoryValidator = this.getValidator(validatorKey);
                    bValid = bValid & executeValidator(mandatoryValidator, objectToValidate, v, setError, this.attributesMap, returnMessages);
                }
            }

            // execute all others validators if no mandatory error
            if (bValid) {
                for (FormFieldValidator validator : attributesValidators) {
                    bValid = executeValidator(validator, objectToValidate, v, setError, this.attributesMap, returnMessages) & bValid;
                }
            }

            // set Errors
            if (setError) {
                this.addError(returnMessages);
            }

        } else {
            //if the component doesn't have any validator, there is no error to show.
            this.clearError();
        }

        this.notifyCommandListeners(bValid);

        this.setValid(bValid);
        return bValid;
    }

    /**
     * Notify the command listeners registered.
     * @param bValid true if the validation is ok, false otherwise
     */
    protected void notifyCommandListeners(boolean bValid) {
        for (CommandStateListener listener : this.commandStateListeners) {
            listener.notifyCommandStateChanged(bValid);
        }
    }

    /**
     * Execute a FormFieldValidator.
     * @param validator the FormFieldValidator to execute
     * @param objectToValidate the object to validate
     * @param validatingView the view of the widget to accept on FormFieldValidator
     * @param setError true if the validation should show an error, false otherwise
     * @param attributesMap a Map containing widget attributes for validation
     * @param returnMap a Map containing previous validation errors
     * @return true if the FormFieldValidator return no error, false otherwise
     */
    protected boolean executeValidator(FormFieldValidator validator, Object objectToValidate, View validatingView, boolean setError, MDKAttributeSet attributesMap, MDKMessages returnMap) {
        boolean bValid = true;
        if (validator.accept(validatingView)) {
            MDKMessage mdkMessage = validator.validate(objectToValidate, attributesMap, returnMap, this.getContext());

            if (mdkMessage != null && mdkMessage.getMessageType() == MDKMessage.ERROR_TYPE) {
                bValid = false;
            }
        }
        return bValid;
    }



    /**
     * Play the animation if it is visible.
     * @param labelTextView the label testView
     * @param visibility the visibility
     */
    private void playAnimIfVisible(TextView labelTextView, int visibility) {
        Animation anim = null;
        if (visibility == View.VISIBLE) {
            if (this.showFloatingLabelAnimId != 0) {
                anim = AnimationUtils.loadAnimation(labelTextView.getContext(), this.showFloatingLabelAnimId);
            }
        } else {
            if (this.hideFloatingLabelAnimId != 0) {
                anim = AnimationUtils.loadAnimation(labelTextView.getContext(), this.hideFloatingLabelAnimId);
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
        if(labelTextView != null) {
            // Set visibility
            labelTextView.setVisibility(visibility);
            // Play animation
            if (playAnim) {
                playAnimIfVisible(labelTextView, visibility);
            }
        }
    }

    /**
     * Sets the floating label visibility, and play the showFloatingLabelAnim.
     * or hideFloatingLabelAnim if asked
     * @param visibility the visibility
     * @param playAnim the play anim toggle
     */
    public void setLabelVisibility(int visibility, boolean playAnim){

        if(labelViewId != 0) {
            View rootView = this.findRootView(true);
            if (rootView != null) {
                TextView labelView = (TextView) rootView.findViewById(this.labelViewId);
                playAnimIfNecessary(labelView, visibility, playAnim);
            }
        }
    }



    /**
     * onSaveInstanceState method.
     * @param superState the super state.
     * @return mdkWidgetDelegateSavedState mdkWidgetDelegateSavedState
     */
    public Parcelable onSaveInstanceState(Parcelable superState) {

        MDKWidgetDelegateSavedState mdkWidgetDelegateSavedState = new MDKWidgetDelegateSavedState(superState);

        mdkWidgetDelegateSavedState.qualifier = this.qualifier;
        mdkWidgetDelegateSavedState.resHelperId = this.resHelperId;
        mdkWidgetDelegateSavedState.richSelectors = this.richSelectors;

        mdkWidgetDelegateSavedState.rootId = this.rootViewId;
        mdkWidgetDelegateSavedState.labelId = this.labelViewId;
        mdkWidgetDelegateSavedState.showFloatingLabelAnimId = this.showFloatingLabelAnimId;
        mdkWidgetDelegateSavedState.hideFloatingLabelAnimId = this.hideFloatingLabelAnimId;
        mdkWidgetDelegateSavedState.helperId = this.helperViewId;
        mdkWidgetDelegateSavedState.errorId = this.errorViewId;
        mdkWidgetDelegateSavedState.uniqueId = this.uniqueId;

        mdkWidgetDelegateSavedState.useRootIdOnlyForError = this.useRootIdOnlyForError;
        mdkWidgetDelegateSavedState.valid = this.valid;
        mdkWidgetDelegateSavedState.mandatory = this.mandatory;
        mdkWidgetDelegateSavedState.error = this.error;

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

        this.qualifier = mdkWidgetDelegateSavedState.qualifier;
        this.resHelperId = mdkWidgetDelegateSavedState.resHelperId;
        this.richSelectors = mdkWidgetDelegateSavedState.richSelectors;

        this.rootViewId = mdkWidgetDelegateSavedState.rootId;
        this.labelViewId = mdkWidgetDelegateSavedState.labelId;
        this.showFloatingLabelAnimId = mdkWidgetDelegateSavedState.showFloatingLabelAnimId;
        this.hideFloatingLabelAnimId = mdkWidgetDelegateSavedState.hideFloatingLabelAnimId;
        this.helperViewId = mdkWidgetDelegateSavedState.helperId;
        this.errorViewId = mdkWidgetDelegateSavedState.errorId;
        this.uniqueId = mdkWidgetDelegateSavedState.uniqueId;

        this.useRootIdOnlyForError = mdkWidgetDelegateSavedState.useRootIdOnlyForError;
        this.valid = mdkWidgetDelegateSavedState.valid;
        this.mandatory = mdkWidgetDelegateSavedState.mandatory;
        this.error = mdkWidgetDelegateSavedState.error;

        return mdkWidgetDelegateSavedState.getSuperState();
    }

    /**
     * Add a CommandStateListener.
     * <p>This listener will be called on each call of the MDKWidgetDelegate#validate.</p>
     * @param commandListener the CommandStateListener to add
     */
    public void addCommandStateListener(CommandStateListener commandListener) {
        this.commandStateListeners.add(commandListener);
    }

    /**
     * MDKWidgetDelegateSavedState class definition.
     */
    private static class MDKWidgetDelegateSavedState extends View.BaseSavedState {

        /**
         * qualifier.
         */
        String qualifier;
        /**
         * resHelperId.
         */
        int resHelperId;
        /**
         * richSelectors.
         */
        List<RichSelector> richSelectors;

        /**
         * rootViewId.
         */
        int rootId;
        /**
         * labelViewId.
         */
        int labelId;
        /**
         * showFloatingLabelAnimId.
         */
        int showFloatingLabelAnimId;
        /**
         * hideFloatingLabelAnimId.
         */
        int hideFloatingLabelAnimId;
        /**
         * helperViewId.
         */
        int helperId;
        /**
         * errorViewId.
         */
        int errorId;
        /**
         * uniqueId.
         */
        int uniqueId;

        /**
         * useRootIdOnlyForError.
         */
        boolean useRootIdOnlyForError;
        /**
         * valid.
         */
        boolean valid;
        /**
         * mandatory.
         */
        boolean mandatory;
        /**
         * error.
         */
        boolean error;

        /**
         * MDKWidgetDelegateSavedState public constructor.
         * @param superState the super state
         */
        MDKWidgetDelegateSavedState(Parcelable superState) {
            super(superState);
        }

        /**
         * MDKWidgetDelegateSavedState private constructor.
         * @param in the super state
         */
        private MDKWidgetDelegateSavedState(Parcel in) {
            super(in);

            this.qualifier = in.readString();
            this.resHelperId = in.readInt();

            // TODO : read the richSelectors

            this.rootId = in.readInt();
            this.labelId = in.readInt();
            this.showFloatingLabelAnimId = in.readInt();
            this.hideFloatingLabelAnimId = in.readInt();
            this.helperId = in.readInt();
            this.errorId = in.readInt();
            this.uniqueId = in.readInt();

            this.useRootIdOnlyForError = in.readByte() != 0;
            this.valid = in.readByte() != 0;
            this.mandatory = in.readByte() != 0;
            this.error = in.readByte() != 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel out, int flags) {
            super.writeToParcel(out, flags);

            out.writeString(this.qualifier);
            out.writeInt(this.resHelperId);
            // TODO : store the richSelectors

            out.writeInt(this.rootId);
            out.writeInt(this.labelId);
            out.writeInt(this.showFloatingLabelAnimId);
            out.writeInt(this.hideFloatingLabelAnimId);
            out.writeInt(this.helperId);
            out.writeInt(this.errorId);
            out.writeInt(this.uniqueId);

            out.writeByte((byte) (this.useRootIdOnlyForError ? 1 : 0));
            out.writeByte((byte) (this.valid ? 1 : 0));
            out.writeByte((byte) (this.mandatory ? 1 : 0));
            out.writeByte((byte) (this.error ? 1 : 0));
        }

        /**
         * Required field that makes Parcelables from a Parcel.
         */
        public static final Parcelable.Creator<MDKWidgetDelegateSavedState> CREATOR =
            new Parcelable.Creator<MDKWidgetDelegateSavedState>() {

                @Override
                public MDKWidgetDelegateSavedState createFromParcel(Parcel in) {
                    return new MDKWidgetDelegateSavedState(in);
                }

                @Override
                public MDKWidgetDelegateSavedState[] newArray(int size) {
                    return new MDKWidgetDelegateSavedState[size];
                }
            };
    }
}
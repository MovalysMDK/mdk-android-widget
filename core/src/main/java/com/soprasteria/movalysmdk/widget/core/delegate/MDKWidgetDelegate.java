package com.soprasteria.movalysmdk.widget.core.delegate;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.core.R;
import com.soprasteria.movalysmdk.widget.core.selector.RichSelector;
import com.soprasteria.movalysmdk.widget.core.selector.SimpleMandatoryRichSelector;
import com.soprasteria.movalysmdk.widget.core.error.MDKErrorWidget;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.error.MDKError;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetApplication;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetComponentProvider;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetSimpleComponentProvider;
import com.soprasteria.movalysmdk.widget.core.validator.FormFieldValidator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * The MDKWidgetDelegate handles the MDK logic for rich widgets.
 */
public class MDKWidgetDelegate implements MDKWidget {

    /**
     * ADDED_MDK_STATE.
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
     * Constructor.
     * @param view the view
     * @param attrs the parameters set
     */
    public MDKWidgetDelegate(View view, AttributeSet attrs) {

        this.weakView = new WeakReference<View>(view);

        this.richSelectors = new ArrayList<>();
        this.richSelectors.add(new SimpleMandatoryRichSelector());

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
    //FIXME: too much return
    private View getMatchRootParent(View parent) {
        View viewToReturn = null;

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
        MDKError mdkError = new MDKError(this.getLabel(), error, MDKError.NO_ERROR_CODE);
        this.setError(mdkError);
    }

    /**
     * Set mdk error widget.
     * @param mdkErrorWidget the error widget
     * @param error the error
     */
    private void setMdkErrorWidget(MDKErrorWidget mdkErrorWidget, MDKError error) {
        View v = this.weakView.get();
        if (v instanceof MDKWidget) {
            if (error == null) {
                (mdkErrorWidget).clear(((MDKWidget) v).getUniqueId());
            } else {
                error.setComponentId(((MDKWidget) v).getUniqueId());
                error.setComponentLabelName(this.getLabel());
                (mdkErrorWidget).addError(((MDKWidget) v).getUniqueId(), error);
            }
        }
    }

    /**
     * Set error.
     * @param error the error to set
     */
    public void setError(MDKError error) {
        View rootView = this.findRootView(true);
        if (rootView != null) {
            TextView errorView = (TextView) rootView.findViewById(this.errorViewId);
            if (errorView instanceof MDKErrorWidget){
                setMdkErrorWidget((MDKErrorWidget) errorView, error);
            } else if (errorView != null){
                errorView.setText(error.getErrorMessage());
            }
        }
        this.error = (error != null);
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
        View v = this.weakView.get();
        if (v != null) {
            v.refreshDrawableState();
        }
    }

    /**
     * Set the root's identifier of the MDK delegate widget to which it is attached to.
     * @param rootId the root's id of a view
     */
    public void setRootViewId(@IdRes int rootId) {
        this.rootViewId = rootId;
    }

    /**
     * Set the label's identifier of the MDK delegate widget to which it is attached to.
     * @param labelId the label's id of a view
     */
    public void setLabelViewId(@IdRes int labelId) {
        this.labelViewId = labelId;
    }

    /**
     * Set the helper's view identifier of the MDK delegate widget to which it is attached to.
     * @param helperId the helper's id of a view
     */
    public void setHelperViewId(@IdRes int helperId) {
        this.helperViewId = helperId;
    }

    /**
     * Set the error's identifier of the MDK delegate widget to which it is attached to.
     * @param errorId the error's id of a view
     */
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

    @Override
    public MDKWidgetDelegate getMDKWidgetDelegate() {
        return this;
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

    @Override
    public boolean isMandatory() {
        return this.mandatory;
    }

    /**
     * Return the base key name for the specified parameters.
     * @param widgetClassName the simple name class of the widget
     * @return the base key associated with the parameters
     */
    @Nullable
    private String validatorBaseKey(String widgetClassName) {
        StringBuilder baseKey = new StringBuilder("mdkwidget_");
        baseKey.append(widgetClassName.toLowerCase());
        baseKey.append("_validator_class");
        return baseKey.toString();
    }

    /**
     * Returns the validator to use based on the context of the delegate.
     * (component, qualifier)
     * @return rValidator the result
     */
    public FormFieldValidator getValidator() {
        FormFieldValidator rValidator = null;

        View v = this.weakView.get();
        if (v != null) {
            if (v.getContext().getApplicationContext() instanceof MDKWidgetApplication) {
                rValidator = ((MDKWidgetApplication) v.getContext().getApplicationContext()).getMDKWidgetComponentProvider().getValidator(
                        validatorBaseKey(v.getClass().getSimpleName()), this.qualifier, v.getContext()
                );
            } else {
                MDKWidgetComponentProvider widgetComponentProvider = new MDKWidgetSimpleComponentProvider();
                rValidator = widgetComponentProvider.getValidator(
                        validatorBaseKey(v.getClass().getSimpleName()), this.qualifier, v.getContext()
                );
            }
        }
        return rValidator;
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
        public void writeToParcel(Parcel out, int flags) {
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
                    /**
                     * createFromParcel method.
                     * @param in a parcel
                     * @return MDKWidgetDelegateSavedState created instance
                     */
                    public MDKWidgetDelegateSavedState createFromParcel(Parcel in) {
                        return new MDKWidgetDelegateSavedState(in);
                    }

                    /**
                     * nCreate a new array of MDKWidgetDelegateSavedState.
                     * @param size the size
                     * @return MDKWidgetDelegateSavedState an array of MDKWidgetDelegateSavedState with size elements
                     */
                    public MDKWidgetDelegateSavedState[] newArray(int size) {
                        return new MDKWidgetDelegateSavedState[size];
                    }
                };
    }
}
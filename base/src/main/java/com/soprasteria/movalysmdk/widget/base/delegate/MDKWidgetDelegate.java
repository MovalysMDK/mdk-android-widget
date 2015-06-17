package com.soprasteria.movalysmdk.widget.base.delegate;

import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.base.R;
import com.soprasteria.movalysmdk.widget.base.RichSelector;
import com.soprasteria.movalysmdk.widget.base.SimpleMandatoryRichSelector;
import com.soprasteria.movalysmdk.widget.base.error.MDKErrorWidget;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.error.MDKError;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetApplication;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetComponentProvider;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetSimpleComponentProvider;
import com.soprasteria.movalysmdk.widget.core.validator.IFormFieldValidator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * MDKWidgetDelegate class definition
 * Created by abelliard on 05/06/2015.
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
     * qualifier.
     */
    private String qualifier;
    /**
     * resHelperId.
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
     * rootId.
     */
    protected int rootId;
    /**
     * labelId.
     */
    protected int labelId;
    /**
     * showFloatingLabelAnimId.
     */
    protected int showFloatingLabelAnimId;
    /**
     * hideFloatingLabelAnimId.
     */
    protected int hideFloatingLabelAnimId;
    /**
     * helperId.
     */
    protected int helperId;
    /**
     * errorId.
     */
    protected int errorId;
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

        this.rootId = typedArray.getResourceId(R.styleable.MDKCommons_rootId, 0);
        this.labelId = typedArray.getResourceId(R.styleable.MDKCommons_labelId, 0);
        this.showFloatingLabelAnimId = typedArray.getResourceId(R.styleable.MDKCommons_showFloatingLabelAnim, 0);
        this.hideFloatingLabelAnimId = typedArray.getResourceId(R.styleable.MDKCommons_hideFloatingLabelAnim, 0);
        this.helperId = typedArray.getResourceId(R.styleable.MDKCommons_helperId, 0);
        this.errorId = typedArray.getResourceId(R.styleable.MDKCommons_errorId, 0);

        this.resHelperId = typedArray.getResourceId(R.styleable.MDKCommons_helper, 0);

        this.mandatory = typedArray.getBoolean(R.styleable.MDKCommons_mandatory, false);

        this.qualifier = typedArray.getString(R.styleable.MDKCommons_qualifier);

        typedArray.recycle();

    }

    // TODO may change the interface of this method
    @Override
    public void setUniqueId(int parentId) {
        this.uniqueId = parentId;
    }

    // TODO may change the interface of this method
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


    // TODO explain why

    /**
     * Find the root view.
     * @param useRootIdForError use id for error
     * @return oView the root view
     */
    public View findRootView(boolean useRootIdForError) {
        View oView = null;
        View v = this.weakView.get();
        if (v != null) {
            if (!useRootIdForError) {
                if (this.rootId == 0 || this.useRootIdOnlyForError ) {
                    oView = (View) v.getParent();
                } else {
                    oView = getMatchRootParent((View) v.getParent());
                }
            } else {
                if (this.useRootIdOnlyForError || this.rootId != 0) {
                    oView = getMatchRootParent((View) v.getParent());
                } else {
                    oView = (View) v.getParent();
                }
            }
        }
        return oView;
    }

    /**
     * Get root match parent.
     * @param parent the parent
     * @return View the matched parent
     */
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

    /**
     * Set error.
     * @param error the new error
     */
    public void setError(CharSequence error) {
        MDKError mdkError = new MDKError(this.getLabel(), error, MDKError.NO_ERROR_CODE);
        this.setMDKError(mdkError);
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
    public void setMDKError(MDKError error) {
        View rootView = this.findRootView(true);
        if (rootView != null) {
            TextView errorView = (TextView) rootView.findViewById(this.errorId);
            if (errorView instanceof MDKErrorWidget){
                setMdkErrorWidget((MDKErrorWidget) errorView, error);
            } else {
                if (error != null) {
                    errorView.setText(error.getErrorMessage());
                } else {
                    errorView.setText("");
                }
            }
        }
        this.error = (error != null);
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

    @Override
    public void setRootId(int rootId) {
        this.rootId = rootId;
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

    // TODO may change the interface of this method
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

    // TODO may change the interface of this method
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
            TextView labelView = (TextView) rootView.findViewById(this.labelId);
            if (labelView != null) {
                return labelView.getText();
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    /**
     * Set the label.
     * @param label the new label
     */
    public void setLabel(CharSequence label) {
        View rootView = this.findRootView(false);
        if (rootView != null) {
            TextView labelView = (TextView) rootView.findViewById(this.labelId);
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
        StringBuilder baseKey = new StringBuilder();

        baseKey.append(widgetClassName.toLowerCase());

        baseKey.append("_validator_class");

        return baseKey.toString();
    }

    /**
     * Returns the validator to use based on the context of the delegate.
     * (component, qualifier)
     * @return rValidator the result
     */
    public IFormFieldValidator getValidator() {
        IFormFieldValidator rValidator = null;

        View v = this.weakView.get();
        if (v != null) {
            if (v.getContext().getApplicationContext() instanceof MDKWidgetApplication) {
                rValidator = ((MDKWidgetApplication) v.getContext().getApplicationContext()).getMDKWidgetComponentProvider().getValidator(
                        v.getContext(),
                        validatorBaseKey(v.getClass().getSimpleName()),
                        this.qualifier);
            } else {
                MDKWidgetComponentProvider widgetComponentProvider = new MDKWidgetSimpleComponentProvider();
                rValidator = widgetComponentProvider.getValidator(
                        v.getContext(),
                        validatorBaseKey(v.getClass().getSimpleName()),
                        this.qualifier);
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

        if(labelId != 0) {
            View rootView = this.findRootView(true);
            if (rootView != null) {
                TextView labelView = (TextView) rootView.findViewById(this.labelId);
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

        mdkWidgetDelegateSavedState.rootId = this.rootId;
        mdkWidgetDelegateSavedState.labelId = this.labelId;
        mdkWidgetDelegateSavedState.showFloatingLabelAnimId = this.showFloatingLabelAnimId;
        mdkWidgetDelegateSavedState.hideFloatingLabelAnimId = this.hideFloatingLabelAnimId;
        mdkWidgetDelegateSavedState.helperId = this.helperId;
        mdkWidgetDelegateSavedState.errorId = this.errorId;
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

        this.rootId = mdkWidgetDelegateSavedState.rootId;
        this.labelId = mdkWidgetDelegateSavedState.labelId;
        this.showFloatingLabelAnimId = mdkWidgetDelegateSavedState.showFloatingLabelAnimId;
        this.hideFloatingLabelAnimId = mdkWidgetDelegateSavedState.hideFloatingLabelAnimId;
        this.helperId = mdkWidgetDelegateSavedState.helperId;
        this.errorId = mdkWidgetDelegateSavedState.errorId;
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
         * rootId.
         */
        int rootId;
        /**
         * labelId.
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
         * helperId.
         */
        int helperId;
        /**
         * errorId.
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
         * Required field that makes Parcelables from a Parcel
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

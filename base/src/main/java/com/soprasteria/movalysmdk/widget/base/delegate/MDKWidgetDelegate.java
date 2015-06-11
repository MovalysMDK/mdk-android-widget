package com.soprasteria.movalysmdk.widget.base.delegate;

import android.content.res.TypedArray;
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
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetApplication;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetComponentProvider;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetSimpleComponentProvider;
import com.soprasteria.movalysmdk.widget.core.validator.IFormFieldValidator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abelliard on 05/06/2015.
 */
public class MDKWidgetDelegate implements MDKWidget {


    private static final int ADDED_MDK_STATE = 3;
    private static final int[] MANDATORY_VALID_STATE = {R.attr.state_valid, R.attr.state_mandatory};
    private static final int[] MANDATORY_ERROR_STATE = {R.attr.state_error, R.attr.state_mandatory};
    private static final int[] MANDATORY_STATE = {R.attr.state_mandatory};
    private static final int[] VALID_STATE = {R.attr.state_valid};
    private static final int[] ERROR_STATE = {R.attr.state_error};

    private final String qualifier;
    private final int resHelperId;
    private List<RichSelector> richSelectors;

    protected final WeakReference<View> weakView;
    protected int rootId;
    protected int labelId;
    protected int showFloatingLabelAnimId;
    protected int hideFloatingLabelAnimId;
    protected int helperId;
    protected int errorId;

    private boolean useRootIdOnlyForError = false;
    private boolean valid = false;
    private boolean mandatory = false;
    private boolean error = false;


    /**
     * Constructor
     * @param view
     * @param attrs
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
        // nothing
    }

    // TODO may change the interface of this method
    @Override
    public int getUniqueId() {
        return -1;
    }


    public View findRootView(boolean useRootIdForError) {
        View v = this.weakView.get();
        if (v != null) {
            if (!useRootIdForError) {
                if (this.rootId == 0) {
                    return (View) v.getParent();
                } else {
                    return getMatchRootParent((View) v.getParent());
                }
            } else {
                if (this.useRootIdOnlyForError || this.rootId != 0) {
                    return getMatchRootParent((View) v.getParent());
                } else {
                    return (View) v.getParent();
                }
            }
        } else {
            return null;
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
        View rootView = this.findRootView(true);
        if (rootView != null) {
            TextView errorView = (TextView) rootView.findViewById(this.errorId);
            if (errorView != null && errorView instanceof MDKErrorWidget) {
                View v = this.weakView.get();
                if (v != null && v instanceof MDKWidget) {
                    if (error == null || error.length() == 0) {
                        ((MDKErrorWidget) errorView).clear(((MDKWidget) v).getUniqueId());
                    } else {
                        ((MDKErrorWidget) errorView).addError(((MDKWidget) v).getUniqueId(), error);
                    }
                }
            } else if (errorView != null){
                errorView.setText(error);
            }
        }

        if (error != null && error.length() > 0) {
            this.error = true;
        } else {
            this.error = false;
        }
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

    public int getStateLength(int extraSpace) {
        return extraSpace + ADDED_MDK_STATE;
    }

    public int[] getWidgetState() {
        int[] state = null;

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

    public void callRichSelector(int[] state) {
        for (RichSelector selector: this.richSelectors) {
            selector.onStateChange(state, this.weakView.get());
        }
    }

    public void setValid(boolean valid) {
        this.valid = valid;
        View v = this.weakView.get();
        if (v != null) {
            v.refreshDrawableState();
        }
    }

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
     * Return the base key name for the specified parameters
     * @param widgetClassName the simple name class of the widget
     * @return the base key associated with the parameters
     */
    @Nullable
    private String validatorBaseKey(String widgetClassName) {
        StringBuffer baseKey = new StringBuffer();

        baseKey.append(widgetClassName.toLowerCase());

        baseKey.append("_validator_class");

        return baseKey.toString();
    }

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
     * Sets the floating label visibility, and play the showFloatingLabelAnim
     * or hideFloatingLabelAnim if asked
     * @param visibility
     * @param playAnim
     */
    public void setLabelVisibility(int visibility, boolean playAnim){

        if(labelId != 0) {
            View rootView = this.findRootView(true);
            if (rootView != null) {
                TextView labelView = (TextView) rootView.findViewById(this.labelId);
                if(labelView != null) {
                    // Set visibility
                    labelView.setVisibility(visibility);
                    // Play animation
                    if (playAnim) {
                        Animation anim = null;
                        if (visibility == View.VISIBLE) {
                            if (this.showFloatingLabelAnimId != 0) {
                                anim = AnimationUtils.loadAnimation(labelView.getContext(), this.showFloatingLabelAnimId);
                            }
                        } else {
                            if (this.hideFloatingLabelAnimId != 0) {
                                anim = AnimationUtils.loadAnimation(labelView.getContext(), this.hideFloatingLabelAnimId);
                            }
                        }
                        if (anim != null) {
                            labelView.startAnimation(anim);
                        }
                    }
                }
            }
        }
    }

}

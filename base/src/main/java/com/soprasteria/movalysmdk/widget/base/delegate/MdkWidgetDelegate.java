package com.soprasteria.movalysmdk.widget.base.delegate;

import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.base.R;
import com.soprasteria.movalysmdk.widget.base.RichSelector;
import com.soprasteria.movalysmdk.widget.base.SimpleMandatoryRichSelector;
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
public class MdkWidgetDelegate implements MDKWidget {


    private static final int ADDED_MDK_STATE = 3;
    private static final int[] MANDATORY_VALID_STATE = {R.attr.state_valid, R.attr.state_mandatory};
    private static final int[] MANDATORY_ERROR_STATE = {R.attr.state_error, R.attr.state_mandatory};
    private static final int[] MANDATORY_STATE = {R.attr.state_mandatory};
    private static final int[] VALID_STATE = {R.attr.state_valid};
    private static final int[] ERROR_STATE = {R.attr.state_error};

    private final WeakReference<View> weakView;
    private final String qualifier;
    private List<RichSelector> richSelectors;

    private int rootId;
    private int labelId;
    private int helperId;
    private int errorId;

    private boolean useRootIdOnlyForError = false;
    private boolean valid = false;
    private boolean mandatory = false;
    private boolean error = false;


    public MdkWidgetDelegate(View view, AttributeSet attrs) {
        
        this.weakView = new WeakReference<View>(view);

        this.richSelectors = new ArrayList<>();
        this.richSelectors.add(new SimpleMandatoryRichSelector());
        
        TypedArray typedArray = view.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons);

        this.rootId = typedArray.getResourceId(R.styleable.MDKCommons_rootId, 0);
        this.labelId = typedArray.getResourceId(R.styleable.MDKCommons_labelId, 0);
        this.helperId = typedArray.getResourceId(R.styleable.MDKCommons_helperId, 0);
        this.errorId = typedArray.getResourceId(R.styleable.MDKCommons_errorId, 0);

        this.mandatory = typedArray.getBoolean(R.styleable.MDKCommons_mandatory, false);

        this.qualifier = typedArray.getString(R.styleable.MDKCommons_qualifier);

        typedArray.recycle();

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
            if (errorView != null) {
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
        TextView labelView = (TextView) rootView.findViewById(this.labelId);
        if (labelView != null) {
            return labelView.getText();
        } else {
            return "";
        }
    }

    public void setLabel(CharSequence label) {
        View rootView = this.findRootView(false);
        TextView labelView = (TextView) rootView.findViewById(this.labelId);
        if (labelView != null) {
            labelView.setText(label);
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
}

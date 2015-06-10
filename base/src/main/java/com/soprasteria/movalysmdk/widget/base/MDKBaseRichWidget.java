package com.soprasteria.movalysmdk.widget.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasError;

/**
 * Base class for rich mdk widgets.
 * This class inflate the layout passed in the constructor and depending
 * on the attributes configuration inflate error and label subwigets.
 * @param <T> the type of inner widget for the rich widget
 */
public class MDKBaseRichWidget<T extends MDKWidget> extends RelativeLayout implements MDKWidget, HasError {

    /** the inner widget */
    private T innerWidget;
    /** the error view */
    private TextView errorView;
    /** should always show the error view */
    private boolean errorAlwaysVisible;
    /** The string ressource id for the hint */
    private int resHintId;

    public MDKBaseRichWidget(int layoutWithLabelId, int layoutWithoutLabelId, Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            init(context, attrs, layoutWithLabelId, layoutWithoutLabelId);
        }

    }

    public MDKBaseRichWidget(int layoutWithLabelId, int layoutWithoutLabelId, Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (!isInEditMode()) {
            init(context, attrs, layoutWithLabelId, layoutWithoutLabelId);
        }
    }

    /**
     * Initialise rich widget
     * @param context the context
     * @param attrs the attribut set
     * @param layoutWithLabelId the layout id for the widget with label
     * @param layoutWithoutLabelId the layout id for the widget without label
     */
    private void init(Context context, AttributeSet attrs, int layoutWithLabelId, int layoutWithoutLabelId) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MDKCommons);
        // parse label attribute
        int resLabelId = typedArray.getResourceId(R.styleable.MDKCommons_label, 0);
        // parse label attribute
        resHintId = typedArray.getResourceId(R.styleable.MDKCommons_hint, 0);
        // parse layout attribute
        int customLayoutId = typedArray.getResourceId(R.styleable.MDKCommons_layout, 0);

        // inflate component layout
        if (customLayoutId != 0) {
            // custom layout
            LayoutInflater.from(context).inflate(customLayoutId, this);
        } else if (resLabelId != 0) {
            // with label
            LayoutInflater.from(context).inflate(layoutWithLabelId, this);
        } else {
            // without label
            LayoutInflater.from(context).inflate(layoutWithoutLabelId, this);
        }

        // get innerWidget component
        this.innerWidget = (T) this.findViewById(R.id.component_internal);

        // get label component if exists
        TextView labelView = (TextView) this.findViewById(R.id.component_label);
        this.errorView = (TextView) this.findViewById(R.id.component_error);
        if (labelView != null && resLabelId != 0) {
            labelView.setText(resLabelId);
        }

        // parse others attributes
        int errorId = typedArray.getResourceId(R.styleable.MDKCommons_errorId, 0);
        if (errorId != 0) {
            int rootId = typedArray.getResourceId(R.styleable.MDKCommons_rootId, 0);
            this.innerWidget.setRootId(rootId);
            this.innerWidget.setErrorId(errorId);
            this.innerWidget.setUseRootIdOnlyForError(true);
        }
        //TODO (always show error text view, ...)

        boolean mandatory = typedArray.getBoolean(R.styleable.MDKCommons_mandatory, false);
        this.getInnerWidget().setMandatory(mandatory);

        this.setAddStatesFromChildren(true);

        // release typed array
        typedArray.recycle();
    }

    /**
     * Getter for the inner widget of the rich widget
     * @return the inner widget
     */
    public T getInnerWidget() {
        return this.innerWidget;
    }

    public int getResHintId() { return this.resHintId; }

    @Override
    public void setError(String error) {
        this.setError(error, false);
    }

    /**
     * Set the error on the inner widget
     * @param error the error to set
     * @param formValidation true if the error comes from validation, false otherwise
     */
    protected void setError(String error, boolean formValidation) {
        if (this.errorView != null) {
            if (error != null && error.length() > 0) {
                errorView.setVisibility(View.VISIBLE);
            } else if (!errorAlwaysVisible) {
                errorView.setVisibility(View.GONE);
            }
        }
        if (!formValidation) {
            this.getInnerWidget().setError(error);
        }
    }

    @Override
    public boolean isMandatory() {
        return this.getInnerWidget().isMandatory();
    }

    @Override
    public void setRootId(int rootId) {
        this.getInnerWidget().setRootId(rootId);
    }

    @Override
    public void setLabelId(int labelId) {
        this.getInnerWidget().setLabelId(labelId);
    }

    @Override
    public void setHelperId(int helperId) {
        this.getInnerWidget().setHelperId(helperId);
    }

    @Override
    public void setErrorId(int errorId) {
        this.getInnerWidget().setErrorId(errorId);
    }

    @Override
    public void setUseRootIdOnlyForError(boolean useRootIdOnlyForError) {
        this.getInnerWidget().setUseRootIdOnlyForError(useRootIdOnlyForError);
    }

    @Override
    public void setError(CharSequence error) {
        this.getInnerWidget().setError(error);
    }

    @Override
    public void setMandatory(boolean mandatory) {
        this.getInnerWidget().setMandatory(mandatory);
    }
}

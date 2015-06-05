package com.soprasteria.movalysmdk.widget.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.core.MDKWidget;

/**
 * Created by abelliard on 04/06/2015.
 */
public class MDKBaseRichWidget<T extends MDKWidget> extends RelativeLayout {

    private T internal;
    private TextView errorView;
    private boolean errorAlwaysVisible;

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

    private void init(Context context, AttributeSet attrs, int layoutWithLabelId, int layoutWithoutLabelId) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MDKCommons);
        // parse label attribute
        int resLabelId = typedArray.getResourceId(R.styleable.MDKCommons_label, 0);
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

        // get internal component
        this.internal = (T) this.findViewById(R.id.component_internal);

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
            this.internal.setRootId(rootId);
            this.internal.setErrorId(errorId);
            this.internal.setUseRootIdOnlyForError(true);
        }
        // (always show error text view, ...)

        // release typed array
        typedArray.recycle();
    }

    public T getInternalWidget() {
        return this.internal;
    }

    public void setError(String error) {
        this.setError(error, false);
    }

    private void setError(String error, boolean formValidation) {
        if (this.errorView != null) {
            if (error != null && error.length() > 0) {
                errorView.setVisibility(View.VISIBLE);
            } else if (!errorAlwaysVisible) {
                errorView.setVisibility(View.GONE);
            }
        }
        if (!formValidation) {
            this.getInternalWidget().setError(error);
        }
    }
}

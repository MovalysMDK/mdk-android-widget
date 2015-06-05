package com.soprasteria.movalysmdk.widget.base.delegate;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.base.R;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;

import java.lang.ref.WeakReference;

/**
 * Created by abelliard on 05/06/2015.
 */
public class MdkWidgetDelegate implements MDKWidget {


    private final WeakReference<View> weakView;
    private int rootId;
    private int labelId;
    private int helperId;
    private int errorId;

    private boolean useRootIdOnlyForError = false;

    public MdkWidgetDelegate(View view, AttributeSet attrs) {
        
        this.weakView = new WeakReference<View>(view);
        
        TypedArray typedArray = view.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons);

        this.rootId = typedArray.getResourceId(R.styleable.MDKCommons_rootId, 0);
        this.labelId = typedArray.getResourceId(R.styleable.MDKCommons_labelId, 0);
        this.helperId = typedArray.getResourceId(R.styleable.MDKCommons_helperId, 0);
        this.errorId = typedArray.getResourceId(R.styleable.MDKCommons_errorId, 0);

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
//        refreshDrawableState();
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
}

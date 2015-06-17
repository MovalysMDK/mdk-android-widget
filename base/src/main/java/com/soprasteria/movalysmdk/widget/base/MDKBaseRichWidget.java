package com.soprasteria.movalysmdk.widget.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.base.error.MDKErrorTextView;
import com.soprasteria.movalysmdk.widget.core.MDKRestoreWidget;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasError;
import com.soprasteria.movalysmdk.widget.core.error.MDKError;

/**
 * Base class for rich mdk widgets.
 * This class inflate the layout passed in the constructor and depending
 * on the attributes configuration inflate error and label subwigets.
 * @param <T> the type of inner widget for the rich widget
 */
public class MDKBaseRichWidget<T extends MDKWidget & MDKRestoreWidget> extends RelativeLayout implements MDKWidget, HasError {

    /** the inner widget. */
    private T innerWidget;
    /** the error view. */
    private TextView errorView;
    /** should always show the error view. */
    private boolean errorAlwaysVisible;
    /** The string ressource id for the hint. */
    private int resHintId;

    /**
     * Constructor.
     * @param layoutWithLabelId layoutWithLabelId
     * @param layoutWithoutLabelId layoutWithoutLabelId
     * @param context the context
     * @param attrs attributes
     */
    public MDKBaseRichWidget(int layoutWithLabelId, int layoutWithoutLabelId, Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            init(context, attrs, layoutWithLabelId, layoutWithoutLabelId);
        }

    }

    /**
     * Constructor.
     * @param layoutWithLabelId layoutWithLabelId
     * @param layoutWithoutLabelId layoutWithoutLabelId
     * @param context the context
     * @param attrs attributes
     * @param defStyleAttr the style
     */
    public MDKBaseRichWidget(int layoutWithLabelId, int layoutWithoutLabelId, Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (!isInEditMode()) {
            init(context, attrs, layoutWithLabelId, layoutWithoutLabelId);
        }
    }

    /**
     * Initialise rich widget.
     * @param context the context
     * @param attrs the attribut set
     * @param layoutWithLabelId the layout id for the widget with label
     * @param layoutWithoutLabelId the layout id for the widget without label
     */
    private void init(Context context, AttributeSet attrs, int layoutWithLabelId, int layoutWithoutLabelId) {

        // replace the creation of the state drawable
        this.setAddStatesFromChildren(true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MDKCommons);
        // parse label attribute
        int resLabelId = typedArray.getResourceId(R.styleable.MDKCommons_label, 0);
        // parse label attribute
        resHintId = typedArray.getResourceId(R.styleable.MDKCommons_hint, 0);
        // parse helper attribute
        int resHelperId = typedArray.getResourceId(R.styleable.MDKCommons_helper, 0);
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
        this.innerWidget.setUniqueId(this.getId());
        ((View)this.innerWidget).setSaveFromParentEnabled(false);

        // get label component if exists
        TextView labelView = (TextView) this.findViewById(R.id.component_label);


        if (labelView != null && resLabelId != 0) {
            labelView.setText(resLabelId);
        }

        // getting the error view
        this.errorView = (TextView) this.findViewById(R.id.component_error);
        if (resHelperId != 0
                && this.errorView != null
                && this.errorView instanceof MDKErrorTextView ) {
            ((MDKErrorTextView) this.errorView).setHelper(context.getString(resHelperId));
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

        // replace the creation of the state drawable
        this.setAddStatesFromChildren(true);

        // release typed array
        typedArray.recycle();
    }

    @Override
    public int getUniqueId() {
        return this.getId();
    }

    @Override
    public void setUniqueId(int parentId) {
        // nothing to do
    }

    /**
     * Getter for the inner widget of the rich widget.
     * @return the inner widget
     */
    public T getInnerWidget()   {return this.innerWidget;
    }

    /**
     * Return resource's hint id.
     * @return resHintId the res hint id
     */
    public int getResHintId() {
        return this.resHintId;
    }

    @Override
    public void setMDKError(MDKError error) {
        this.setMDKError(error, false);
    }

    @Override
    public void setError(CharSequence error) {
        this.setError(error, false);
    }

    /**
     * Set the error on the inner widget.
     * @param error the error to set
     * @param formValidation true if the error comes from validation, false otherwise
     */
    protected void setMDKError(MDKError error, boolean formValidation) {
        if (this.errorView != null) {
            if (error != null) {
                errorView.setVisibility(View.VISIBLE);
            } else if (!errorAlwaysVisible) {
                errorView.setVisibility(View.GONE);
            }
        }
        if (!formValidation) {
            this.getInnerWidget().setMDKError(error);
        }
    }

    /**
     * Set error.
     * @param error the error
     * @param formValidation is form Validation
     */
    protected void setError(CharSequence error, boolean formValidation) {
        //TODO A factoriser
        if (this.errorView != null) {
            if (error != null) {
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
    public void setMandatory(boolean mandatory) {
        this.getInnerWidget().setMandatory(mandatory);
    }

    // TODO may change the interface of this method
    @Override
    public void callMergeDrawableStates(int[] baseState, int[] additionalState) {
        mergeDrawableStates(baseState, additionalState);
    }

    // TODO may change the interface of this method
    @Override
    public int[] superOnCreateDrawableState(int extraSpace) {
        return super.onCreateDrawableState(extraSpace);
    }

    @Override
    public Parcelable onSaveInstanceState() {

        Parcelable superState = super.onSaveInstanceState();
        MDKBaseRichWidgetSavedState mdkBaseRichWidgetSavedState = new MDKBaseRichWidgetSavedState(superState);

        mdkBaseRichWidgetSavedState.errorAlwaysVisible = this.errorAlwaysVisible;
        mdkBaseRichWidgetSavedState.resHintId = this.resHintId;
        mdkBaseRichWidgetSavedState.innerWidget = this.innerWidget.superOnSaveInstanceState();
        // TODO : add the save of the MDKErrorWidget

        return mdkBaseRichWidgetSavedState;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {

        if(!(state instanceof MDKBaseRichWidgetSavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        MDKBaseRichWidgetSavedState mdkBaseRichWidgetSavedState = (MDKBaseRichWidgetSavedState)state;
        super.onRestoreInstanceState(mdkBaseRichWidgetSavedState.getSuperState());

        this.errorAlwaysVisible = mdkBaseRichWidgetSavedState.errorAlwaysVisible;
        this.resHintId = mdkBaseRichWidgetSavedState.resHintId;
        this.innerWidget.superOnRestoreInstanceState(mdkBaseRichWidgetSavedState.innerWidget);
        // TODO : add the restore of the MDKErrorWidget
    }

    /**
     * MDKBaseRichWidgetSavedState class definition.
     */
    private static class MDKBaseRichWidgetSavedState extends View.BaseSavedState {

        boolean errorAlwaysVisible;
        int resHintId;
        Parcelable innerWidget;

        /**
         * Constructor.
         * @param superState the new Parcelable
         */
        MDKBaseRichWidgetSavedState(Parcelable superState) {
            super(superState);
        }

        /**
         * Constructor.
         * @param in the new parcel
         */
        private MDKBaseRichWidgetSavedState(Parcel in) {
            super(in);

            this.errorAlwaysVisible = in.readByte() != 0;
            this.resHintId = in.readInt();
            this.innerWidget = in.readParcelable(ClassLoader.getSystemClassLoader());
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);

            out.writeByte((byte) (this.errorAlwaysVisible ? 1 : 0));
            out.writeInt(this.resHintId);
            out.writeParcelable(innerWidget, 0);
        }

        //required field that makes Parcelables from a Parcel
        public static final Parcelable.Creator<MDKBaseRichWidgetSavedState> CREATOR =
                new Parcelable.Creator<MDKBaseRichWidgetSavedState>() {
                    /**
                     * createFromParcel method.
                     * @param in a parcel
                     * @return MDKWidgetDelegateSavedState created instance
                     */
                    public MDKBaseRichWidgetSavedState createFromParcel(Parcel in) {
                        return new MDKBaseRichWidgetSavedState(in);
                    }

                    /**
                     * nCreate a new array of MDKWidgetDelegateSavedState.
                     * @param size the size
                     * @return MDKWidgetDelegateSavedState an array of MDKWidgetDelegateSavedState with size elements
                     */
                    public MDKBaseRichWidgetSavedState[] newArray(int size) {
                        return new MDKBaseRichWidgetSavedState[size];
                    }
                };
    }
}

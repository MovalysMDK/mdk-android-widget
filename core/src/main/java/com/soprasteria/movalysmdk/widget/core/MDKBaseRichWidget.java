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
package com.soprasteria.movalysmdk.widget.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.error.MDKError;
import com.soprasteria.movalysmdk.widget.core.error.MDKErrorTextView;
import com.soprasteria.movalysmdk.widget.core.error.MDKErrorWidget;
import com.soprasteria.movalysmdk.widget.core.helper.MDKAttributeSet;

/**
 * MDK Rich Widget.
 * <p>A rich widget adds the following features on an base widget :</p>
 * <ul>
 *     <li>label (with floating label)</li>
 *     <li>hint</li>
 *     <li>error/helper</li>
 * </ul>
 * <p>The layout can be customized with the attribute mdk:layout</p>
 * @param <T> the type of inner widget for the rich widget
 */
public class MDKBaseRichWidget<T extends MDKWidget & MDKRestorableWidget & HasValidator> extends RelativeLayout implements MDKRichWidget, HasValidator {

    /**
     * Base widget.
     * Warning: business rules are included in the baseWidget, not in this class or inherited class.
     */
    private T innerWidget;

    /** the error view. */
    private MDKErrorWidget errorView;

    /** should always show the error view. */
    private boolean errorAlwaysVisible;
    
    /** The string resource id for the hint. */
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

        init(context, attrs, layoutWithLabelId, layoutWithoutLabelId);
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

        init(context, attrs, layoutWithLabelId, layoutWithoutLabelId);
    }

    /**
     * Initialise rich widget.
     * @param context the context
     * @param attrs the attribute set
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

        inflateLayout(context, attrs, layoutWithLabelId, layoutWithoutLabelId, customLayoutId, resLabelId);

        if (!this.isInEditMode()) {
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
            this.errorView = (MDKErrorWidget) this.findViewById(R.id.component_error);
            if (resHelperId != 0
                    && this.errorView != null
                    && this.errorView instanceof MDKErrorTextView ) {
                ((MDKErrorTextView) this.errorView).setHelper(context, context.getString(resHelperId));
            }

            // parse others attributes
            int errorId = typedArray.getResourceId(R.styleable.MDKCommons_errorId, 0);
            if (errorId != 0) {
                int rootId = typedArray.getResourceId(R.styleable.MDKCommons_rootId, 0);
                this.innerWidget.setRootViewId(rootId);
                this.innerWidget.setErrorViewId(errorId);
                this.innerWidget.setUseRootIdOnlyForError(true);
            }

            boolean mandatory = typedArray.getBoolean(R.styleable.MDKCommons_mandatory, false);
            this.getInnerWidget().setMandatory(mandatory);
        }

        // replace the creation of the state drawable
        this.setAddStatesFromChildren(true);

        // release typed array
        typedArray.recycle();

        initAttributeMap(attrs);

    }

    /**
     * inflate the widget layout.
     * @param context the android context
     * @param attrs the xml attributes
     * @param layoutWithLabelId the layout with label
     * @param layoutWithoutLabelId the layout without label
     * @param customLayoutId a custom layout
     * @param resLabelId a res label id
     */
    private void inflateLayout(Context context, AttributeSet attrs, int layoutWithLabelId, int layoutWithoutLabelId, int customLayoutId, int resLabelId) {
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
    }

    /**
     * Initialise the attribute map for the widget.
     * @param attrs the xml attributes
     */
    private void initAttributeMap(AttributeSet attrs) {
        if (!isInEditMode()) {
            MDKAttributeSet attributeMap = new MDKAttributeSet(attrs);
            // copy attribute from rich widget to inner widget
            this.getInnerWidget().setAttributeMap(attributeMap);
        }
    }

    /**
     * Getter for the inner widget of the rich widget.
     * @return the inner widget
     */
    public T getInnerWidget()   {
        return this.innerWidget;
    }

    /**
     * Return resource's hint id.
     * @return resHintId the res hint id
     */
    public int getResHintId() {
        return this.resHintId;
    }

    @Override
    public boolean isMandatory() {
        return this.getInnerWidget().isMandatory();
    }

    @Override
    public void setRootViewId(int rootId) {
        this.getInnerWidget().setRootViewId(rootId);
    }

    @Override
    public void setLabelViewId(int labelId) {
        this.getInnerWidget().setLabelViewId(labelId);
    }

    @Override
    public void setHelperViewId(int helperId) {
        this.getInnerWidget().setHelperViewId(helperId);
    }

    @Override
    public void setErrorViewId(int errorId) {
        this.getInnerWidget().setErrorViewId(errorId);
    }

    @Override
    public void setUseRootIdOnlyForError(boolean useRootIdOnlyForError) {
        this.getInnerWidget().setUseRootIdOnlyForError(useRootIdOnlyForError);
    }

    @Override
    public void addError(MDKError error) {
        this.getInnerWidget().addError(error);
    }

    @Override
    public void setError(CharSequence error) {
        this.getInnerWidget().setError(error);
    }

    @Override
    public void clearError() {
        this.getInnerWidget().clearError();
    }
    
    @Override
    public int[] getValidators() {
        return new int[0];
    }

    @Override
    public boolean validate() {
        return this.getInnerWidget().validate();
    }

    @Override
    public void setMandatory(boolean mandatory) {
        this.getInnerWidget().setMandatory(mandatory);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        MDKSavedState ss = new MDKSavedState(superState);
        ss.childrenStates = new SparseArray();
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).saveHierarchyState(ss.childrenStates);
        }
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        MDKSavedState ss = (MDKSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).restoreHierarchyState(ss.childrenStates);
        }

    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        dispatchThawSelfOnly(container);
    }

    /**
     * MDKBaseRichWidgetSavedState class definition.
     */
    private static class MDKSavedState extends View.BaseSavedState {

        /** Children states. */
        SparseArray childrenStates;

        /**
         * Constructor.
         * @param superState the new Parcelable
         */
        MDKSavedState(Parcelable superState) {
            super(superState);
        }

        /**
         * Constructor.
         * @param in the new Parcelable
         * @param classLoader the class loader
         */
        private MDKSavedState(Parcel in, ClassLoader classLoader) {
            super(in);
            childrenStates = in.readSparseArray(classLoader);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeSparseArray(childrenStates);
        }

        /**
         * Required field that makes Parcelables from a Parcel.
         */
        public static final Parcelable.ClassLoaderCreator<MDKSavedState> CREATOR =
                new Parcelable.ClassLoaderCreator<MDKSavedState>() {

                    @Override
                    public MDKSavedState createFromParcel(Parcel source, ClassLoader loader) {
                        return new MDKSavedState(source, loader);
                    }

                    @Override
                    public MDKSavedState createFromParcel(Parcel source) {
                        return new MDKSavedState(source, MDKSavedState.class.getClassLoader());
                    }

                    @Override
                    public MDKSavedState[] newArray(int size) {
                        return new MDKSavedState[size];
                    }
                };
    }
}

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
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.core.behavior.HasChangeListener;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasSeekBar;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.error.MDKErrorTextView;
import com.soprasteria.movalysmdk.widget.core.error.MDKErrorWidget;
import com.soprasteria.movalysmdk.widget.core.helper.MDKAttributeSet;
import com.soprasteria.movalysmdk.widget.core.helper.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Base implementation of the rich mdk widget for widget that uses uri.
 * @param <T> the inner widget type
 */
public class MDKBaseRichSeekBarWidget<T extends MDKWidget & MDKRestorableWidget & HasValidator & HasDelegate & HasSeekBar & HasChangeListener> extends RelativeLayout implements MDKRichWidget, HasValidator, HasChangeListener, HasSeekBar {


    /**
     * Base widget.
     * Warning: business rules are included in the baseWidget, not in this class or inherited class.
     */
    private T innerWidget;

    /** the error view. */
    private MDKErrorWidget errorView;

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     */
    public MDKBaseRichSeekBarWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     * @param defStyleAttr the style
     */
    public MDKBaseRichSeekBarWidget(Context context, AttributeSet attrs, @StyleableRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Getter for the inner widget of the rich widget.
     * @return the inner widget
     */
    public T getInnerWidget()   {
        return this.innerWidget;
    }

    /**
     * Initialise rich widget.
     * @param context the context
     * @param attrs the attribute set
     * @param layoutWithLabelId the layout id for the widget with label
     * @param layoutWithoutLabelId the layout id for the widget without label
     */
    protected void init(Context context, AttributeSet attrs, @LayoutRes int layoutWithLabelId, @LayoutRes int layoutWithoutLabelId) {

        // replace the creation of the state drawable
        this.setAddStatesFromChildren(true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MDKCommons);
        // parse label attribute
        int resLabelId = typedArray.getResourceId(R.styleable.MDKCommons_label, 0);
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
                    && this.errorView instanceof MDKErrorTextView) {
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

             int selectorResId = typedArray.getResourceId(R.styleable.MDKCommons_selectors, R.array.selectors);
            String[] selectorKeys = this.getContext().getResources().getStringArray(selectorResId);
            List<String> richSelectors = new ArrayList<>();
            for (String selectorKey : selectorKeys) {
                richSelectors.add(selectorKey);
            }
            this.innerWidget.setRichSelectors(richSelectors);
        }

        // replace the creation of the state drawable
        this.setAddStatesFromChildren(true);

        // release typed array
        typedArray.recycle();

        TypedArray typedArrayCustom = this.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKSeekBarComponent);

        String maxValueStr = typedArrayCustom.getString(R.styleable.MDKCommons_MDKSeekBarComponent_maxSeekBarValue);
        if (maxValueStr != null) {
            int seekBarMaxValue = Integer.parseInt(maxValueStr);
            setSeekBarMaxValue(seekBarMaxValue);
        }

        String initalValueStr = typedArrayCustom.getString(R.styleable.MDKCommons_MDKSeekBarComponent_initialSeekBarValue);
        if (initalValueStr != null) {
            int seekBarValue = Integer.parseInt(initalValueStr);
            this.setSeekBarValue(seekBarValue);
            this.setSeekProgress(seekBarValue);

        }

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
     * Initializes the attribute map for the widget.
     * @param attrs the xml attributes
     */
    private void initAttributeMap(AttributeSet attrs) {
        if (!isInEditMode()) {
            MDKAttributeSet attributeMap = new MDKAttributeSet(attrs);
            // copy attribute from rich widget to inner widget
            this.getInnerWidget().getMDKWidgetDelegate().setAttributeMap(attributeMap);
        }
    }


    @Override
    public boolean isMandatory() {
        return this.getInnerWidget().isMandatory();
    }

    @Override
    public void setRootViewId(@IdRes int rootId) {
        this.getInnerWidget().setRootViewId(rootId);
    }

    @Override
    public void setLabelViewId(@IdRes int labelId) {
        this.getInnerWidget().setLabelViewId(labelId);
    }

    @Override
    public void setHelperViewId(@IdRes int helperId) {
        this.getInnerWidget().setHelperViewId(helperId);
    }

    @Override
    public void setErrorViewId(@IdRes int errorId) {
        this.getInnerWidget().setErrorViewId(errorId);
    }

    @Override
    public void setUseRootIdOnlyForError(boolean useRootIdOnlyForError) {
        this.getInnerWidget().setUseRootIdOnlyForError(useRootIdOnlyForError);
    }

    @Override
    public void addError(MDKMessages error) {
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
    public boolean validate(@EnumFormFieldValidator.EnumValidationMode int validationMode) {
        return this.getInnerWidget().validate(validationMode);
    }

    @Override
    public boolean validate() {
        return this.getInnerWidget().validate(EnumFormFieldValidator.VALIDATE);
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
    protected void dispatchSaveInstanceState(@NonNull SparseArray<Parcelable> container) {
        dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(@NonNull SparseArray<Parcelable> container) {
        dispatchThawSelfOnly(container);
    }

    @Override
    public void registerChangeListener(ChangeListener listener) {
        this.getInnerWidget().registerChangeListener(listener);
    }

    @Override
    public void unregisterChangeListener(ChangeListener listener) {
        this.getInnerWidget().unregisterChangeListener(listener);
    }

    /**
     * Override the default android setEnable on view and
     * call the inner component setEnable.
     * @param enabled Enable or not the view
     */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        ((View) this.getInnerWidget()).setEnabled(enabled);
    }

    @Override
    public int getSeekBarValue() {
        return this.getInnerWidget().getSeekBarValue();
    }

    @Override
    public void setSeekBarValue(int seekBarValue) {
        this.getInnerWidget().setSeekBarValue(seekBarValue);
    }

    @Override
    public int getSeekBarMaxValue() {
        return this.getInnerWidget().getSeekBarMaxValue();
    }

    @Override
    public void setSeekBarMaxValue(int seekBarMaxValue) {
        this.getInnerWidget().setSeekBarMaxValue(seekBarMaxValue);
    }

    @Override
    public void setSeekProgress(int value) {
        this.getInnerWidget().setSeekProgress(value);
    }
}

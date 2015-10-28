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
import android.support.annotation.StringRes;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessageWidget;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessagesTextView;
import com.soprasteria.movalysmdk.widget.core.helper.MDKAttributeSet;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;

import java.util.ArrayList;
import java.util.List;

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
public class MDKBaseRichWidget<T extends MDKWidget & HasValidator & HasDelegate> extends RelativeLayout implements MDKRichWidget, MDKTechnicalWidgetDelegate, HasValidator {

    /**
     * Base widget.
     * Warning: business rules are included in the baseWidget, not in this class or inherited class.
     */
    protected T innerWidget;

    /** the error view. */
    protected MDKMessageWidget errorView;
    
    /** The string resource id for the hint. */
    private int resHintId;

    /**
     * Constructor.
     * @param layoutWithLabelId layoutWithLabelId
     * @param layoutWithoutLabelId layoutWithoutLabelId
     * @param context the context
     * @param attrs attributes
     */
    public MDKBaseRichWidget(@LayoutRes int layoutWithLabelId, @LayoutRes int layoutWithoutLabelId, Context context, AttributeSet attrs) {
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
    public MDKBaseRichWidget(@LayoutRes int layoutWithLabelId, @LayoutRes int layoutWithoutLabelId, Context context, AttributeSet attrs, @StyleableRes int defStyleAttr) {
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
    private void init(Context context, AttributeSet attrs, @LayoutRes int layoutWithLabelId, @LayoutRes int layoutWithoutLabelId) {

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

            ((View)this.innerWidget).setSaveFromParentEnabled(false);

            // get label component if exists
            TextView labelView = (TextView) this.findViewById(R.id.component_label);


            if (labelView != null && resLabelId != 0) {
                labelView.setText(resLabelId);
            }

            // getting the error view
            this.errorView = (MDKMessageWidget) this.findViewById(R.id.component_error);
            if (resHelperId != 0
                    && this.errorView != null
                    && this.errorView instanceof MDKMessagesTextView) {
                ((MDKMessagesTextView) this.errorView).setHelper(context, context.getString(resHelperId));
            }

            // parse others attributes
            int errorId = typedArray.getResourceId(R.styleable.MDKCommons_errorId, 0);
            // should override the inner widget only if it has a value
            if (errorId != 0) {
                this.innerWidget.getMDKWidgetDelegate().setErrorViewId(errorId);
            }

            boolean mandatory = typedArray.getBoolean(R.styleable.MDKCommons_mandatory, false);
            this.getInnerWidget().setMandatory(mandatory);

            boolean editable = typedArray.getBoolean(R.styleable.MDKCommons_editable, true);
            this.setEditable(editable);

            int selectorResId = typedArray.getResourceId(R.styleable.MDKCommons_selectors, R.array.selectors);
            String[] selectorKeys = this.getContext().getResources().getStringArray(selectorResId);
            List<String> richSelectors = new ArrayList<>();
            for (String selectorKey : selectorKeys) {
                richSelectors.add(selectorKey);
            }
            this.innerWidget.getMDKWidgetDelegate().setRichSelectors(richSelectors);
        }

        // replace the creation of the state drawable
        this.setAddStatesFromChildren(true);

        // release typed array
        typedArray.recycle();

        initAttributeMap(attrs);

    }

    /**
     * Override the default android setEnable on view and call the inner component setEnable.
     * We disable the component in order to have the opportunity to add a selector on a rich component.
     * @param enabled Enable or not the view
     */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        ((View) this.getInnerWidget()).setEnabled(enabled);
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
            this.getInnerWidget().getMDKWidgetDelegate().setAttributeMap(attributeMap);
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
    @StringRes public int getResHintId() {
        return this.resHintId;
    }

    @Override
    public boolean isMandatory() {
        return this.getInnerWidget().isMandatory();
    }

    @Override
    public void setLabelViewId(@IdRes int labelId) {
        this.getInnerWidget().getMDKWidgetDelegate().setLabelViewId(labelId);
    }

    @Override
    public void setHelperViewId(@IdRes int helperId) {
        this.getInnerWidget().getMDKWidgetDelegate().setHelperViewId(helperId);
    }

    @Override
    public void setErrorViewId(@IdRes int errorId) {
        this.getInnerWidget().getMDKWidgetDelegate().setErrorViewId(errorId);
    }

    @Override
    public void addError(MDKMessages error) {
        this.getInnerWidget().addError(error);
    }

    @Override
    public void setError(CharSequence error) {
        if (error != null && error.length() > 0) {
            this.getInnerWidget().setError(error);
        } else {
            this.getInnerWidget().setError(null);
        }
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
    public Object getValueToValidate() {
        return this.getInnerWidget().getValueToValidate();
    }

    @Override
    public MDKTechnicalWidgetDelegate getTechnicalWidgetDelegate() {
        return this;
    }

    @Override
    public void setMandatory(boolean mandatory) {
        this.getInnerWidget().setMandatory(mandatory);
    }

    @Override
    public void setEditable(boolean editable) {
        this.getInnerWidget().setEditable(editable);
    }

    @Override
    public boolean isEditable() {
        return this.getInnerWidget().isEditable();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        MDKSavedState ss = new MDKSavedState(superState);
        ss.childrenStates = new SparseArray();
        saveAll(this, ss.childrenStates);
        return ss;
    }

    /**
     * Save all subviews states on rich widget.
     * @param viewGroup the ViewGroup to save
     * @param states the state so save
     */
    private void saveAll(ViewGroup viewGroup, SparseArray states) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup && !(child instanceof MDKWidget)) {
                saveAll((ViewGroup) child, states);
            } else {
                child.saveHierarchyState(states);
            }
        }
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        MDKSavedState ss = (MDKSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        restoreAll(this, ss.childrenStates);
    }

    /**
     * Restore all subviews states on rich widget.
     * @param viewGroup the ViewGroup to restore
     * @param state the state to restore
     */
    private void restoreAll(ViewGroup viewGroup, SparseArray state) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup && !(child instanceof MDKWidget)) {
                restoreAll((ViewGroup) child, state);
            } else {
                child.restoreHierarchyState(state);
            }
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
}

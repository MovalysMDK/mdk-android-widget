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
package com.soprasteria.movalysmdk.widget.basic;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;

import com.soprasteria.movalysmdk.widget.core.MDKRestorableWidget;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasChangeListener;
import com.soprasteria.movalysmdk.widget.core.behavior.HasChecked;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasLabel;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKChangeListenerDelegate;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.helper.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;

import java.util.List;

/**
 * <p>Represents a Check Box conforming to the Material Design guidelines.</p>
 */
public class MDKCheckBox extends AppCompatCheckBox implements MDKWidget, MDKRestorableWidget, HasValidator, HasLabel, HasChecked, HasDelegate, HasChangeListener {

    /** The MDKWidgetDelegate handling the component logic. */
    protected MDKWidgetDelegate mdkWidgetDelegate;

    /** listeners delegate */
    protected MDKChangeListenerDelegate mdkListenerDelegate;

    /**
     * Constructor.
     * @param context the context
     * @param attrs the xml attributes
     */
    public MDKCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs the xml attributes
     * @param defStyleAttr the style attribute
     */
    public MDKCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Instantiate the MDKWidgetDelegate.
     * @param context the context
     * @param attrs attributes set
     */
    private final void init(Context context, AttributeSet attrs) {
        // Parse the MDKCommons:label attribute
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MDKCommons);
        int resLabelId = typedArray.getResourceId(R.styleable.MDKCommons_label, 0);
        if (resLabelId != 0) {
            this.setText(resLabelId);
        }
        typedArray.recycle();

        this.mdkWidgetDelegate = new MDKWidgetDelegate(this, attrs);

        this.mdkListenerDelegate = new MDKChangeListenerDelegate();
    }

    @Override
    public MDKWidgetDelegate getMDKWidgetDelegate() {
        return this.mdkWidgetDelegate;
    }

    @Override
    public CharSequence getLabel() {
        return this.getText();
    }

    @Override
    public void setLabel(CharSequence label) {
        this.setText(label);
    }

    @Override
    public int[] getValidators() {
        return new int[] { R.string.mdkwidget_checkable_validator_class };
    }

    @Override
    public boolean validate() {
        return this.getMDKWidgetDelegate().validate(true, EnumFormFieldValidator.VALIDATE);
    }

    @Override
    public boolean validate(@EnumFormFieldValidator.EnumValidationMode int validationMode) {
        return this.getMDKWidgetDelegate().validate(true, validationMode);
    }

    @Override
    public void addError(MDKMessages error) {
        this.mdkWidgetDelegate.addError(error);
    }

    @Override
    public void clearError() {
        this.mdkWidgetDelegate.clearError();
    }

    @Override
    public void setUniqueId(int parentId) {
        this.mdkWidgetDelegate.setUniqueId(parentId);
    }

    @Override
    public int getUniqueId() {
        return this.mdkWidgetDelegate.getUniqueId();
    }

    @Override
    public int[] superOnCreateDrawableState(int extraSpace) {
        return super.onCreateDrawableState(extraSpace);
    }

    @Override
    public void callMergeDrawableStates(int[] baseState, int[] additionalState) {
        mergeDrawableStates(baseState, additionalState);
    }

    @Override
    public void setRichSelectors(List<String> richSelectors) {
        this.getMDKWidgetDelegate().setRichSelectors(richSelectors);
    }

    @Override
    public void setRootViewId(int rootId) {
        this.mdkWidgetDelegate.setRootViewId(rootId);
    }

    @Override
    public void setLabelViewId( @IdRes int labelId) {
        this.mdkWidgetDelegate.setLabelViewId(labelId);
    }

    @Override
    public void setHelperViewId( @IdRes int helperId) {
        this.mdkWidgetDelegate.setHelperViewId(helperId);
    }

    @Override
    public void setErrorViewId( @IdRes int errorId) {
        this.mdkWidgetDelegate.setErrorViewId(errorId);
    }

    @Override
    public void setUseRootIdOnlyForError(boolean useRootIdOnlyForError) {
        this.mdkWidgetDelegate.setUseRootIdOnlyForError(useRootIdOnlyForError);
    }

    @Override
    public void setMandatory(boolean mandatory) {
        this.mdkWidgetDelegate.setMandatory(mandatory);
    }

    @Override
    public boolean isMandatory() {
        return this.mdkWidgetDelegate.isMandatory();
    }

    @Override
    public Parcelable superOnSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    public void superOnRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);

        if (this.mdkListenerDelegate != null) {
            this.mdkListenerDelegate.notifyListeners();
        }
    }

    @Override
    public void registerChangeListener(ChangeListener listener) {
        this.mdkListenerDelegate.registerChangeListener(listener);
    }

    @Override
    public void unregisterChangeListener(ChangeListener listener) {
        this.mdkListenerDelegate.unregisterChangeListener(listener);
    }
}

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
import android.util.AttributeSet;
import android.widget.Switch;

import com.soprasteria.movalysmdk.widget.basic.delegate.MDKCheckableWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKTechnicalInnerWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKTechnicalWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasChangeListener;
import com.soprasteria.movalysmdk.widget.core.behavior.HasChecked;
import com.soprasteria.movalysmdk.widget.core.behavior.HasCheckedTexts;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasLabel;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.helper.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;

/**
 * <p>Represents a Switch conforming to the Material Design guidelines.</p>
 */
public class MDKSwitch extends Switch implements MDKWidget, HasValidator, HasLabel, HasChecked, HasCheckedTexts, HasDelegate, HasChangeListener {

    /** The MDKCheckableWidgetDelegate handling the component logic. */
    protected MDKCheckableWidgetDelegate mdkWidgetDelegate;

    /**
     * Constructor.
     * @param context the context
     * @param attrs the xml attributes
     */
    public MDKSwitch(Context context, AttributeSet attrs) {
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
    public MDKSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
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
        this.mdkWidgetDelegate = new MDKCheckableWidgetDelegate(this, attrs);
    }

    @Override
    public int[] getValidators() {
        return this.mdkWidgetDelegate.getValidators();
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        if (this.mdkWidgetDelegate != null) {
            this.mdkWidgetDelegate.doOnChecked();
        }
    }

    /* technical delegate methods */

    @Override
    public MDKTechnicalWidgetDelegate getTechnicalWidgetDelegate() {
        return this.mdkWidgetDelegate;
    }

    @Override
    public MDKTechnicalInnerWidgetDelegate getTechnicalInnerWidgetDelegate() {
        return this.mdkWidgetDelegate;
    }

    @Override
    public MDKWidgetDelegate getMDKWidgetDelegate() {
        return this.mdkWidgetDelegate;
    }

    /* rich selector methods */

    @Override
    public int[] superOnCreateDrawableState(int extraSpace) {
        return super.onCreateDrawableState(extraSpace);
    }

    @Override
    public void callMergeDrawableStates(int[] baseState, int[] additionalState) {
        mergeDrawableStates(baseState, additionalState);
    }

    /* delegate accelerator methods */

    @Override
    public void setMandatory(boolean mandatory) {
        this.mdkWidgetDelegate.setMandatory(mandatory);
    }

    @Override
    public boolean isMandatory() {
        return this.mdkWidgetDelegate.isMandatory();
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
    public CharSequence getLabel() {
        return this.getText();
    }

    @Override
    public void setLabel(CharSequence label) {
        this.setText(label);
    }

    @Override
    public boolean validate() {
        return this.mdkWidgetDelegate.validate(true, EnumFormFieldValidator.VALIDATE);
    }

    @Override
    public boolean validate(@EnumFormFieldValidator.EnumValidationMode int validationMode) {
        return this.mdkWidgetDelegate.validate(true, validationMode);
    }

    @Override
    public void registerChangeListener(ChangeListener listener) {
        this.mdkWidgetDelegate.registerChangeListener(listener);
    }

    @Override
    public void unregisterChangeListener(ChangeListener listener) {
        this.mdkWidgetDelegate.unregisterChangeListener(listener);
    }

    @Override
    public String getFixedText() {
        return mdkWidgetDelegate.getFixedText();
    }

    @Override
    public void setFixedText(String text) {
        mdkWidgetDelegate.setFixedText(text);
    }

    @Override
    public String getCheckedText() {
        return mdkWidgetDelegate.getCheckedText();
    }

    @Override
    public void setCheckedText(String text) {
        mdkWidgetDelegate.setCheckedText(text);
    }

    @Override
    public String getUncheckedText() {
        return mdkWidgetDelegate.getUncheckedText();
    }

    @Override
    public void setUncheckedText(String text) {
        mdkWidgetDelegate.setUncheckedText(text);
    }
}

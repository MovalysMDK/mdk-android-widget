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
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.soprasteria.movalysmdk.widget.core.MDKTechnicalInnerWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKTechnicalWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasChangeListener;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasLabel;
import com.soprasteria.movalysmdk.widget.core.behavior.HasSeekBar;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKChangeListenerDelegate;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.exception.MDKWidgetException;
import com.soprasteria.movalysmdk.widget.core.helper.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;

/**
 * MDK SeekBar.
 * <p>Representing a slidable bar to select values.</p>
 * <p>Validation is done each time the seek bar value change</p>
 * <p>
 *     Due to SeekBar listener visibility, this class implement OnSeekBarChangeListener
 *     in order to manage user actions. It is impossible for the developer to set an
 *     OnSeekBarChangeListener on MDKSeekBar without throwing an exception. This is done in
 *     order to protect the MDK widget behaviour.
 * </p>
 */
public class MDKSeekBar extends SeekBar implements OnSeekBarChangeListener, MDKWidget, HasValidator, HasLabel, HasDelegate, HasChangeListener, HasSeekBar, TextWatcher{

    /** MDK Widget implementation. */
    private MDKWidgetDelegate mdkWidgetDelegate;

    /** listeners delegate. */
    protected MDKChangeListenerDelegate mdkListenerDelegate;

    /** Seek bar value from widget.*/
    private Integer seekBarValue;

    /** Maximum seek bar value from widget.*/
    private Integer seekBarMaxValue;

    /** Seekbar value field **/
    @IdRes private int seekbarEditTextId;
    private EditText seekbarEditText;

    /**
     * Constructor.
     * @param context the context
     * @param attrs the xml attributes
     */
    public MDKSeekBar(Context context, AttributeSet attrs) {
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
    public MDKSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
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
    private void init(Context context, AttributeSet attrs) {

        super.setOnSeekBarChangeListener(this);
        this.mdkListenerDelegate = new MDKChangeListenerDelegate();

        this.mdkWidgetDelegate = new MDKWidgetDelegate(this, attrs);

        TypedArray typedArray = this.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKSeekBarComponent);

        this.seekbarEditTextId = typedArray.getResourceId(R.styleable.MDKCommons_MDKSeekBarComponent_attachedEditText, 0);

        String maxStr = typedArray.getString(R.styleable.MDKCommons_MDKSeekBarComponent_seekbar_max);
        if (maxStr != null) {
            int max = Integer.parseInt(maxStr);
            this.setMax(max);
        }

        String maxValueStr = typedArray.getString(R.styleable.MDKCommons_MDKSeekBarComponent_maxSeekBarValue);
        if (maxValueStr != null) {
            this.seekBarMaxValue = Integer.parseInt(maxValueStr);
        }

        String initialValueStr = typedArray.getString(R.styleable.MDKCommons_MDKSeekBarComponent_initialSeekBarValue);
        if (initialValueStr != null) {
            seekBarValue = Integer.parseInt(initialValueStr);
            setSeekProgress(seekBarValue);
        }else{
            seekBarValue=0;
        }

        typedArray.recycle();
    }

    @Override
    public void onAttachedToWindow(){
        super.onAttachedToWindow();

        //finding edittext & initializing once
        if (seekbarEditTextId!=0 && seekbarEditText == null && mdkWidgetDelegate != null) {
            seekbarEditText = (EditText) mdkWidgetDelegate.reverseFindViewById(seekbarEditTextId);

            if (seekbarEditText != null) {

                //init edittext
                seekbarEditText.addTextChangedListener(this);
                InputFilter[] FilterArray = new InputFilter[1];
                FilterArray[0] = new InputFilter.LengthFilter((int) Math.floor(Math.log(getMax())) - 1);
                seekbarEditText.setFilters(FilterArray);
                setAttachedEditTextValue(seekBarValue);

                //growing edittext width if needed
                if (getMax() > getResources().getInteger(R.integer.mdkwidget_seekbar_edittext_max_value_before_resize))
                    seekbarEditText.getLayoutParams().width += (Math.floor(Math.log(getMax())) - 2) * getResources().getDimension(R.dimen.mdkwidget_seekbar_edittext_incremental_width);
            }
        }
    }

    private void setAttachedEditTextValue(int value){
        if (seekbarEditText != null) {
            seekbarEditText.removeTextChangedListener(this);
            seekbarEditText.setText(String.valueOf(value));
            seekbarEditText.addTextChangedListener(this);
        }
    }

    @Override
    public int[] getValidators() {
        return new int[]
                {R.string.mdkvalidator_seekbar_class};
    }

    @Override
    public int getSeekBarValue() {
        return seekBarValue;
    }

    @Override
    public void setSeekBarValue(int seekBarValue) {
        this.seekBarValue = seekBarValue;
        if (this.getMDKWidgetDelegate() != null && !isInEditMode() ) {
            this.validate(EnumFormFieldValidator.ON_USER);
        }
    }

    @Override
    public int getSeekBarMaxValue() {
        return seekBarMaxValue;
    }

    @Override
    public void setSeekBarMaxValue(int seekBarMaxValue) {
        this.seekBarMaxValue = seekBarMaxValue;
    }

    @Override
    public void setSeekProgress(int value) {
        super.setProgress(value);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        this.setSeekBarValue(progress);
        if (this.mdkListenerDelegate != null) {
            this.mdkListenerDelegate.notifyListeners();
        }

        //updating edittext value
        setAttachedEditTextValue(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // Nothing to do
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // Nothing to do
    }

    /**
     * This method should not be called in a project, it would destroy the logic of the component.
     * Use {@link MDKSeekBar#registerChangeListener} instead
     * @param listener the listener to register
     */
    @Override
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener listener){
        throw new MDKWidgetException(
                getContext().getString(R.string.mdkwidget_seekbar_listener_exception)
                        + MDKSeekBar.class);
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
        if (this.getMDKWidgetDelegate() != null) {
            return this.getMDKWidgetDelegate().superOnCreateDrawableState(extraSpace);
        } else {
            // first called in the super constructor
            return super.onCreateDrawableState(extraSpace);
        }
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
        // Widget control left to the integrator
        return this.mdkWidgetDelegate.isMandatory();
    }

    @Override
    public void setError(CharSequence error) {
        this.mdkWidgetDelegate.setError(error);
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
        return this.mdkWidgetDelegate.getLabel();
    }

    @Override
    public void setLabel(CharSequence label) {
        this.mdkWidgetDelegate.setLabel(label);
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
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if(seekbarEditText!=null){
            seekbarEditText.setEnabled(enabled);
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        super.setOnSeekBarChangeListener(null);

        int value;

        if(s.length()>0){
            value = Integer.parseInt(s.toString());

            if(value>super.getMax()){
                s.clear();
                s.append(String.valueOf(super.getMax()));
            }

        }else{
            value = 0;
        }
        setSeekProgress(value);

        super.setOnSeekBarChangeListener(this);
    }
}

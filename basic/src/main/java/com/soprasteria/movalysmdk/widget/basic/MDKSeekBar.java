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
import android.support.v7.widget.AppCompatSeekBar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.basic.formatter.SeekbarDefaultFormatter;
import com.soprasteria.movalysmdk.widget.core.MDKTechnicalInnerWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKTechnicalWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasChangeListener;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasFormatter;
import com.soprasteria.movalysmdk.widget.core.behavior.HasLabel;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.behavior.types.HasSeekBar;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKChangeListenerDelegate;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.exception.MDKWidgetException;
import com.soprasteria.movalysmdk.widget.core.formatter.MDKBaseFormatter;
import com.soprasteria.movalysmdk.widget.core.helper.AttributesHelper;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessages;
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
public class MDKSeekBar extends AppCompatSeekBar implements OnSeekBarChangeListener, MDKWidget, HasFormatter<Integer, String>, HasValidator, HasLabel, HasDelegate, HasChangeListener, HasSeekBar, View.OnFocusChangeListener, TextView.OnEditorActionListener, TextWatcher, View.OnKeyListener{

    /** Log tag.*/
    public static final String LOG_TAG = "MDKRichSeekBar";

    /** MDK Widget implementation. */
    private MDKWidgetDelegate mdkWidgetDelegate;

    /** listeners delegate. */
    protected MDKChangeListenerDelegate mdkListenerDelegate;

    /** Seek bar value from widget.*/
    private Integer seekBarValue;

    /** Maximum allowed seek bar value from widget.*/
    private Integer seekBarMaxAllowed;

    /** Minimum allowed seek bar value from widget.*/
    private Integer seekBarMinAllowed;

    /** Seekbar value field. **/
    @IdRes private int seekbarEditTextId;
    /** Linked EditText. */
    private EditText seekbarEditText;

    /** Seekbar minimum, doesn't exist in original widget. **/
    private int min;

    /** readonly property of the attached edittext. **/
    private boolean readonlyEditText;

    /** Formatter to convert seekbar value to and from displayed value. **/
    private MDKBaseFormatter<Integer, String> formatter;

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
    @SuppressWarnings("unchecked")
    private void init(Context context, AttributeSet attrs) {

        super.setOnSeekBarChangeListener(this);
        this.mdkListenerDelegate = new MDKChangeListenerDelegate();
        this.mdkWidgetDelegate = new MDKWidgetDelegate(this, attrs);

        TypedArray typedArray = this.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons);
        TypedArray typedArrayComponent = this.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKSeekBarComponent);

        this.seekbarEditTextId = typedArrayComponent.getResourceId(R.styleable.MDKCommons_MDKSeekBarComponent_attachedEditText, 0);

        String readonlyStr = typedArrayComponent.getString(R.styleable.MDKCommons_MDKSeekBarComponent_readonlyEditText);
        setReadonlyEditText(readonlyStr != null && Boolean.parseBoolean(readonlyStr));

        int formatterResourceId = typedArray.getResourceId(R.styleable.MDKCommons_formatter,0);
        String formatterStr = formatterResourceId!=0?getResources().getString(formatterResourceId):null;
        if (formatterStr != null) {
            try {
                this.formatter = (MDKBaseFormatter<Integer, String>) Class.forName(formatterStr).newInstance();
            } catch (Exception e) {
                Log.e(LOG_TAG,"Unknown formatter class", e);
                this.formatter = new SeekbarDefaultFormatter();
            }
        }else{
            this.formatter = new SeekbarDefaultFormatter();
        }

        setSeekBarMinAllowed(AttributesHelper.getIntFromStringAttribute(typedArrayComponent, R.styleable.MDKCommons_MDKSeekBarComponent_min_allowed, 0));

        setSeekBarMaxAllowed(AttributesHelper.getIntFromStringAttribute(typedArrayComponent, R.styleable.MDKCommons_MDKSeekBarComponent_max_allowed, 100));

        setMin(AttributesHelper.getIntFromStringAttribute(typedArrayComponent, R.styleable.MDKCommons_MDKSeekBarComponent_seekbar_min, getSeekBarMinAllowed()));

        setMax(AttributesHelper.getIntFromStringAttribute(typedArrayComponent, R.styleable.MDKCommons_MDKSeekBarComponent_seekbar_max, getSeekBarMaxAllowed()));

        setSeekProgress(AttributesHelper.getIntFromStringAttribute(typedArrayComponent, R.styleable.MDKCommons_MDKSeekBarComponent_initialSeekBarValue, getSeekBarMinAllowed()));


        typedArray.recycle();
        typedArrayComponent.recycle();
    }



    @Override
    public void onAttachedToWindow(){
        super.onAttachedToWindow();

        //finding edittext & initializing once
        if (seekbarEditTextId!=0 && seekbarEditText == null && mdkWidgetDelegate != null) {
            seekbarEditText = (EditText) mdkWidgetDelegate.reverseFindViewById(seekbarEditTextId);

            if (seekbarEditText != null) {

                initEditText();

            }
        }

        if (this.getMDKWidgetDelegate() != null ) {
            this.validate();
        }
    }


    /**
     * Validates the edittext and updates the seekbar value.
     * @param s the text of the edittext to validate.
     */
    private void validateEditText(Editable s) {

        super.setOnSeekBarChangeListener(null);

        if(s.length()>0){
            try {
                int value = formatter.unformat(s.toString());

                if(value<this.getMin()){
                    s.clear();
                    s.append(formatter.format(this.getMin()));
                    setSeekProgress(this.getMin());
                }else if(value>this.getMax()){
                    s.clear();
                    s.append(formatter.format(this.getMax()));
                    setSeekProgress(this.getMax());
                }else{
                    setSeekProgress(value);
                }

            }catch(NumberFormatException e){
                s.clear();
                s.append(formatter.format(this.getMax()));
                setSeekProgress(this.getMax());
            }
        }else{
            s.clear();
            s.append(formatter.format(this.getMin()));
            setSeekProgress(this.getMin());
        }


        if (this.getMDKWidgetDelegate() != null && !isInEditMode() ) {
            this.validate(EnumFormFieldValidator.ON_USER);
        }

        super.setOnSeekBarChangeListener(this);
    }

    /**
     * Initializes the attached edittext.
     */
    private void initEditText(){
        //init edittext
        seekbarEditText.addTextChangedListener(this);
        seekbarEditText.setOnFocusChangeListener(this);
        seekbarEditText.setOnKeyListener(this);
        seekbarEditText.setOnEditorActionListener(this);
        seekbarEditText.setEnabled(this.isEnabled());
        setAttachedEditTextValue(seekBarValue);

        seekbarEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

        if(readonlyEditText || isReadonly()) {
            toggleReadonlyEditText(true);
        }

        //grows the edittext automatically
        if((formatter.format(getMax()).length()) * getResources().getDimensionPixelSize(R.dimen.mdkwidget_seekbar_edittext_incremental_width) > seekbarEditText.getLayoutParams().width) {
            seekbarEditText.getLayoutParams().width = formatter.format(getMax()).length() * getResources().getDimensionPixelSize(R.dimen.mdkwidget_seekbar_edittext_incremental_width);
        }
    }

    /**
     * Formats the value and displays it in the attached edittext.
     * @param value the value to set
     */
    private void setAttachedEditTextValue(int value){
        if (seekbarEditText != null) {
            seekbarEditText.removeTextChangedListener(this);
            seekbarEditText.setText(formatter.format(value));
            seekbarEditText.addTextChangedListener(this);
        }
    }

    @Override
    public MDKBaseFormatter<Integer, String> getFormatter(){
        return formatter;
    }

    @Override
    public void setFormatter(MDKBaseFormatter<Integer, String> newFormatter){
        this.formatter = newFormatter;
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

    /**
     * Set the SeekBar's value.
     * @param seekBarValue value to set
     */
    private void setSeekBarValue(int seekBarValue) {
        this.seekBarValue = seekBarValue;

        if (this.mdkListenerDelegate != null) {
            this.mdkListenerDelegate.notifyListeners();
        }

    }

    @Override
    public int getSeekBarMinAllowed() {
        return seekBarMinAllowed;
    }

    @Override
    public void setSeekBarMinAllowed(int seekBarMinAllowed) {
        this.seekBarMinAllowed = seekBarMinAllowed;
    }


    @Override
    public int getSeekBarMaxAllowed() {
        return seekBarMaxAllowed;
    }

    @Override
    public void setSeekBarMaxAllowed(int seekBarMaxAllowed) {
        this.seekBarMaxAllowed = seekBarMaxAllowed;
    }

    @Override
    public void setSeekProgress(int val) {

        int value=val;

        if(val<min) {
            value = min;
        }

        this.setSeekBarValue(value);

        //updating seekbar thumb position with proper scaling
        if((getMax()-getMin())!=0) {
            this.setProgress(Math.round(((float) value - min) / (getMax() - min) * getMax()));
        }
    }

    @Override
    public EditText getAttachedEditText() {
        return seekbarEditText;
    }

    @Override
    public void setAttachedEditText(EditText attachedEditText) {

        //unbinding previous edittext
        if(seekbarEditText!=null) {
            seekbarEditText.removeTextChangedListener(this);
            seekbarEditText.setOnFocusChangeListener(null);
        }

        seekbarEditText = attachedEditText;
        initEditText();
    }

    @Override
    public boolean isReadonlyEditText() {
        return seekbarEditText.getInputType() == InputType.TYPE_NULL;
    }

    @Override
    public void setReadonlyEditText(boolean readonly) {
        readonlyEditText =readonly;
        toggleReadonlyEditText(readonly);
    }

    /**
     * Toggles the readonly state of the attached edit text.
     * @param readonly the readonly state
     */
    private void toggleReadonlyEditText(boolean readonly){
        if(seekbarEditText!=null) {
            if (readonly) {
                seekbarEditText.setInputType(InputType.TYPE_NULL);
                seekbarEditText.setFocusable(false);
            } else {
                seekbarEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                seekbarEditText.setFocusableInTouchMode(true);
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        float valFloat=min;

        //updating seekbar value with proper scaling
        if(getMax()!=0) {
            valFloat = min + ((float) progress / getMax()) * (getMax() - min);
        }

        this.setSeekBarValue(Math.round(valFloat));

        if (this.getMDKWidgetDelegate() != null && !isInEditMode() ) {
            this.validate(EnumFormFieldValidator.ON_USER);
        }

        //updating edittext value
        setAttachedEditTextValue(seekBarValue);
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
    public void setReadonly(boolean readonly) {
        mdkWidgetDelegate.setReadonly(readonly);
        if(readonly) {
            toggleReadonlyEditText(readonlyEditText);
        }else{
            toggleReadonlyEditText(false);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return !isReadonly() && super.onTouchEvent(event);
    }

    @Override
    public boolean isReadonly() {
        return mdkWidgetDelegate.isReadonly();
    }

    @Override
    public void setMin(int min) {
        this.min = min;
    }

    @Override
    public int getMin() {
        return min;
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
    public Object getValueToValidate() {
        return getSeekBarValue();
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

    /** attached EditText listener methods. **/

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus) {
            validateEditText(((EditText) v).getText());
            setAttachedEditTextValue(seekBarValue);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {

            validateEditText(((EditText) v).getText());
            setAttachedEditTextValue(seekBarValue);
            return true;
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //No need for that
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //No need for that
    }

    @Override
    public void afterTextChanged(Editable s) {

        if(s.length()>0) {
            try {
                int value = formatter.unformat(s.toString());
                if(value>=this.getMin() && value<=this.getMax()) {
                    validateEditText(s);
                }
            }catch(NumberFormatException ignored){
                //Nothing to do here, see validateEditText()
            }
        }

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        // If the event is a key-down event on the "enter" button
        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)) {
            // Perform action on key press
            validateEditText(((EditText) v).getText());
            setAttachedEditTextValue(seekBarValue);
            return true;
        }
        return false;
    }

    /* save / restore */

    @Override
    public Parcelable onSaveInstanceState() {
        // Save the android view instance state
        Parcelable state = super.onSaveInstanceState();
        // Save the MDKWidgetDelegate instance state
        state = this.mdkWidgetDelegate.onSaveInstanceState(state);

        return state;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        // Restore the MDKWidgetDelegate instance state
        Parcelable innerState = this.mdkWidgetDelegate.onRestoreInstanceState(this, state);

        // Restore the android view instance state
        super.onRestoreInstanceState(innerState);
    }
}

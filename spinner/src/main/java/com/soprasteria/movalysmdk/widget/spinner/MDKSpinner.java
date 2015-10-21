package com.soprasteria.movalysmdk.widget.spinner;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;

import com.example.spinner.R;
import com.soprasteria.movalysmdk.widget.core.MDKTechnicalInnerWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKTechnicalWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasHint;
import com.soprasteria.movalysmdk.widget.core.behavior.HasLabel;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.behavior.types.IsNullable;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;

import java.util.Arrays;

/**
 * MDK Spinner.
 * <p>Represents a Spinner.</p>
 * <p>This spinner could add a blank row to any of your adapter.</p>
 * <p>For blank row add mdk:has_blank_row="true" to your XML attrs.</p>
 * <p>The mdk:has_blank_row default value is false.</p>
 */
public class MDKSpinner extends AppCompatSpinner implements MDKWidget, HasValidator, HasDelegate, AdapterView.OnItemSelectedListener, IsNullable, HasLabel, HasHint {
    /**
     * User's adapter.
     */
    private MDKWrapperAdapter innerAdapter;
    /**
     * The hint for the spinner view.
     */
    private CharSequence hint;
    /**
     * The MDKWidgetDelegate handling the component logic.
     */
    protected MDKWidgetDelegate mdkWidgetDelegate;
    /**
     * Widget specific validators.
     */
    protected int[] specificValidators;

    /**
     * Value of the selected item in dropDown spinner.
     */
    protected Object valueToValidate;

    /**
     * Boolean to test if blank row is required (True for Blank row).
     */
    private boolean hasBlank;

    /**
     * ExternalListener.
     */
    private OnItemSelectedListener externalListener;

    /**
     * Constructor.
     *
     * @param context      the context
     * @param attrs        attributes set
     * @param defStyleAttr spinner defStyleAttr
     * @param mode         spinner mode
     */
    public MDKSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
        if (!isInEditMode()) {
            init(attrs);
        }
    }

    /**
     * Constructor.
     *
     * @param context the context
     * @param attrs   attributes set
     */
    public MDKSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(attrs);
        }
    }

    /**
     * Instantiate the MDKWidgetDelegate and get XML attrs.
     * Called by the constructor.
     *
     * @param attrs attributes set
     */
    private final void init(AttributeSet attrs) {
        TypedArray typedArray = this.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons);
        TypedArray typedArrayComponent = this.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKSpinnerComponent);

        this.hasBlank = typedArrayComponent.getBoolean(R.styleable.MDKCommons_MDKSpinnerComponent_has_blank_row, false);
        this.hint = typedArray.getString(R.styleable.MDKCommons_hint);

        this.mdkWidgetDelegate = new MDKWidgetDelegate(this, attrs);
        this.setValueToValidate(0);
        super.setOnItemSelectedListener(this);

        typedArray.recycle();
        typedArrayComponent.recycle();
    }

    /**
     * Sets hasBlank value.
     *
     * @param spinnerBlankValue boolean that represent if blankRow is needed
     */
    public void setSpinnerHasBlankRow(boolean spinnerBlankValue) {
        this.hasBlank = spinnerBlankValue;
    }

    /**
     * Sets the data behind this ListView with the user's adapter.
     *
     * @param adapter user's adapter.
     */
    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        this.innerAdapter = new MDKWrapperAdapter(adapter, this.hasBlank, this.hint);
        super.setAdapter(this.innerAdapter);
    }

    /**
     * Sets the data behind this ListView with the user's adapter and allow to use a same custom layout for dropDownBlankView and spinnerBlankView.
     *
     * @param adapter     user's adapter.
     * @param blankLayout layout for dropDownBlankView and spinnerBlankView
     */
    public void setAdapterWithCustomBlankLayout(SpinnerAdapter adapter, int blankLayout) {
        this.innerAdapter = new MDKWrapperAdapter(adapter, this.hasBlank, blankLayout, blankLayout, this.hint);
        super.setAdapter(this.innerAdapter);
    }

    /**
     * Sets the data behind this ListView with the user's adapter and allow to use a custom layout for dropDownBlankView and spinnerBlankView.
     * Example : to have an hint you can have an hint layout for spinnerBlankLayout and a layout without dimensions for dropDownBlankLayout.
     *
     * @param adapter             user's adapter.
     * @param spinnerBlankLayout  layout for spinnerBlankView
     * @param dropDownBlankLayout layout for dropDownBlankView
     */
    public void setAdapterSpinnerDropDownBlankLayout(SpinnerAdapter adapter, int spinnerBlankLayout, int dropDownBlankLayout) {
        this.innerAdapter = new MDKWrapperAdapter(adapter, this.hasBlank, spinnerBlankLayout, dropDownBlankLayout, this.hint);
        super.setAdapter(this.innerAdapter);
    }

    /**
     * Set the value for valueToValidate.
     *
     * @param position position in the adapter
     */
    private void setValueToValidate(int position) {
        this.valueToValidate = this.getItemAtPosition(position);
    }

    @Override
    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.externalListener = listener;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (this.externalListener != null) {
            this.externalListener.onItemSelected(parent, view, position, id);
        }

        this.setValueToValidate(position);
        this.validate(EnumFormFieldValidator.ON_FOCUS);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        if (this.externalListener != null) {
            this.externalListener.onNothingSelected(parent);
        }
        this.validate(EnumFormFieldValidator.ON_FOCUS);
    }


    @Override
    public int[] getValidators() {

        int[] basicValidators = {R.string.mdkvalidator_mandatory_class};
        int[] validators;

        if (this.specificValidators != null && this.specificValidators.length > 0) {
            validators = Arrays.copyOf(basicValidators, basicValidators.length + this.specificValidators.length);

            System.arraycopy(this.specificValidators, 0, validators, basicValidators.length, this.specificValidators.length);
        } else {
            validators = basicValidators;
        }

        return validators;
    }

    @Override
    public CharSequence getHint() {
        if (this.innerAdapter != null) {
            this.hint = this.innerAdapter.getHint();
        }
        return this.hint;

    }

    @Override
    public void setHint(CharSequence hint) {
        if (this.innerAdapter != null) {
            this.innerAdapter.setHint(hint);
        }
        this.hint = hint;
    }

    /* technical delegate methods */
    @Override
    public MDKTechnicalInnerWidgetDelegate getTechnicalInnerWidgetDelegate() {
        return this.mdkWidgetDelegate.getTechnicalInnerWidgetDelegate();
    }

    @Override
    public MDKTechnicalWidgetDelegate getTechnicalWidgetDelegate() {
        return this.mdkWidgetDelegate.getTechnicalWidgetDelegate();
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

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        if (this.getMDKWidgetDelegate() != null) {
            return this.getMDKWidgetDelegate().superOnCreateDrawableState(extraSpace);
        } else {
            // first called in the super constructor
            return super.onCreateDrawableState(extraSpace);
        }
    }
    /* delegate accelerator methods */

    @Override
    public boolean isMandatory() {

        return this.mdkWidgetDelegate.isMandatory();
    }

    @Override
    public void setMandatory(boolean mandatory) {

        this.mdkWidgetDelegate.setMandatory(mandatory);
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
    public Object getValueToValidate() {
        return this.valueToValidate;
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

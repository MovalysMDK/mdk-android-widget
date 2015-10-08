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
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.soprasteria.movalysmdk.widget.core.MDKTechnicalInnerWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKTechnicalWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.behavior.types.HasEnum;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * ImageView widget where the image can be set with enum values.
 */
public class MDKEnumImage extends ImageView implements HasDelegate, HasEnum, MDKWidget, HasValidator {

    /** Default prefix of the images. */
    public static final String DEFAULT_IMG_PREFIX = "enum";

    /** Prefix used to retrieve the image resource. */
    private String enumPrefix;

    /** Enum value defining the image resource. */
    private Enum imageEnumValue;

    /** String name of the image resource. */
    private String imageName;

    /** Integer pointer to the image resource. */
    private int imageResourceId;

    /** The default widget delegate. */
    private MDKWidgetDelegate mdkWidgetDelegate;

    /** widget specific validators. */
    private List<Integer> validators;

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     */
    public MDKEnumImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     * @param defStyleAttr the style
     */
    public MDKEnumImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Instanciates the widget delegate and initializes the attributes.
     * @param context the context
     * @param attrs attributes
     */
    private void init(Context context, AttributeSet attrs) {

        // Parse the enum_prefix attribute
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKEnumImage);
        String resEnumPrefix = typedArray.getString(R.styleable.MDKCommons_MDKEnumImage_enum_prefix);
        if(resEnumPrefix != null) {
            enumPrefix = resEnumPrefix;
        }else{
            enumPrefix = DEFAULT_IMG_PREFIX;
        }
        typedArray.recycle();

        // Create the widget delegate
        mdkWidgetDelegate = new MDKWidgetDelegate(this, attrs);

        //initializing
        validators = new ArrayList<>();
    }

    @Override
    public MDKWidgetDelegate getMDKWidgetDelegate() {
        return mdkWidgetDelegate;
    }

    @Override
    public int[] getValidators() {
        //converting list of Integer to array of int
        int[] validatorsArray = new int[validators.size()];
        for(int i = 0;i < validatorsArray.length;i++) {
            validatorsArray[i] = validators.get(i);
        }

        return validatorsArray;
    }

    /**
     * Adds the resource pointer of a validator class name to this widget's list of validators.
     * @param validator the validator to add
     */
    public void addValidator(int validator){
        validators.add(validator);
    }

    /**
     * Removes a validator from the list of validators.
     * @param validator the validator to remove
     */
    public void removeValidator(int validator){
        validators.remove(validator);
    }

    @Override
    public boolean validate() {
        return mdkWidgetDelegate.validate(true,EnumFormFieldValidator.VALIDATE);
    }

    @Override
    public boolean validate(@EnumFormFieldValidator.EnumValidationMode int validationMode) {
        return mdkWidgetDelegate.validate(true,validationMode);
    }

    @Override
    public void setError(CharSequence error) {
        mdkWidgetDelegate.setError(error);
    }

    @Override
    public void addError(MDKMessages error) {
        mdkWidgetDelegate.addError(error);
    }

    @Override
    public void clearError() {
        mdkWidgetDelegate.clearError();
    }

    @Override
    public Object getValueToValidate() {
        return imageEnumValue;
    }

    @Override
    public MDKTechnicalInnerWidgetDelegate getTechnicalInnerWidgetDelegate() {
        return mdkWidgetDelegate.getTechnicalInnerWidgetDelegate();
    }

    @Override
    public int[] superOnCreateDrawableState(int extraSpace) {
        return mdkWidgetDelegate.superOnCreateDrawableState(extraSpace);
    }

    @Override
    public void callMergeDrawableStates(int[] baseState, int[] additionalState) {
        mergeDrawableStates(baseState, additionalState);
    }

    @Override
    public MDKTechnicalWidgetDelegate getTechnicalWidgetDelegate() {
        return mdkWidgetDelegate.getTechnicalWidgetDelegate();
    }

    @Override
    public void setMandatory(boolean mandatory) {
        mdkWidgetDelegate.setMandatory(mandatory);
    }

    @Override
    public boolean isMandatory() {
        return mdkWidgetDelegate.isMandatory();
    }

    @Override
    public Enum getValueAsEnumValue() {
        return imageEnumValue;
    }

    @Override
    public void setValueFromEnum(Enum value) {

        imageEnumValue = value;

        //recreating the image name, in the form "enumclassname_enumvaluename"
        String nameStr = (value.getClass().getSimpleName() + "_" + value.name()).toLowerCase();

        //setting the name and the imageview drawable
        setValueFromString(nameStr);

    }

    @Override
    public String getValueAsString() {
        return imageName;
    }

    @Override
    public void setValueFromString(String name) {
        imageName=name;

        //recreating the resource name, in the form "prefix_name"
        String resourceCompleteName = (enumPrefix + "_" + imageName).toLowerCase();

        //setting the image drawable from value
        this.setImageDrawable(ContextCompat.getDrawable(getContext(),getResources().getIdentifier(resourceCompleteName, "drawable", getContext().getPackageName())));
    }

    @Override
    public int getValueAsInt() {
        return imageResourceId;
    }

    @Override
    public void setValueFromInt(int id) {
        imageResourceId = id;

        setImageResource(imageResourceId);
    }

    @Override
    public String getResourceNamePrefix() {
        return enumPrefix;
    }

    @Override
    public void setResourceNamePrefix(String prefix) {
        enumPrefix = prefix.toLowerCase();
    }
}

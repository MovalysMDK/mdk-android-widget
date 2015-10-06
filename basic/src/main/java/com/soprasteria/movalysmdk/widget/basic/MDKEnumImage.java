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
import com.soprasteria.movalysmdk.widget.core.behavior.HasEnum;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;

/**
 * ImageView widget where the image can be set with enum values.
 */
public class MDKEnumImage extends ImageView implements HasDelegate, HasEnum, MDKWidget, HasValidator {

    /** Default prefix of the images. */
    public static final String DEFAULT_IMG_PREFIX = "enum";

    /** Prefix used to retrieve the image resource. */
    private String enumPrefix;

    /** Enum value mapping the image resource. */
    private Enum imageValue;

    /** The default widget delegate. */
    private MDKWidgetDelegate mdkWidgetDelegate;

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
    }

    @Override
    public Enum getEnumValue() {
        return imageValue;
    }

    @Override
    public void setEnumValue(Enum value) {
        imageValue = value;

        //recreating the resource name, in the form "prefix_enumclassname_enumvaluename"
        String imageValueString = (enumPrefix + "_" + value.getClass().getSimpleName() + "_" + value.name()).toLowerCase();

        //setting the image drawable from value
        this.setImageDrawable(ContextCompat.getDrawable(getContext(),getResources().getIdentifier(imageValueString, "drawable", getContext().getPackageName())));
    }

    @Override
    public String getEnumPrefix() {
        return enumPrefix;
    }

    @Override
    public void setEnumPrefix(String prefix) {
        enumPrefix = prefix;
    }

    @Override
    public MDKWidgetDelegate getMDKWidgetDelegate() {
        return mdkWidgetDelegate;
    }

    @Override
    public int[] getValidators() {
        return new int[0];
    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public boolean validate(@EnumFormFieldValidator.EnumValidationMode int validationMode) {
        return true;
    }

    @Override
    public void setError(CharSequence error) {
        //Unnecessary.
    }

    @Override
    public void addError(MDKMessages error) {
        //Unnecessary.
    }

    @Override
    public void clearError() {
        //Unnecessary.
    }

    @Override
    public Object getValueToValidate() {
        return imageValue;
    }

    @Override
    public MDKTechnicalInnerWidgetDelegate getTechnicalInnerWidgetDelegate() {
        return mdkWidgetDelegate;
    }

    @Override
    public int[] superOnCreateDrawableState(int extraSpace) {
        return new int[0];
    }

    @Override
    public void callMergeDrawableStates(int[] baseState, int[] additionalState) {
        //Unnecessary.
    }

    @Override
    public MDKTechnicalWidgetDelegate getTechnicalWidgetDelegate() {
        return mdkWidgetDelegate;
    }

    @Override
    public void setMandatory(boolean mandatory) {
        //Unnecessary.
    }

    @Override
    public boolean isMandatory() {
        return false;
    }
}

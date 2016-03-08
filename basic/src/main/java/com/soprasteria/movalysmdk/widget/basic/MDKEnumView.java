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
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.core.MDKTechnicalInnerWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKTechnicalWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasChangeListener;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.behavior.types.HasEnum;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKChangeListenerDelegate;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.helper.AttributesHelper;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * ImageView widget where the image can be set with enum values.
 */
public class MDKEnumView extends RelativeLayout implements HasDelegate, HasEnum, MDKWidget, HasValidator, View.OnClickListener, HasChangeListener {

    // TODO a mettre en constante dans les styles
    /**
     * Alpha applied when component is disabled.
     */
    private static final int DISABLED_ALPHA = 70;

    /**
     * Enumeration listing possible MDKEnumView modes.
     */
    @IntDef({MODE_IMAGE, MODE_TEXT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface EnumMode {
    }

    /**
     * notify change listeners.
     */
    private MDKChangeListenerDelegate mdkListenerDelegate;

    /**
     * Default prefix of the images.
     */
    public static final String DEFAULT_ENUM_PREFIX = "enum";

    /**
     * Image EnumView mode.
     */
    public static final int MODE_IMAGE = 0;

    /**
     * Text EnumView mode.
     */
    public static final int MODE_TEXT = 1;

    /**
     * Current mode of the EnumView (default 0).
     */
    private int mode;

    /**
     * Prefix used to retrieve the image resource.
     */
    private String enumPrefix;

    /**
     * Enum value defining the resource.
     */
    private Enum resourceEnumValue;

    /**
     * String name of the resource.
     */
    private String resourceName;

    /**
     * Integer pointer to the resource.
     */
    private int resourceId;

    /**
     * The default widget delegate.
     */
    private MDKWidgetDelegate mdkWidgetDelegate;

    /**
     * Internal view (view type depends on the mode).
     */
    private View view;

    /**
     * Constructor.
     *
     * @param context the context
     * @param attrs   attributes
     */
    public MDKEnumView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Constructor.
     *
     * @param context      the context
     * @param attrs        attributes
     * @param defStyleAttr the style
     */
    public MDKEnumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Instanciates the widget delegate and initializes the attributes.
     *
     * @param context the context
     * @param attrs   attributes
     */
    private void init(Context context, AttributeSet attrs) {

        LayoutInflater.from(context).inflate(R.layout.mdkwidget_enumview_layout, this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKEnumView);

        // Parse the enum_prefix attribute
        enumPrefix = AttributesHelper.getStringFromStringAttribute(typedArray, R.styleable.MDKCommons_MDKEnumView_enumPrefix, DEFAULT_ENUM_PREFIX);

        // Parse the mode of EnumView
        mode = AttributesHelper.getIntFromIntAttribute(typedArray, R.styleable.MDKCommons_MDKEnumView_enumMode, 0);

        typedArray.recycle();

        // Create the widget delegate
        mdkWidgetDelegate = new MDKWidgetDelegate(this, attrs);

        this.mdkListenerDelegate = new MDKChangeListenerDelegate();
    }

    /**
     * Initializes the EnumView in image mode.
     */
    private void initImageMode() {
        if(view!=null){
            view.setVisibility(GONE);
        }
        view = findViewById(R.id.image);
        view.setVisibility(VISIBLE);
    }

    /**
     * Initializes the EnumView in text mode.
     */
    private void initTextMode() {
        if(view!=null){
            view.setVisibility(GONE);
        }
        view = findViewById(R.id.text);
        view.setVisibility(VISIBLE);
    }


    /**
     * Gets the view managed by this widget. Type depends on the mode:
     * - ImageView for image mode.
     * - TextView for text mode.
     *
     * @return the inner view
     */
    public View getModeView() {
        return view;
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
        return mdkWidgetDelegate.validate(true, EnumFormFieldValidator.VALIDATE);
    }

    @Override
    public boolean validate(@EnumFormFieldValidator.EnumValidationMode int validationMode) {
        return mdkWidgetDelegate.validate(true, validationMode);
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
        return resourceEnumValue;
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
        return resourceEnumValue;
    }

    @Override
    public void setValueFromEnum(Enum value) {

        resourceEnumValue = value;

        //recreating the image name, in the form "enumclassname_enumvaluename"
        String nameStr = value.getClass().getSimpleName() + "_" + value.name();

        //setting the name and the imageview drawable
        setValueFromString(nameStr);

    }

    @Override
    public String getValueAsString() {
        return resourceName;
    }

    @Override
    public void setValueFromString(String name) {
        resourceName = name;

        //recreating the resource name, in the form "prefix_name"
        String resourceCompleteName = enumPrefix + "_" + resourceName;


        switch (mode) {
            case 1:
                setTextFromString(resourceCompleteName);
                break;
            case 0:
                // Intentional fallthrough.
            default:
                setDrawableFromString(resourceCompleteName);
                break;
        }
        this.validate(EnumFormFieldValidator.VALIDATE);
    }

    /**
     * Initializes the textview if needed and sets the text from a resource name string.
     *
     * @param textStr the resource name
     */
    private void setTextFromString(String textStr) {
        if (view == null || !(view instanceof TextView)) {
            initTextMode();
        }
        String textIdentifier = null;
        if (textStr != null) {
            textIdentifier = textStr;
        }
        int textRes = getResources().getIdentifier(textIdentifier, "string", getContext().getPackageName());
        if (textRes != 0) {
            if (mode == MODE_TEXT) {
                ((TextView) view).setText(getContext().getString(textRes));
            } else {
                this.imageFallbackText(textIdentifier);
            }
        } else {
            //fallback behavior: displaying resource name
            this.imageFallbackText(textIdentifier);
        }
    }

    /**
     * Fallback to text mode.
     * @param textStr the resource name
     */
    private void imageFallbackText(String textStr) {
        initTextMode();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        view.setLayoutParams(params);
        // we are in fallback
        String textIdentifier = null;
        if (textStr != null) {
            textIdentifier = textStr;
        }
        int textRes = getResources().getIdentifier(textIdentifier, "string", getContext().getPackageName());
        if (textRes != 0) {
            ((TextView) view).setText(getContext().getString(textRes));
        } else {
            String text = enumPrefix + "_" + resourceName;
            ((TextView) view).setText(text);
        }
        // we override the background
        this.setBackgroundResource(R.drawable.round_enum_background);
    }

    /**
     * Initializes the imageview if needed and sets the drawable from a resource name string.
     *
     * @param drawableStr the resource name
     */
    private void setDrawableFromString(String drawableStr) {
        if (view == null || !(view instanceof ImageView)) {
            initImageMode();
        }
        int imgRes = getResources().getIdentifier(drawableStr.toLowerCase(), "drawable", getContext().getPackageName());
        if (imgRes != 0) {
            ((ImageView) view).setImageDrawable(ContextCompat.getDrawable(getContext(), imgRes));
        } else {
            //fallback behavior: look for text
            setTextFromString(drawableStr);
        }
    }


    @Override
    public int getValueAsId() {
        return resourceId;
    }

    @Override
    public void setValueFromId(int id) {
        resourceId = id;
        switch (mode) {
            case 1:
                setTextFromId(id);
                break;
            case 0:
                // Intentional fallthrough.
            default:
                setDrawableFromId(id);
                break;
        }
        this.validate(EnumFormFieldValidator.VALIDATE);
    }

    /**
     * Initializes the textview if needed and sets the text from a resource identifier.
     *
     * @param id the resource id
     */
    private void setTextFromId(int id) {
        if (view == null || !(view instanceof TextView)) {
            initTextMode();
        }
        try {
            ((TextView) view).setText(getContext().getString(id));
        } catch (Resources.NotFoundException e) {
            Log.w(this.getClass().getSimpleName(), "String resource not found: " + id, e);
            //fallback behavior: displaying id
            ((TextView) view).setText(String.valueOf(id));
        }
    }

    /**
     * Initializes the imageview if needed and sets the drawable from a resource identifier.
     *
     * @param id the resource id
     */
    private void setDrawableFromId(int id) {
        if (view == null || !(view instanceof ImageView)) {
            initImageMode();
        }
        try {
            ((ImageView) view).setImageResource(id);
        } catch (Resources.NotFoundException e) {
            Log.w(this.getClass().getSimpleName(), "Drawable resource not found: " + id, e);
            //fallback behavior: look for text
            setTextFromId(id);
        }
    }


    @Override
    public String getResourceNamePrefix() {
        return enumPrefix;
    }

    @Override
    public void setResourceNamePrefix(String prefix) {
        enumPrefix = prefix.toLowerCase();
    }


    /**
     * Gets the widget's mode.
     *
     * @return the resource name prefix
     */
    public int getMode() {
        return mode;
    }

    /**
     * Sets the widget's mode.
     *
     * @param mode the mode from the list of possible modes
     */
    public void setMode(@EnumMode int mode) {
        this.mode = mode;
    }


    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        if (view instanceof ImageView) {
            ((ImageView) view).setAlpha(isEnabled() ? 255 : DISABLED_ALPHA);
        }
    }

    @Override
    public boolean isReadonly() {
        return mdkWidgetDelegate.isReadonly();
    }

    @Override
    public void setReadonly(boolean readonly) {
        this.mdkWidgetDelegate.setReadonly(readonly);
        if (readonly) {
            this.setOnClickListener(null);
        } else {
            this.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        //Cycling through the possible enum values
        if (resourceEnumValue != null) {
            List<Enum> values = new ArrayList<>(EnumSet.allOf(resourceEnumValue.getClass()));
            //going back to the first element if we reached the end of the list.
            if (values.indexOf(resourceEnumValue) + 1 < values.size()) {
                setValueFromEnum(values.get(values.indexOf(resourceEnumValue) + 1));
            } else {
                setValueFromEnum(values.get(0));
            }
            this.mdkListenerDelegate.notifyListeners();
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable state = super.onSaveInstanceState();

        // Save the MDKWidgetDelegate instance state
        state = this.mdkWidgetDelegate.onSaveInstanceState(state);

        // Save the enum value
        Bundle bundle = new Bundle();
        bundle.putParcelable("state", state);

        if (resourceEnumValue != null) {
            bundle.putSerializable("enumValue", resourceEnumValue);
        }

        return bundle;

    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;

            // Restoring the enum value
            Enum enumValue = (Enum) bundle.getSerializable("enumValue");
            if (resourceEnumValue != null && enumValue != null) {
                setValueFromEnum(enumValue);
            }

            Parcelable parcelable = bundle.getParcelable("state");
            parcelable = this.mdkWidgetDelegate.onRestoreInstanceState(this, parcelable);
            super.onRestoreInstanceState(parcelable);


            return;
        }
        // Restore the android view instance state
        super.onRestoreInstanceState(state);
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

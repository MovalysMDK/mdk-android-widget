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
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import com.soprasteria.movalysmdk.widget.basic.formatter.SeekbarDefaultFormatter;
import com.soprasteria.movalysmdk.widget.core.MDKBaseRichWidget;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasChangeListener;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasFormatter;
import com.soprasteria.movalysmdk.widget.core.behavior.HasSeekBar;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.formatter.MDKBaseFormatter;
import com.soprasteria.movalysmdk.widget.core.helper.AttributesHelper;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;

/**
 * Rich widget representing a seek bar component, conforming to the Material Design guidelines,
 * and including by default the error component.
 * @param <T> The class of the widget to encapsulate*
 */
public class MDKRichSeekBar <T extends MDKWidget & HasFormatter<Integer,String> & HasValidator & HasDelegate & HasSeekBar & HasChangeListener> extends MDKBaseRichWidget implements HasChangeListener, HasSeekBar, HasFormatter<Integer,String> {

    /** Log tag.*/
    public static final String LOG_TAG = "MDKRichSeekBar";

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     */
    public MDKRichSeekBar(Context context, AttributeSet attrs) {
        super(R.layout.mdkwidget_seekbar_edit_label, R.layout.mdkwidget_seekbar_edit, context, attrs);

        initDedicatedAttributes(attrs);
    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     * @param defStyleAttr defStyleAttr
     */
    public MDKRichSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(R.layout.mdkwidget_seekbar_edit_label, R.layout.mdkwidget_seekbar_edit, context, attrs, defStyleAttr);

        initDedicatedAttributes(attrs);
    }

    /**
     * Getter for the inner widget of the rich widget.
     * @return the inner widget
     */
    @Override
    @SuppressWarnings("unchecked")
    public T getInnerWidget()   {
        return (T) super.getInnerWidget();
    }

    /**
     * Initialize MDK widget class variables with layout attributes of the Rich component.
     * @param attrs Array of attributes of the MDK widget
     */
    @SuppressWarnings("unchecked")
    private void initDedicatedAttributes(AttributeSet attrs){

        // Retrieve attributes of the Seek Bar widget in order to initialize MDK widget class variables.
        TypedArray typedArray = this.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons);
        TypedArray typedArrayComponent = this.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKSeekBarComponent);

        String editableStr = typedArrayComponent.getString(R.styleable.MDKCommons_MDKSeekBarComponent_editableEditText);
        this.getInnerWidget().setEditableEditText(editableStr == null || Boolean.parseBoolean(editableStr));

        int formatterResourceId = typedArray.getResourceId(R.styleable.MDKCommons_formatter,0);
        String formatterStr = formatterResourceId!=0?getResources().getString(formatterResourceId):null;
        if (formatterStr != null) {
            try {
                setFormatter((MDKBaseFormatter<Integer, String>) Class.forName(formatterStr).newInstance());
            } catch (Exception e) {
                Log.e(LOG_TAG,"Unknown formatter class", e);
                setFormatter(new SeekbarDefaultFormatter());
            }
        }else{
            setFormatter(new SeekbarDefaultFormatter());
        }

        setSeekBarMinAllowed(AttributesHelper.getIntFromStringAttribute(typedArrayComponent, R.styleable.MDKCommons_MDKSeekBarComponent_min_allowed, 0));

        setSeekBarMaxAllowed(AttributesHelper.getIntFromStringAttribute(typedArrayComponent, R.styleable.MDKCommons_MDKSeekBarComponent_max_allowed, 100));

        setMin(AttributesHelper.getIntFromStringAttribute(typedArrayComponent, R.styleable.MDKCommons_MDKSeekBarComponent_seekbar_min, getSeekBarMinAllowed()));

        setMax(AttributesHelper.getIntFromStringAttribute(typedArrayComponent, R.styleable.MDKCommons_MDKSeekBarComponent_seekbar_max, getSeekBarMaxAllowed()));

        String initialValueStr = typedArrayComponent.getString(R.styleable.MDKCommons_MDKSeekBarComponent_initialSeekBarValue);
        if (initialValueStr != null) {
            int seekBarValue = Integer.parseInt(initialValueStr);
            this.setSeekBarValue(seekBarValue);
            this.setSeekProgress(seekBarValue);
        }else{
            this.setSeekBarValue(getMin());
        }

        typedArray.recycle();
        typedArrayComponent.recycle();
    }

    @Override
    public void registerChangeListener(ChangeListener listener) {
        this.getInnerWidget().registerChangeListener(listener);
    }

    @Override
    public void unregisterChangeListener(ChangeListener listener) {
        this.getInnerWidget().unregisterChangeListener(listener);
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
    public int getSeekBarMinAllowed() {
        return this.getInnerWidget().getSeekBarMinAllowed();
    }

    @Override
    public void setSeekBarMinAllowed(int seekBarMinValue) {
        this.getInnerWidget().setSeekBarMinAllowed(seekBarMinValue);
    }

    @Override
    public int getSeekBarMaxAllowed() {
        return this.getInnerWidget().getSeekBarMaxAllowed();
    }

    @Override
    public void setSeekBarMaxAllowed(int seekBarMaxValue) {
        this.getInnerWidget().setSeekBarMaxAllowed(seekBarMaxValue);
    }


    @Override
    public int getMax() {
        return this.getInnerWidget().getMax();
    }

    @Override
    public void setMax(int max) {
        this.getInnerWidget().setMax(max);
    }

    @Override
    public int getMin() {
        return this.getInnerWidget().getMin();
    }

    @Override
    public void setMin(int min) {
        this.getInnerWidget().setMin(min);
    }

    @Override
    public void setSeekProgress(int value) {
        this.getInnerWidget().setSeekProgress(value);
    }

    @Override
    public EditText getAttachedEditText() {
        return this.getInnerWidget().getAttachedEditText();
    }

    @Override
    public void setAttachedEditText(EditText attachedEditText) {
        this.getInnerWidget().setAttachedEditText(attachedEditText);
    }

    @Override
    public boolean isEditableEditText() {
        return this.getInnerWidget().isEditableEditText();
    }

    @Override
    public void setEditableEditText(boolean editable) {
        this.getInnerWidget().setEditableEditText(editable);
    }

    @Override
    public MDKBaseFormatter<Integer, String> getFormatter() {
        return getInnerWidget().getFormatter();
    }

    @Override
    public void setFormatter(MDKBaseFormatter<Integer, String> newFormatter) {
        getInnerWidget().setFormatter(newFormatter);
    }
}

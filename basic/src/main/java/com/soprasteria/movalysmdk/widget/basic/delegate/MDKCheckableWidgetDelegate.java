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
package com.soprasteria.movalysmdk.widget.basic.delegate;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;

import com.soprasteria.movalysmdk.widget.basic.R;
import com.soprasteria.movalysmdk.widget.core.behavior.HasCheckedTexts;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKChangeListenerDelegate;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;

import java.lang.ref.WeakReference;

/**
 * Specific delegate for Checkable widgets.
 * Those include the CheckBox and the Switch.
 */
public class MDKCheckableWidgetDelegate extends MDKWidgetDelegate implements ChangeListener, HasCheckedTexts {

    /** change listener. */
    private MDKChangeListenerDelegate mdkListenerDelegate;

    /** Text to display at all times if set. Overrides checkedText and uncheckedText */
    private String fixedText;

    /** Text to display when component is in checked state. */
    private String checkedText;

    /** Text to display when component is in unchecked state. */
    private String uncheckedText;

    /** ID of the checkable view. */
    private int checkableViewId;

    /** Cached reference of the checkable view. */
    private WeakReference<CompoundButton> cachedCheckableView;

    /**
     * Constructor.
     * @param view  the view
     * @param attrs the parameters set
     */
    public MDKCheckableWidgetDelegate(View view, AttributeSet attrs) {
        super(view, attrs);

        init(view, attrs);
    }

    /**
     * Initialise the object.
     * @param view the related view
     * @param attrs the view attributes
     */
    private void init(View view, AttributeSet attrs) {

        TypedArray typedArray = view.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKCheckableComponent);

        checkableViewId = view.getId();

        String textFixedStr = typedArray.getString(R.styleable.MDKCommons_MDKCheckableComponent_textFixed);
        if (textFixedStr != null) {
            fixedText = textFixedStr;
            ((CompoundButton) view).setText(fixedText);
        }else {
            String textCheckedStr = typedArray.getString(R.styleable.MDKCommons_MDKCheckableComponent_textChecked);
            if (textCheckedStr != null) {
                checkedText = textCheckedStr;
                if(((CompoundButton) view).isChecked()) {
                    ((CompoundButton) view).setText(checkedText);
                }
            }

            String textUncheckedStr = typedArray.getString(R.styleable.MDKCommons_MDKCheckableComponent_textUnchecked);
            if (textUncheckedStr != null) {
                uncheckedText = textUncheckedStr;
                if(!((CompoundButton) view).isChecked()) {
                    ((CompoundButton) view).setText(uncheckedText);
                }
            }
        }

        typedArray.recycle();

        cachedCheckableView = new WeakReference<>((CompoundButton) view);

        this.mdkListenerDelegate = new MDKChangeListenerDelegate();
        registerChangeListener(this);

    }

    /**
     * Updates the text beside the checkable.
     */
    private void updateText(){

        if(fixedText==null && checkedText!= null && uncheckedText!= null){
            getCheckableView().setText(getCheckableView().isChecked()?checkedText:uncheckedText);
        }else if(fixedText!=null){
            getCheckableView().setText(fixedText);
        }
    }

    /**
     * Returns the checkable CompoundView, if it exists.
     * @return foundCheckableView the found view
     */
    private CompoundButton getCheckableView() {

        View foundCheckableView = null;

        // Try to reuse the cached view
        if (this.cachedCheckableView != null) {
            foundCheckableView = this.cachedCheckableView.get();
        }
        // if there is no valid cached view, try to get it
        if (foundCheckableView == null) {
            foundCheckableView = reverseFindViewById(this.checkableViewId);
            this.cachedCheckableView = new WeakReference<>((CompoundButton)foundCheckableView);
        }

        return (CompoundButton) foundCheckableView;
    }

    /**
     * Returns the validators used by a Checkable widget.
     * @return the specific validators for Checkable widgets
     */
    public int[] getValidators() {
        return new int[] { R.string.mdkvalidator_checkable_class};
    }

    /**
     * Called when the Checkable widget gets checked.
     */
    public void doOnChecked() {
        if (this.mdkListenerDelegate != null) {
            this.mdkListenerDelegate.notifyListeners();
        }
    }


    /**
     * Register a ChangeListener.
     * @param listener the ChangeListener to register
     */
    public void registerChangeListener(ChangeListener listener) {
        this.mdkListenerDelegate.registerChangeListener(listener);
    }

    /**
     * Unregisters a ChangeListener.
     * @param listener the listener to unregister
     */
    public void unregisterChangeListener(ChangeListener listener) {
        this.mdkListenerDelegate.unregisterChangeListener(listener);
    }

    @Override
    public void onChanged() {
        updateText();
    }

    @Override
    public String getFixedText() {
        return fixedText;
    }

    @Override
    public void setFixedText(String text) {
        fixedText = text;
        updateText();
    }

    @Override
    public String getCheckedText() {
        return checkedText;
    }

    @Override
    public void setCheckedText(String text) {
        checkedText = text;
        updateText();
    }

    @Override
    public String getUncheckedText() {
        return uncheckedText;
    }

    @Override
    public void setUncheckedText(String text) {
        uncheckedText = text;
        updateText();
    }
}

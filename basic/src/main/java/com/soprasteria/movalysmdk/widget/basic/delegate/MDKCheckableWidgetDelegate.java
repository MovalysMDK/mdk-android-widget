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
import com.soprasteria.movalysmdk.widget.core.delegate.MDKChangeListenerDelegate;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;

/**
 * Specific delegate for Checkable widgets.
 * Those include the CheckBox and the Switch.
 */
public class MDKCheckableWidgetDelegate extends MDKWidgetDelegate {

    /** change listener. */
    private MDKChangeListenerDelegate mdkListenerDelegate;

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
        // Parse the MDKCommons:label attribute
        TypedArray typedArray = view.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons);
        int resLabelId = typedArray.getResourceId(R.styleable.MDKCommons_label, 0);
        if (resLabelId != 0) {
            ((CompoundButton)view).setText(resLabelId);
        }
        typedArray.recycle();

        this.mdkListenerDelegate = new MDKChangeListenerDelegate();
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

}

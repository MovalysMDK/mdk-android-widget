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
package com.soprasteria.movalysmdk.widget.core.delegate;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.soprasteria.movalysmdk.widget.core.R;
import com.soprasteria.movalysmdk.widget.core.helper.MDKAttributeSet;
import com.soprasteria.movalysmdk.widget.core.listener.ValidationListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Object storing values processed by the class {@link MDKWidgetDelegate}.
 */
public class MDKWidgetDelegateValueObject {
    /**
     * Number of state handle by the mdk.<br/>
     * R.attr.state_mandatory<br/>
     * R.attr.state_valid<br/>
     * R.attr.state_error<br/>
     */
    protected static final int ADDED_MDK_STATE = 3;
    /** MANDATORY_VALID_STATE. */
    static final int[] MANDATORY_VALID_STATE = {R.attr.state_valid, R.attr.state_mandatory};
    /** MANDATORY_ERROR_STATE. */
    static final int[] MANDATORY_ERROR_STATE = {R.attr.state_error, R.attr.state_mandatory};
    /** MANDATORY_STATE. */
    static final int[] MANDATORY_STATE = {R.attr.state_mandatory};
    /** VALID_STATE. */
    static final int[] VALID_STATE = {R.attr.state_valid};
    /** ERROR_STATE. */
    static final int[] ERROR_STATE = {R.attr.state_error};
    /** user error key. */
    static final String USER_ERROR = "user_error";
    /** Component qualifier. */
    private String qualifier;
    /** Resource id of the helper. */
    private int resHelperId;
    /** richSelectors. */
    private List<String> richSelectors;
    /** weakView. */
    private WeakReference<View> weakView;

    /** Widget label id. */
    private int labelViewId;
    /** showFloatingLabelAnimId. */
    private int showFloatingLabelAnimId;
    /** hideFloatingLabelAnimId. */
    private int hideFloatingLabelAnimId;
    /** helperViewId. */
    private int helperViewId;
    /** errorViewId. */
    private int errorViewId;
    /** uniqueId. */
    private int uniqueId;
    /** valid. */
    private boolean valid = false;
    /** mandatory. */
    private boolean mandatory = false;
    /** error. */
    private boolean error = false;
    /** Command state change listener, triggered when widget is validate. */
    private List<ValidationListener> validationListeners;
    /** attribute map for validator. */
    private MDKAttributeSet attributesMap;

    /**
     * Initializes the object.
     * @param view the view to initialize from
     * @param attrs the attributes set on the view
     */
    public void initialize(View view, AttributeSet attrs) {
        this.weakView = new WeakReference<View>(view);
        this.richSelectors = new ArrayList<>();
        this.validationListeners = new ArrayList<>();

        TypedArray typedArray = view.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons);

        this.labelViewId = typedArray.getResourceId(R.styleable.MDKCommons_labelId, 0);
        this.showFloatingLabelAnimId = typedArray.getResourceId(R.styleable.MDKCommons_showFloatingLabelAnim, 0);
        this.hideFloatingLabelAnimId = typedArray.getResourceId(R.styleable.MDKCommons_hideFloatingLabelAnim, 0);
        this.helperViewId = typedArray.getResourceId(R.styleable.MDKCommons_helperId, 0);
        this.errorViewId = typedArray.getResourceId(R.styleable.MDKCommons_errorId, 0);
        this.resHelperId = typedArray.getResourceId(R.styleable.MDKCommons_helper, 0);
        this.mandatory = typedArray.getBoolean(R.styleable.MDKCommons_mandatory, false);
        this.qualifier = typedArray.getString(R.styleable.MDKCommons_qualifier);

        int selectorResId = typedArray.getResourceId(R.styleable.MDKCommons_selectors, R.array.selectors);
        String[] selectorKeys = view.getContext().getResources().getStringArray(selectorResId);
        for (String selectorKey : selectorKeys) {
            this.richSelectors.add(selectorKey);
        }
        typedArray.recycle();

        this.attributesMap = new MDKAttributeSet(attrs);
        this.attributesMap.setBoolean(R.attr.mandatory, this.mandatory);
    }

    /**
     * Returns the component qualifier.
     * @return the component qualifier
     */
    public String getQualifier() {
        return qualifier;
    }

    /**
     * Sets the component qualifier.
     * @param qualifier the component qualifier to set
     */
    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    /**
     * Returns the resource id of the helper.
     * @return the resource id of the helper
     */
    public int getResHelperId() {
        return resHelperId;
    }

    /**
     * Sets the resource id of the helper.
     * @param resHelperId the resource id of the helper
     */
    public void setResHelperId(int resHelperId) {
        this.resHelperId = resHelperId;
    }

    /**
     * Returns the widget rich selectors.
     * @return the widget rich selectors
     */
    public List<String> getRichSelectors() {
        return richSelectors;
    }

    /**
     * Sets the widget rich selectors.
     * @param richSelectors the value to set
     */
    public void setRichSelectors(List<String> richSelectors) {
        this.richSelectors = richSelectors;
    }

    /**
     * Returns the weak reference to the widget view.
     * @return the weak reference to the widget view
     */
    public WeakReference<View> getWeakView() {
        return weakView;
    }

    /**
     * Sets the weak reference to the widget view.
     * @param weakView the value to set
     */
    public void setWeakView(WeakReference<View> weakView) {
        this.weakView = weakView;
    }

    /**
     * Returns the widget view.
     * @return the widget view
     */
    public View getView() {
        return this.weakView.get();
    }

    /**
     * Returns the widget label view identifier.
     * @return the widget label view identifier
     */
    public int getLabelViewId() {
        return labelViewId;
    }

    /**
     * Sets the widget label view identifier.
     * @param labelViewId the value to set
     */
    public void setLabelViewId(int labelViewId) {
        this.labelViewId = labelViewId;
    }

    /**
     * Returns the Floating label show animation identifier.
     * @return the Floating label show animation identifier
     */
    public int getShowFloatingLabelAnimId() {
        return showFloatingLabelAnimId;
    }

    /**
     * Sets the Floating label show animation identifier.
     * @param showFloatingLabelAnimId the value to set
     */
    public void setShowFloatingLabelAnimId(int showFloatingLabelAnimId) {
        this.showFloatingLabelAnimId = showFloatingLabelAnimId;
    }

    /**
     * Returns the Floating label hide animation identifier.
     * @return the Floating label hide animation identifier
     */
    public int getHideFloatingLabelAnimId() {
        return hideFloatingLabelAnimId;
    }

    /**
     * Sets the Floating label hide animation identifier.
     * @param hideFloatingLabelAnimId the value to set
     */
    public void setHideFloatingLabelAnimId(int hideFloatingLabelAnimId) {
        this.hideFloatingLabelAnimId = hideFloatingLabelAnimId;
    }

    /**
     * Returns the widget helper view identifier.
     * @return the widget helper view identifier
     */
    public int getHelperViewId() {
        return helperViewId;
    }

    /**
     * Sets the widget helper view identifier.
     * @param helperViewId the value to set
     */
    public void setHelperViewId(int helperViewId) {
        this.helperViewId = helperViewId;
    }

    /**
     * Returns the widget error view identifier.
     * @return the widget error view identifier
     */
    public int getErrorViewId() {
        return errorViewId;
    }

    /**
     * Sets the widget error view identifier.
     * @param errorViewId the value to set
     */
    public void setErrorViewId(int errorViewId) {
        this.errorViewId = errorViewId;
    }

    /**
     * Returns the widget unique identifier.
     * @return the widget unique identifier
     */
    public int getUniqueId() {
        return uniqueId;
    }

    /**
     * Sets the widget unique identifier.
     * @param uniqueId the value to set
     */
    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    /**
     * Returns true if the widget is valid.
     * @return true if the widget is valid
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Sets whether the widget is valid.
     * @param valid the value to set
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * Returns true if the widget is mandatory.
     * @return true if the widget is mandatory
     */
    public boolean isMandatory() {
        return mandatory;
    }

    /**
     * Sets whether the widget is mandatory.
     * @param mandatory the value to set
     */
    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    /**
     * Returns true if the component has an error.
     * @return true if the component has an error
     */
    public boolean isError() {
        return error;
    }

    /**
     * Sets whether the component has an error.
     * @param error the value to set
     */
    public void setError(boolean error) {
        this.error = error;
    }

    /**
     * Returns the command state change listener.
     * @return the command state change listener
     */
    public List<ValidationListener> getValidationListeners() {
        return validationListeners;
    }

    /**
     * Sets the command state change listener.
     * @param validationListeners the value to set
     */
    public void setValidationListeners(List<ValidationListener> validationListeners) {
        this.validationListeners = validationListeners;
    }

    /**
     * Returns the widget attributes map.
     * @return the widget attributes map
     */
    public MDKAttributeSet getAttributesMap() {
        return attributesMap;
    }

    /**
     * Sets the widget attributes map.
     * @param attributesMap the value to set
     */
    public void setAttributesMap(MDKAttributeSet attributesMap) {
        this.attributesMap = attributesMap;
    }
}
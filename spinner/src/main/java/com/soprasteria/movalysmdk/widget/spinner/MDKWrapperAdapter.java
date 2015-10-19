package com.soprasteria.movalysmdk.widget.spinner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;

/**
 * MDK Adapter Wrapper.
 * <p>Use to add a blank row on MDKSpinner and MDKRichSpinner Component.</p>
 * <p>Use XML attrs to specify if Blank row is require.</p>
 */
public class MDKWrapperAdapter extends BaseAdapter {


    /**
     * User's adapter.
     */
    private SpinnerAdapter innerAdapter;

    /**
     * Boolean to test if blank row is required (True for Blank row).
     */
    private boolean hasBlank;

    /**
     * View for blank row in the spinner.
     */
    private View spinnerBlankView;

    /**
     * View for blank row in dropDown spinner.
     */
    private View dropDownBlankView;

    /**
     * Resource layout for spinnerBlankView.
     */
    private int spinnerBlankLayout;

    /**
     * Resource layout for dropDownBlankView.
     */
    private int dropDownBlankLayout;


    /**
     * Constructor.
     *
     * @param innerAdapter user's adapter.
     * @param hasBlank     boolean to test if blank row is required (True for Blank row).
     */
    public MDKWrapperAdapter(SpinnerAdapter innerAdapter, boolean hasBlank) {
        this(innerAdapter, hasBlank, android.R.layout.simple_spinner_dropdown_item, android.R.layout.simple_spinner_dropdown_item);
    }


    /**
     * Constructor.
     *
     * @param innerAdapter        user's adapter
     * @param hasBlank            boolean to test if blank row is required (True for Blank row)
     * @param spinnerBlankLayout  resource layout for spinnerBlankView
     * @param dropDownBlankLayout resource layout for dropDownBlankView
     */
    public MDKWrapperAdapter(SpinnerAdapter innerAdapter, boolean hasBlank, int spinnerBlankLayout, int dropDownBlankLayout) {
        this.innerAdapter = innerAdapter;
        this.hasBlank = hasBlank;
        this.spinnerBlankLayout = spinnerBlankLayout;
        this.dropDownBlankLayout = dropDownBlankLayout;
    }

    /**
     * Return number of item.
     *
     * @return number of item
     */
    @Override
    public int getCount() {
        if (hasBlank) {
            return innerAdapter.getCount() + 1;
        } else {
            return innerAdapter.getCount();
        }
    }

    /**
     * Get Item at specific position in the adapter.
     *
     * @param position Position of the item in the spinner
     * @return the item at specific position
     */
    @Override
    public Object getItem(int position) {
        if (hasBlank) {
            if (position > 0) {
                return innerAdapter.getItem(position - 1);
            } else {
                return null;
            }
        } else {
            return innerAdapter.getItem(position);
        }
    }

    /**
     * Get item id at specific position in the adapter.
     *
     * @param position Position of the item in the spinner
     * @return the item's id
     */
    @Override
    public long getItemId(int position) {
        if (hasBlank) {
            if (position == 0) {
                return Long.MAX_VALUE;
            } else {
                return innerAdapter.getItemId(position - 1);
            }
        } else {
            return innerAdapter.getItemId(position);
        }
    }

    /**
     * Return the view for spinnerView.
     *
     * @param position    Position of the item in the spinner
     * @param convertView Convert view of the adapter
     * @param parent      Parent View
     * @return the spinnerView of the innerAdapter or the blank view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (hasBlank) {
            if (position == 0) {
                this.spinnerBlankView = this.viewInflater(convertView, parent, this.spinnerBlankLayout);
                return this.spinnerBlankView;
            } else {
                if (convertView != null && convertView.equals(this.spinnerBlankView)) {
                    return innerAdapter.getView(position - 1, null, parent);
                } else {
                    return innerAdapter.getView(position - 1, convertView, parent);
                }
            }
        } else {
            return innerAdapter.getView(position, convertView, parent);
        }
    }

    /**
     * Return the view for the dropDownView.
     *
     * @param position    Position of the item in the spinner
     * @param convertView Convert view of the adapter
     * @param parent      Parent View
     * @return the dropDownView of the innerAdapter or the dropDownBlankView
     */
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (hasBlank) {
            if (position == 0) {
                this.dropDownBlankView = this.viewInflater(convertView, parent, this.dropDownBlankLayout);
                return this.dropDownBlankView;
            } else {
                if (convertView != null && convertView.equals(this.dropDownBlankView)) {
                    return innerAdapter.getDropDownView(position - 1, null, parent);
                } else {
                    return innerAdapter.getDropDownView(position - 1, convertView, parent);
                }
            }
        } else {
            return innerAdapter.getDropDownView(position, convertView, parent);
        }
    }

    /**
     * Return the blank view and take in account recycling to optimise adapter performances.
     *
     * @param convertView Convert view of the adapter (to know if the view is recycled or not)
     * @param parent      Parent View
     * @param blankLayout The layout used to inflate rhe blankView
     * @return the blank view for spinner or dropDown view
     */
    private View viewInflater(View convertView, ViewGroup parent, int blankLayout) {
        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
            return mInflater.inflate(blankLayout, null);
        }
        return convertView;
    }
}

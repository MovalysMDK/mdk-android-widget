package com.soprasteria.movalysmdk.widget.spinner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * MDK Adapter Wrapper.
 * <p>Use to add a blank row on MDKSpinner and MDKRichSpinner Component.</p>
 * <p>Use XML attrs to specify if Blank row is require.</p>
 */
public class MDKWrapperAdapter extends BaseAdapter {
    /**
     * The default layout for spinnerBlankView.
     */
    private static final int DEFAULT_SPINNER_BLANK_LAYOUT = android.R.layout.simple_spinner_item;
    /**
     * The default layout for dropDownBlankView.
     */
    private static final int DEFAULT_DROP_DOWN_BLANK_LAYOUT = android.R.layout.simple_list_item_1;
    /**
     * The hint for the spinner view.
     */
    private CharSequence hint;
    /**
     * User's adapter.
     */
    private BaseAdapter innerAdapter;

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
     * @param hint         the user's hint specified in XML attrs
     */
    public MDKWrapperAdapter(BaseAdapter innerAdapter, boolean hasBlank, CharSequence hint) {
        init(innerAdapter, hasBlank, this.DEFAULT_SPINNER_BLANK_LAYOUT, this.DEFAULT_DROP_DOWN_BLANK_LAYOUT, hint);
    }


    /**
     * Constructor.
     *
     * @param innerAdapter        user's adapter
     * @param hasBlank            boolean to test if blank row is required (True for Blank row)
     * @param spinnerBlankLayout  resource layout for spinnerBlankView
     * @param dropDownBlankLayout resource layout for dropDownBlankView
     * @param hint                the user's hint specified in XML attrs
     */
    public MDKWrapperAdapter(BaseAdapter innerAdapter, boolean hasBlank, int spinnerBlankLayout, int dropDownBlankLayout, CharSequence hint) {
        init(innerAdapter, hasBlank, spinnerBlankLayout, dropDownBlankLayout, hint);
    }

    /**
     * Instantiate MDKWrapperAdapter variables.
     *
     * @param innerAdapter        user's adapter
     * @param hasBlank            boolean to test if blank row is required (True for Blank row)
     * @param spinnerBlankLayout  resource layout for spinnerBlankView
     * @param dropDownBlankLayout resource layout for dropDownBlankView
     * @param hint                the user's hint specified in XML attrs
     */
    private void init(BaseAdapter innerAdapter, boolean hasBlank, int spinnerBlankLayout, int dropDownBlankLayout, CharSequence hint) {
        this.innerAdapter = innerAdapter;
        this.hasBlank = hasBlank;
        this.spinnerBlankLayout = spinnerBlankLayout;
        this.dropDownBlankLayout = dropDownBlankLayout;
        this.hint = hint;
    }

    /**
     * Return the hint value (called from MDKSpinner).
     *
     * @return the hint for the spinner view
     */
    public CharSequence getHint() {
        return this.hint;
    }

    /**
     * Set the hint value (called from MDKSpinner).
     *
     * @param hint The hint for the spinner view
     */
    public void setHint(CharSequence hint) {
        this.spinnerBlankView = null;
        if (hint == null && this.spinnerBlankLayout == this.DEFAULT_SPINNER_BLANK_LAYOUT) {
            this.hint = "";
        } else {
            this.hint = hint;
        }
        this.notifyDataSetChanged();
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
                return this.spinnerViewInflater(convertView, parent, this.spinnerBlankLayout);
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
                return this.dropDownViewInflater(convertView, parent, this.dropDownBlankLayout);
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
     * @return the blank view for spinner view
     */
    private View spinnerViewInflater(View convertView, ViewGroup parent, int blankLayout) {
        if (convertView == null || this.spinnerBlankView == null) {
            LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
            if (this.hint != null && this.hint.length() > 0) {
                TextView hintView = (TextView) mInflater.inflate(android.R.layout.simple_spinner_item, null);
                hintView.setText(this.hint);
                this.spinnerBlankView = hintView;
            } else {
                this.spinnerBlankView = mInflater.inflate(blankLayout, null);
            }
        }
        return this.spinnerBlankView;
    }

    /**
     * Return the blank view and take in account recycling to optimise adapter performances.
     *
     * @param convertView Convert view of the adapter (to know if the view is recycled or not)
     * @param parent      Parent View
     * @param blankLayout The layout used to inflate rhe blankView
     * @return the blank view dropDown view
     */
    private View dropDownViewInflater(View convertView, ViewGroup parent, int blankLayout) {
        if (convertView == null || this.dropDownBlankView == null) {
            LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
            this.dropDownBlankView = mInflater.inflate(blankLayout, null);
        }
        return this.dropDownBlankView;
    }
}

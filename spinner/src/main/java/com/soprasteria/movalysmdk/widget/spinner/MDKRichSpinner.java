package com.soprasteria.movalysmdk.widget.spinner;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.BaseAdapter;

import com.soprasteria.movalysmdk.widget.core.MDKBaseRichWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.behavior.types.HasAdapter;

/**
 * MDK RichSpinner.
 * <p>Represents a Spinner with label and error field.</p>
 * <p>This spinner could add a blank row to any of your adapter.</p>
 * <p>For blank row add mdk:has_blank_row="true" to your XML attrs.</p>
 * <p>The mdk:has_blank_row default value is false.</p>
 * <p>The following behaviors are implemented:</p>
 * <ul>
 * <li>Mandatory : When XML attrs mandatory is set to "true" when blank row is selected the MDK spinner will return an error</li>
 * </ul>
 */
public class MDKRichSpinner extends MDKBaseRichWidget<MDKSpinner> implements HasValidator,HasAdapter {

    /**
     * Constructor.
     *
     * @param context the context
     * @param attrs   attributes set
     */
    public MDKRichSpinner(Context context, AttributeSet attrs) {
        super(R.layout.mdkwidget_spinner_edit_label, R.layout.mdkwidget_spinner_edit, context, attrs);
        if (!isInEditMode()) {
            init(attrs);
        }
    }

    /**
     * Constructor.
     *
     * @param context      the context
     * @param attrs        attributes set
     * @param defStyleAttr spinner defStyleAttr
     */
    public MDKRichSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(R.layout.mdkwidget_spinner_edit_label, R.layout.mdkwidget_spinner_edit, context, attrs, defStyleAttr);
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

        this.getInnerWidget().setSpinnerHasBlankRow(typedArrayComponent.getBoolean(R.styleable.MDKCommons_MDKSpinnerComponent_has_blank_row, false));
        this.getInnerWidget().setHint(typedArray.getString(R.styleable.MDKCommons_hint));

        typedArray.recycle();
        typedArrayComponent.recycle();
    }


    /**
     * Sets the data behind this ListView with the user's adapter.
     *
     * @param adapter user's adapter.
     */
    @Override
    public void setAdapter(BaseAdapter adapter) {
        this.getInnerWidget().setAdapter(adapter);
    }

    /**
     * Sets the data behind this ListView with the user's adapter and allow to use a same custom layout for dropDownBlankView and spinnerBlankView.
     *
     * @param adapter     user's adapter.
     * @param blankLayout layout for dropDownBlankView and spinnerBlankView
     */
    public void setAdapterWithCustomBlankLayout(BaseAdapter adapter, int blankLayout) {
        this.getInnerWidget().setAdapterWithCustomBlankLayout(adapter, blankLayout);
    }

    /**
     * Sets the data behind this ListView with the user's adapter and allow to use a custom layout for dropDownBlankView and spinnerBlankView.
     * Example : to have an hint you can have an hint layout for spinnerBlankLayout and a layout without dimensions for dropDownBlankLayout.
     *
     * @param adapter             user's adapter.
     * @param spinnerBlankLayout  layout for spinnerBlankView
     * @param dropDownBlankLayout layout for dropDownBlankView
     */
    public void setAdapterSpinnerDropDownBlankLayout(BaseAdapter adapter, int spinnerBlankLayout, int dropDownBlankLayout) {
        this.getInnerWidget().setAdapterSpinnerDropDownBlankLayout(adapter, spinnerBlankLayout, dropDownBlankLayout);
    }

}

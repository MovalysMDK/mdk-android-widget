package com.soprasteria.movalysmdk.widget.fixedlist;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.StyleableRes;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.soprasteria.movalysmdk.widget.core.MDKBaseRichWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasChangeListener;
import com.soprasteria.movalysmdk.widget.core.helper.AttributesHelper;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;
import com.soprasteria.movalysmdk.widget.fixedlist.adapters.WrapperViewHolder;

/**
 * Rich widget representing a fixed list component, conforming to the Material Design guidelines,
 * and including by default the error component.
 */
public class MDKRichFixedList extends MDKBaseRichWidget<MDKFixedList> implements HasChangeListener {

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes set
     */
    public MDKRichFixedList(Context context, AttributeSet attrs) {
        super(R.layout.mdkwidget_fixedlist_edit_label, R.layout.mdkwidget_fixedlist_edit, context, attrs);

        if (!isInEditMode()) {
            init(attrs);
        }
    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes set
     * @param defStyleAttr the style
     */
    public MDKRichFixedList(Context context, AttributeSet attrs, @StyleableRes int defStyleAttr) {
        super(R.layout.mdkwidget_fixedlist_edit_label, R.layout.mdkwidget_fixedlist_edit, context, attrs, defStyleAttr);

        if (!isInEditMode()) {
            init(attrs);
        }
    }

    /**
     * Sets an adapter on the inner MDKFixedList.
     * @param adapter the adapter to set
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        this.innerWidget.setAdapter(adapter);
    }

    /**
     * Set the attributes on the inner widget.
     * @param attrs the attributes to set
     */
    private void init(AttributeSet attrs) {
        TypedArray typedArray = this.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKFixedListComponent);

        this.innerWidget.getMDKWidgetDelegate().setAddButtonViewId(
                AttributesHelper.getIntFromResourceAttribute(typedArray, R.styleable.MDKCommons_MDKFixedListComponent_addButtonViewId, 0)
        );

        this.innerWidget.getMDKWidgetDelegate().setWrapperViewHolderClass(
                (Class<? extends WrapperViewHolder>) AttributesHelper
                        .getClassFromStringAttribute(typedArray, R.styleable.MDKCommons_MDKFixedListComponent_wrapperViewHolderClass, WrapperViewHolder.class.getName())
        );

        this.innerWidget.getMDKWidgetDelegate().setWrapperViewHolderLayout(
                AttributesHelper.getIntFromResourceAttribute(typedArray, R.styleable.MDKCommons_MDKFixedListComponent_wrapperViewHolderLayout, R.layout.delete_item_wrapper)
        );

        this.innerWidget.getMDKWidgetDelegate().setWrapperViewHolderDeleteId(
                AttributesHelper.getIntFromResourceAttribute(typedArray, R.styleable.MDKCommons_MDKFixedListComponent_wrapperViewHolderDeleteId, R.id.delete_item)
        );

        typedArray.recycle();
    }

    /**
     * Add a {@link FixedListAddListener} element.
     * @param listener the listener to add
     */
    public void addAddListener(FixedListAddListener listener) {
        this.innerWidget.addAddListener(listener);
    }

    /**
     * Remove a {@link FixedListAddListener} element.
     * @param listener the listener to remove
     */
    public void addRemoveListener(FixedListRemoveListener listener) {
        this.innerWidget.addRemoveListener(listener);
    }

    /**
     * Add a {@link FixedListItemClickListener} element.
     * @param listener the listener to add
     */
    public void addItemClickListener(FixedListItemClickListener listener) {
        this.innerWidget.addItemClickListener(listener);
    }

    @Override
    public void registerChangeListener(ChangeListener listener) {
        this.innerWidget.registerChangeListener(listener);
    }

    @Override
    public void unregisterChangeListener(ChangeListener listener) {
        this.innerWidget.unregisterChangeListener(listener);
    }

    /**
     * Sets a layout manager on the inner widget.
     * @param mLayoutManager the layout manager to set
     */
    public void setLayoutManager(RecyclerView.LayoutManager mLayoutManager) {
        this.innerWidget.setLayoutManager(mLayoutManager);
    }
}

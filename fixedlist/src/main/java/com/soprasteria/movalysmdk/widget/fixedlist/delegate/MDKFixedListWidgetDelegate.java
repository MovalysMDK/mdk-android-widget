package com.soprasteria.movalysmdk.widget.fixedlist.delegate;

import android.content.res.TypedArray;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.helper.AttributesHelper;
import com.soprasteria.movalysmdk.widget.fixedlist.R;
import com.soprasteria.movalysmdk.widget.fixedlist.adapters.WrapperViewHolder;
import com.soprasteria.movalysmdk.widget.fixedlist.layoutmanagers.WrapLinearLayoutManager;

import java.lang.ref.WeakReference;

/**
 * Delegate class for the MDKFixedList widget.
 */
public class MDKFixedListWidgetDelegate extends MDKWidgetDelegate {

    /** weak reference to the add button of the widget. */
    private WeakReference<View> addButtonView;

    /** class of the wrapping view holder of the list views. */
    private Class<? extends WrapperViewHolder> wrapperViewHolderClass;

    /** layout of the wrapping view holder. */
    @LayoutRes private int wrapperViewHolderLayout;

    /** identifier of the delete button in the layout of the wrapping view holder. */
    @IdRes private int wrapperViewHolderDeleteId;

    /** class of the layout manager to apply on the fixed list. */
    private Class<? extends RecyclerView.LayoutManager> layoutManagerClass;

    private int layoutManagerOrientation;

    /**
     * Constructor.
     * @param view  the view
     * @param attrs the parameters set
     */
    public MDKFixedListWidgetDelegate(View view, AttributeSet attrs) {
        super(view, attrs);

        TypedArray typedArray = view.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKFixedListComponent);

        addButtonView = AttributesHelper.getWeakViewFromResourceAttribute(view, typedArray, R.styleable.MDKCommons_MDKFixedListComponent_addButtonViewId, 0);

        wrapperViewHolderClass = (Class<? extends WrapperViewHolder>) AttributesHelper
                .getClassFromStringAttribute(typedArray, R.styleable.MDKCommons_MDKFixedListComponent_wrapperViewHolderClass, WrapperViewHolder.class.getName());
        wrapperViewHolderLayout = AttributesHelper
                .getIntFromResourceAttribute(typedArray, R.styleable.MDKCommons_MDKFixedListComponent_wrapperViewHolderLayout, R.layout.delete_item_wrapper);
        wrapperViewHolderDeleteId = AttributesHelper
                .getIntFromResourceAttribute(typedArray, R.styleable.MDKCommons_MDKFixedListComponent_wrapperViewHolderDeleteId, R.id.delete_item);

        layoutManagerClass = (Class<? extends RecyclerView.LayoutManager>) AttributesHelper
                .getClassFromStringAttribute(typedArray, R.styleable.MDKCommons_MDKFixedListComponent_layoutManagerClass, WrapLinearLayoutManager.class.getName());
        layoutManagerOrientation = AttributesHelper
                .getIntFromIntAttribute(typedArray, R.styleable.MDKCommons_MDKFixedListComponent_layoutManagerOrientation, LinearLayoutManager.VERTICAL);

        typedArray.recycle();
    }

    /**
     * Returns the add button set on the widget.
     * @return the add button set, null if none
     */
    public View getAddButton() {
        if (this.addButtonView != null) {
            return addButtonView.get();
        }
        return null;
    }

    /**
     * Sets the add button on the widget.
     * @param addButtonView the view to set as add button
     */
    public void setAddButtonView(View addButtonView) {
        this.addButtonView = new WeakReference<>(addButtonView);
    }

    /**
     * Returns the class of the wrapper view holder.
     * @return class of the wrapper view holder
     */
    public Class<? extends WrapperViewHolder> getWrapperViewHolderClass() {
        return wrapperViewHolderClass;
    }

    /**
     * Sets the class of the wrapper view holder.
     * @param wrapperViewHolderClass the class to use as a wrapper view holder
     */
    public void setWrapperViewHolderClass(Class<? extends WrapperViewHolder> wrapperViewHolderClass) {
        this.wrapperViewHolderClass = wrapperViewHolderClass;
    }

    /**
     * Returns the layout of the wrapper view holder.
     * @return the layout of the wrapper view holder
     */
    public int getWrapperViewHolderLayout() {
        return wrapperViewHolderLayout;
    }

    /**
     * Sets the layout of the wrapper view holder.
     * @param wrapperViewHolderLayout the layout of the wrapper view holder to set
     */
    public void setWrapperViewHolderLayout(int wrapperViewHolderLayout) {
        this.wrapperViewHolderLayout = wrapperViewHolderLayout;
    }

    /**
     * Returns the identifier of the delete button in the wrapper view holder layout.
     * @return the identifier of the delete button
     */
    public int getWrapperViewHolderDeleteId() {
        return wrapperViewHolderDeleteId;
    }

    /**
     * Sets the identifier of the delete button in the wrapper view holder layout.
     * @param wrapperViewHolderDeleteId the identifier of the delete button to set
     */
    public void setWrapperViewHolderDeleteId(int wrapperViewHolderDeleteId) {
        this.wrapperViewHolderDeleteId = wrapperViewHolderDeleteId;
    }

    /**
     * Returns the class of the layout manager.
     * @return class of the layout manager
     */
    public Class<? extends RecyclerView.LayoutManager> getLayoutManagerClass() {
        return layoutManagerClass;
    }

    /**
     * Sets the class of the layout manager.
     * @param layoutManagerClass the class to use as a layout manager
     */
    public void setLayoutManagerClass(Class<? extends RecyclerView.LayoutManager> layoutManagerClass) {
        this.layoutManagerClass = layoutManagerClass;
    }

    /**
     * Returns the layout manager orientation to apply.
     * @return the set orientation
     */
    public int getLayoutManagerOrientation() {
        return layoutManagerOrientation;
    }

    /**
     * Sets the layout manager orientation to apply.
     * @param layoutManagerOrientation the orientation to set
     */
    public void setLayoutManagerOrientation(int layoutManagerOrientation) {
        this.layoutManagerOrientation = layoutManagerOrientation;
    }
}

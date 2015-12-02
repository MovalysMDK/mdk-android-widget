package com.soprasteria.movalysmdk.widget.fixedlist.layoutmanagers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.soprasteria.movalysmdk.widget.core.behavior.NoScrollable;
import com.soprasteria.movalysmdk.widget.fixedlist.MDKFixedList;


/**
 * WrapLinearLayoutManager.
 **/
public class WrapLinearLayoutManager extends android.support.v7.widget.LinearLayoutManager implements NoScrollable {

    /**
     * Constructor.
     *
     * @param context an Android context
     */
    @SuppressWarnings("UnusedDeclaration")
    public WrapLinearLayoutManager(Context context) {
        super(context);
    }

    /**
     * Constructor.
     *
     * @param context       an Android context
     * @param orientation   the orientation to set
     * @param reverseLayout true if the layout can be reversed
     */
    @SuppressWarnings("UnusedDeclaration")
    public WrapLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    /**
     * Constructor.
     *
     * @param view the view of the RecyclerView
     */
    @SuppressWarnings("UnusedDeclaration")
    public WrapLinearLayoutManager(RecyclerView view) {
        super(view.getContext());
    }

    /**
     * Constructor.
     *
     * @param view          the view of the RecyclerView
     * @param orientation   the orientation to set
     * @param reverseLayout true if the layout can be reversed
     */
    @SuppressWarnings("UnusedDeclaration")
    public WrapLinearLayoutManager(RecyclerView view, int orientation, boolean reverseLayout) {
        super(view.getContext(), orientation, reverseLayout);
    }

    @Override
    public void updateDimension(RecyclerView recyclerView) {
        if (this.getOrientation() == VERTICAL) {
            this.updateHeight(recyclerView);
        } else {
            this.updateWidth(recyclerView);
        }
    }

    @Override
    public void updateHeight(RecyclerView recyclerView) {
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        if (params != null) {
            params.height = ((MDKFixedList) recyclerView).getWrapperAdapter().computeHeight(new LinearLayout(recyclerView.getContext()));
            recyclerView.setLayoutParams(params);
        }
    }

    @Override
    public void updateWidth(RecyclerView recyclerView) {
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        if (params != null) {
            params.height = ((MDKFixedList) recyclerView).getWrapperAdapter().computeHeight(new LinearLayout(recyclerView.getContext()));
            recyclerView.setLayoutParams(params);
        }
    }
}
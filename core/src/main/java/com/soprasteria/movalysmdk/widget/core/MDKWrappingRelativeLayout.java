package com.soprasteria.movalysmdk.widget.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Overloaded implementation of the RelativeLayout that
 */
public class MDKWrappingRelativeLayout extends RelativeLayout {

    public MDKWrappingRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MDKWrappingRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // First change the children layout parameters from "match_parent" to "wrap_content" and store
        // the list of modified children
        List<View> modifiedWidthChildren = new ArrayList<>();
        List<View> modifiedHeightChildren = new ArrayList<>();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; ++i) {
            // Get the view
            View currentView = getChildAt(i);

            // If the view is in match_parent, record that fact and force it to wrap_content for the
            // time of the measure
            if (currentView.getLayoutParams().width == ViewGroup.LayoutParams.MATCH_PARENT) {
                modifiedWidthChildren.add(currentView);
                currentView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
            if (currentView.getLayoutParams().height == ViewGroup.LayoutParams.MATCH_PARENT) {
                modifiedHeightChildren.add((currentView));
                currentView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
        }

        // Trigger the relative layout measure
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Enforce measured width and height
        for (int i = 0; i < modifiedWidthChildren.size(); ++i) {
            modifiedWidthChildren.get(i).getLayoutParams().width = this.getMeasuredWidth();
        }
        for (int i = 0; i < modifiedHeightChildren.size(); ++i) {
            modifiedHeightChildren.get(i).getLayoutParams().height = this.getMeasuredHeight();
        }
    }
}

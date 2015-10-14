package com.soprasteria.movalysmdk.widget.fixedlist.layoutmanagers;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.soprasteria.movalysmdk.widget.fixedlist.BuildConfig;

import java.lang.reflect.Field;

/**
 * {@link android.support.v7.widget.LinearLayoutManager} which wraps its content. Note that this class will always
 * wrap the content regardless of {@link android.support.v7.widget.RecyclerView} layout parameters.
 * <p/>
 * Now it's impossible to run add/remove animations with child views which have arbitrary dimensions (height for
 * VERTICAL orientation and width for HORIZONTAL). However if child views have fixed dimensions
 * {@link #setChildSize(int)} method might be used to let the layout manager know how big they are going to be.
 * If animations are not used at all then a normal measuring procedure will run and child views will be measured during
 * the measure pass.
 */
public class WrapLinearLayoutManager extends android.support.v7.widget.LinearLayoutManager {

    /** Tag for debugging. */
	private static final String TAG = WrapLinearLayoutManager.class.getSimpleName();

    /** Tag for debugging. */
    private static final String LINEAR_LAYOUT_MANAGER = "LinearLayoutManager";

    /** set to true if the insets can be made dirty. */
	private static boolean canMakeInsetsDirty = true;

	/** insets dirty field. */
	private static Field insetsDirtyField = null;

	/** width of the child. */
	private static final int CHILD_WIDTH = 0;

	/** height of the child. */
	private static final int CHILD_HEIGHT = 1;

	/** default child size. */
	private static final int DEFAULT_CHILD_SIZE = 100;

	/** array storing a child dimensions. */
	private final int[] childDimensions = new int[2];

	/** a view of the RecyclerView. */
	private final RecyclerView view;

	/** current child size. */
	private int childSize = DEFAULT_CHILD_SIZE;

	/** true if a child size was set. */
	private boolean hasChildSize;

	/** overscroll mode. */
	private int overScrollMode = ViewCompat.OVER_SCROLL_ALWAYS;

	/** temporary rectangle. */
	private final Rect tmpRect = new Rect();

	/**
	 * Constructor.
	 * @param context an Android context
	 */
	@SuppressWarnings("UnusedDeclaration")
	public WrapLinearLayoutManager(Context context) {
		super(context);
		this.view = null;
	}

	/**
	 * Constructor.
	 * @param context an Android context
	 * @param orientation the orientation to set
	 * @param reverseLayout true if the layout can be reversed
	 */
	@SuppressWarnings("UnusedDeclaration")
	public WrapLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
		super(context, orientation, reverseLayout);
		this.view = null;
	}

	/**
	 * Constructor.
	 * @param view the view of the RecyclerView
	 */
	@SuppressWarnings("UnusedDeclaration")
	public WrapLinearLayoutManager(RecyclerView view) {
		super(view.getContext());
		this.view = view;
		this.overScrollMode = ViewCompat.getOverScrollMode(view);
	}

	/**
	 * Constructor.
	 * @param view the view of the RecyclerView
	 * @param orientation the orientation to set
	 * @param reverseLayout true if the layout can be reversed
	 */
	@SuppressWarnings("UnusedDeclaration")
	public WrapLinearLayoutManager(RecyclerView view, int orientation, boolean reverseLayout) {
		super(view.getContext(), orientation, reverseLayout);
		this.view = view;
		this.overScrollMode = ViewCompat.getOverScrollMode(view);
	}

	/**
	 * Sets the overscroll mode.
	 * @param overScrollMode the mode to apply
	 */
	public void setOverScrollMode(int overScrollMode) {
		if (overScrollMode < ViewCompat.OVER_SCROLL_ALWAYS || overScrollMode > ViewCompat.OVER_SCROLL_NEVER) {
			throw new IllegalArgumentException("Unknown overscroll mode: " + overScrollMode);
		}

		if (this.view == null) {
			throw new IllegalStateException("view == null");
		}

		this.overScrollMode = overScrollMode;
		ViewCompat.setOverScrollMode(view, overScrollMode);
	}

	/**
	 * returns an unspecified measure spec.
	 * @return an unspecified measure spec
	 */
	public static int makeUnspecifiedSpec() {
		return View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
	}

	@Override
	public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
		final int widthMode = View.MeasureSpec.getMode(widthSpec);
		final int heightMode = View.MeasureSpec.getMode(heightSpec);

		final int widthSize = View.MeasureSpec.getSize(widthSpec);
		final int heightSize = View.MeasureSpec.getSize(heightSpec);

		final boolean hasWidthSize = widthMode != View.MeasureSpec.UNSPECIFIED;
		final boolean hasHeightSize = heightMode != View.MeasureSpec.UNSPECIFIED;

		final boolean exactWidth = widthMode == View.MeasureSpec.EXACTLY;
		final boolean exactHeight = heightMode == View.MeasureSpec.EXACTLY;

		final int unspecified = makeUnspecifiedSpec();

		if (exactWidth && exactHeight) {
			// in case of exact calculations for both dimensions let's use default "onMeasure" implementation
			super.onMeasure(recycler, state, widthSpec, heightSpec);
			return;
		}

		final boolean vertical = getOrientation() == VERTICAL;

		initChildDimensions(widthSize, heightSize, vertical);

		int width = 0;
		int height = 0;

		// it's possible to get scrap views in recycler which are bound to old (invalid) adapter entities. This
		// happens because their invalidation happens after "onMeasure" method. As a workaround let's clear the
		// recycler now (it should not cause any performance issues while scrolling as "onMeasure" is never
		// called whiles scrolling)
		recycler.clear();

		final int stateItemCount = state.getItemCount();
		final int adapterItemCount = getItemCount();
		// adapter always contains actual data while state might contain old data (f.e. data before the animation is
		// done). As we want to measure the view with actual data we must use data from the adapter and not from  the
		// state
		for (int i = 0; i < adapterItemCount; i++) {
			if (vertical) {
				if (!hasChildSize) {
					if (i < stateItemCount) {
						// we should not exceed state count, otherwise we'll get IndexOutOfBoundsException. For such items
						// we will use previously calculated dimensions
						measureChild(recycler, i, widthSize, unspecified, childDimensions);
					} else {
						logMeasureWarning(i);
					}
				}
				height += childDimensions[CHILD_HEIGHT];
				if (i == 0) {
					width = childDimensions[CHILD_WIDTH];
				}
				if (hasHeightSize && height >= heightSize) {
					break;
				}
			} else {
				if (!hasChildSize) {
					if (i < stateItemCount) {
						// we should not exceed state count, otherwise we'll get IndexOutOfBoundsException. For such items
						// we will use previously calculated dimensions
						measureChild(recycler, i, unspecified, heightSize, childDimensions);
					} else {
						logMeasureWarning(i);
					}
				}
				width += childDimensions[CHILD_WIDTH];
				if (i == 0) {
					height = childDimensions[CHILD_HEIGHT];
				}
				if (hasWidthSize && width >= widthSize) {
					break;
				}
			}
		}

		if (exactWidth) {
			width = widthSize;
		} else {
			width += getPaddingLeft() + getPaddingRight();
			if (hasWidthSize) {
				width = Math.min(width, widthSize);
			}
		}

		if (exactHeight) {
			height = heightSize;
		} else {
			height += getPaddingTop() + getPaddingBottom();
			if (hasHeightSize) {
				height = Math.min(height, heightSize);
			}
		}

		setMeasuredDimension(width, height);

		if (view != null && overScrollMode == ViewCompat.OVER_SCROLL_IF_CONTENT_SCROLLS) {
			final boolean fit = (vertical && (!hasHeightSize || height < heightSize))
					|| (!vertical && (!hasWidthSize || width < widthSize));

			ViewCompat.setOverScrollMode(view, fit ? ViewCompat.OVER_SCROLL_NEVER : ViewCompat.OVER_SCROLL_ALWAYS);
		}
	}

	/**
	 * logger method for the widget measurement.
	 * @param child the identifier of the child being measured
	 */
	private void logMeasureWarning(int child) {
		if (BuildConfig.DEBUG) {
			Log.w(LINEAR_LAYOUT_MANAGER, "Can't measure child #" + child + ", previously used dimensions will be reused." +
					"To remove this message either use #setChildSize() method or don't run RecyclerView animations");
		}
	}

	/**
	 * Initializes the child dimensions.
	 * @param width the width to set
	 * @param height the height to set
	 * @param vertical true if the child will be made vertical
	 */
	private void initChildDimensions(int width, int height, boolean vertical) {
		if (childDimensions[CHILD_WIDTH] != 0 || childDimensions[CHILD_HEIGHT] != 0) {
			// already initialized, skipping
			return;
		}
		if (vertical) {
			childDimensions[CHILD_WIDTH] = width;
			childDimensions[CHILD_HEIGHT] = childSize;
		} else {
			childDimensions[CHILD_WIDTH] = childSize;
			childDimensions[CHILD_HEIGHT] = height;
		}
	}

	@Override
	public void setOrientation(int orientation) {
		// might be called before the constructor of this class is called
		//noinspection ConstantConditions
		if (childDimensions != null && getOrientation() != orientation) {
			childDimensions[CHILD_WIDTH] = 0;
			childDimensions[CHILD_HEIGHT] = 0;
		}
		super.setOrientation(orientation);
	}

	/**
	 * Clears the child size.
	 */
	public void clearChildSize() {
		hasChildSize = false;
		setChildSize(DEFAULT_CHILD_SIZE);
	}

	/**
	 * Sets the child size.
	 * @param childSize the size to set
	 */
	public void setChildSize(int childSize) {
		hasChildSize = true;
		if (this.childSize != childSize) {
			this.childSize = childSize;
			requestLayout();
		}
	}

	/**
	 * Internal measurement method.
	 * @param recycler the RecyclerView being measured
	 * @param position the position of the view in the RecyclerView
	 * @param widthSize the width to measure
	 * @param heightSize the height to measure
	 * @param dimensions the measured dimensions
	 */
	private void measureChild(RecyclerView.Recycler recycler, int position, int widthSize, int heightSize, int[] dimensions) {
		final View child;
		try {
			child = recycler.getViewForPosition(position);
		} catch (IndexOutOfBoundsException e) {
			if (BuildConfig.DEBUG) {
				Log.w(LINEAR_LAYOUT_MANAGER, "LinearLayoutManager doesn't work well with animations. Consider switching them off", e);
			}
			return;
		}

		final RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) child.getLayoutParams();

		final int hPadding = getPaddingLeft() + getPaddingRight();
		final int vPadding = getPaddingTop() + getPaddingBottom();

		final int hMargin = p.leftMargin + p.rightMargin;
		final int vMargin = p.topMargin + p.bottomMargin;

		// we must make insets dirty in order calculateItemDecorationsForChild to work
		makeInsetsDirty(p);
		// this method should be called before any getXxxDecorationXxx() methods
		calculateItemDecorationsForChild(child, tmpRect);

		final int hDecoration = getRightDecorationWidth(child) + getLeftDecorationWidth(child);
		final int vDecoration = getTopDecorationHeight(child) + getBottomDecorationHeight(child);

		final int childWidthSpec = getChildMeasureSpec(widthSize, hPadding + hMargin + hDecoration, p.width, canScrollHorizontally());
		final int childHeightSpec = getChildMeasureSpec(heightSize, vPadding + vMargin + vDecoration, p.height, canScrollVertically());

		child.measure(childWidthSpec, childHeightSpec);

		dimensions[CHILD_WIDTH] = getDecoratedMeasuredWidth(child) + p.leftMargin + p.rightMargin;
		dimensions[CHILD_HEIGHT] = getDecoratedMeasuredHeight(child) + p.bottomMargin + p.topMargin;

		// as view is recycled let's not keep old measured values
		makeInsetsDirty(p);
		recycler.recycleView(child);
	}

	/**
	 * call this to make insets dirty.
	 * @param p the params to set
	 */
	private static void makeInsetsDirty(RecyclerView.LayoutParams p) {
		if (!canMakeInsetsDirty) {
			return;
		}
		try {
			if (insetsDirtyField == null) {
				insetsDirtyField = RecyclerView.LayoutParams.class.getDeclaredField("mInsetsDirty");
				insetsDirtyField.setAccessible(true);
			}
			insetsDirtyField.set(p, true);
		} catch (NoSuchFieldException e) {
			Log.e(TAG, "NoSuchFieldException", e);
			onMakeInsertDirtyFailed();
		} catch (IllegalAccessException e) {
			Log.e(TAG, "IllegalAccessException", e);
			onMakeInsertDirtyFailed();
		}
	}

	/**
	 * Called when the makeInsetsDirty method fails.
	 */
	private static void onMakeInsertDirtyFailed() {
		canMakeInsetsDirty = false;
		if (BuildConfig.DEBUG) {
			Log.w(LINEAR_LAYOUT_MANAGER, "Can't make LayoutParams insets dirty, decorations measurements might be incorrect");
		}
	}
}
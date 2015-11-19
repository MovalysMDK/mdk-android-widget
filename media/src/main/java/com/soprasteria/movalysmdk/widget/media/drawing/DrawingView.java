/**
 * Copyright (C) 2010 Sopra Steria Group (movalys.support@soprasteria.com)
 * <p/>
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
package com.soprasteria.movalysmdk.widget.media.drawing;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;

import com.soprasteria.movalysmdk.widget.media.drawing.data.Brush;
import com.soprasteria.movalysmdk.widget.media.drawing.data.CircleElement;
import com.soprasteria.movalysmdk.widget.media.drawing.data.DrawingElement;
import com.soprasteria.movalysmdk.widget.media.drawing.data.DrawingElement.UpdateResult;
import com.soprasteria.movalysmdk.widget.media.drawing.data.SvgDocument;

import java.util.LinkedList;
import java.util.List;

/**
 * Display a drawing enabled area. The DrawingView class is a View in which you can draw with many tools,
 * and apply any style to your drawn elements. Drawing data is loaded and saved as SVG, which permits
 * to edit and save custom drawing in a lighweight manner.
 * <p/>
 * The component has the following features :
 * - background image : you can set a background image on which the user will be able to draw
 * - zoom in/out move : for more precision in the drawing, you can easily manipulate the drawing
 * - picking : each drawn element is corresponding to an SVG element (eg. if you draw a line, that
 * line corresponds to &lt;line/&gt; SVG element). The component has a picking mode that permits
 * to select a drawn element and manipulate it (remove,...)
 * - SVG loading/saving : all the drawing data is imported/exported as SVG, which is a vectorial
 * format. No more quality loss !
 * - Edition history : you can undo/redo drawing
 */
public class DrawingView extends View implements OnScaleGestureListener {

    /**
     * Callback interface for observing the DrawingView events.
     */
    public interface DrawingViewEventHandler {
        /**
         * Handles an item selected event.
         *
         * @param selectedElement the drawing element that has been selected.
         */
        void onItemSelected(DrawingElement selectedElement);

        /**
         * Handles the deselection event.
         */
        void onItemDeselected();

        /**
         * Handles the DrawingView's mode changed event.
         *
         * @param mode the DrawingView's new mode
         */
        void onModeChanged(Mode mode);

        /**
         * Handle the DrawingView's tool changed event.
         *
         * @param toolClassName the DrawingView's new tool
         */
        void onDrawingToolChanged(Class<? extends DrawingElement> toolClassName);
    }

    /**
     * Available view modes
     */
    public enum Mode {
        /**
         * Element selection mode.
         **/
        SELECTING,
        /**
         * Element moving mode.
         **/
        MOVING,
        /**
         * Element drawing mode.
         **/
        DRAWING,
    }

    /**
     * Available drawing tools
     */
    public enum Tool {
        /**
         * Text svg element.
         **/
        TEXT,
        /**
         * Hand free svg element.
         **/
        HAND_FREE,
        /**
         * Circle svg element.
         **/
        CIRCLE,
        /**
         * Oval svg element.
         **/
        OVAL,
        /**
         * Rectangle svg element.
         **/
        RECTANGLE,
        /**
         * Arrow svg element.
         **/
        ARROW,
        /**
         * Double arrow svg element.
         **/
        DOUBLE_ARROW
    }

    /**
     * Handler for event callbacks.
     */
    DrawingViewEventHandler mHandler = null;

    // Global view handling
    /**
     * Handler for scale gesture detection.
     */
    ScaleGestureDetector mScaleGestureDetector = new ScaleGestureDetector(this.getContext(), this);
    /**
     * Handler for scroll gesture detection.
     */
    GestureDetector mScrollGestureDetector;
    /**
     * Width of the drawing.
     */
    private int mDrawingWidth;
    /**
     * Height of the drawing.
     */
    private int mDrawingHeight;
    /**
     * Background to be painted.
     */
    private Bitmap mBackground = null;
    /**
     * Brush to fill the background.
     */
    private Brush mBackgroundBrush;
    /**
     * Defines the area in which drawing is allowed.
     */
    private RectF mDrawingArea = new RectF();
    /**
     * Minimum scale factor applied.
     */
    private float mMinScaleFactor = 1.0f;
    /**
     * Maximum scale factor applicable.
     */
    private float mMaxScaleFactor = 20.0f;
    /**
     * Maximum scale factor percentage applicable based on the original factor.
     */
    private float mMaxScalePercentage = 20.0f;
    /**
     * Current scale factor applied.
     */
    private float mCurrentScaleFactor;
    /**
     * Translation vector applied to the drawing.
     */
    private PointF mTranslation;
    /**
     * Transformation matrix applied to the view.
     */
    private Matrix mViewMatrix;
    /**
     * Invert of the transformation matrix applied to the view.
     */
    private Matrix mInvertViewMatrix;
    /**
     * Helper array for coordinates transformation.
     */
    private float[] points = new float[2];

    // Current drawing mode & tool dependent data
    /**
     * Current drawing mode.
     */
    private Mode mMode;
    /**
     * Previous drawing mode.
     */
    private Mode mOldMode;
    /**
     * Current drawing tool.
     */
    Class<? extends DrawingElement> mDrawingTool = null;
    /**
     * Current brush used for drawing.
     */
    private Brush mCurrentBrush;
    /**
     * Current drawing data.
     */
    private DrawingElement mCurrentDrawingData;
    /**
     * Keyboard visible flag.
     */
    private boolean mIsKeyboardShown = false;
    /**
     * Translation vector applied before the text edition start.
     */
    private PointF mTranslationBeforeKeyboardEdit = new PointF(0f, 0f);
    /**
     * Duration of the animation focusing the input text.
     */
    private int mTextFocusAnimationDuration = 400;
    /**
     * Duration of the animation unfocusing the input text.
     */
    private int mTextUnfocusAnimationDuration = 400;

    /**
     * Read only drawing elements stack.
     */
    private List<DrawingElement> mReadOnlyElementsStack;
    /**
     * Editable drawing elements stack.
     */
    private List<DrawingElement> mEditableElementsStack;
    /**
     * Max data stack pointer.
     */
    private int mDataHighStackPointer;
    /**
     * Min data stack pointer.
     */
    private int mDataLowStackPointer;
    /**
     * Selected line index.
     */
    private int mSelectedIndex = -1;
    /**
     * Selected brush.
     */
    private Brush mSelectedBrush;
    /**
     * Centroid brush.
     */
    private Brush mCentroidBrush;
    /**
     * Selection zone brush.
     */
    private Brush mSelectionZoneBrush;
    /**
     * Threshold used to determine wether something has to be selected or not.
     */
    private float mSelectionThreshold;
    /**
     * Percentage of the view used for selection ratio.
     */
    private float mSelectionThresholdRatio = 0.04f;


    /**
     * Scroll gesture listener.
     */
    private final GestureDetector.SimpleOnGestureListener mScrollListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            mTranslation.x -= distanceX;
            mTranslation.y -= distanceY;

            // Only prevent parent to intercept touch event if the view can be scrolled
            if ((distanceY < 0.0f && mTranslation.y >= 0.0f)
                    || (distanceY > 0.0f && mTranslation.y <= getHeight() - mDrawingHeight * mCurrentScaleFactor)) {
                // Allow parent views to intercept touch events
                getParent().requestDisallowInterceptTouchEvent(false);
            }

            // Update the view matrices
            updateMatrices();

            return true;
        }
    };

    /**
     * Constructor.
     * Calls the initialization method.
     *
     * @param context the parent activity's context.
     */
    public DrawingView(Context context) {
        super(context);
        reset();
    }

    /**
     * Constructor.
     * Calls the initialization method.
     *
     * @param context the parent activity's context.
     * @param attrs   the attribute set
     */
    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        reset();
    }

    /**
     * Constructor.
     * Calls the initialization method.
     *
     * @param context      the parent activity's context.
     * @param attrs        the attribute set
     * @param defStyleAttr the style to apply
     */
    public DrawingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        reset();
    }

    /**
     * Initialize the drawing view.
     */
    public void reset() {

        // Enable hardware caching
        setLayerType(View.LAYER_TYPE_HARDWARE, null);

        setFocusable(true);
        setFocusableInTouchMode(true);

        // Instantiate the scroll gesture detector
        mScrollGestureDetector = new GestureDetector(this.getContext(), mScrollListener);

        mTranslation = new PointF(0f, 0f);
        mViewMatrix = new Matrix();
        mInvertViewMatrix = new Matrix();
        mCurrentScaleFactor = 1.0f;

        mReadOnlyElementsStack = new LinkedList<>();
        mEditableElementsStack = new LinkedList<>();
        mDataHighStackPointer = 0;
        mDataLowStackPointer = 0;
        mSelectedIndex = -1;

        mMode = Mode.MOVING;
        mOldMode = Mode.MOVING;

        mDrawingTool = CircleElement.class;
        mCurrentBrush = new Brush();

        mBackground = null;
        mBackgroundBrush = new Brush();
        mBackgroundBrush.setFillColor(0xFFFFFFFF);

        mSelectedBrush = new Brush();
        mSelectedBrush.setColor(0xFF9370DB);
        mSelectedBrush.setStrokeWidth(5);
        mSelectedBrush.setStyle(Brush.Style.STROKE);
        mSelectedBrush.setStrokeJoin(Paint.Join.MITER);
        mSelectedBrush.setStrokeCap(Paint.Cap.BUTT);

        mCentroidBrush = new Brush(mSelectedBrush);
        mCentroidBrush.setStrokeWidth(10);

        mSelectionZoneBrush = new Brush(mCurrentBrush);
        mSelectionZoneBrush.setColor(0xAA0000FF);
        mSelectionZoneBrush.setStyle(Brush.Style.FILL);

        mSelectionThreshold = 0;

        setDrawingSize(100, 100, true);
    }

    /**
     * Sets the callback handler for view events.
     *
     * @param handler handler to set
     */
    public void setDrawingViewEventHandler(DrawingViewEventHandler handler) {
        this.mHandler = handler;
    }

    /**
     * Sets the drawing background to the provided drawable resource.
     *
     * @param drawableId the drawable to set as background
     */
    public void setDrawingBackground(int drawableId) {

        this.mBackground = BitmapFactory.decodeResource(this.getResources(), drawableId);

        setDrawingBackground(mBackground);
    }

    /**
     * Sets the background to the provided bitmap.
     *
     * @param backgroundBitmap the bitmap to set as background
     */
    public void setDrawingBackground(Bitmap backgroundBitmap) {

        this.mBackground = backgroundBitmap;

        if (this.mBackground != null) {
            setDrawingSize(mBackground.getWidth(), mBackground.getHeight());
        }
    }

    /**
     * Returns the background bitmap or null if there is no background.
     *
     * @return the background's bitmap
     */
    public Bitmap getDrawingBackground() {

        return mBackground;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = -1;
        int height = -1;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST) {
            width = widthSize;
        }
        if (heightMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.AT_MOST) {
            height = heightSize;
        }
        if (width != -1 && height == -1) {
            height = width * mDrawingHeight / mDrawingWidth;
        }
        if (width == -1 && height != -1) {
            width = height * mDrawingWidth / mDrawingHeight;
        }
        if (width == -1) {
            width = mDrawingWidth;
        }
        if (height == -1) {
            height = mDrawingHeight;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        // Updates the scale factors
        updateScaleFactor();
        // Updates the various matrices used for rendering
        updateMatrices();

        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * Sets the drawing size.
     * If updateDrawingArea is true, sets the drawing allowed area at the drawing size.
     *
     * @param width             width of the area
     * @param height            height of the area
     * @param updateDrawingArea flag that allows to update the drawing area
     */
    public void setDrawingSize(int width, int height, boolean updateDrawingArea) {

        // Keep the largest drawing area out of the previous and the new one
        mDrawingWidth = Math.max(mDrawingWidth, width);
        mDrawingHeight = Math.max(mDrawingHeight, height);
        // Update the allowed drawing area (by default the whole area)
        if (updateDrawingArea) {
            mDrawingArea.left = 0;
            mDrawingArea.top = 0;
            mDrawingArea.bottom = mDrawingHeight;
            mDrawingArea.right = mDrawingWidth;
        }
        // Updates the scale factors
        updateScaleFactor();
        // Updates the various matrices used for rendering
        updateMatrices();

        this.requestLayout();
        this.invalidate();
    }

    /**
     * Sets the drawing size and the drawing allowed size.
     *
     * @param width  width of the area
     * @param height height of the area
     */
    public void setDrawingSize(int width, int height) {
        setDrawingSize(width, height, true);
    }

    /**
     * Updates the scale factors, used to limit the minimum and maximum allowed zoom.
     * As this operation could result in an invalid camera position state, a camera reset is done
     * (reset of the scale factor to the minimum, reset of the translation vector). The result of
     * this reset is that the whole drawing area will be visible.
     */
    private void updateScaleFactor() {

        // Updates the scale factor range
        float widthRatio = this.getWidth() / (float) mDrawingWidth;
        float heightRatio = this.getHeight() / (float) mDrawingHeight;
        mMinScaleFactor = Math.min(widthRatio, heightRatio);
        mMaxScaleFactor = mMinScaleFactor * mMaxScalePercentage;
        // Set the current scale factor to the minimum (image fits undeformed in the view)
        mCurrentScaleFactor = mMinScaleFactor;
        // Reset the translation vector
        mTranslation.x = 0;
        mTranslation.y = 0;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle b = new Bundle();

        Parcelable superState = super.onSaveInstanceState();

        b.putParcelable("superState", superState);

        b.putString("svg", this.saveSvg());

        return b;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {

        Bundle b = (Bundle) state;
        Parcelable superState = ((Bundle) state).getParcelable("superState");
        this.loadSvg(b.getString("svg"));

        super.onRestoreInstanceState(superState);
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {

        // Apply the scale factor delta detected to the view scale factor
        float scaleFactor = detector.getScaleFactor();
        mCurrentScaleFactor = Math.max(mMinScaleFactor, Math.min(mCurrentScaleFactor * scaleFactor, mMaxScaleFactor));
        // Compute the new origin coordinates
        mTranslation.x = scaleFactor * (mTranslation.x - detector.getFocusX()) + detector.getFocusX();
        mTranslation.y = scaleFactor * (mTranslation.y - detector.getFocusY()) + detector.getFocusY();
        // Updates the various matrices used for rendering
        updateMatrices();
        // Returns true so that the event is considered as consumed by the ScaleGestureDetector
        return true;
    }


    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {

        onScale(detector);
        // Returns true so that the event is considered as consumed by the ScaleGestureDetector
        return true;
    }


    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

        onScale(detector);
    }

    /**
     * Main drawing method. Draws the background, then the read only SVG elements, and finally the
     * read/write SVG elements.
     *
     * @param canvas Canvas to draw in
     */
    @Override
    protected void onDraw(Canvas canvas) {

        DrawingElement currentData;
        int index;

        super.onDraw(canvas);

        canvas.save();

        canvas.concat(mViewMatrix);

        // Fill the background with base color
        canvas.drawRect(0, 0, mDrawingWidth, mDrawingHeight, mBackgroundBrush.getFillPaint());
        // Draw the background layer
        if (mBackground != null) {
            canvas.drawBitmap(mBackground, 0, 0, null);
        }

        // Draw the read only elements
        for (DrawingElement currentElement : mReadOnlyElementsStack) {
            currentElement.draw(canvas, null, null);
        }

        // Draw the editable elements
        for (index = mDataLowStackPointer; index < mDataHighStackPointer; ++index) {

            currentData = mEditableElementsStack.get(index);
            // Draw the data
            if (index == mSelectedIndex) {
                mSelectedBrush.setStrokeWidth(currentData.getBrush().getStrokePaint().getStrokeWidth());
                currentData.draw(canvas, mSelectedBrush, mCentroidBrush);
            } else {
                currentData.draw(canvas, null, null);
            }
        }

        // While selecting, draw all the centroids and the selection threshold circle
        if (mMode == Mode.SELECTING) {
            for (index = mDataLowStackPointer; index < mDataHighStackPointer; ++index) {
                currentData = mEditableElementsStack.get(index);
                canvas.drawCircle(currentData.getCentroid().x, currentData.getCentroid().y, mSelectionThreshold, mSelectionZoneBrush.getFillPaint());
            }
        }

        canvas.restore();
    }

    /**
     * Updates the camera matrix, according to the current scale factor and translation vector.
     * Also computes the inverted matrix for coordinates projection.
     *
     * @param clampData If true, the translation vector is corrected so that the drawing area cannot be
     *                  "lost" while translating.
     */
    private void updateMatrices(boolean clampData) {

        // Clamp the translation vector
        if (clampData) {
            if (mDrawingWidth * mCurrentScaleFactor < getWidth()) {
                mTranslation.x = (getWidth() - mDrawingWidth * mCurrentScaleFactor) / 2;
            } else {
                mTranslation.x = (float) Math.min(0.0, Math.max(mTranslation.x, getWidth() - mDrawingWidth * mCurrentScaleFactor));
            }
            if (mDrawingHeight * mCurrentScaleFactor < getHeight()) {
                mTranslation.y = (getHeight() - mDrawingHeight * mCurrentScaleFactor) / 2;
            } else {
                mTranslation.y = (float) Math.min(0.0, Math.max(mTranslation.y, getHeight() - mDrawingHeight * mCurrentScaleFactor));
            }
        }

        // Update the view matrix
        mViewMatrix.setValues(new float[]{mCurrentScaleFactor, 0, mTranslation.x,
                0, mCurrentScaleFactor, mTranslation.y,
                0, 0, 1});
        // Update the inverse of the view matrix (can always be inverted)
        mViewMatrix.invert(mInvertViewMatrix);

        // Update the selection circle radius so that it is
        mSelectionThreshold = Math.max(this.getWidth(), this.getHeight()) * mSelectionThresholdRatio / mCurrentScaleFactor;
    }

    /**
     * Updates the camera matrix, according to the current scale factor and translation vector.
     * Also computes the inverted matrix for coordinates projection.
     */
    private void updateMatrices() {
        updateMatrices(true);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!mIsKeyboardShown) {
            switch (mMode) {
                case DRAWING:
                    // If in read only mode, don't let the user draw anything
                    onDrawEvent(event);
                    break;
                case SELECTING:
                    onSelectEvent(event);
                    break;
                case MOVING:
                    onMovingEvent(event);
                    break;
                default:
                    break;
            }

            // Refresh the view when there have been a touch event
            // TODO: can be optimised to refresh only when wanted
            this.invalidate();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            // Hide the keyboard
            hideKeyboard();
        }

        return true;
    }

    /**
     * Motion event handler used while in Drawing mode.
     *
     * @param event the motion event that was used to draw
     */
    private void onDrawEvent(MotionEvent event) {

        // Prevent parent views to intercept touch events
        getParent().requestDisallowInterceptTouchEvent(true);

        // Convert from view coordinates to drawing coordinates
        points[0] = event.getX();
        points[1] = event.getY();
        mInvertViewMatrix.mapPoints(points);
        // Constrain the point into the drawing bounds
        points[0] = (points[0] < mDrawingArea.left) ? mDrawingArea.left : points[0];
        points[0] = (points[0] > mDrawingArea.right) ? mDrawingArea.right : points[0];
        points[1] = (points[1] < mDrawingArea.top) ? mDrawingArea.top : points[1];
        points[1] = (points[1] > mDrawingArea.bottom) ? mDrawingArea.bottom : points[1];

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            try {
                // Start by destroying the eventual redo actions
                while (mDataHighStackPointer < mEditableElementsStack.size()) {
                    ((LinkedList)mEditableElementsStack).removeLast();
                }
                // Create a new drawing data instance, with the currently selected brush
                mCurrentDrawingData = mDrawingTool.newInstance();
                mCurrentDrawingData.setBrush(new Brush(mCurrentBrush));
                // Update the drawing data
                DrawingElement.UpdateResult updateResult = mCurrentDrawingData.onTouchEvent(event, points[0], points[1]);
                // Add the currently creating data to the data stack
                mEditableElementsStack.add(mCurrentDrawingData);
                ++mDataHighStackPointer;
                // Handle update result
                handleUpdateResult(updateResult);

            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), e.getMessage(), e);
            }
        } else {
            // Update the drawing data
            DrawingElement.UpdateResult updateResult = null;
            if (mCurrentDrawingData != null) {
                updateResult = mCurrentDrawingData.onTouchEvent(event, points[0], points[1]);
                // Handle update result
                handleUpdateResult(updateResult);
            }
            // Reset the current drawing data field
            if (event.getAction() == MotionEvent.ACTION_UP && updateResult != UpdateResult.ASK_FOR_KEYBOARD) {
                mCurrentDrawingData = null;
            }
        }
    }


    /**
     * When a DrawingElement asks for a specific action, this method handles it.
     * Handled cases are :
     * - The DrawingElement asks the keyboard to be shown
     *
     * @param updateResult the updateresult to handle
     */
    private void handleUpdateResult(UpdateResult updateResult) {

        if (updateResult == UpdateResult.ASK_FOR_KEYBOARD) {
        // Move the view so that the text input can be seen
            // Backup the previous translation data
            mTranslationBeforeKeyboardEdit.x = mTranslation.x;
            mTranslationBeforeKeyboardEdit.y = mTranslation.y;
            // Compute the future new translation coordinates
            points[0] = mCurrentDrawingData.getCentroid().x;
            points[1] = mCurrentDrawingData.getCentroid().y;
            mViewMatrix.mapPoints(points);
            // Start the view animation to the text edition position
            ValueAnimator animator = ValueAnimator.ofObject(new PositionEvaluator(),
                    mTranslationBeforeKeyboardEdit,
                    new PointF(mTranslation.x - points[0] + mDrawingWidth / 8f, mTranslation.y - points[1] + mDrawingHeight / 8f));
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator arg0) {
                    //Nothing to do
                }
                @Override
                public void onAnimationRepeat(Animator arg0) {
                    //Nothing to do
                }
                @Override
                public void onAnimationEnd(Animator arg0) {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInputFromWindow(DrawingView.this.getWindowToken(),InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    mIsKeyboardShown = true;
                }
                @Override
                public void onAnimationCancel(Animator arg0) {
                    //Nothing to do
                }
            });

            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    PointF animatedValue = (PointF) animator.getAnimatedValue();
                    mTranslation.x = animatedValue.x;
                    mTranslation.y = animatedValue.y;
                    updateMatrices(false);
                    DrawingView.this.invalidate();
                }
            });
            animator.setDuration(mTextFocusAnimationDuration);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.start();


        }
    }

    /**
     * Motion event handler used while in Selection mode.
     * This handler selects the nearest data path by searching the nearest centroid path
     *
     * @param event Current MotionEvent
     */
    private void onSelectEvent(MotionEvent event) {

        // Used for computing local score
        float score;
        // Used for keeping the best score, and best found index
        float bestScore = Float.MAX_VALUE;
        int bestIndex = -1;

        // Get the touch coordinates (first convert them from view to drawing coordinates)
        points[0] = event.getX();
        points[1] = event.getY();
        mInvertViewMatrix.mapPoints(points);
        float tx = points[0];
        float ty = points[1];

        // For each path, compare the distance
        // TODO: this is brute force and can be improved
        for (int i = mDataLowStackPointer; i < mDataHighStackPointer; ++i) {
            // Get the centroid coordinates
            float cx = mEditableElementsStack.get(i).getCentroid().x;
            float cy = mEditableElementsStack.get(i).getCentroid().y;
            // Compute euclidean distance (sqrt is useless for comparison only)
            score = (tx - cx) * (tx - cx) + (ty - cy) * (ty - cy);
            // If nearer, store the match
            if (score < bestScore) {
                bestScore = score;
                bestIndex = i;
            }
        }

        // If the best match is under the threshold, select it
        if (Math.sqrt(bestScore) < mSelectionThreshold) {
            mSelectedIndex = bestIndex;
            if (mHandler != null) {
                mHandler.onItemSelected(mEditableElementsStack.get(mSelectedIndex));
            }

        } else {
            unselectElement();
        }
    }

    /**
     * Resets the selection.
     */
    private void unselectElement() {
        mSelectedIndex = -1;
        if (mHandler != null) {
            mHandler.onItemDeselected();
        }
    }

    /**
     * Handles a single point moving gesture while in moving mode.
     *
     * @param event Current MotionEvent
     */
    private void onMovingEvent(MotionEvent event) {

        // Prevent parent views to intercept touch events
        getParent().requestDisallowInterceptTouchEvent(true);
        // Scale and scroll gesture detection
        mScaleGestureDetector.onTouchEvent(event);
        mScrollGestureDetector.onTouchEvent(event);
    }

    /**
     * Decrement the data stack pointer.
     * If there was data on the stack, the last drawn data will be hidden.
     */
    public void undo() {
        // If there is no data, do nothing
        if (mDataHighStackPointer > 0) {
            // Decrement the pointer and refresh the display
            mDataHighStackPointer--;
            // Force redraw
            this.invalidate();
        }
    }

    /**
     * Try to increment the data stack pointer.
     * If there are unshown data on the stack, the next data
     * in the stack will be displayed.
     */
    public void redo() {
        // If there is no more data on the stack, do nothing
        if (mDataHighStackPointer < mEditableElementsStack.size()) {
            // Increment the max pointer and refresh display
            mDataHighStackPointer++;
            // Force redraw
            this.invalidate();
        }
    }

    /**
     * Sets the view mode.
     *
     * @param mode The new drawing view mode to set
     */
    public void setMode(Mode mode) {

        // Update old mode
        if (mOldMode != mMode) {
            this.mOldMode = this.mMode;

            if (mOldMode == Mode.SELECTING) {
                unselectElement();
            }

        }
        // Set new mode
        this.mMode = mode;

        // Change mode events
        if (mHandler != null) {
            mHandler.onModeChanged(mode);
        }
        // Refresh the display in case of mode specific elements
        this.invalidate();
    }

    /**
     * Gets this view mode.
     *
     * @return the mode
     */
    public Mode getMode() {
        return mMode;
    }

    /**
     * Set the drawing type used by the view.
     *
     * @param toolClass class type of the tool to use.
     */
    public void setDrawingTool(Class<? extends DrawingElement> toolClass) {
        this.mDrawingTool = toolClass;
        // Reset current brush
        this.mCurrentBrush = new Brush();

        // Change mode events
        if (mHandler != null) {
            mHandler.onDrawingToolChanged(toolClass);
        }
    }

    /**
     * Parses the style parameter and updates the current brush accordingly.
     *
     * @param style String representing the style to apply to the current brush.
     */
    public void setDrawingStyle(String style) {
        if (mSelectedIndex != -1) {
            // Update the brush of the selectd element
            mEditableElementsStack.get(mSelectedIndex).applyStyle(style);
            // Refresh the drawing
            this.invalidate();
        } else {
            // Update the current brush
            Brush.fromSvgString(style, mCurrentBrush);
        }
    }

    /**
     * Delete the currently selected DrawingElement.
     */
    public void remove() {
        // If no path selected, do nothing
        if (mSelectedIndex != -1) {
            // Store the index to remove, unselect it and decrease the max stack pointer
            int indexToRemove = mSelectedIndex;
            mDataHighStackPointer--;
            mSelectedIndex = -1;
            // Remove the path
            mEditableElementsStack.remove(indexToRemove);
            // Unselect events
            if (mHandler != null) {
                mHandler.onItemDeselected();
            }
            // Refresh the display
            this.invalidate();
        }
    }

    /**
     * Returns a Bitmap containing the current state of the drawing.
     *
     * @return the rasterized picture.
     */
    public Bitmap rasterize() {

        // Create a bitmap of the correct size
        Bitmap currentDrawing = Bitmap.createBitmap(mDrawingWidth, mDrawingHeight, Config.ARGB_8888);
        // Create a canvas in order to manipulate the bitmap
        Canvas canvas = new Canvas(currentDrawing);

        // Draw the background layer
        canvas.drawBitmap(mBackground, 0, 0, null);
        // Draw the data
        for (int i = mDataLowStackPointer; i < mDataHighStackPointer; ++i) {
            DrawingElement data = mEditableElementsStack.get(i);
            data.draw(canvas, null, null);
        }

        return currentDrawing;
    }

    /**
     * Returns the list of shown editable DrawingElements.
     *
     * @return list of editable elements.
     */
    public List<DrawingElement> getEditableElementsStack() {

        List<DrawingElement> dataStack = new LinkedList<>();

        for (int i = mDataLowStackPointer; i < mDataHighStackPointer; ++i) {
            dataStack.add(mEditableElementsStack.get(i));
        }

        return dataStack;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // If the enter key has been pressed, close the keyboard
        if (mIsKeyboardShown && keyCode == KeyEvent.KEYCODE_ENTER) {
            hideKeyboard();
            return true;
        }

        if (mCurrentDrawingData != null && mCurrentDrawingData.onKeyDown(keyCode, event, mDrawingArea)) {
            this.invalidate();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {

        // If the enter key has been pressed, close the keyboard
        if (mIsKeyboardShown && keyCode == KeyEvent.KEYCODE_ENTER) {
            hideKeyboard();
            return true;
        }

        if (mCurrentDrawingData != null && mCurrentDrawingData.onKeyDown(keyCode, event, mDrawingArea)) {
            this.invalidate();
            return true;
        }

        return super.onKeyMultiple(keyCode, count, event);
    }

    /**
     * Hides the soft keyboard.
     */
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getWindowToken(), 0);
        mIsKeyboardShown = false;
        animateOutFromTextFocus();
    }

    /**
     * In order to catch the order to hide the keyboard from the back key.
     *
     * @param keyCode code of the pressed key
     * @param event   key event
     * @return false
     */
    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            // Keyboard will be hidden
            mIsKeyboardShown = false;
            animateOutFromTextFocus();
        }
        return false;
    }

    /**
     * Starts the zoom out animation from text edit to the previous zoom and position.
     */
    private void animateOutFromTextFocus() {
        // Start the view animation back from the text edition position
        ValueAnimator animator = ValueAnimator.ofObject(new PositionEvaluator(),
                new PointF(mTranslation.x, mTranslation.y),
                mTranslationBeforeKeyboardEdit);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                PointF animatedValue = (PointF) animator.getAnimatedValue();
                mTranslation.x = animatedValue.x;
                mTranslation.y = animatedValue.y;
                updateMatrices(false);
                DrawingView.this.invalidate();
            }
        });
        animator.setDuration(mTextUnfocusAnimationDuration);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    /**
     * Loads an SVG document to the read only drawing layer.
     *
     * @param svgStr the SVG document to be loaded
     */
    public void loadReadOnlySvg(String svgStr) {

        try {
            SvgDocument svgDocument = SvgDocument.parse(svgStr);
            this.mReadOnlyElementsStack = svgDocument.getDrawingData();

            setDrawingSize(svgDocument.getWidth(), svgDocument.getHeight(), false);


        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), e.getMessage(), e);
        }
    }

    /**
     * Loads an SVG document to the read/write drawing layer.
     *
     * @param svgStr the SVG document to be loaded
     */
    public void loadSvg(String svgStr) {

        try {
            SvgDocument svgDocument = SvgDocument.parse(svgStr);
            this.mEditableElementsStack = svgDocument.getDrawingData();
            this.mDataLowStackPointer = 0;
            this.mDataHighStackPointer = mEditableElementsStack.size();

            setDrawingSize(svgDocument.getWidth(), svgDocument.getHeight(), false);


        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), e.getMessage(), e);
        }
    }

    /**
     * Returns an SVG document representing the current read/write layer.
     *
     * @return The SVG document
     */
    @SuppressWarnings("unchecked")
    public String saveSvg() {

        SvgDocument svgDocument = new SvgDocument((int) mDrawingArea.right, (int) mDrawingArea.bottom, (LinkedList)getEditableElementsStack());
        return svgDocument.toString();
    }
}

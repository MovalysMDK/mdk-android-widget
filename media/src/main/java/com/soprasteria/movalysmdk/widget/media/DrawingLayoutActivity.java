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
package com.soprasteria.movalysmdk.widget.media;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;

import com.soprasteria.movalysmdk.widget.media.drawing.DrawingView;
import com.soprasteria.movalysmdk.widget.media.drawing.DrawingView.Mode;
import com.soprasteria.movalysmdk.widget.media.drawing.data.DrawingElement;

import java.lang.ref.WeakReference;

/**
 * Activity that allows the user to modify a bitmap, applying an svg layer to it.
 * It must be called by passing the URI of the image to modify in the intent extra.
 * It returns the modified image URI along with the raw SVG data in string format.
 */
public class DrawingLayoutActivity extends AppCompatActivity implements DrawingView.DrawingViewEventHandler, View.OnClickListener {

    /**
     * Intent extras key for image uri. To use when calling this activity.
     */
    public static final String REQUEST_URI_KEY = "rawPhotoUri";

    /**
     * Intent extras key for svg to edit. To use when calling this activity.
     */
    public static final String REQUEST_SVG_KEY = "requestSVG";

    /**
     * Intent extras key for result svg. To use when receiving result of this activity.
     */
    public static final String RESULT_SVG_KEY = "resultSVG";

    /**
     * Result ntent action. To check when receiving result activity.
     */
    public static final String DRAWING_ACTION = "drawing_action";

    /**
     * The drawing view.
     */
    private WeakReference<DrawingView> drawingView;

    /**
     * Button to move background.
     */
    private WeakReference<ImageButton> moveButton;

    /**
     * Button to draw on canvas.
     */
    private WeakReference<ImageButton> drawButton;

    /**
     * Button to finish activity and send result.
     */
    private WeakReference<ImageButton> doneButton;

    /**
     * Button to select an area of the canvas.
     */
    private WeakReference<ImageButton> selectButton;

    /**
     * Toolbox for drawing elements and styles.
     */
    private WeakReference<ToolBoxView> toolbox;

    /**
     * Button for removing an element.
     */
    private WeakReference<ImageButton> removeButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout
        this.setContentView(R.layout.drawing_activity_layout);

        drawingView = new WeakReference<>((DrawingView) findViewById(R.id.drawing_view));
        moveButton = new WeakReference<>((ImageButton) findViewById(R.id.move_button));
        drawButton = new WeakReference<>((ImageButton) findViewById(R.id.draw_button));
        selectButton = new WeakReference<>((ImageButton) findViewById(R.id.select_button));
        doneButton = new WeakReference<>((ImageButton) findViewById(R.id.done_button));

        removeButton = new WeakReference<>((ImageButton) findViewById(R.id.remove_element));

        ImageButton ib = drawButton.get();
        if(ib!=null) {
            ib.setOnClickListener(this);
        }
        ib = moveButton.get();
        if(ib!=null) {
            ib.setOnClickListener(this);
        }
        ib = selectButton.get();
        if(ib!=null) {
            ib.setOnClickListener(this);
        }
        ib = doneButton.get();
        if(ib!=null) {
            ib.setOnClickListener(this);
        }
        ib = removeButton.get();
        if(ib!=null) {
            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DrawingView dv = drawingView.get();
                    if (dv != null) {
                        dv.remove();
                    }
                }
            });
        }

        toolbox = new WeakReference<>((ToolBoxView) findViewById(R.id.toolbox));

        //initialize the commands

        Uri mediaUri = getIntent().getParcelableExtra(REQUEST_URI_KEY);
        String svg = getIntent().getStringExtra(REQUEST_SVG_KEY);


        //initialize the drawing view
        DrawingView dv = drawingView.get();
        if (dv != null) {
            Bitmap bg = BitmapHelper.createViewBitmap(this,mediaUri,null);
            dv.setDrawingBackground(bg);

            if (svg != null) {
                dv.loadSvg(svg);
            }

            // Set this drawing layout as the drawing view event handler
            dv.setDrawingViewEventHandler(this);
        }


        setMode(Mode.DRAWING);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            exitEditMode();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Exits the activity, without returning the result.
     */
    private void exitEditMode() {
        this.finish();
    }

    @Override
    public void onBackPressed() {
        exitEditMode();
    }

    @Override
    public void onItemSelected(DrawingElement selectedElement) {
        ImageButton ib = removeButton.get();
        if (ib != null && ib.getVisibility() == View.INVISIBLE && selectedElement != null) {
            ib.setVisibility(View.VISIBLE);
            ib.setAnimation(AnimationUtils.makeInAnimation(this, false));
            ib.animate();
        }
    }

    @Override
    public void onItemDeselected() {
        ImageButton ib = removeButton.get();
        if (ib != null && ib.getVisibility() == View.VISIBLE) {
            ib.setAnimation(AnimationUtils.makeOutAnimation(this, true));
            ib.animate();
            ib.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onModeChanged(DrawingView.Mode mode) {
        //Nothing to do for the moment.
    }

    @Override
    public void onDrawingToolChanged(Class<? extends DrawingElement> toolClassName) {
        //Nothing to do for the moment.
    }

    /**
     * Sets the mode of the drawing view, selecting the right command button.
     *
     * @param mode the new mode of the activity
     */
    public void setMode(Mode mode) {

        DrawingView dv = drawingView.get();
        if (dv != null) {

            switch (mode) {
                case MOVING:
                    if (dv.getMode() != Mode.MOVING) {
                        initMoveMode();
                    }
                    break;
                case DRAWING:
                    if (dv.getMode() != Mode.DRAWING) {
                        initDrawMode();
                    }
                    break;
                case SELECTING:
                    if (dv.getMode() != Mode.SELECTING) {
                        initSelectMode();
                    }
                    break;
                default:
                    break;
            }

            dv.setMode(mode);
        }
    }

    /**
     * Initializes the moving mode.
     */
    private void initMoveMode() {
        deselectButtons();

        ImageButton ib = moveButton.get();
        if (ib != null) {
            ib.setImageResource(R.drawable.ic_cursor_move_white_36dp);
        }

        toggleToolbox(false);
    }


    /**
     * Initializes the drawing mode.
     */
    private void initDrawMode() {
        deselectButtons();

        ImageButton ib = drawButton.get();
        if (ib != null) {
            ib.setImageResource(R.drawable.ic_pencil_white_36dp);
        }

        toggleToolbox(true);

    }

    /**
     * Initializes the selecting mode.
     */
    private void initSelectMode() {
        deselectButtons();

        ImageButton ib = selectButton.get();
        if (ib != null) {
            ib.setImageResource(R.drawable.ic_selection_white_36dp);
        }

        toggleToolbox(false);
    }

    /**
     * Toggles the toolbox's visibility.
     *
     * @param visible the visibility
     */
    private void toggleToolbox(boolean visible) {
        ToolBoxView tb = toolbox.get();
        if (tb != null) {

            if (visible) {
                tb.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            } else {
                tb.animate().translationY(-tb.getHeight() - 16).setInterpolator(new AccelerateInterpolator(2)).start();
            }
            tb.animate();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.equals(moveButton.get())) {
            setMode(Mode.MOVING);
        } else if (v.equals(drawButton.get())) {
            setMode(Mode.DRAWING);
        } else if (v.equals(selectButton.get())) {
            setMode(Mode.SELECTING);
        } else if (v.equals(doneButton.get())) {

            //Saving svg to result intent and finish activity.


            Intent resultIntent = new Intent();
            resultIntent.setAction(DRAWING_ACTION);

            DrawingView dv = drawingView.get();
            if (dv != null && !dv.getEditableElementsStack().isEmpty()) {

                resultIntent.putExtra(RESULT_SVG_KEY, dv.saveSvg());

            }

            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
    }

    /**
     * Deselects all command buttons (changing their icon's color to grey).
     */
    private void deselectButtons() {
        ImageButton ib = moveButton.get();
        if (ib != null) {
            ib.setImageResource(R.drawable.ic_cursor_move_grey600_36dp);
        }
        ib = drawButton.get();
        if (ib != null) {
            ib.setImageResource(R.drawable.ic_pencil_grey600_36dp);
        }
        ib = selectButton.get();
        if (ib != null) {
            ib.setImageResource(R.drawable.ic_selection_grey600_36dp);
        }
    }

    /**
     * Gets the drawing view.
     * @return this activity's drawing view.
     */
    public DrawingView getDrawingView() {
        return drawingView.get();
    }

}

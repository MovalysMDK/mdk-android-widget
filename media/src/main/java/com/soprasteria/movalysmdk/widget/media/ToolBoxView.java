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

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.larswerkman.lobsterpicker.LobsterPicker;
import com.larswerkman.lobsterpicker.sliders.LobsterShadeSlider;
import com.soprasteria.movalysmdk.widget.media.drawing.DrawingView;
import com.soprasteria.movalysmdk.widget.media.drawing.DrawingView.Tool;
import com.soprasteria.movalysmdk.widget.media.drawing.data.ArrowLineElement;
import com.soprasteria.movalysmdk.widget.media.drawing.data.CircleElement;
import com.soprasteria.movalysmdk.widget.media.drawing.data.DoubleArrowLineElement;
import com.soprasteria.movalysmdk.widget.media.drawing.data.HandFreeElement;
import com.soprasteria.movalysmdk.widget.media.drawing.data.LineElement;
import com.soprasteria.movalysmdk.widget.media.drawing.data.OvalElement;
import com.soprasteria.movalysmdk.widget.media.drawing.data.RectangleElement;
import com.soprasteria.movalysmdk.widget.media.drawing.data.TextElement;

import java.lang.ref.WeakReference;

/**
 * Toolbox of a DrawingLayoutActivity.
 */
public class ToolBoxView extends RelativeLayout implements View.OnClickListener, Spinner.OnItemSelectedListener{

    /**
     * Default brush color.
     */
    private static final int DEFAULT_COLOR = 0xff000000;

    /**
     * Reference to the text tool button.
     */
    private WeakReference<ImageButton> textButton;

    /**
     * Reference to the hand free tool button.
     */
    private WeakReference<ImageButton> handFreeButton;

    /**
     * Reference to the line tool button.
     */
    private WeakReference<ImageButton> lineButton;

    /**
     * Reference to the circle tool button.
     */
    private WeakReference<ImageButton> circleButton;

    /**
     * Reference to the oval tool button.
     */
    private WeakReference<ImageButton> ellipseButton;

    /**
     * Reference to the rectangle tool button.
     */
    private WeakReference<ImageButton> rectangleButton;

    /**
     * Reference to the arrow tool button.
     */
    private WeakReference<ImageButton> arrowButton;

    /**
     * Reference to the double arrow tool button.
     */
    private WeakReference<ImageButton> doubleArrowButton;

    /**
     * Reference to the color picker button.
     */
    private WeakReference<Button> colorPickerButton;

    /**
     * Reference to the brush size edit text.
     */
    private WeakReference<Spinner> brushSizeSpinner;


    /**
     * Reference to the color picker view.
     */
    private WeakReference<LobsterPicker> colorPickerView;

    /**
     * Reference to the color shade slider view.
     */
    private WeakReference<LobsterShadeSlider> shadeSliderView;

    /**
     * Reference to the drawing view.
     */
    private WeakReference<DrawingView> drawingView;

    /**
     * The selected tool.
     */
    private Tool selectedTool;

    /**
     * The color picker dialog.
     */
    private Dialog dialog;

    /**
     * Hex code of the current brush color.
     */
    int currentColor;

    /**
     * Position of the current brush color in the color picker color adapter.
     */
    int currentColorPosition;

    /**
     * Position of the current brush color fade in the color shade slider color adapter.
     */
    int currentShadePosition;

    /**
     * Current brush size in pixels.
     */
    int currentBrushSize = 0;

    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */
    public ToolBoxView(Context context) {
        super(context);
        if(!isInEditMode()) {
            init(context);
        }
    }

    /**
     * Constructor that is called when inflating a view from XML.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     */
    public ToolBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode()) {
            init(context);
        }
    }

    /**
     * Perform inflation from XML and apply a class-specific base style from a
     * theme attribute.
     * @param context      The Context the view is running in, through which it can
     *                     access the current theme, resources, etc.
     * @param attrs        The attributes of the XML tag that is inflating the view.
     * @param defStyleAttr An attribute in the current theme that contains a
     *                     reference to a style resource that supplies default values for
     *                     the view. Can be 0 to not look for defaults.
     */
    public ToolBoxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(!isInEditMode()) {
            init(context);
        }
    }

    /**
     * Initializes the layout.
     * @param context The Context the view is running in.
     */
    private void init(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(this.getLayoutResource(), this);

        currentColor = DEFAULT_COLOR;

        textButton = new WeakReference<>((ImageButton) findViewById(R.id.element_text));
        handFreeButton = new WeakReference<>((ImageButton) findViewById(R.id.element_hand_free));
        lineButton = new WeakReference<>((ImageButton) findViewById(R.id.element_line));
        circleButton = new WeakReference<>((ImageButton) findViewById(R.id.element_circle));
        ellipseButton = new WeakReference<>((ImageButton) findViewById(R.id.element_ellipse));
        rectangleButton = new WeakReference<>((ImageButton) findViewById(R.id.element_rect));
        arrowButton = new WeakReference<>((ImageButton) findViewById(R.id.element_arrow));
        doubleArrowButton = new WeakReference<>((ImageButton) findViewById(R.id.element_double_arrow));
        colorPickerButton = new WeakReference<>((Button) findViewById(R.id.color_button));
        brushSizeSpinner = new WeakReference<>((Spinner) findViewById(R.id.brush_size));


        //initializing colorpicker
        RelativeLayout rl = new RelativeLayout(getContext());
        inflater.inflate(R.layout.colorpicker,rl);
        colorPickerView = new WeakReference<>((LobsterPicker)rl.findViewById(R.id.lobsterpicker));
        shadeSliderView = new WeakReference<>((LobsterShadeSlider)rl.findViewById(R.id.shadeslider));
        final LobsterPicker lp = colorPickerView.get();
        final LobsterShadeSlider lss = shadeSliderView.get();
        if(lp!=null && lss!=null) {
            lp.addDecorator(lss);
            lp.setColorHistoryEnabled(true);
        }

        dialog = new AlertDialog.Builder(getContext())
                .setView(rl)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(lp!=null && lss!=null) {
                            currentColor = lp.getColor();
                            currentColorPosition = lp.getColorPosition();
                            currentShadePosition = lss.getShadePosition();
                            lp.setHistory(lp.getColor());
                            validateCurrentColor();
                            dialog.dismiss();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        if(lp!=null && lss!=null) {
                            lp.setColorPosition(currentColorPosition);
                            lss.setShadePosition(currentShadePosition);
                        }
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                        if(lp!=null && lss!=null) {
                            lp.setColorPosition(currentColorPosition);
                            lss.setShadePosition(currentShadePosition);
                        }
                    }
                }).create();

        //initializing the brush size spinner
        Spinner s = brushSizeSpinner.get();
        if(s!=null) {
            s.setAdapter(new BrushSizeAdapter(getContext()));
            s.setOnItemSelectedListener(this);
        }

        //initializing the commands
        ImageButton ib = textButton.get();
        if(ib!=null) {
            ib.setOnClickListener(this);
        }
        ib = handFreeButton.get();
        if(ib!=null) {
            ib.setOnClickListener(this);
        }
        ib = lineButton.get();
        if(ib!=null) {
            ib.setOnClickListener(this);
        }
        ib = circleButton.get();
        if(ib!=null) {
            ib.setOnClickListener(this);
        }
        ib = ellipseButton.get();
        if(ib!=null) {
            ib.setOnClickListener(this);
        }
        ib = rectangleButton.get();
        if(ib!=null) {
            ib.setOnClickListener(this);
        }
        ib = arrowButton.get();
        if(ib!=null) {
            ib.setOnClickListener(this);
        }
        ib = doubleArrowButton.get();
        if(ib!=null) {
            ib.setOnClickListener(this);
        }
        Button b = colorPickerButton.get();
        if(b!=null){
            b.setOnClickListener(this);
        }
        HorizontalScrollView view = (HorizontalScrollView)findViewById(R.id.scrollView);
        view.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(!isInEditMode()) {
            drawingView = new WeakReference<>(((DrawingLayoutActivity) getContext()).getDrawingView());


            LobsterPicker lp = colorPickerView.get();
            LobsterShadeSlider lss = shadeSliderView.get();
            if (lp != null && lss != null) {
                lp.setColorPosition(currentColorPosition);
                lss.setShadePosition(currentShadePosition);
            }

            setTool(selectedTool != null ? selectedTool : Tool.HAND_FREE);
        }
    }

    /**
     * Returns the layout of the widget.
     * @return the layout of the widget
     */
    @LayoutRes
    protected int getLayoutResource() {
        return R.layout.toolbox_layout;
    }


    @Override
    public void onClick(View v) {
        if(v.equals(textButton.get())){
            setTool(Tool.TEXT);
        }else if(v.equals(handFreeButton.get())){
            setTool(Tool.HAND_FREE);
        }else if(v.equals(lineButton.get())){
            setTool(Tool.LINE);
        }else if(v.equals(circleButton.get())){
            setTool(Tool.CIRCLE);
        }else if(v.equals(ellipseButton.get())){
            setTool(Tool.OVAL);
        }else if(v.equals(rectangleButton.get())){
            setTool(Tool.RECTANGLE);
        }else if(v.equals(arrowButton.get())){
            setTool(Tool.ARROW);
        }else if(v.equals(doubleArrowButton.get())){
            setTool(Tool.DOUBLE_ARROW);
        }else if(v.equals(colorPickerButton.get())){
            dialog.show();
        }
    }


    /**
     * Sets the selected tool.
     * @param tool the selected tool
     */
    public void setTool(Tool tool){
        DrawingView dv = drawingView.get();
        if (dv != null) {
            switch (tool) {
                case TEXT:
                    initText();
                    break;
                case HAND_FREE:
                    initHandFree();
                    break;
                case LINE:
                    initLine();
                    break;
                case CIRCLE:
                    initCircle();
                    break;
                case OVAL:
                    initOval();
                    break;
                case RECTANGLE:
                    initRectangle();
                    break;
                case ARROW:
                    initArrow();
                    break;
                case DOUBLE_ARROW:
                    initDoubleArrow();
                    break;
                default:
                    break;
            }
        }
        validateBrushSize();
        validateCurrentColor();
    }

    /**
     * Initializes the text tool.
     */
    private void initText() {
        deselectButtons();

        ImageButton ib = textButton.get();
        if(ib!=null) {
            ib.setImageResource(R.drawable.ic_format_text_white_24dp);
        }

        DrawingView dv = drawingView.get();
        if (dv != null) {
            dv.setDrawingTool(TextElement.class);
        }

        selectedTool = Tool.TEXT;
    }

    /**
     * Initializes the hand free tool.
     */
    private void initHandFree() {
        deselectButtons();

        ImageButton ib = handFreeButton.get();
        if(ib!=null) {
            ib.setImageResource(R.drawable.ic_pen_white_24dp);
        }

        DrawingView dv = drawingView.get();
        if (dv != null) {
            dv.setDrawingTool(HandFreeElement.class);
        }

        selectedTool = Tool.HAND_FREE;
    }

    /**
     * Initializes the line tool.
     */
    private void initLine() {
        deselectButtons();

        ImageButton ib = lineButton.get();
        if(ib!=null) {
            ib.setImageResource(R.drawable.ic_vector_line_white_24dp);
        }

        DrawingView dv = drawingView.get();
        if (dv != null) {
            dv.setDrawingTool(LineElement.class);
        }

        selectedTool = Tool.LINE;
    }

    /**
     * Initializes the circle tool.
     */
    private void initCircle() {
        deselectButtons();

        ImageButton ib = circleButton.get();
        if(ib!=null) {
            ib.setImageResource(R.drawable.ic_checkbox_blank_circle_outline_white_24dp);
        }

        DrawingView dv = drawingView.get();
        if (dv != null) {
            dv.setDrawingTool(CircleElement.class);
        }

        selectedTool = Tool.CIRCLE;
    }

    /**
     * Initializes the ellipse tool.
     */
    private void initOval() {
        deselectButtons();

        ImageButton ib = ellipseButton.get();
        if(ib!=null) {
            ib.setImageResource(R.drawable.ic_ellipse_white_24dp);
        }

        DrawingView dv = drawingView.get();
        if (dv != null) {
            dv.setDrawingTool(OvalElement.class);
        }

        selectedTool = Tool.OVAL;
    }

    /**
     * Initializes the rectangle tool.
     */
    private void initRectangle() {
        deselectButtons();

        ImageButton ib = rectangleButton.get();
        if(ib!=null) {
            ib.setImageResource(R.drawable.ic_crop_square_white_24dp);
        }

        DrawingView dv = drawingView.get();
        if (dv != null) {
            dv.setDrawingTool(RectangleElement.class);
        }

        selectedTool = Tool.RECTANGLE;
    }

    /**
     * Initializes the arrow line tool.
     */
    private void initArrow() {
        deselectButtons();

        ImageButton ib = arrowButton.get();
        if(ib!=null) {
            ib.setImageResource(R.drawable.ic_long_arrow_right_white_24dp);
        }

        DrawingView dv = drawingView.get();
        if (dv != null) {
            dv.setDrawingTool(ArrowLineElement.class);
        }

        selectedTool = Tool.ARROW;
    }

    /**
     * Initializes the text tool.
     */
    private void initDoubleArrow() {
        deselectButtons();

        ImageButton ib = doubleArrowButton.get();
        if(ib!=null) {
            ib.setImageResource(R.drawable.ic_double_arrow_white_24dp);
        }

        DrawingView dv = drawingView.get();
        if (dv != null) {
            dv.setDrawingTool(DoubleArrowLineElement.class);
        }

        selectedTool = Tool.DOUBLE_ARROW;
    }

    /**
     * Deselects all command buttons (changing their icon's color to grey).
     */
    private void deselectButtons() {
        ImageButton ib = textButton.get();
        if(ib!=null) {
            ib.setImageResource(R.drawable.ic_format_text_grey600_24dp);
        }
        ib = handFreeButton.get();
        if(ib!=null) {
            ib.setImageResource(R.drawable.ic_pen_grey600_24dp);
        }
        ib = lineButton.get();
        if(ib!=null) {
            ib.setImageResource(R.drawable.ic_vector_line_grey600_24dp);
        }
        ib = circleButton.get();
        if(ib!=null) {
            ib.setImageResource(R.drawable.ic_checkbox_blank_circle_outline_grey600_24dp);
        }
        ib = ellipseButton.get();
        if(ib!=null) {
            ib.setImageResource(R.drawable.ic_ellipse_grey600_24dp);
        }
        ib = rectangleButton.get();
        if(ib!=null) {
            ib.setImageResource(R.drawable.ic_crop_square_grey600_24dp);
        }
        ib = arrowButton.get();
        if(ib!=null) {
            ib.setImageResource(R.drawable.ic_long_arrow_right_grey600_24dp);
        }
        ib = doubleArrowButton.get();
        if(ib!=null) {
            ib.setImageResource(R.drawable.ic_double_arrow_grey600_24dp);
        }
    }

    /**
     * Applies the designated size to the brush (actual pixel brush size will be calculated according to image size).
     */
    private void validateBrushSize() {
        DrawingView dv = drawingView.get();
        Spinner s = brushSizeSpinner.get();
        if (dv != null && s!=null) {
            int size = (int) s.getSelectedItem();

            if(size!=0 && dv.getDrawingBackground() != null) {
                if (dv.getDrawingBackground().getHeight() > dv.getDrawingBackground().getWidth()) {
                    currentBrushSize = (int) (size * dv.getDrawingBackground().getWidth() * 0.005);
                } else {
                    currentBrushSize = (int) (size * dv.getDrawingBackground().getHeight() * 0.005);
                }

                dv.setDrawingStyle("stroke-width:" + currentBrushSize + ";");
            }
        }


    }

    /**
     * Applies the currently selected color to the brush.
     */
    private void validateCurrentColor() {
        DrawingView dv = drawingView.get();
        if (dv != null) {
            dv.setDrawingStyle("stroke:#" + Integer.toHexString(currentColor).substring(2) + ";");
        }

        Button b = colorPickerButton.get();
        if(b!=null){
            LayerDrawable bgDrawable = (LayerDrawable)b.getBackground();
            final GradientDrawable shape = (GradientDrawable)   bgDrawable.findDrawableByLayerId(R.id.background);
            shape.setColor(currentColor);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle b = new Bundle();
        b.putParcelable("innerState", super.onSaveInstanceState());

        b.putInt("color", currentColor);
        b.putInt("colorPosition", currentColorPosition);
        b.putInt("shadePosition", currentShadePosition);
        b.putInt("brushSize",currentBrushSize);
        b.putSerializable("tool", selectedTool);

        return b;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Parcelable innerState = ((Bundle) state).getParcelable("innerState");

        selectedTool = (Tool) ((Bundle) state).getSerializable("tool");

        currentColor = ((Bundle) state).getInt("color");
        currentColorPosition = ((Bundle) state).getInt("colorPosition");
        currentShadePosition = ((Bundle) state).getInt("shadePosition");
        currentBrushSize = ((Bundle) state).getInt("brushSize");

        super.onRestoreInstanceState(innerState);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        validateBrushSize();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //Nothing to do
    }
}

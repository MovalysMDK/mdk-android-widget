package com.soprasteria.movalysmdk.widget.positionmaps;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import com.soprasteria.movalysmdk.widget.core.MDKBaseRichWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasChangeListener;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.behavior.model.Position;
import com.soprasteria.movalysmdk.widget.core.behavior.types.HasPosition;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;
import com.soprasteria.movalysmdk.widget.position.MDKPosition;

/**
 * Rich widget representing a static maps position component, conforming to the Material Design guidelines,
 * and including by default the floating label and the error component.
 */
public class MDKRichStaticMapsPosition extends MDKBaseRichWidget<MDKStaticMapsPosition> implements HasValidator, HasPosition, HasChangeListener {

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     */
    public MDKRichStaticMapsPosition(Context context, AttributeSet attrs) {
        super(R.layout.mdkwidget_staticpositionmaps_edit_label, R.layout.mdkwidget_staticpositionmaps_edit, context, attrs);
        init(context, attrs);
    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     * @param defStyleAttr defStyleAttr
     */
    public MDKRichStaticMapsPosition(Context context, AttributeSet attrs, int defStyleAttr) {
        super(R.layout.mdkwidget_staticpositionmaps_edit_label, R.layout.mdkwidget_staticpositionmaps_edit, context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    public void registerChangeListener(ChangeListener listener) {
        this.getInnerWidget().registerChangeListener(listener);
    }

    @Override
    public void unregisterChangeListener(ChangeListener listener) {
        this.getInnerWidget().unregisterChangeListener(listener);
    }

    @Override
    public Position getPosition() {
        return this.getInnerWidget().getPosition();
    }

    @Override
    public void setPosition(Position position) {
        this.getInnerWidget().setPosition(position);
    }

    @Override
    public void setLatitudeHint(String latHint) {
        this.getInnerWidget().setLatitudeHint(latHint);
    }

    @Override
    public void setLongitudeHint(String lngHint) {
        this.getInnerWidget().setLongitudeHint(lngHint);
    }

    /**
     * onCreateInputConnection method.
     * @param outAttrs attributes
     * @return InputConnection the input connection
     */
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return getInnerWidget().onCreateInputConnection(outAttrs);
    }

    /**
     * Initialization.
     * @param context the context
     * @param attrs attributes
     */
    private void init(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKPositionComponent);

        @MDKPosition.PositionMode int mode = typedArray.getInt(R.styleable.MDKCommons_MDKPositionComponent_positionMode, 0);

        if (!this.isInEditMode()) {
            this.innerWidget.setMode(mode);

            boolean autoStart = typedArray.getBoolean(R.styleable.MDKCommons_MDKPositionComponent_autoStart, false);

            this.innerWidget.setAutoStart(autoStart);

            boolean activateGoto = typedArray.getBoolean(R.styleable.MDKCommons_MDKPositionComponent_activeGoto, true);

            this.innerWidget.setActivateGoto(activateGoto);

            typedArray.recycle();

            typedArray = context.obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKMapsPositionComponent);

        }
        typedArray.recycle();
    }
}

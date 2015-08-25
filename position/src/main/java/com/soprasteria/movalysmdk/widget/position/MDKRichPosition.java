package com.soprasteria.movalysmdk.widget.position;

import android.content.Context;
import android.location.Location;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import com.soprasteria.movalysmdk.widget.core.MDKBaseRichWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasChangeListener;
import com.soprasteria.movalysmdk.widget.core.behavior.HasLocation;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;

/**
 * Rich widget representing a position component, conforming to the Material Design guidelines,
 * and including by default the floating label and the error component.
 */
public class MDKRichPosition extends MDKBaseRichWidget<MDKPosition> implements HasValidator, HasLocation, HasChangeListener {

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     */
    public MDKRichPosition(Context context, AttributeSet attrs) {
        super(R.layout.mdkwidget_position_edit_label, R.layout.mdkwidget_position_edit, context, attrs);
    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     * @param defStyleAttr defStyleAttr
     */
    public MDKRichPosition(Context context, AttributeSet attrs, int defStyleAttr) {
        super(R.layout.mdkwidget_position_edit_label, R.layout.mdkwidget_position_edit, context, attrs, defStyleAttr);
    }

    /**
     * Override the default android setEnable on view and
     * call the inner component setEnable.
     * @param enabled Enable or not the view
     */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.getInnerWidget().setEnabled(enabled);
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
    public String[] getCoordinates() {
        return this.getInnerWidget().getCoordinates();
    }

    @Override
    public Location getLocation() {
        return this.getInnerWidget().getLocation();
    }

    @Override
    public void setLocation(Location location) {
        this.getInnerWidget().setLocation(location);
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
}

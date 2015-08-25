package com.soprasteria.movalysmdk.widget.position.delegate;

import android.util.AttributeSet;

import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.delegate.WidgetCommandDelegate;

/**
 * Command handler for MDKPosition widget.
 * <p>This class manages primary and secondary commands on the MDK button component.</p>
 * <p>It registers listeners and launches commands.</p>
 */
public class PositionCommandDelegate extends WidgetCommandDelegate {

    /**
     * Constructor.
     * @param mdkWidget view the widget view
     * @param attrs attributes the widget xml attributes
     */
    public PositionCommandDelegate(MDKWidget mdkWidget, AttributeSet attrs) {
        super(mdkWidget, attrs);
    }

    /**
     * Constructor.
     * @param mdkWidget view the widget view
     * @param attrs attributes the widget xml attributes
     * @param validationCommand Use validation for enable command
     */
    public PositionCommandDelegate(MDKWidget mdkWidget, AttributeSet attrs, boolean validationCommand) {
        super(mdkWidget, attrs, validationCommand);
    }

    /**
     * Activate or not command button.
     * @param enable Activation toggle
     */
    @Override
    public void enableCommand(boolean enable) {
        if (validationCommand) {
            // in position widget, the first command is the location button which is always enabled
            enableCommandOnView(enable, this.secondaryCommandViewId);
        }
    }
}

package com.soprasteria.movalysmdk.widget.core.behavior;

import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;

/**
 * Add delegate to a widget.
 */
public interface HasDelegate {

    /**
     * Access to the MDK rich widget's logic.
     * @return MDKWidgetDelegate MDK widget logic
     */
    MDKWidgetDelegate getMDKWidgetDelegate();
}

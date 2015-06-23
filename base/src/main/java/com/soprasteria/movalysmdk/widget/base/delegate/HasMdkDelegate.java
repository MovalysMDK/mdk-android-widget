package com.soprasteria.movalysmdk.widget.base.delegate;

/**
 * Provide an interface to MDK rich widget's logic.
 */
//FIXME: rename to HasMdkWidgetDelegate
public interface HasMdkDelegate {

    /**
     * Access to the MDK rich widget's logic.
     * @return MDKWidgetDelegate MDK widget logic
     */
    MDKWidgetDelegate getMDKWidgetDelegate();
}

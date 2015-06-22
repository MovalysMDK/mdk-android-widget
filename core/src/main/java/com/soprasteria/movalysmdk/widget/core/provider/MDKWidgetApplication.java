package com.soprasteria.movalysmdk.widget.core.provider;

/**
 * Provide the MDKWidgetComponentProvider instance.
 *
 * This interface is usable on Android Application class only.
 */
public interface MDKWidgetApplication {

    /**
     * Return the implementation of MDKWidgetComponentProvider.
     * The returned instance must be a singleton
     * @return the singleton of MDKWidgetComponentProvider implementation
     */
    MDKWidgetComponentProvider getMDKWidgetComponentProvider();

}

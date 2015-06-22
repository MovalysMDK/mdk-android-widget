package com.soprasteria.movalysmdk.widget.core.provider;

/**
 * Provide the MDKWidgetComponentProvider instance.
 *
 * <p>This interface is usable on Android Application class only.</p>
 */
public interface MDKWidgetApplication {

    /**
     * Return the implementation of MDKWidgetComponentProvider.
     * <p>The returned instance must be a singleton.</p>
     * @return the singleton of MDKWidgetComponentProvider implementation
     */
    MDKWidgetComponentProvider getMDKWidgetComponentProvider();

}

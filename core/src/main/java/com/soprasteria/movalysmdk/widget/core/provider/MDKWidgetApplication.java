package com.soprasteria.movalysmdk.widget.core.provider;

/**
 * Interface to return the implementation of the
 * MDKWidgetComponentProvider
 *
 * this interface is usable on Android Application class only
 */
public interface MDKWidgetApplication {

    /**
     * Return the implementation of MDKWidgetComponentProvider
     * This returned instance must be a singleton
     * @return the singleton of MDKWidgetComponentProvider implementation
     */
    public MDKWidgetComponentProvider getMDKWidgetComponentProvider();

}

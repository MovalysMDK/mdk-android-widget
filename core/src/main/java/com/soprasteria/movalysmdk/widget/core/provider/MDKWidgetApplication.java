package com.soprasteria.movalysmdk.widget.core.provider;

/**
 * Interface to return the implementation of the
 * MDKWidgetComponentProvider
 */
public interface MDKWidgetApplication {

    /**
     * Return the implementation of MDKWidgetComponentProvider
     * This returned instance must be a singleton
     * @return the singleton of MDKWidgetComponentProvider implementation
     */
    public MDKWidgetComponentProvider getMDKWidgetComponentProvider();

}

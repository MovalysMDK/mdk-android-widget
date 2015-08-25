package com.soprasteria.movalysmdk.widget.position.command;

import android.location.Location;

/**
 * Position Command Widget listener
 */
public interface PositionCommandListener {

    /**
     * Indicated that the location is being calculated.
     */
    void computingLocation();

    /**
     * Called when the location has been recalculated.
     * @param location the new location
     */
    void locationChanged(Location location);

    /**
     * Called when the desired precision has been reached.
     * @param location the precise location
     */
    void locationFixed(Location location);

}

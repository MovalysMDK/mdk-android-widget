package com.soprasteria.movalysmdk.widget.position.command;

import android.location.Location;

/**
 * Position Command Widget listener.
 */
public interface PositionCommandListener {

    /**
     * Returns the time out to set on the location manager.
     * @return the desired timeout
     */
    int getTimeOut();

    /**
     * Indicated that the location is being calculated.
     * @param location the last known location
     */
    void computingLocation(Location location);

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

    /**
     * Called when the location manager could not fix the position after a defined timeout.
     */
    void locationTimedOut();

}

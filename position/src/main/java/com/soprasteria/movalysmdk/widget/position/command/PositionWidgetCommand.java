package com.soprasteria.movalysmdk.widget.position.command;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.soprasteria.movalysmdk.widget.core.command.WidgetCommand;
import com.soprasteria.movalysmdk.widget.position.R;

/**
 * Primary command class for the position widget.
 * Will try and locate the device and update the widget consequently
 */
public class PositionWidgetCommand implements WidgetCommand<PositionCommandListener, Void> {

    /** tag for logging */
    private static final String TAG = PositionWidgetCommand.class.getSimpleName();

    /** update timer value */
    private static final int UPDATE_TIMER = 500;

    /** coarse accuracy level */
    private static final int COARSE_ACCURACY_LEVEL = 1000;

    /** fine accuracy level */
    private static final int FINE_ACCURACY_LEVEL = 50;

    private Context context;

    /** the location manager. */
    private LocationManager locationManager;

    /** the two listeners. */
    private LocationListener oListenerFine, oListenerCoarse;

    /** current location. */
    private Location location;

    /** true if a location is available. */
    private boolean locationAvailable;

    /** callback listener */
    private PositionCommandListener listener;

    /**
     * Send an email using the email parameters.
     * <p>This method call the ACTION_SEND Intent.</p>
     *
     * @param context the Android context
     * @return null
     */
    @Override
    public Void execute(Context context, PositionCommandListener... listeners) {
        if (listeners == null || listeners.length != 1 || listeners[0] == null) {
            throw new IllegalArgumentException("position command should only have one PositionCommandListener parameter.");
        } else {
            this.context = context;
            this.listener = listeners[0];

            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            registerLocationListeners();

            start();
        }
        return null;
    }

    private void start() {
        boolean locationRequested = false;

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Criteria oFine = new Criteria();
            oFine.setAccuracy(Criteria.ACCURACY_FINE);

            // Will keep updating about every 500 ms until
            // accuracy is about 50 meters to get accurate fix.
            locationManager.requestLocationUpdates(locationManager.getBestProvider(oFine, true), UPDATE_TIMER, FINE_ACCURACY_LEVEL, oListenerFine);

            locationRequested = true;
        }

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Criteria oCoarse = new Criteria();
            oCoarse.setAccuracy(Criteria.ACCURACY_COARSE);

            // Will keep updating about every 500 ms until
            // accuracy is about 1000 meters to get accurate fix.
            // Replace oLocationManager.getBestProvider(oCoarse, true) to
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, UPDATE_TIMER, COARSE_ACCURACY_LEVEL,
                    oListenerCoarse);

            locationRequested = true;
        }

        if (locationRequested) {
            listener.computingLocation();
        }
    }

    private void stop() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.removeUpdates(oListenerFine);
        }
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.removeUpdates(oListenerCoarse);
        }
    }

    private Context getContext() {
        return this.context;
    }

    /**
     *
     * Updates the dialog ui to use the last currentLocation.
     */
    private void onCurrentLocationChange() {
        if (location != null) {
            listener.locationChanged(location);
        }
    }

    /**
     * registers the 2 listener on Coarse and Fine location.
     */
    private void registerLocationListeners() {
        locationManager = (LocationManager) this.getContext().getSystemService(Context.LOCATION_SERVICE);

        // Initialize criteria for location providers
        Criteria oFine = new Criteria();
        oFine.setAccuracy(Criteria.ACCURACY_FINE);
        Criteria oCoarse = new Criteria();
        oCoarse.setAccuracy(Criteria.ACCURACY_COARSE);

        // Get at least something from the device,
        // could be very inaccurate though
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            location = locationManager.getLastKnownLocation(locationManager.getBestProvider(oFine, true));
            this.onCurrentLocationChange();
        } else {
            Toast.makeText(this.getContext(), this.getContext().getString(R.string.alert_gps_disabled), Toast.LENGTH_LONG).show();
        }

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            this.onCurrentLocationChange();
        }

        if (oListenerFine == null || oListenerCoarse == null) {
            createLocationListeners();
        }
    }

    /**
     * Creates LocationListeners.
     */
    private void createLocationListeners() {
        oListenerCoarse = new LocationListener() {

            @Override
            public void onStatusChanged(String p_sProvider, int p_iStatus, Bundle p_oExtras) {
                switch (p_iStatus) {
                    case LocationProvider.OUT_OF_SERVICE:
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        PositionWidgetCommand.this.locationAvailable = false;
                        break;
                    case LocationProvider.AVAILABLE:
                        PositionWidgetCommand.this.locationAvailable = true;
                        break;
                    default:
                        PositionWidgetCommand.this.locationAvailable = false;
                }
            }

            @Override
            public void onProviderEnabled(String p_sProvider) {
                // nothing to do
            }

            @Override
            public void onProviderDisabled(String p_sProvider) {
                // nothing to do
            }

            @Override
            public void onLocationChanged(Location p_oLocation) {
                location = p_oLocation;
                PositionWidgetCommand.this.onCurrentLocationChange();
                if (p_oLocation!=null && p_oLocation.getAccuracy() < COARSE_ACCURACY_LEVEL && p_oLocation.hasAccuracy()) {
                    locationManager.removeUpdates(this);
                    PositionWidgetCommand.this.locationCoarseOk();
                }
            }
        };
        oListenerFine = new LocationListener() {

            @Override
            public void onStatusChanged(String p_sProvider, int p_iStatus, Bundle p_oExtras) {
                switch (p_iStatus) {
                    case LocationProvider.OUT_OF_SERVICE:
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        PositionWidgetCommand.this.locationAvailable = false;
                        break;
                    case LocationProvider.AVAILABLE:
                        PositionWidgetCommand.this.locationAvailable = true;
                        break;
                    default:
                        PositionWidgetCommand.this.locationAvailable = false;
                }
            }

            @Override
            public void onProviderEnabled(String p_sProvider) {
                // nothing to do
            }

            @Override
            public void onProviderDisabled(String p_sProvider) {
                // nothing to do
            }

            @Override
            public void onLocationChanged(Location p_oLocation) {
                location = p_oLocation;
                PositionWidgetCommand.this.onCurrentLocationChange();
                if (p_oLocation!=null && p_oLocation.getAccuracy() < FINE_ACCURACY_LEVEL && p_oLocation.hasAccuracy()) {
                    locationManager.removeUpdates(this);
                    PositionWidgetCommand.this.locationFineOk();
                }
            }
        };
    }

    /**
     *
     * called when the coarse location get the defined accuracy.
     */
    public void locationCoarseOk() {
        Log.d(TAG, "CoarseLocartion ok " + String.valueOf(location.getAccuracy()));
        Log.d(TAG, "CoarseLocartion ok-bLocationAvailable: " + String.valueOf(locationAvailable));

        listener.locationFixed(location);

        stop();
    }

    /**
     *
     * called when the fine location get the defined accuracy.
     */
    public void locationFineOk() {
        Log.d(TAG, "FineLocartion ok: " + String.valueOf(location.getAccuracy()));
        Log.d(TAG, "FineLocartion ok-bLocationAvailable: " + String.valueOf(locationAvailable));

        listener.locationFixed(location);

        stop();
    }
}
